package com.secdec.codedx.api.client;

import java.net.MalformedURLException;

import org.apache.http.HttpRequest;

class KeyAuthCodeDxClient extends CodeDxClient{

	private final String KEY_HEADER  = "API-Key";
	private String apiKey;
	
	public KeyAuthCodeDxClient(String server, String apiKey) throws MalformedURLException{
		
		super(server);	
		
		this.apiKey = apiKey;
	}
	
	@Override
	protected <T> T execute(HttpRequest request, Class<T> payloadType)
			throws CodeDxClientException {
		
		request.addHeader(KEY_HEADER,apiKey);
		
		return super.execute(request, payloadType);
	}

}
