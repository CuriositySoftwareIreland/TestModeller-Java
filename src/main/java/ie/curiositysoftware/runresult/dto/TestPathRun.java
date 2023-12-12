package ie.curiositysoftware.runresult.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPathRun
{
    public TestPathRunStatusEnum testStatus;

    public String message;

    public String vipRunId;

    public String lastRunGuid;

    public Date runTimeStamp;

    public String runColId;

    public long jobId;

    public String testPathGuid;

    public int runTime;


    private String runLogs;

    public Long profileId;

    public Long testSuiteId;

    public String runSource;

    private List<TestPathRunStep> testPathRunSteps;

    public TestPathRun()
    {
        testPathRunSteps = new ArrayList<TestPathRunStep>();
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setRunColId(String runColId) {
        this.runColId = runColId;
    }

    public String getRunColId() {
        return runColId;
    }

    public TestPathRunStatusEnum getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestPathRunStatusEnum testStatus) {
        this.testStatus = testStatus;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setTestPathGuid(String testPathGuid) {
        this.testPathGuid = testPathGuid;
    }

    public String getTestPathGuid() {
        return testPathGuid;
    }

    public void setVipRunId(String vipRunId) {
        this.vipRunId = vipRunId;
    }

    public void setRunTimeStamp(Date runTimeStamp) {
        this.runTimeStamp = runTimeStamp;
    }

    public void setLastRunGuid(String lastRunGuid) {
        this.lastRunGuid = lastRunGuid;
    }

    public String getVipRunId() {
        return vipRunId;
    }

    public String getLastRunGuid() {
        return lastRunGuid;
    }

    public Date getRunTimeStamp() {
        return runTimeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setTestPathRunSteps(List<TestPathRunStep> testPathRunSteps) {
        this.testPathRunSteps = testPathRunSteps;
    }

    public List<TestPathRunStep> getTestPathRunSteps() {
        return testPathRunSteps;
    }

    public void setRunSource(String runSource) {
        this.runSource = runSource;
    }

    public String getRunSource() {
        return runSource;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getTestSuiteId() {
        return testSuiteId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setTestSuiteId(Long testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public String getRunLogs() {
        return runLogs;
    }

    public void setRunLogs(String runLogs) {
        this.runLogs = runLogs;
    }
}
