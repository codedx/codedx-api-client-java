/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.FileContentClientHandler
 * FileName: FileContentClientHandler.java
 */

package com.codedx.handlers;

public interface FileContentClientHandler<T> {

	//Regular client handler methods
	void onSuccess(String result);

	void onError(Throwable t);

	void onExpectedError(CodeDxError e);

	//One for each error code possible when getting file contents
	void onNotFoundResponse(String message);

	void onUnsupportedMediaType(String message);

	void onInternalError(String message);

}
