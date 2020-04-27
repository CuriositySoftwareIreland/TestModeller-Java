package ie.curiositysoftware.pageobjects.dto;

import java.util.Date;

public class PageObjectHistoryEntity {
    private long id;

    private Date latestRun;

    private PageObjectParameterStateEnum pageObjectStatus;

    private String testGuid;

    private String runId;

    private String testName;

    private long pageObject;

    public PageObjectHistoryEntity()
    {

    }

    public void setPageObject(long pageObject) {
        this.pageObject = pageObject;
    }

    public void setTestGuid(String testGuid) {
        this.testGuid = testGuid;
    }

    public void setPageObjectStatus(PageObjectParameterStateEnum pageObjectStatus) {
        this.pageObjectStatus = pageObjectStatus;
    }

    public void setLatestRun(Date latestRun) {
        this.latestRun = latestRun;
    }

    public String getTestGuid() {
        return testGuid;
    }

    public PageObjectParameterStateEnum getPageObjectStatus() {
        return pageObjectStatus;
    }

    public Date getLatestRun() {
        return latestRun;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getPageObject() {
        return pageObject;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getRunId() {
        return runId;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }
}

