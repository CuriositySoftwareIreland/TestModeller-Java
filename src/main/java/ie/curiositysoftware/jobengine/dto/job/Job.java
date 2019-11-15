package ie.curiositysoftware.jobengine.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ie.curiositysoftware.jobengine.dto.job.settings.*;

import java.util.Date;

@JsonIgnoreProperties
public class Job {
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

    private TestGenerationJobSettings generationJobSettings;

    private AutomationGenerationJobSettings automationJobSettings;

    private DataGenerationJobSettings dataJobSettings;

    private ExportTestsJobSettings exportTestsJobSettings;

    private VIPAutomationExecutionJobSettings vipAutomationJobSettings;

    public Job()
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

    public void setGenerationJobSettings(TestGenerationJobSettings generationJobSettings) {
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

    public VIPAutomationExecutionJobSettings getVipAutomationJobSettings() {
        return vipAutomationJobSettings;
    }

    public void setLocalJob(Boolean localJob) {
        this.localJob = localJob;
    }

    public void setVipAutomationJobSettings(VIPAutomationExecutionJobSettings vipAutomationJobSettings) {
        this.vipAutomationJobSettings = vipAutomationJobSettings;
    }

    public void setExportTestsJobSettings(ExportTestsJobSettings exportTestsJobSettings) {
        this.exportTestsJobSettings = exportTestsJobSettings;
    }

    public ExportTestsJobSettings getExportTestsJobSettings() {
        return exportTestsJobSettings;
    }

    public void setDataJobSettings(DataGenerationJobSettings dataJobSettings) {
        this.dataJobSettings = dataJobSettings;
    }

    public DataGenerationJobSettings getDataJobSettings() {
        return dataJobSettings;
    }

    public void setAutomationJobSettings(AutomationGenerationJobSettings automationJobSettings) {
        this.automationJobSettings = automationJobSettings;
    }

    public AutomationGenerationJobSettings getAutomationJobSettings() {
        return automationJobSettings;
    }

    public TestGenerationJobSettings getGenerationJobSettings() {
        return generationJobSettings;
    }
}
