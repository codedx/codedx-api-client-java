package com.secdec.codedx.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secdec.codedx.api.client.data.AnalysisRun;
import com.secdec.codedx.api.client.data.CountGroup;
import com.secdec.codedx.api.client.data.Filter;
import com.secdec.codedx.api.client.data.Finding;
import com.secdec.codedx.api.client.data.GroupedCountQuery;
import com.secdec.codedx.api.client.data.Job;
import com.secdec.codedx.api.client.data.Project;
import com.secdec.codedx.api.client.data.ProjectFile;
import com.secdec.codedx.api.client.data.Status;
import com.secdec.codedx.api.client.data.SystemInfo;
import com.secdec.codedx.api.client.data.TableQuery;
import com.secdec.codedx.api.client.data.CreateAnalysisRunResult;

public class CodeDxClient implements ICodeDxClient{

	protected CloseableHttpClient client;
	protected URL url;
	protected HttpHost targetHost;
	protected HttpClientContext context;

	public CodeDxClient(String server) throws MalformedURLException{
		
		this.client = HttpClients.createDefault();
		this.url = null;
		this.url = new URL(server);
		
		this.targetHost = new HttpHost(this.url.getHost(), this.url.getPort(), this.url.getProtocol());
		this.context = HttpClientContext.create();
	}

	public SystemInfo getSystemInfo() throws CodeDxClientException
	{
		HttpGet get = new HttpGet("/x/system-info");
		return execute(get, SystemInfo.class);
	}
	
	public Project getProject(Long projectId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/api/projects/%s",projectId));
		return execute(get, Project.class);
	}
	
	public Project[] getProjects() throws CodeDxClientException
	{
		HttpGet get = new HttpGet("/api/projects");
		return execute(get, GetProjectsResponse.class).getProjects();
	}

	public AnalysisRun[] getAnalysisRuns(Long projectId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/api/projects/%s/runs",projectId));
		return execute(get, AnalysisRun[].class);
	}
	
	public AnalysisRun getAnalysisRun(Long runId) throws CodeDxClientException
	{
		HttpGet request = new HttpGet(String.format("/x/runs/%s", runId));
		return execute(request, AnalysisRun.class);
	}
	
	public AnalysisRun getLatestAnalysisRun(Long projectId) throws CodeDxClientException
	{

		HttpGet get = new HttpGet(String.format("/api/projects/%s/runs/latest",projectId));
		return execute(get, AnalysisRun.class);
	}
	
	public Job getJob(String jobId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/api/jobs/%s", jobId));
		return execute(get, Job.class);
	}

	public String getJobResult(String jobId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/api/jobs/%s/result", jobId));
		return execute(get, String.class);
	}
	
	public Job requestReport(Long runId, String reportFormat) throws CodeDxClientException
	{
		HttpPost post = new HttpPost(String.format("/api/runs/%s/report/%s", runId,reportFormat));
		return execute(post, Job.class);
	}

	public Finding[] getFindingsTable(Long runId, TableQuery query) throws CodeDxClientException
	{
		
		HttpPost post = new HttpPost(String.format("/x/runs/%s/findings/table", runId));
		
		try
		{
			post.setEntity(new StringEntity(objectToString(query)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize query", 0);
		}
		
		return execute(post, Finding[].class);
	}
	
	public Long getFindingsCount(Long runId, Filter filter) throws CodeDxClientException
	{
		GetFindingsCountRequest query = new GetFindingsCountRequest();
		query.setFilter(filter);

		HttpPost post = new HttpPost(String.format("/x/runs/%s/findings/count", runId));

		try
		{
			post.setEntity(new StringEntity(objectToString(query)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize filter", 0);
		}

		GetFindingsCountResponse count = execute(post, GetFindingsCountResponse.class);

		return count.getCount();
	}
	
	public Long getFindingsCount(Long runId) throws CodeDxClientException
	{
		
		HttpGet get = new HttpGet(String.format("/x/runs/%s/findings/count", runId));
		GetFindingsCountResponse count = execute(get, GetFindingsCountResponse.class);
		return count.getCount();
	}
	
	public CountGroup[] getGroupedFindingCounts(Long runId, GroupedCountQuery query) throws CodeDxClientException
	{
		HttpPost post = new HttpPost(String.format("/x/runs/%s/findings/grouped-counts", runId));

		try
		{
			post.setEntity(new StringEntity(objectToString(query)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize query", 0);
		}

		CountGroup[] counts = execute(post, CountGroup[].class);

		return counts;
	}
	
	public Status[] getStatuses(Long projectId) throws CodeDxClientException
	{
		HttpGet request = new HttpGet(String.format("/x/projects/%s/statuses", projectId));
		GetStatusesResponse statuses = execute(request, GetStatusesResponse.class);

		for (Entry<String, Status> entry : statuses.getStatuses().entrySet())
		{
			entry.getValue().setId(entry.getKey());
		}

		return statuses.getStatuses().values().toArray(new Status[0]);
	}
	
	public Map<String, ProjectFile> getMappings(Long projectId, List<String> files) throws CodeDxClientException
	{
		GetMappingsRequest request = new GetMappingsRequest();
		request.setFiles(files);

		HttpPost post = new HttpPost(String.format("/x/projects/%s/files/mappings", projectId));

		try
		{
			post.setEntity(new StringEntity(objectToString(request)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize files list", 0);
		}

		GetMappingsResponse response = execute(post, GetMappingsResponse.class);

		return response.getMappings();
	}
	

	public String getProjectFileContents(Long runId, Long pathId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/x/runs/%s/files/%s", runId,pathId));
		return execute(get, String.class);
	}

	public String getProjectFileContents(Long runId, String path) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/x/runs/%s/files/tree/%s", runId, path));
		return execute(get, String.class);
	}
	
	public ProjectFile[] getProjectFiles(Long projectId) throws CodeDxClientException
	{
		HttpGet get = new HttpGet(String.format("/x/projects/%s/files", projectId));
		return execute(get, ProjectFile[].class);
	}
	
	
	public void updateStatus(Long findingId, String statusId) throws CodeDxClientException
	{
		UpdateStatusRequest payload = new UpdateStatusRequest();
		payload.setStatus(statusId.toString());

		HttpPut put = new HttpPut(String.format("/x/findings/%s/status", findingId));
		try
		{
			put.setEntity(new StringEntity(objectToString(payload)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize status update request", 0);
		}
		
		execute(put, String.class);
	}

	public Job updateStatusBulk(Long runId, Filter filter, String statusId) throws CodeDxClientException
	{

		UpdateStatusRequest payload = new UpdateStatusRequest();
		payload.setStatus(statusId.toString());
		payload.setFilter(filter);

		HttpPost post = new HttpPost(String.format("/x/runs/%s/bulk-status-update", runId));
		try
		{
			post.setEntity(new StringEntity(objectToString(payload)));
		}
		catch (Exception exc)
		{
			throw new CodeDxClientException("Serialization Error: " + 
					"Could not serialize status update request", 0);
		}
		
		Job job = execute(post, Job.class);
		
		return job;
	}

	public URL getEmbeddedFindingDetailsUrl(Long runId, Long findingId) throws MalformedURLException {

		return new URL(this.url,String.format("/run/%s/detailsembed/%s", runId,findingId));
	}
	
	public URL buildBrowsableAnalysisRunUrl(int analysisRunId) throws MalformedURLException{
		
		return new URL(this.url,String.format("run/%s",analysisRunId));
	}
	
	public URL buildLatestAnalysisRunUrl(int projectId) throws MalformedURLException{
		
		return new URL(this.url,String.format("projects/%s/latest",projectId));
	}
	
	public CreateAnalysisRunResult createAnalysisRun(Long projectId, InputStream[] artifacts) throws CodeDxClientException {
		
		HttpPost post = new HttpPost(String.format("/api/projects/%s/analysis", projectId));
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


		for(InputStream artifact : artifacts){
			
			builder.addPart("file[]", new InputStreamBody(artifact,"file[]"));
		}
		
		HttpEntity entity = builder.build();
		
		post.setEntity(entity);
		
		return execute(post, CreateAnalysisRunResult.class);
		
	}
	
	private <T> T stringToObject(String string, Class<T> cls) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		object = mapper.readValue(string, cls);
		return object;
	}

	private String objectToString(Object obj) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String string = null;
		string = mapper.writeValueAsString(obj);
		return string;
	}

	@SuppressWarnings("unchecked")
	protected <T> T execute(HttpRequest request, Class<T> payloadType) throws CodeDxClientException
	{
		T toReturn = null;

		CloseableHttpResponse response = null;

		try
		{
			response = this.client.execute(this.targetHost, request, this.context);
		} 
		catch (Exception exc)
		{
			throw new CodeDxClientException("Connection Failure: " + 
					"Could not connect to the Code Dx server", 0);
		}

		if (response != null)
		{
			String string = "";

			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				try
				{
					string = EntityUtils.toString(entity);
				}
				catch (Exception exc)
				{
					throw new CodeDxClientException("Read Error: " + 
							"Could not read string data", 0);
				}
			}

			try { response.close(); } catch(Exception exc) {};


			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status < 300)
			{
				if (!payloadType.equals(String.class))
				{
					try {
						toReturn = stringToObject(string, payloadType);
					} catch (Exception exc) {

						throw new CodeDxClientException("Parse Failure: " + 
								"Could not parse the response", 0);
					}
				}
				else
				{
					toReturn = (T) string;
				}
			}
			else if (status == HttpStatus.SC_FORBIDDEN)
			{
				throw new CodeDxClientException("Authorization Failure: " + 
						"The credentials provided to the Code Dx server are incorrect.", status);
			}
			else if (status == HttpStatus.SC_NOT_FOUND)
			{

				throw new CodeDxClientException("Not Found: " + 
						"The requested resource was not found on the Code Dx server.", status);
			}
			else
			{
				throw new CodeDxClientException("Unexpected status code", status);
			}
		}

		return toReturn;
	}
}
