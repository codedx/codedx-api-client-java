package test;

import com.codedx.api.CodeDxAPIClient;
import com.codedx.api.PlatformInterface;
import com.codedx.model.api.*;
import com.codedx.model.x.*;
import com.codedx.handlers.*;

import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        PlatInter plat = new PlatInter();
        CodeDxAPIClient client = new CodeDxAPIClient(plat);
        client.getFindingsTable(1,100,1,null,null,null,new DefaultClientHandler<Finding[]>() {
            @Override
            public void onSuccess(Finding[] result) {

            }

            @Override
            public void onError(Throwable t) {
                //count down latch in case of errors to ensure progress thread isn't waiting forever
            }

            @Override
            public void onExpectedError(CodeDxError e) {
                //count down latch in case of errors to ensure progress thread isn't waiting forever
            }
        });
    }
}
class PlatInter implements PlatformInterface{
    @Override
    public String getSavedUrl() {
        return "http://localhost:8080";
    }

    @Override
    public ScheduledExecutorService getAppScheduledExecutorService() {
        return null;
    }

    @Override
    public String getUsername() {
        return "codedx";
    }

    @Override
    public String getPassword() {
        return "codedx";
    }

    @Override
    public String getTrustStoreDirectory() {
        return "dont use this?";
    }

    @Override
    public int getReadTimeout() {
        return 120000;
    }
}
