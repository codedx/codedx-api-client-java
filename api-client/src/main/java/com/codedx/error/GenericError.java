package com.codedx.error;

public class GenericError implements ExpectedError{

    private CodeDxError error;

    @Override
    public String getDescription() {
        return this.error.getDescription();
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
