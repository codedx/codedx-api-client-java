package com.codedx.security;

import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.SslEngineFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class CodeDxSslEngineFactory implements SslEngineFactory {

	private String pluginTrustStoreLocation;

	private ExtraCertManager currentCertManager;
	private String lastPeerHost = "";

	public CodeDxSslEngineFactory(String pluginTrustStoreLocation){
		this.pluginTrustStoreLocation = pluginTrustStoreLocation;
	}

	/**
	 * Determines the location for the <code>truststore</code> file for the
	 * given host. Each host has a file to store
	 * user-accepted invalid certificates; these files will be stored in the
	 * Code Dx IntelliJ plugin's directory.
	 *
	 * @param host A URL hostname, e.g. "www.google.com"
	 * @return The file where the trust store for the given host should be
	 * stored
	 */
	private File getTrustStoreForHost(String host) {
		File pluginStateDir = new File(pluginTrustStoreLocation);
		File keystoreDir = new File(pluginStateDir, ".usertrust");
		keystoreDir.mkdirs();

		// Host may only contain alphanumerics, dash, and dot.
		// Replace anything else with an underscore.
		String safeHost = host.replaceAll("[^a-zA-Z0-9\\-\\.]", "_");

		// <pluginstate>/.usertrust/<hostname>.truststore
		return new File(keystoreDir, safeHost + ".truststore");
	}

	/**
	 * Create a new currentCertManager if it doesn't exist or if the host changed
	 * If host didn't change, and it already exists, leave the cert manager
	 *
	 * @param peerHost A URL hostname, e.g. "www.google.com"
	 */
	private void createCertManager(String peerHost) {
		if (currentCertManager == null || !(peerHost.equals(lastPeerHost))) {
			File managedKeyStoreFile = getTrustStoreForHost(peerHost);
			currentCertManager = new SingleExtraCertManager(managedKeyStoreFile, "floopydoop");
			lastPeerHost = peerHost;
		}
	}

	/**
	 * Returns a SSLContext for the given host. Reuses currentCertManager if possible
	 * Called from {@link #getSSLContext(String)}
	 *
	 * @param peerHost A URL hostname, e.g. "www.google.com"
	 * @return SSLContext for given host
	 */
	public SSLContext getSSLContext(String peerHost) {

		// get the default hostname verifier that gets used by the modified one
		// and the invalid cert dialog
		DefaultHostnameVerifier defaultHostnameVerifier = new DefaultHostnameVerifier();

		// invalid cert strat that pops up a dialog asking the user if they want
		// to accept the cert
		InvalidCertificateStrategy invalidCertStrat = new InvalidCertificateDialogStrategy(defaultHostnameVerifier, peerHost);

		/*
		 * Set up a composite trust manager that uses the default trust manager
		 * before delegating to the "reloadable" trust manager that allows users
		 * to accept invalid certificates.
		 */
		List<X509TrustManager> trustManagersForComposite = new LinkedList<>();

		SSLContext sslContext;
		try {
			X509TrustManager systemTrustManager = getDefaultTrustManager();
			// make sure that currentCertManager is created
			createCertManager(peerHost);
			ReloadableX509TrustManager customTrustManager = new ReloadableX509TrustManager(currentCertManager, invalidCertStrat);
			trustManagersForComposite.add(systemTrustManager);
			trustManagersForComposite.add(customTrustManager);
			X509TrustManager trustManager = new CompositeX509TrustManager(trustManagersForComposite);

			// setup the SSLContext using the custom trust manager
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{trustManager}, null);
		} catch (IOException | GeneralSecurityException e) {
			//logger.error("Error while creating trust managers and SSL context", e);
			try {
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, null, null);
			} catch (GeneralSecurityException ge) {
//				This should never happen
				//logger.error("Failed default sslContext", ge);
				return null;
			}
		}
		return sslContext;
	}

	/**
	 * Implementation of {@link SslEngineFactory} method to produce a SSLEngine
	 * Called internally within async-http-client when creating connections
	 * Factory is set for CodeDxAPIClient in {}
	 *
	 * @param config   The configuration for the async-http-client using this SslEngineFactory
	 * @param peerHost A URL hostname, e.g. "www.google.com"
	 * @param peerPort A URL port, e.g. 8080
	 * @return The SSLEngine to use for the connection
	 */
	@Override
	public SSLEngine newSslEngine(AsyncHttpClientConfig config, String peerHost, int peerPort) {

		//Create cert manager if it wasn't created before, or if host changed
		createCertManager(peerHost);

		SSLContext sslContext = getSSLContext(peerHost);
		if (sslContext == null) {
			return null;
		}

		// Create the engine
		SSLEngine engine = sslContext.createSSLEngine(peerHost, peerPort);

		// Use as client
		engine.setUseClientMode(true);
		return engine;
	}


	private static X509TrustManager getDefaultTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
		TrustManagerFactory defaultFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		defaultFactory.init((KeyStore) null);

		TrustManager[] managers = defaultFactory.getTrustManagers();
		for (TrustManager mgr : managers) {
			if (mgr instanceof X509TrustManager) {
				return (X509TrustManager) mgr;
			}
		}

		return null;
	}


}
