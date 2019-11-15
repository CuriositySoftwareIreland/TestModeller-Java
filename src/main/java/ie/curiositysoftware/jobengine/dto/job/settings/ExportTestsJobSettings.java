package ie.curiositysoftware.jobengine.dto.job.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ie.curiositysoftware.jobengine.dto.job.ExportTestJobParameter;

import java.util.List;

@JsonIgnoreProperties
public class ExportTestsJobSettings {
    public Long id;

    public Long connectorId;

    public Long testSuiteId;

    public Long modelVersionId;

    public List<ExportTestJobParameter> exportTestJobParameters;

    public ExportTestsJobSettings()
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

    public List<ExportTestJobParameter> getExportTestJobParameters() {
        return exportTestJobParameters;
    }

    public void setExportTestJobParameters(List<ExportTestJobParameter> exportTestJobParameters) {
        this.exportTestJobParameters = exportTestJobParameters;
    }

    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public Long getConnectorId() {
        return connectorId;
    }
}
