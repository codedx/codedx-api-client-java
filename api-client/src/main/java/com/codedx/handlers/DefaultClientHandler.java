/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.DefaultClientHandler
 * FileName: DefaultClientHandler.java
 */

package com.codedx.handlers;

public abstract class DefaultClientHandler<T> implements BaseClientHandler<T> {

	//private static final Logger logger = Logger.getInstance(CodeDxAPIClient.class.getName());

	//Abstracts away the onNonSuccess function to just call the onExpectedError handler method
	public void onNonSuccess(int errorCode, String message) {
		//logger.warn("Code Dx Error: HTTP ERROR CODE " + errorCode + ". Message: " + message);
		if (Integer.compare(errorCode, 403) == 0) {
			onExpectedError(CodeDxError.INVALID_CREDENTIALS);
		} else {
			onExpectedError(CodeDxError.HTTP_ERROR_CODE);
		}
	}

}
