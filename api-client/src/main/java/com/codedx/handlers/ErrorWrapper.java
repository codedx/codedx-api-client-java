/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: handlers.ErrorWrapper
 * FileName: ErrorWrapper.java
 */

package com.codedx.handlers;

import java.util.concurrent.atomic.AtomicBoolean;

//Class to wrap multiple ClientHandlers during 1 operation that makes multiple requests at once. Prevents multiple error messages from being displayed
public class ErrorWrapper {

	private AtomicBoolean gotError = new AtomicBoolean(false);

	public <T> BaseClientHandler<T> wrap(BaseClientHandler<T> handler) {
		return new BaseClientHandler<T>() {
			@Override
			public void onSuccess(T result) {
				handler.onSuccess(result);
			}

			@Override
			public void onError(Throwable t) {
				if (gotError.compareAndSet(false, true)) {
					handler.onError(t);
				}
			}

			@Override
			public void onExpectedError(CodeDxError e) {
				if (gotError.compareAndSet(false, true)) {
					handler.onExpectedError(e);
				}
			}

			@Override
			public void onNonSuccess(int errorCode, String message) {
				if (gotError.compareAndSet(false, true)) {
					handler.onNonSuccess(errorCode, message);
				}
			}

		};
	}


}
