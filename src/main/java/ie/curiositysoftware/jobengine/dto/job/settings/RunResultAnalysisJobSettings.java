package ie.curiositysoftware.jobengine.dto.job.settings;

import ie.curiositysoftware.jobengine.dto.job.TestCoverageEnum;

public class RunResultAnalysisJobSettings {

    private Long id;
    private Long modelId;
    private Long profileId;
    private Long modelVersionId;
    private String newProfileName;
    private Boolean includeOldTests;
    private TestCoverageEnum targetCoverage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getModelVersionId() {
        return modelVersionId;
    }

    public void setModelVersionId(Long modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public String getNewProfileName() {
        return newProfileName;
    }

    public void setNewProfileName(String newProfileName) {
        this.newProfileName = newProfileName;
    }

    public Boolean getIncludeOldTests() {
        return includeOldTests;
    }

    public void setIncludeOldTests(Boolean includeOldTests) {
        this.includeOldTests = includeOldTests;
    }

    public TestCoverageEnum getTargetCoverage() {
        return targetCoverage;
    }

    public void setTargetCoverage(TestCoverageEnum targetCoverage) {
        this.targetCoverage = targetCoverage;
    }
}
