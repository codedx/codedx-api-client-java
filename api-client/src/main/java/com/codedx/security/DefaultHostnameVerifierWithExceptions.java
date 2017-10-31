package com.codedx.security;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.Set;

public class DefaultHostnameVerifierWithExceptions implements HostnameVerifier {

	private final HostnameVerifier delegate;
	private final Set<String> allowedHosts;

	public DefaultHostnameVerifierWithExceptions(HostnameVerifier delegate, Set<String> allowedHosts) {
		this.delegate = delegate;
		this.allowedHosts = allowedHosts;
	}

	@Override
	public boolean verify(String hostname, SSLSession sslSession) {
		//If it's verified by default or it contains an allowed host
		if (delegate.verify(hostname, sslSession) || allowedHosts.contains(hostname)) {
			return true;
		}

		//if failed both, don't verify
		return false;
	}

}
