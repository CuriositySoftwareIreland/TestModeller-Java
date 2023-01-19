package ie.curiositysoftware.runresult.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private TestPathRunStepType stepType;

    private TestPathRunStepHTTPResponse httpResponse;

    public TestPathRunStep()
    {
        stepType = TestPathRunStepType.Default;
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
}
