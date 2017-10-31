/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: api.PlatformInterface
 * FileName: PlatformInterface.java
 */

package com.codedx.api;

import java.util.concurrent.ScheduledExecutorService;

public interface PlatformInterface {

	String getSavedUrl();

	ScheduledExecutorService getAppScheduledExecutorService();

	String getUsername();

	String getPassword();

	String getTrustStoreDirectory();

	int getReadTimeout();

}
