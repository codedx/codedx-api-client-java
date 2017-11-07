package com.codedx.error;

public class ProjectError implements ExpectedError {

    private CodeDxError error;

    @Override
    public String getDescription() {
        //Switch on the CodeDxError and give a more descriptive error message
        switch (error) {
            case CONFLICT: return "CONFLICT: project cannot be deleted due to a queued or running analysis";
            default: return this.error.getDescription();
        }
    }

    @Override
    public void setError(CodeDxError error) {
        this.error = error;
    }
}
