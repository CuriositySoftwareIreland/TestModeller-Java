package ie.curiositysoftware.JobEngine.Entities.Job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class ExportTestsJobEntity {
    public Long id;

    public Long connectorId;

    public Long testSuiteId;

    public Long modelVersionId;

    public List<ExportTestJobParameterEntity> exportTestJobParameters;

    public ExportTestsJobEntity()
    {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public List<ExportTestJobParameterEntity> getExportTestJobParameters() {
        return exportTestJobParameters;
    }

    public void setExportTestJobParameters(List<ExportTestJobParameterEntity> exportTestJobParameters) {
        this.exportTestJobParameters = exportTestJobParameters;
    }

    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public Long getConnectorId() {
        return connectorId;
    }
}
