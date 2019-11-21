package ie.curiositysoftware.jobengine.dto.job;

public class RunResultAnalysisJobResult {

    private Long newProfileId;
    private Long newSuiteId;

    public Long getNewProfileId() {
        return newProfileId;
    }

    public void setNewProfileId(Long newProfileId) {
        this.newProfileId = newProfileId;
    }

    public Long getNewSuiteId() {
        return newSuiteId;
    }

    public void setNewSuiteId(Long newSuiteId) {
        this.newSuiteId = newSuiteId;
    }
}
