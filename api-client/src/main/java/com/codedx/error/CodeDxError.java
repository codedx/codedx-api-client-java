/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.CodeDxError
 * FileName: CodeDxError.java
 */

package com.codedx.error;

public enum CodeDxError {

	//API client error codes
	INVALID_URL("INVALID_URL: The entered server URL is invalid."),
	CANCELLATION("CANCELLATION: The thread was canceled."),
	INTERRUPTED("INTERRUPTED: The thread was interrupted."),
	FAILED_EXECUTE("FAILED_EXECUTE: The thread failed to execute."),
	FAILED_CONNECT("FAILED_CONNECT: The connection to the server failed."),
	FAILED_IO("FAILED_IO: I/O Operation failed."),
	NULL_POINTER("NULL_POINTER: A null value occurred where it was not expected."),
	// 400
	BAD_REQUEST("BAD_REQUEST: invalid request body"),
	// 403
	INVALID_CREDENTIALS("INVALID_CREDENTIALS: The wrong credentials were provided for the given server"),
	//404
	NOT_FOUND("404: Not Found"),
	// 409
	CONFLICT("CONFLICT: The request could not be completed due to a conflict"),
	// 415
	UNSUPPORTED_MEDIA_TYPE("UNSUPPORTED_MEDIA_TYPE: The server refuses to accept the request because the payload format is in an unsupported format."),
	HTTP_ERROR_CODE("HTTP_ERROR_CODE: An error code was returned from the server."),
	READ_TIMEOUT_ERROR("READ_TIMEOUT_ERROR: A query took too long to complete and timed out."),
	SSL_ERROR("SSL_ERROR: The connection to the server failed because of an ssl error."),
	UNEXPECTED("UNEXPECTED: The error that occured was unexpected");


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

	public static CodeDxError decode(int errorCode){
		switch (errorCode){
			case 403: return INVALID_CREDENTIALS;
			case 404: return NOT_FOUND;
			case 409: return CONFLICT;
			default: return UNEXPECTED;
		}
	}

}
