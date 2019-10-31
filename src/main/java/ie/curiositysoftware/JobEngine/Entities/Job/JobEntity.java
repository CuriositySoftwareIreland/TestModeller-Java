package ie.curiositysoftware.JobEngine.Entities.Job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties
public class JobEntity {
    private Long id;

    private JobState jobState;

    private JobType jobType;

    private Date createdDate;

    private Date lastUpdate;

    private Boolean cancel;

    private Boolean expectResult;

    private String progressMessage;

    private String createdUser;

    private String tenant;

    private String APIKey;

    private Boolean localJob;

    private TestGenerationJobEntity generationJobSettings;

    private AutomationGenerationJobEntity automationJobSettings;

    private DataGenerationJobEntity dataJobSettings;

    private ExportTestsJobEntity exportTestsJobSettings;

    private VIPAutomationExecutionJobEntity vipAutomationJobSettings;

    public JobEntity()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setExpectResult(Boolean expectResult) {
        this.expectResult = expectResult;
    }

    public Boolean getExpectResult() {
        return expectResult;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }

    public Boolean getLocalJob() {
        return localJob;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getProgressMessage() {
        return progressMessage;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setGenerationJobSettings(TestGenerationJobEntity generationJobSettings) {
        this.generationJobSettings = generationJobSettings;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobState getJobState() {
        return jobState;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public VIPAutomationExecutionJobEntity getVipAutomationJobSettings() {
        return vipAutomationJobSettings;
    }

    public void setLocalJob(Boolean localJob) {
        this.localJob = localJob;
    }

    public void setVipAutomationJobSettings(VIPAutomationExecutionJobEntity vipAutomationJobSettings) {
        this.vipAutomationJobSettings = vipAutomationJobSettings;
    }

    public void setExportTestsJobSettings(ExportTestsJobEntity exportTestsJobSettings) {
        this.exportTestsJobSettings = exportTestsJobSettings;
    }

    public ExportTestsJobEntity getExportTestsJobSettings() {
        return exportTestsJobSettings;
    }

    public void setDataJobSettings(DataGenerationJobEntity dataJobSettings) {
        this.dataJobSettings = dataJobSettings;
    }

    public DataGenerationJobEntity getDataJobSettings() {
        return dataJobSettings;
    }

    public void setAutomationJobSettings(AutomationGenerationJobEntity automationJobSettings) {
        this.automationJobSettings = automationJobSettings;
    }

    public AutomationGenerationJobEntity getAutomationJobSettings() {
        return automationJobSettings;
    }

    public TestGenerationJobEntity getGenerationJobSettings() {
        return generationJobSettings;
    }
}
