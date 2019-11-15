package ie.curiositysoftware.runresult.services;

import java.util.UUID;

public class TestRunIdGenerator {
    private static String RunId = null;

    public static String getRunId()
    {
        if (RunId == null) {
            RunId = UUID.randomUUID().toString();
        }

        return RunId;
    }
}
