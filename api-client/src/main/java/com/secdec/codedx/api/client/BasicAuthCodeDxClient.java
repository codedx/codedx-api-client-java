package com.secdec.codedx.api.client;

import java.net.MalformedURLException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

class BasicAuthCodeDxClient extends CodeDxClient{

	public BasicAuthCodeDxClient(String server, String username, String password) throws MalformedURLException {
		super(server);
		
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		
		if (username != null && password != null)
		{
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			AuthScope authScope = new AuthScope(this.targetHost);
			credentialsProvider.setCredentials(authScope, credentials);
		}

		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicScheme = new BasicScheme();
		authCache.put(targetHost, basicScheme);

		context.setCredentialsProvider(credentialsProvider);
		context.setAuthCache(authCache);
		
	}
}
