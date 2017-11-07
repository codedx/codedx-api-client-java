/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc
 * Project: api-client
 * ClassName: api.CodeDxAPIClient
 * FileName: CodeDxAPIClient.java
 */

package com.codedx.api;

import com.codedx.error.*;
import com.codedx.security.CodeDxSslEngineFactory;
import com.codedx.model.api.*;
import com.codedx.model.x.*;
import com.codedx.handlers.*;
import com.codedx.util.JsonUtil;

import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.Part;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
			// TODO handle this
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
	 * Getter for the SslEngineFactory.
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
	 * Sets the realm for this class. This should be called when user credentials change.
	 *
	 * @param username          - username for the connection
	 * @param password          - password for the connection
	 * @param usePreemptiveAuth - sets if connection will use PreemptiveAuthorization or not
	 */
	public void setClientRealm(String username, String password, boolean usePreemptiveAuth) {
		this.realm = getBasicAuthRealm(username, password, usePreemptiveAuth);
	}

	/**
	 * Class to extend async-http-client's {@link AsyncCompletionHandler} class
	 * Used in all of the sendXYZRequest methods.
	 */
	private class ApiCompletionHandler<T, E extends ExpectedError> extends AsyncCompletionHandler<T> {

		private final Class<T> classType;
		private E error;
		private final ApiHandler<T, E> handler;

		private ApiCompletionHandler(Class<T> classType, E error, ApiHandler<T, E> handler) {
			this.classType = classType;
			this.error = error;
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
			CodeDxError genericError = CodeDxError.decode(response.getStatusCode());
			this.error.setError(genericError);
			handler.onApiError(this.error);
			return null;
		}

		@Override
		public void onThrowable(Throwable t) {
			handler.onThrowable(t);
		}
	}

	/**
	 * Preforms an asynchronous POST request to the Code Dx Server at the apiPath. Success and error cases handled by handler.
	 *
	 * @param apiPath       - The path that is added to the server URL to get to API endpoint
	 * @param body          - The JSON string that will be the body of the POST Request
	 * @param classType     - The model class that the API response will be converted into
	 * @param expectedError - The error class used for error handling.
	 * @param handler       - The ApiHandler that will handle all of the success and error cases.
	 */
	private <T, E extends ExpectedError> void sendPostRequest(String apiPath, String body, Class<T> classType, E expectedError, ApiHandler<T, E> handler){
		URL url;
		try{
			url = new URL(platformInterface.getSavedUrl() + apiPath);
			client.prepareGet(url.toString())
					.setRealm(realm)
					.setHeader("Content-Type", "application/json")
					.setBody(body)
					.setRequestTimeout(platformInterface.getReadTimeout())
					.execute(new ApiCompletionHandler<>(classType, expectedError, handler));
		} catch (MalformedURLException e) {
			handler.onThrowable(e);
		}
	}

	/**
	 * Preforms an asynchronous POST request with a multipart body to the Code Dx Server at the apiPath. Success and error cases handled by handler.
	 *
	 * @param apiPath       - The path that is added to the server URL to get to API endpoint
	 * @param bodyParts     - A list of {@link Part} objects that make up the Mutipart body. Part is included in async-http-client library
	 * @param classType     - The model class that the API response will be converted into
	 * @param expectedError - The error class used for error handling.
	 * @param handler       - The ApiHandler that will handle all of the success and error cases.
	 */
	private <T, E extends ExpectedError> void sendMultipartPostRequest(String apiPath, List<Part> bodyParts, Class<T> classType, E expectedError, ApiHandler<T, E> handler) {
		String savedUrl = platformInterface.getSavedUrl();

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(savedUrl + apiPath);

			client.preparePost(url.toString())
					.setRealm(realm)
					.setHeader("Content-Type", "multipart/form-data")
					.setBodyParts(bodyParts)
					.setRequestTimeout(platformInterface.getReadTimeout())
					.execute(new ApiCompletionHandler<>(classType, expectedError, handler));
		} catch (MalformedURLException e) {
			handler.onThrowable(e);
		}
	}

	/**
	 * Preforms an asynchronous GET request with a multipart body to the Code Dx Server at the apiPath. Success and error cases handled by handler.
	 *
	 * @param apiPath       - The path that is added to the server URL to get to API endpoint
	 * @param classType     - The model class that the API response will be converted into
	 * @param expectedError - The error class used for error handling.
	 * @param handler       - The ApiHandler that will handle all of the success and error cases.
	 */
	private <T, E extends ExpectedError> void sendGetRequest(String apiPath, Class<T> classType, E expectedError, ApiHandler<T, E> handler){
		URL url;
		try{
			url = new URL(platformInterface.getSavedUrl() + apiPath);

			client.prepareGet(url.toString())
					.setRealm(realm)
					.setReadTimeout(platformInterface.getReadTimeout())
					.execute(new ApiCompletionHandler<>(classType, expectedError, handler));
		} catch (MalformedURLException e){
			handler.onThrowable(e);
		}
	}

	/**
	 * Preforms an asynchronous PUT request to the Code Dx Server at the apiPath and returns a Future for the response. Success and error cases handled by handler.
	 * If no response body is expected, enter null for classType
	 *
	 * @param apiPath       - The path that is added to the server URL to get to API endpoint
	 * @param classType     - The model class that the API response will be converted into
	 * @param expectedError - The error class used for error handling.
	 * @param handler       - The ApiHandler that will handle all of the success and error cases
	 */
	private <T, E extends ExpectedError> void sendPutRequest(String apiPath, String body, Class<T> classType, E expectedError, ApiHandler<T, E> handler) {

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(platformInterface.getSavedUrl() + apiPath);

			//Prepare and get response
			client.preparePut(url.toString())
					.setRealm(realm)
					.setBody(body)
					.setRequestTimeout(platformInterface.getReadTimeout())
					.execute(new ApiCompletionHandler<>(classType, expectedError, handler));
		} catch (MalformedURLException e) {
			handler.onThrowable(e);
		}
	}

	/** TODO: get more info about the use case for this method
	 * This method polls until the job indicates it completed, then execute either the failure or complete runnable depending on the status of the job. The polling rate determined by JOB_POLL_INTERVAL
	 *
	 * @param jobId         - The id of the job to wait for completion
	 * @param afterComplete - The runnable to run after the job if it completes successfully
	 * @param afterFailure  - The runnable to run after the job if it fails
	 */
	public void runOnJobCompletion(String jobId, Runnable afterComplete, Runnable afterFailure) {

		ScheduledExecutorService service = platformInterface.getAppScheduledExecutorService();

		Runnable checkJobStatus = new Runnable() {

			//Save this runnable into a variable so it can be called recursively
			Runnable thisRunnable = this;

			@Override
			public void run() {

				//make query to get job status. Can block on this
				getJob(jobId, new ApiHandler<Job, JobError>() {
					@Override
					public void onSuccess(Job result) {
						// If completed, run the correct runnable. Else, schedule another check
						if (result.getStatus().equals("completed")) {
							afterComplete.run();
						} else if (result.getStatus().equals("failed")) {
							afterFailure.run();
						} else if (result.getStatus().equals("queued") || result.getStatus().equals("running")) { // If didn't complete, schedule another check
							service.schedule(thisRunnable, JOB_POLL_INTERVAL, TimeUnit.MILLISECONDS);
						} else { /* Unexpected status. Fail job */
							afterFailure.run();
						}
					}

					@Override
					public void onApiError(JobError err) {
						// Maybe more than just
						afterFailure.run();
					}

					@Override
					public void onThrowable(Throwable err) {
						// Maybe more than just
						afterFailure.run();
					}
				});

			}
		};

		//Schedule the first check
		service.schedule(checkJobStatus, 250, TimeUnit.MILLISECONDS);
	}

	/**
	 * Calls api to get the job status
	 *
	 * @param jobId   - The id of the job
	 * @param handler - The ApiHandler that will handle all of the success and error cases
	 */
	public void getJob(String jobId, ApiHandler<Job, JobError> handler) {
		String apiPath = "/api/jobs/" + jobId;
		sendGetRequest(apiPath, Job.class, new JobError(), handler);
	}

	/**
	 * Calls api to get the job result
	 *
	 * @param classType - The class to convert the response of the request to
	 * @param handler   - The ApiHandler that will handle all of the success and error cases
	 */
	public <T> void getJobResult(Class<T> classType, ApiHandler<T, JobError> handler) {
		String apiPath = "/api/jobs";
		sendGetRequest(apiPath, classType, new JobError(), handler);
	}


	/**
	 * Tests connection to the api to see if it gets a valid response
	 *
	 * @param realm    - The realm (username/password) to preform the query on
	 * @param givenUrl - The server URL to test
	 * @param handler  - The ApiHandler that will handle all of the success and error cases
	 */
	public void testConnection(Realm realm, String givenUrl, ApiHandler<Integer, GenericError> handler) {

		//get response status code to see if able to access api

		//verify url
		//If it's not valid, let handler handle the exception
		URL url;
		try {
			url = new URL(givenUrl + "/api/projects");
		} catch (MalformedURLException e) {
			handler.onThrowable(e);
			return;
		}

		//Prepare and get response
		client.prepareGet(url.toString())
				.setRealm(realm)
				.execute(new ApiCompletionHandler<>(Integer.class, new GenericError(), handler));

	}

	/**
	 * Calls API to retrieve findings table. Logic to handle the Findings table results should be included in the ApiHandler onSuccess method passed into this function's parameters
	 *
	 * @param projectId      - The Code Dx project ID number to get the table for
	 * @param pageSize       - How many findings to include in the response
	 * @param pageNumber     - The page of findings to return. Ie. pageNumber == 1 would give the first page of findings
	 * @param sortDescriptor - A {@link SortDescriptor} object that determines what the results are sorted by
	 * @param sortDirection  - A {@link SortDirection} object that determines if the sort is ascending or descending
	 * @param filter         - A {@link Filter} object to filter the findings
	 * @param handler        - The ApiHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 */
	public void getFindingsTable(long projectId, long pageSize, long pageNumber, SortDescriptor sortDescriptor, SortDirection sortDirection, Filter filter, ApiHandler<Finding[], FindingError> handler) {

		String apiPath = String.format("/x/projects/%s/findings/table?expand=descriptor,issue,results.descriptor", projectId);
		FindingError error = new FindingError();

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
			error.setError(CodeDxError.FAILED_IO);
			handler.onApiError(error);
			return;
		}

		sendPostRequest(apiPath, body, Finding[].class, error, handler);
	}

	/**
	 * Calls API to get the number of findings
	 *
	 * @param projectId - The Code Dx project ID number to get the table for
	 * @param filter    - A {@link Filter} object to filter the findings count
	 * @param handler   - The ApiHandler that will handle all of the success and error cases
	 */
	public void getFindingsCount(long projectId, Filter filter, ApiHandler<Count, FindingError> handler) {

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
			handler.onThrowable(e);
			return;
		}

		sendPostRequest(apiPath, body, Count.class, new FindingError(), handler);

	}

	/**
	 * Calls API to get the number of findings of a specific severity
	 *
	 * @param projectId - The Code Dx project ID number to get the group count
	 * @param countBy   - A string to signify what to group the findings count by. Ie. "severity"
	 * @param filter    - A {@link Filter} object to filter the group findings count
	 * @param handler   - The ApiHandler that will handle all of the success and error cases
	 */
	public void getFindingsGroupCount(long projectId, String countBy, Filter filter, ApiHandler<GroupedCount[], FindingError> handler) {

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
			handler.onThrowable(e);
			return;
		}

		sendPostRequest(apiPath, body, GroupedCount[].class, new FindingError(), handler);
	}

	/**
	 * Calls API for projects available for a particular user
	 *
	 * @param handler - The ApiHandler that will handle all of the success and error cases
	 */
	public void getProjects(ApiHandler<Projects, ProjectError> handler) {
		String apiPath = "/api/projects";
		sendGetRequest(apiPath, Projects.class, new ProjectError(), handler);
	}

	public void createProject(String name, ApiHandler<Project, ProjectError> handler){
		String apiPath = "/api/projects";
		HashMap<String, String> map = new HashMap<>();
		map.put("name", name);
		String body;
		try {
			body = JsonUtil.objectToJsonString(map);
		} catch (IOException e) {
			handler.onThrowable(e);
			return;
		}
		sendPutRequest(apiPath, body, Project.class, new ProjectError(), handler);

	}

	/**
	 * Calls API for statuses for a particular project
	 *
	 * @param projectId - The project ID of the Code Dx project to get statuses for
	 * @param handler   - The ApiHandler that will handle all of the success and error cases
	 */
	public void getStatuses(long projectId, ApiHandler<Statuses, FindingError> handler){
		String apiPath = "/x/projects/" + projectId + "/statuses";
		sendGetRequest(apiPath, Statuses.class, new FindingError(), handler);
	}

	/**
	 * Calls API to set status of a single finding
	 *
	 * @param findingId - The finding ID of the Code Dx finding to set statuses for
	 * @param statusId  - The ID of the status to set finding to
	 * @param handler   - The ApiHandler that will handle all of the success and error cases
	 */
	public void setStatus(long findingId, String statusId, ApiHandler<Integer, FindingError> handler) {
		String apiPath = "/x/findings/" + findingId + "/status";

		//build request body
		SetStatus payload = new SetStatus();
		payload.setStatus(statusId);
		String body;
		try {
			body = JsonUtil.objectToJsonString(payload);
		} catch (IOException e) {
			handler.onThrowable(e);
			return;
		}

		//pass in null as class type because there's no response body, only response code which should be handled by handler
		sendPutRequest(apiPath, body, Integer.class, new FindingError(), handler);
	}

	/**
	 * Calls API to set status of a multiple findings
	 *
	 * @param projectId  - Project ID that the findings are in
	 * @param findingIds - Array of finding Ids to set the status of
	 * @param statusId   - The ID of the status to set findings to
	 * @param handler    - The ApiHandler that will handle all of the success and error cases
	 */
	public void setStatuses(long projectId, long[] findingIds, String statusId, ApiHandler<Job, FindingError> handler) {

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
			handler.onThrowable(e);
			return;
		}

		sendPostRequest(apiPath, body, Job.class, new FindingError(), handler);

	}

	/**
	 * Calls API to get a file's contents based on file path
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param filePath  - The filepath to the file
	 * @param handler   - The ApiHandler that will handle all of the success and error cases. See {} for implementations of this interface
	 */
	public void getFileContent(long projectId, String filePath, ApiHandler<String, FileContentError> handler) {
		String apiPath = "/x/projects/" + projectId + "/files/tree/" + filePath;
		sendGetRequest(apiPath, String.class, new FileContentError(), handler);
	}

	/**
	 * Calls API to get the mappings for a list of files.
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param files     - A list of filepaths
	 * @param handler   - The ApiHandler that will handle all of the success and error cases.
	 */
	private void getMappings(long projectId, List<String> files, ApiHandler<MappingsResponse, MappingsError> handler) {

		String apiPath = String.format("/x/projects/%s/files/mappings", projectId);

		MappingsRequest request = new MappingsRequest();
		request.setFiles(files);

		// Create body from request
		String body;
		try {
			body = JsonUtil.objectToJsonString(request);
		} catch (IOException e) {
			handler.onThrowable(e);
			return;
		}

		sendPostRequest(apiPath, body, MappingsResponse.class, new MappingsError(), handler);
	}

	/**
	 * Takes in a zip file, and sends the file for analysis
	 *
	 * @param projectId - Project ID that the findings are in
	 * @param inputs    - An array of files, or a zip file to be analyzed
	 * @param handler   - The ApiHandler that will handle all of the success and error cases.
	 */
	public void createAnalysisRun(long projectId, File[] inputs, ApiHandler<CreateAnalysisRunResponse, AnalysisError> handler) {

		String apiPath = String.format("/api/projects/%s/analysis", projectId);

		//Add each of the files to a list
		List<Part> filePartList = new ArrayList<>();
		for (File input : inputs) {

			FilePart filePart = new FilePart("file1", input, "text/plain", StandardCharsets.UTF_8, input.getName());

			filePartList.add(filePart);
		}

		sendMultipartPostRequest(apiPath, filePartList, CreateAnalysisRunResponse.class, new AnalysisError(), handler);
	}

}
