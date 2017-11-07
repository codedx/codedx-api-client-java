package com.codedx.error;

public class FindingError implements ExpectedError {

    private CodeDxError error;

    @Override
    public String getDescription() {
        switch (error){
            case NOT_FOUND: return "The project or the finding do not exist";
            default: return this.error.getDescription();
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
