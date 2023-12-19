package ie.curiositysoftware.runresult.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPathRunStep {
    public enum TestPathRunStepType {
        Default,
        APIStep
    }

    private Long id;

    private String stepName;

    private String stepDescription;

    private TestPathRunStatusEnum testStatus;

    private String message;

    private byte[] image;

    private String nodeGuid;

    private Long moduleColId;

    private Date runTimeStamp;

    private String pageSource;

    private String elementSource;

    private Long moduleObjId;

    private TestPathRunStepType stepType;

    private TestPathRunStepHTTPResponse httpResponse;

    private TestPathRunStepHTTPRequest httpRequest;

    private List<TestPathRunStepAssertion> assertions;

    private Long testPathRun;

    public TestPathRunStep()
    {
        stepType = TestPathRunStepType.Default;

        assertions = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepName() {
        return stepName;
    }

    public TestPathRunStatusEnum getTestStatus() {
        return testStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public void setTestStatus(TestPathRunStatusEnum testStatus) {
        this.testStatus = testStatus;
    }

    public void setNodeGuid(String nodeGuid) {
        this.nodeGuid = nodeGuid;
    }

    public String getNodeGuid() {
        return nodeGuid;
    }

    public TestPathRunStepType getStepType() {
        if (stepType == null)
            return TestPathRunStepType.Default;

        return stepType;
    }

    public void setStepType(TestPathRunStepType stepType) {
        this.stepType = stepType;
    }

    public TestPathRunStepHTTPResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(TestPathRunStepHTTPResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public TestPathRunStepHTTPRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(TestPathRunStepHTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setModuleObjId(Long moduleObjId) {
        this.moduleObjId = moduleObjId;
    }

    public void setModuleColId(Long moduleColId) {
        this.moduleColId = moduleColId;
    }

    public Long getModuleObjId() {
        return moduleObjId;
    }

    public Long getModuleColId() {
        return moduleColId;
    }

    public Long getTestPathRun() {
        return testPathRun;
    }

    public void setTestPathRun(Long testPathRun) {
        this.testPathRun = testPathRun;
    }

    public void setRunTimeStamp(Date runTimeStamp) {
        this.runTimeStamp = runTimeStamp;
    }

    public void setElementSource(String elementSource) {
        this.elementSource = elementSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    public String getPageSource() {
        return pageSource;
    }

    public String getElementSource() {
        return elementSource;
    }

    public Date getRunTimeStamp() {
        return runTimeStamp;
    }

    public List<TestPathRunStepAssertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<TestPathRunStepAssertion> assertions) {
        this.assertions = assertions;
    }

    public TestPathRunStepAssertion addFailStep(String type, String message)
    {
        TestPathRunStepAssertion assertion = new TestPathRunStepAssertion();
        assertion.setTestStatus(TestPathRunStatusEnum.Failed);
        assertion.setType(type);
        assertion.setMessage(message);
        assertions.add(assertion);

        setTestStatus(TestPathRunStatusEnum.Failed);

        return assertion;
    }

    public TestPathRunStepAssertion addPassStep(String type, String message)
    {
        TestPathRunStepAssertion assertion = new TestPathRunStepAssertion();
        assertion.setTestStatus(TestPathRunStatusEnum.Passed);
        assertion.setType(type);
        assertion.setMessage(message);
        assertions.add(assertion);

        return assertion;
    }

    public List<String> getAssertionString()
    {
        List<String> assertionStrings = new ArrayList<>();
        for (TestPathRunStepAssertion assertion : assertions)
        {
            assertionStrings.add(assertion.getTestStatus().toString() + ": " + assertion.getType() + " - " +  assertion.getMessage());
        }

        return assertionStrings;
    }
}
