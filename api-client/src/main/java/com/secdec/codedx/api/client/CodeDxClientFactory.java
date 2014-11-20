package com.secdec.codedx.api.client;

import java.net.MalformedURLException;

public class CodeDxClientFactory {

	public ICodeDxClient createClient(String server) throws MalformedURLException{
		
		return new CodeDxClient(server);
	}
	
	public ICodeDxClient createClient(String server, String apiKey) throws MalformedURLException{
		
		return new KeyAuthCodeDxClient(server,apiKey);
	}
	
	public ICodeDxClient createClient(String server, String username, String password) throws MalformedURLException{
		
		return new BasicAuthCodeDxClient(server,username,password);
	}

}
