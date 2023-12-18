package ie.curiositysoftware.runresult.dto;

public class TestPathRunStepAssertion {
    private Long id;

    private TestPathRunStatusEnum testStatus;

    private String type;

    private String message;

    private Long testPathRunStep;

    public void setTestPathRunStep(Long testPathRunStep) {
        this.testPathRunStep = testPathRunStep;
    }

    public TestPathRunStatusEnum getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestPathRunStatusEnum testStatus) {
        this.testStatus = testStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getTestPathRunStep() {
        return testPathRunStep;
    }
}
