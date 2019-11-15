package ie.curiositysoftware.jobengine.dto.job.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AutomationGenerationJobSettings {
    private Long id;

    private Long testSuiteId;

    private Long modelVersionId;

    public AutomationGenerationJobSettings()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTestSuiteId(Long testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public Long getModelVersionId() {
        return modelVersionId;
    }

    public void setModelVersionId(Long modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public Long getTestSuiteId() {
        return testSuiteId;
    }
}
