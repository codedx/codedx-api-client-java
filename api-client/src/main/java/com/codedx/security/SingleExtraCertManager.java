package com.codedx.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;

/**
 * ExtraCertManager implementation that only allows a single accepted
 * certificate at once. Any time a certificate is added (be it temporarily or
 * permanently), any previous certificates will be forgotten. At any given time,
 * the {@link #asKeyStore()} method should return a KeyStore with 0 or 1
 * certificates registered.
 */
public class SingleExtraCertManager implements ExtraCertManager {

	private final File keystoreFile;
	private final char[] password;
	private boolean isUsingFile;
	private Certificate tempCert = null;

	public SingleExtraCertManager(File keystoreFile, String password) {
		this.keystoreFile = keystoreFile;
		this.password = password.toCharArray();
		isUsingFile = true;
	}

	@Override
	public void addTemporaryCert(Certificate cert) {
		if (isUsingFile) {
			keystoreFile.delete();
		}
		isUsingFile = false;
		tempCert = cert;
	}

	@Override
	public void addPermanentCert(Certificate cert) throws IOException, GeneralSecurityException {
		tempCert = null;
		isUsingFile = true;

		// create a keystore and put the cert in it
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, password);
		ks.setCertificateEntry("default", cert);

		// store the new keystore to the keystoreFile
		FileOutputStream out = new FileOutputStream(keystoreFile);
		try {
			ks.store(out, password);
		} finally {
			out.close();
		}

	}

	@Override
	public void purgeTemporaryCerts() {
		isUsingFile = true;
		tempCert = null;
	}

	@Override
	public void purgePermanentCerts() {
		if (isUsingFile) {
			// keep the flag, but delete the file
			keystoreFile.delete();
		}
	}

	@Override
	public void purgeAllCerts() {
		isUsingFile = true;
		tempCert = null;
		keystoreFile.delete();
	}

	@Override
	public KeyStore asKeyStore() throws IOException, GeneralSecurityException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

		if (isUsingFile) {
			// load from the file, as long as it exists
			if (keystoreFile.canRead()) {
				FileInputStream in = new FileInputStream(keystoreFile);
				try {
					ks.load(in, password);
				} finally {
					in.close();
				}
			} else {
				ks.load(null, password);
			}
		} else {
			ks.load(null, password);
			// insert the tempCert to the keystore
			if (tempCert != null) {
				ks.setCertificateEntry("default", tempCert);
			}
		}

		if (ks.aliases().hasMoreElements()) {
			return ks;
		} else {
			return null;
		}
	}
}
