package com.codedx.security;


import org.apache.http.conn.ssl.DefaultHostnameVerifier;

import com.codedx.util.HashUtil;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.net.ssl.SSLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * An InvalidCertificateStrategy implementation that opens a modal dialog in the
 * eclipse workbench, prompting the user for their choice of action.
 */
public class InvalidCertificateDialogStrategy implements InvalidCertificateStrategy {

	//private static final Logger logger = Logger.getInstance(InvalidCertificateDialogStrategy.class.getName());

	private final DefaultHostnameVerifier defaultHostVerifier;
	private final String host;

	private CertificateAcceptance userChoice = CertificateAcceptance.REJECT;

	public InvalidCertificateDialogStrategy(DefaultHostnameVerifier defaultHostVerifier, String host) {
		this.defaultHostVerifier = defaultHostVerifier;
		this.host = host;
	}

	@Override
	public CertificateAcceptance checkAcceptance(Certificate genericCert, CertificateException certError) {
		if (genericCert instanceof X509Certificate) {
			X509Certificate cert = (X509Certificate) genericCert;

			//get issuer, fingerprint and host mismatch to send to dialog
			String issuer = cert.getIssuerDN().toString();
			String fingerprint = "";
			try {
				fingerprint = HashUtil.toHexString(HashUtil.getSHA1(cert.getEncoded()), ":");
			} catch (CertificateEncodingException e) {
				//logger.error(e);
				// this shouldn't actually ever happen
			}

			/*
			 * Add a "Host Mismatch" message
			 */
			String hostMismatch = "";
			try {
				defaultHostVerifier.verify(host, cert);
			} catch (SSLException e) {

				//get cn with non-depreciated methods. Referenced stack overflow: https://stackoverflow.com/a/7634755/5017214
				String dn = cert.getSubjectX500Principal().getName();
				LdapName ldapName;
				String cn = "";
				try {
					ldapName = new LdapName(dn);
					cn = ldapName.getRdns().get(0).getValue().toString();
				} catch (InvalidNameException ine) {
					//logger.warn("Could not get cn for certificate.", ine);
				}

				String msg;
				if (!cn.equals("")) {
					msg = String.format("Expected '%s', but the certificate is for '%s'.", host, cn);
				} else {
					msg = e.getMessage();
				}

				hostMismatch = msg;
			}

			final String finalFingerprint = fingerprint;
			final String finalHostMismatch = hostMismatch;

			/*ApplicationManager.getApplication().invokeAndWait(() -> {
				*//*
			 * Initialize the dialog to be opened
			 *//*
				SSLDialog dialog = new SSLDialog(issuer, finalFingerprint, finalHostMismatch);
			*//*
			 * Open the dialog, and return its result
			 *//*
				dialog.show();
				userChoice = dialog.getUserChoice();
			}, ModalityState.any());*/

			return userChoice;

		} else {
			return CertificateAcceptance.REJECT;
		}
	}
}
