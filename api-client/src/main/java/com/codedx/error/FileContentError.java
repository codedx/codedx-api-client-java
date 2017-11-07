package com.codedx.error;


public class FileContentError implements ExpectedError {

    private CodeDxError error;

    @Override
    public String getDescription() {
        switch (error){
            case NOT_FOUND: return "Project or file does not exist";
            case UNSUPPORTED_MEDIA_TYPE: return "The file was not able to be interpreted as a text file";
            default: return this.error.getDescription();
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
