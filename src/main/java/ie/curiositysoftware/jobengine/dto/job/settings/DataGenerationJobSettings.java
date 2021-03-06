package ie.curiositysoftware.jobengine.dto.job.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DataGenerationJobSettings {
    private Long id;

    private Long testSuiteId;

    private Long modelVersionId;

    private Long configurationId;

    public DataGenerationJobSettings()
    {

    }

    public Long getTestSuiteId() {
        return testSuiteId;
    }

    public void setModelVersionId(Long modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public Long getModelVersionId() {
        return modelVersionId;
    }

    public void setTestSuiteId(Long testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public Long getConfigurationId() {
        return configurationId;
    }
}
