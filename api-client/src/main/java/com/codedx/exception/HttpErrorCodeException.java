/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: exception.HttpErrorCodeException
 * FileName: HttpErrorCodeException.java
 */

package com.codedx.exception;

public class HttpErrorCodeException extends Exception {

	public int errorCode;
	public String url;
	public String message;

	public HttpErrorCodeException(int errorCode, String url) {
		super("Code Dx server responded with code " + errorCode + " while serving " + url);
	}

}
