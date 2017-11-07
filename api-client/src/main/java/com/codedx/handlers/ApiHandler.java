package com.codedx.handlers;

import com.codedx.error.ExpectedError;

public interface ApiHandler<T, E extends ExpectedError> {

    //When query is successful and returns a success code with a result of type T
    void onSuccess(T result);

    //When an api error occurs (some non-200 status code)
    void onApiError(E err);

    void onThrowable(Throwable err);
}
