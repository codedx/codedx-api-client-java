/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.BaseClientHandler
 * FileName: BaseClientHandler.java
 */

package com.codedx.handlers;

// See DefaultClientHandler and UIClientHandler for implementations of this base interface
public interface BaseClientHandler<T> {

	//When query is successful and returns a success code with a result of type T
	void onSuccess(T result);

	//When a non-handled error happens
	void onError(Throwable t);

	//When a query completes but returns an error code
	void onNonSuccess(int errorCode, String message);

	//When a query fails in an expected way (One of the enums in CodeDxError)
	void onExpectedError(CodeDxError e);

}
