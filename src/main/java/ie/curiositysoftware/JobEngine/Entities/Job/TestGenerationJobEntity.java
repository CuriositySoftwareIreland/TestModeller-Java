package ie.curiositysoftware.JobEngine.Entities.Job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class TestGenerationJobEntity
{
    private Long id;

    private Long modelId;

    private Long configurationId;

    private Long modelVersionId;

    public TestGenerationJobEntity()
    {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setModelVersionId(Long modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public Long getModelVersionId() {
        return modelVersionId;
    }

    public Long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Long configurationId) {
        this.configurationId = configurationId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
