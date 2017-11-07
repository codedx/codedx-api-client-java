package com.codedx.error;

public class AnalysisError implements ExpectedError {

    private CodeDxError error;

    @Override
    public String getDescription() {
        switch(error){
            case NOT_FOUND:  return "Project does not exist";
            case UNSUPPORTED_MEDIA_TYPE: return "One or more of the files do not match the expected format";
            default: return this.error.getDescription();
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
