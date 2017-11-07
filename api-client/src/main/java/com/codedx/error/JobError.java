package com.codedx.error;

public class JobError implements ExpectedError {

    private CodeDxError error;

    @Override
    public String getDescription() {
        switch(error){
            case NOT_FOUND: return "Job does not exist or has expired";
            default: return this.error.getDescription();
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
