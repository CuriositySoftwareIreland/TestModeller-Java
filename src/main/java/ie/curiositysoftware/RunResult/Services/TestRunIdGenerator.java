package ie.curiositysoftware.RunResult.Services;

import java.util.UUID;

public class TestRunIdGenerator {
    private static String RunId = null;

    public static String GetRunId()
    {
        if (RunId == null) {
            RunId = UUID.randomUUID().toString();
        }

        return RunId;
    }
}
