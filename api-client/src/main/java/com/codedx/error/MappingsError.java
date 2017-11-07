package com.codedx.error;

public class MappingsError implements ExpectedError{

    private CodeDxError error;

    @Override
    public String getDescription() {
        if (error != null) {
            switch (error) {
                case NOT_FOUND:
                    return "Project does not exist";
                default:
                    return this.error.getDescription();
            }
        } else {
            return "There is no error";
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
