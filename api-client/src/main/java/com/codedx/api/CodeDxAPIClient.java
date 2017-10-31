/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: api.CodeDxAPIClient
 * FileName: CodeDxAPIClient.java
 */

package com.codedx.api;

import com.codedx.security.CodeDxSslEngineFactory;
import com.codedx.model.api.*;
import com.codedx.model.x.*;
import com.codedx.exception.*;
import com.codedx.handlers.*;
import com.codedx.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.Part;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


// CodeDxAPIClient implementation for Intellij plugin. Adapted and used code from eclipse plugin - CodeDxAPIClient.java Author: Applied Visions, Inc. - Chris Ellsworth
public class CodeDxAPIClient {

	private AsyncHttpClient client;

	private static Realm realm;

	// Sets how often to poll for jobs
	// Number of milliseconds to wait between polling
	private static final int JOB_POLL_INTERVAL = 3000;

	//private static final Logger logger = Logger.getInstance(CodeDxAPIClient.class.getName());

	//Custom SSL factory to be set for the async-http-client
	private final CodeDxSslEngineFactory codeDxSslEngineFactory;

	//The platform interface to handle getting url, username, password and other methods that will differ from platform to platform.
	private final PlatformInterface platformInterface;


	/**
	 * Constructor for the CodeDxAPIClient
	 *
	 * @param platformInterface an implementation of PlatFormInterface to handle getting url, username, etc. from the platform
	 */
	// Takes a PlatformInterface to set it for the class
	//Creates the Async-Http-Client and sets the realm
	public CodeDxAPIClient(PlatformInterface platformInterface) {

		codeDxSslEngineFactory = new CodeDxSslEngineFactory(platformInterface.getTrustStoreDirectory());

		this.platformInterface = platformInterface;

		//client configuration settings
		AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
				.setKeepAlive(true)
				.setReadTimeout(platformInterface.getReadTimeout())
				.setSslEngineFactory(codeDxSslEngineFactory)
				.build();

		this.client = new DefaultAsyncHttpClient(config);

		//initialize the realm to take saved credentials
		String username = platformInterface.getUsername();
		if (username == null) {
			username = "";
		}

		String password = platformInterface.getPassword();
		if (password == null) {
			password = "";
		}

		realm = new Realm.Builder(username, password).setUsePreemptiveAuth(true).setScheme(Realm.AuthScheme.BASIC).build();
	}

	/**
	 * Closes the async-http-client created for this class
	 */
	void closeClient() {
		try {
			this.client.close();
		} catch (IOException e) {
			//logger.warn("IOException when trying to close Code Dx API client", e);
		}
	}

	/**
	 * Returns a Realm to be used for the client (username, password and authorization scheme). A different realm is needed for each username/password combo
	 *
	 * @param username          - username for the connection
	 * @param password          - password for the connection
	 * @param usePreemptiveAuth - sets if connection will use PreemptiveAuthorization or not
	 */
	private static Realm getBasicAuthRealm(String username, String password, boolean usePreemptiveAuth) {

		Realm r = new Realm.Builder(username, password)
				.setUsePreemptiveAuth(usePreemptiveAuth)
				.setScheme(Realm.AuthScheme.BASIC).build();

		return r;
	}

	/**
	 * Getter for the SslEngineFactory. Used in {} to set up Details View Connection with same SSL factory
	 *
	 * @return the SslEngineFactory for the client
	 */
	public CodeDxSslEngineFactory getCodeDxSslEngineFactory() {
		return codeDxSslEngineFactory;
	}

	/**
	 * Getter for the PlatformInterface used in this class
	 *
	 * @return PlatformInterface
	 */
	public PlatformInterface getPlatformInterface() {
		return platformInterface;
	}

	/**
	 * Creates a basic Query object to be used for sending POST data to Code Dx Server
	 *
	 * @return model Query object with nothing set
	 */
	private static Query createQuery() {
		Query query = new Query();
		query.setFilter(new Filter());
		query.setSort(new Sort());
		query.setPagination(new Pagination());
		return query;
	}

	/**
	 * Sets the realm for this class. This should be called when user credentials change. ie. User clicks apply
	 *
	 * @param username          - username for the connection
	 * @param password          - password for the connection
	 * @param usePreemptiveAuth - sets if connection will use PreemptiveAuthorization or not
	 */
	public void setClientRealm(String username, String password, boolean usePreemptiveAuth) {
		this.realm = getBasicAuthRealm(username, password, usePreemptiveAuth);
	}

	/**
	 * Helper function to handle request expected error cases. Logs the exception to the IntelliJ  Passes error handling to the handler
	 *
	 * @param handler - The BaseClientHandler that will handle all of the error cases. See {handlers} for implementations of this interface
	 * @param t       - The exception that was thrown during the API query
	 */
	private <T> void handleAllErrors(BaseClientHandler<T> handler, Throwable t) {

		//logger.warn(t);

		//check if root cause is because of ssl
		if (t.getCause() instanceof javax.net.ssl.SSLException) {
			handler.onExpectedError(CodeDxError.SSL_ERROR);
		} else if (t instanceof MalformedURLException) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
		} else if (t instanceof ExecutionException) {
			handler.onExpectedError(CodeDxError.FAILED_EXECUTE);
		} else if (t instanceof CancellationException) {
			handler.onExpectedError(CodeDxError.CANCELLATION);
		} else if (t instanceof InterruptedException) {
			handler.onExpectedError(CodeDxError.INTERRUPTED);
		} else if (t instanceof IOException) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
		} else if (t instanceof InvalidCredentialsException) {
			handler.onExpectedError(CodeDxError.INVALID_CREDENTIALS);
		} else if (t instanceof HttpErrorCodeException) {
			handler.onExpectedError(CodeDxError.HTTP_ERROR_CODE);
		} else if (t instanceof TimeoutException) {
			handler.onExpectedError(CodeDxError.READ_TIMEOUT_ERROR);
		} else {
			handler.onError(t);
		}

	}

	/**
	 * Class to extend async-http-client's {@link AsyncCompletionHandler} class
	 * Used in all of the sendXYZRequest methods.
	 */
	// Class to extend AsyncCompletionHandler to work with async-http-library
	private class ClientCompletionHandler<T> extends AsyncCompletionHandler<T> {

		private final Class<T> classType;
		private final BaseClientHandler<T> handler;

		private ClientCompletionHandler(Class<T> classType, BaseClientHandler<T> handler) {
			this.classType = classType;
			this.handler = handler;
		}


		@Override
		@SuppressWarnings("unchecked")
		public T onCompleted(Response response) throws Exception {
			if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {

				// No content response
				if (response.getStatusCode() == 204) {
					handler.onSuccess(null);
					return null;
				}

				//If class is a string, return the response body directly
				T classObj;
				if (classType.equals(String.class)) {
					//This is why @SuppressWarnings("unchecked") is needed. However, we know that if classType is String, we can cast the responseBody as a String (it already is a String)
					classObj = (T) response.getResponseBody();
				} else { /* Otherwise convert it to the classType */
					classObj = JsonUtil.jsonStringToObject(response.getResponseBody(), classType);
				}

				handler.onSuccess(classObj);
				return classObj;
			}

			//If not a success code, send to NonSuccess handler
			handler.onNonSuccess(response.getStatusCode(), response.getResponseBody());
			return null;
		}

		@Override
		public void onThrowable(Throwable t) {
			handleAllErrors(handler, t);
		}


	}


	//@DEVELOPER_NOTE For most of these client methods, a future is returned in case a blocking synchronous call is necessary.
	//However in order to keep call asynchronous, ignore the future and use the BaseClientHandler interface to handle success, expected errors and unexpected errors

	/**
	 * Preforms an asynchronous POST request to the Code Dx Server at the apiPath and returns a Future for the response. Success and error cases handled by handler.
	 *
	 * @param apiPath   - The path that is added to the server URL to get to API endpoint
	 * @param body      - The JSON string that will be the body of the POST Request
	 * @param classType - The model class that the API response will be converted into
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future for the query. If you need to block on calling thread until server responds, use future.get(). Otherwise, ignore the return type
	 */
	private <T> Future<T> sendPostRequest(String apiPath, String body, Class<T> classType, BaseClientHandler<T> handler) {

		String savedUrl = platformInterface.getSavedUrl();

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(savedUrl + apiPath);
		} catch (MalformedURLException e) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
			return null;
		}

		Future<T> f = client.preparePost(url.toString())
				.setRealm(realm)
				.setHeader("Content-Type", "application/json")
				.setBody(body)
				.setRequestTimeout(platformInterface.getReadTimeout())
				.execute(new ClientCompletionHandler<>(classType, handler));

		return f;
	}

	/**
	 * Preforms an asynchronous POST request with a multipart body to the Code Dx Server at the apiPath and returns a Future for the response. Success and error cases handled by handler.
	 *
	 * @param apiPath   - The path that is added to the server URL to get to API endpoint
	 * @param bodyParts - A list of {@link Part} objects that make up the Mutipart body. Part is included in async-http-client library
	 * @param classType - The model class that the API response will be converted into
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future for the query. If you need to block on calling thread until server responds, use future.get(). Otherwise, ignore the return type
	 */
	private <T> Future<T> sendMultipartPostRequest(String apiPath, List<Part> bodyParts, Class<T> classType, BaseClientHandler<T> handler) {
		String savedUrl = platformInterface.getSavedUrl();

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(savedUrl + apiPath);
		} catch (MalformedURLException e) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
			return null;
		}

		Future<T> f = client.preparePost(url.toString())
				.setRealm(realm)
				.setHeader("Content-Type", "multipart/form-data")
				.setBodyParts(bodyParts)
				.setRequestTimeout(platformInterface.getReadTimeout())
				.execute(new ClientCompletionHandler<>(classType, handler));

		return f;
	}

	/**
	 * Preforms an asynchronous GET request to the Code Dx Server at the apiPath and returns a Future for the response. Success and error cases handled by handler.
	 *
	 * @param apiPath   - The path that is added to the server URL to get to API endpoint
	 * @param classType - The model class that the API response will be converted into
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future for the query. If you need to block on calling thread until server responds, use future.get(). Otherwise, ignore the return type
	 */
	private <T> Future<T> sendGetRequest(String apiPath, Class<T> classType, BaseClientHandler<T> handler) {

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(platformInterface.getSavedUrl() + apiPath);
		} catch (MalformedURLException e) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
			return null;
		}

		//Prepare and get response
		Future<T> f = client.prepareGet(url.toString())
				.setRealm(realm)
				.setRequestTimeout(platformInterface.getReadTimeout())
				.execute(new ClientCompletionHandler<>(classType, handler));

		return f;

	}

	/**
	 * Preforms an asynchronous PUT request to the Code Dx Server at the apiPath and returns a Future for the response. Success and error cases handled by handler.
	 * If no response body is expected, enter null for classType
	 *
	 * @param apiPath   - The path that is added to the server URL to get to API endpoint
	 * @param classType - The model class that the API response will be converted into
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future for the query. If you need to block on calling thread until server responds, use future.get(). Otherwise, ignore the return type
	 */
	private <T> Future<T> sendPutRequest(String apiPath, String body, Class<T> classType, BaseClientHandler<T> handler) {

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(platformInterface.getSavedUrl() + apiPath);
		} catch (MalformedURLException e) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
			return null;
		}

		//Prepare and get response
		Future<T> f = client.preparePut(url.toString())
				.setRealm(realm)
				.setBody(body)
				.setRequestTimeout(platformInterface.getReadTimeout())
				.execute(new ClientCompletionHandler<>(classType, handler));

		return f;

	}

	/**
	 * This method polls until the job indicates it completed, then execute either the failure or complete runnable depending on the status of the job. The polling rate determined by JOB_POLL_INTERVAL
	 *
	 * @param jobId         - The id of the job to wait for completion
	 * @param afterComplete - The runnable to run after the job if it completes successfully
	 * @param afterFailure  - The runnable to run after the job if it fails
	 */
	public void runOnJobCompletion(String jobId, Runnable afterComplete, Runnable afterFailure) {

		//ScheduledExecutorService service = platformInterface.getAppScheduledExecutorService();

		Runnable checkJobStatus = new Runnable() {

			//Save this runnable into a variable so it can be called recursively
			Runnable thisRunnable = this;

			@Override
			public void run() {

				//make query to get job status. Can block on this
				/*getJob(jobId, new UIClientHandler<Job>() {
					@Override
					public void onSuccess(Job result) {
						// If completed, run the correct runnable. Else, schedule another check
						if (result.getStatus().equals("completed")) {
							afterComplete.run();
						} else if (result.getStatus().equals("failed")) {
							afterFailure.run();
						} else if (result.getStatus().equals("queued") || result.getStatus().equals("running")) { // If didn't complete, schedule another check
							service.schedule(thisRunnable, JOB_POLL_INTERVAL, TimeUnit.MILLISECONDS);
						} else { *//* Unexpected status. Fail job *//*
							afterFailure.run();
						}
					}
				});*/

			}
		};

		//Schedule the first check
		//service.schedule(checkJobStatus, 250, TimeUnit.MILLISECONDS);
	}

	/**
	 * Calls api to get the job status
	 *
	 * @param jobId   - The id of the job
	 * @param handler - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 */
	public Future<Job> getJob(String jobId, BaseClientHandler<Job> handler) {
		String apiPath = "/api/jobs/" + jobId;
		return sendGetRequest(apiPath, Job.class, handler);
	}

	/**
	 * Calls api to get the job result
	 *
	 * @param classType - The class to convert the response of the request to
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 */
	public <T> Future<T> getJobResult(Class<T> classType, BaseClientHandler<T> handler) {
		String apiPath = "/api/jobs";
		return sendGetRequest(apiPath, classType, handler);
	}


	/**
	 * Tests connection to the api to see if it gets a valid response
	 *
	 * @param realm    - The realm (username/password) to preform the query on
	 * @param givenUrl - The server URL to test
	 * @param handler  - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 */
	public void testConnection(Realm realm, String givenUrl, BaseClientHandler<Integer> handler) {

		//get response status code to see if able to access api

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(givenUrl + "/api/projects");
		} catch (MalformedURLException e) {
			handler.onExpectedError(CodeDxError.INVALID_URL);
			return;
		}

		//Prepare and get response
		client.prepareGet(url.toString())
				.setRealm(realm)
				.execute(new AsyncCompletionHandler<Integer>() {
							 @Override
							 public Integer onCompleted(Response response) throws Exception {

								 if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
									 handler.onSuccess(response.getStatusCode());
									 return response.getStatusCode();
								 }
								 //If not a success code, send to NonSuccess handler
								 handler.onNonSuccess(response.getStatusCode(), response.getResponseBody());
								 return null;
							 }

							 @Override
							 public void onThrowable(Throwable t) {
								 handleAllErrors(handler, t);
							 }
						 }
				);

	}

	/**
	 * Calls API to retrieve findings table. Logic to handle the Findings table results should be included in the BaseClientHandler onSuccess method passed into this function's parameters
	 *
	 * @param projectId      - The Code Dx project ID number to get the table for
	 * @param pageSize       - How many findings to include in the response
	 * @param pageNumber     - The page of findings to return. Ie. pageNumber == 1 would give the first page of findings
	 * @param sortDescriptor - A {@link SortDescriptor} object that determines what the results are sorted by
	 * @param sortDirection  - A {@link SortDirection} object that determines if the sort is ascending or descending
	 * @param filter         - A {@link Filter} object to filter the findings
	 * @param handler        - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<Finding[]> getFindingsTable(long projectId, long pageSize, long pageNumber, SortDescriptor sortDescriptor, SortDirection sortDirection, Filter filter, BaseClientHandler<Finding[]> handler) {

		String apiPath = String.format("/x/projects/%s/findings/table?expand=descriptor,issue,results.descriptor", projectId);

		//Set up Query object
		Query query = createQuery();
		query.getPagination().setPerPage(pageSize);
		query.getPagination().setPage(pageNumber);

		if (sortDescriptor != null) {
			query.getSort().setBy(sortDescriptor.getKey());
		}

		if (sortDirection != null) {
			query.getSort().setDirection(sortDirection.getKey());
		}

		if (filter != null) {
			query.setFilter(filter);
		}

		//build post body by converting Query Object to string
		String body;
		try {
			body = JsonUtil.objectToJsonString(query);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return null;
		}

		return sendPostRequest(apiPath, body, Finding[].class, handler);
	}

	/**
	 * Calls API to get the number of findings
	 *
	 * @param projectId - The Code Dx project ID number to get the table for
	 * @param filter    - A {@link Filter} object to filter the findings count
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<Count> getFindingsCount(long projectId, Filter filter, BaseClientHandler<Count> handler) {

		String apiPath = String.format("/x/projects/%s/findings/count", projectId);

		Query query = createQuery();

		if (filter != null) {
			query.setFilter(filter);
		}

		//build post request
		String body;
		try {
			body = JsonUtil.objectToJsonString(query);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return null;
		}

		return sendPostRequest(apiPath, body, Count.class, handler);

	}

	/**
	 * Calls API to get the number of findings of a specific severity
	 *
	 * @param projectId - The Code Dx project ID number to get the group count
	 * @param countBy   - A string to signify what to group the findings count by. Ie. "severity"
	 * @param filter    - A {@link Filter} object to filter the group findings count
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<GroupedCount[]> getFindingsGroupCount(long projectId, String countBy, Filter filter, BaseClientHandler<GroupedCount[]> handler) {

		String apiPath = String.format("/x/projects/%s/findings/grouped-counts", projectId);

		// Create request
		GroupedCountsRequest request = new GroupedCountsRequest();
		request.setCountBy(countBy);

		if (filter != null) {
			request.setFilter(filter);
		} else {
			request.setFilter(new Filter());
		}

		// Create body from request
		String body;
		try {
			body = JsonUtil.objectToJsonString(request);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return null;
		}

		return sendPostRequest(apiPath, body, GroupedCount[].class, handler);
	}

	/**
	 * Calls API for projects available for a particular user
	 *
	 * @param handler - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<Projects> getProjects(BaseClientHandler<Projects> handler) {
		String apiPath = "/api/projects";
		return sendGetRequest(apiPath, Projects.class, handler);
	}

	/**
	 * Calls API for statuses for a particular project
	 *
	 * @param projectId - The project ID of the Code Dx project to get statuses for
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<Statuses> getStatuses(long projectId, BaseClientHandler<Statuses> handler) {

		return getStatusesHelper(projectId, new BaseClientHandler<Statuses>() {
			@Override
			public void onSuccess(Statuses result) {

				// Need to set the IDs for statuses because they are not auto mapped by Jackson
				//for (Map.Entry<String, Status> entry : result.getStatuses().entrySet()) {
				//	entry.getValue().setId(entry.getKey());
				//}

				handler.onSuccess(result);
			}

			@Override
			public void onError(Throwable t) {
				handler.onError(t);
			}

			@Override
			public void onExpectedError(CodeDxError e) {
				handler.onExpectedError(e);
			}

			@Override
			public void onNonSuccess(int errorCode, String message) {
				handler.onNonSuccess(errorCode, message);
			}
		});
	}

	private Future<Statuses> getStatusesHelper(long projectId, BaseClientHandler<Statuses> handler) {
		String apiPath = "/x/projects/" + projectId + "/statuses";
		return sendGetRequest(apiPath, Statuses.class, handler);
	}

	/**
	 * Calls API to set status of a single finding
	 *
	 * @param findingId - The finding ID of the Code Dx finding to set statuses for
	 * @param statusId  - The ID of the status to set finding to
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public void setStatus(long findingId, String statusId, BaseClientHandler<Integer> handler) {
		String apiPath = "/x/findings/" + findingId + "/status";

		//build request body
		SetStatus payload = new SetStatus();
		payload.setStatus(statusId);
		String body;
		try {
			body = JsonUtil.objectToJsonString(payload);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return;
		}

		//pass in null as class type because there's no response body, only response code which should be handled by handler
		sendPutRequest(apiPath, body, Integer.class, handler);
	}

	/**
	 * Calls API to set status of a multiple findings
	 *
	 * @param projectId  - Project ID that the findings are in
	 * @param findingIds - Array of finding Ids to set the status of
	 * @param statusId   - The ID of the status to set findings to
	 * @param handler    - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<Job> setStatuses(long projectId, long[] findingIds, String statusId, BaseClientHandler<Job> handler) {

		String apiPath = "/x/projects/" + projectId + "/bulk-status-update";

		Filter filter = new Filter();
		filter.set(Filter.FINDING, findingIds);

		SetStatus payload = new SetStatus();
		payload.setStatus(statusId);
		payload.setFilter(filter);

		String body;
		try {
			body = JsonUtil.objectToJsonString(payload);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return null;
		}

		return sendPostRequest(apiPath, body, Job.class, handler);

	}

	/**
	 * Calls API to get a file's contents based on file path
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param filePath  - The filepath to the file
	 * @param handler   - The FileContentClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<String> getFileContent(long projectId, String filePath, FileContentClientHandler<String> handler) {
		String apiPath = "/x/projects/" + projectId + "/files/tree/" + filePath;

		BaseClientHandler<String> baseClientHandler = new BaseClientHandler<String>() {
			@Override
			public void onSuccess(String result) {
				handler.onSuccess(result);
			}

			@Override
			public void onError(Throwable t) {
				handler.onError(t);
			}

			@Override
			public void onNonSuccess(int errorCode, String message) {

				//Handle the possible error codes calling the correct FileContentClientHandler methods
				switch (errorCode) {
					case 403:
						onExpectedError(CodeDxError.INVALID_CREDENTIALS);
						break;
					case 404:
						handler.onNotFoundResponse(message);
						break;
					case 415:
						handler.onUnsupportedMediaType(message);
						break;
					case 500:
						handler.onInternalError(message);
						break;
					default:
						onExpectedError(CodeDxError.HTTP_ERROR_CODE);
				}
			}

			@Override
			public void onExpectedError(CodeDxError e) {
				handler.onExpectedError(e);
			}
		};

		return sendGetRequest(apiPath, String.class, baseClientHandler);
	}

	/**
	 * Calls API to get the mappings for a list of files. Called form helper function {@link #getMappings(long, List, BaseClientHandler)}
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param files     - A list of filepaths
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	private Future<MappingsResponse> getMappingsResponse(long projectId, List<String> files, BaseClientHandler<MappingsResponse> handler) {

		String apiPath = String.format("/x/projects/%s/files/mappings", projectId);

		MappingsRequest request = new MappingsRequest();
		request.setFiles(files);

		// Create body from request
		String body;
		try {
			body = JsonUtil.objectToJsonString(request);
		} catch (IOException e) {
			handler.onExpectedError(CodeDxError.FAILED_IO);
			return null;
		}

		return sendPostRequest(apiPath, body, MappingsResponse.class, handler);
	}

	/**
	 * Calls API to get the mappings for a list of files. Helper function for {@link #getMappingsResponse(long, List, BaseClientHandler)} to abstract away the "MappingsResponse" and converting it to the Map
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param files     - A list of filepaths
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public void getMappings(long projectId, List<String> files, BaseClientHandler<Map<String, ProjectFile>> handler) {

		getMappingsResponse(projectId, files, new BaseClientHandler<MappingsResponse>() {
			@Override
			public void onSuccess(MappingsResponse result) {

				Map<String, ProjectFile> mappings = new HashMap<>();

				if (result != null) {
					ObjectMapper mapper = new ObjectMapper();
					for (Map.Entry<String, Object> any : result.any().entrySet()) {
						ProjectFile mapping = mapper.convertValue(any.getValue(), ProjectFile.class);
						mappings.put(any.getKey(), mapping);
					}
				}

				handler.onSuccess(mappings);
			}

			@Override
			public void onError(Throwable t) {
				handler.onError(t);
			}

			@Override
			public void onExpectedError(CodeDxError e) {
				handler.onExpectedError(e);
			}

			@Override
			public void onNonSuccess(int errorCode, String message) {
				handler.onNonSuccess(errorCode, message);
			}
		});

	}

	/**
	 * Takes in a zip file, and sends the file for analysis
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param inputs    - An array of files, or a zip file to be analyzed
	 * @param handler   - The BaseClientHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 * @return a future of the response, or null on error. If you want to block on the calling thread use future.get() which blocks until the query finishes
	 */
	public Future<CreateAnalysisRunResponse> createAnalysisRun(long projectId, File[] inputs, BaseClientHandler<CreateAnalysisRunResponse> handler) {

		String apiPath = String.format("/api/projects/%s/analysis", projectId);

		//Add each of the files to a list
		List<Part> filePartList = new ArrayList<>();
		for (File input : inputs) {

			FilePart filePart = new FilePart("file1", input, "text/plain", StandardCharsets.UTF_8, input.getName());

			filePartList.add(filePart);
		}

		return sendMultipartPostRequest(apiPath, filePartList, CreateAnalysisRunResponse.class, handler);

	}

}
