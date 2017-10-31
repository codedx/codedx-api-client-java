/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.CodeDxError
 * FileName: CodeDxError.java
 */

package com.codedx.handlers;

public enum CodeDxError {

	//API client error codes
	INVALID_URL("INVALID_URL: The entered server URL is invalid."),
	CANCELLATION("CANCELLATION: The thread was canceled."),
	INTERRUPTED("INTERRUPTED: The thread was interrupted."),
	FAILED_EXECUTE("FAILED_EXECUTE: The thread failed to execute."),
	FAILED_CONNECT("FAILED_CONNECT: The connection to the server failed."),
	FAILED_IO("FAILED_IO: I/O Operation failed."),
	NULL_POINTER("NULL_POINTER: A null value occurred where it was not expected."),
	INVALID_CREDENTIALS("INVALID_CREDENTIALS: The wrong credentials were provided for the given server"),
	HTTP_ERROR_CODE("HTTP_ERROR_CODE: An error code was returned from the server."),
	READ_TIMEOUT_ERROR("READ_TIMEOUT_ERROR: A query took too long to complete and timed out."),
	SSL_ERROR("SSL_ERROR: The connection to the server failed because of an ssl error."),
	//Intellij Error codes
	FILE_NOT_FOUND("FILE_NOT_FOUND: The selected file could not be found in current project."),
	NO_ROW_SELECTED("NO_ROW_SELECTED: The operation could not be performed because no row from the table was selected.");


	private final String description;

	CodeDxError(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

}
