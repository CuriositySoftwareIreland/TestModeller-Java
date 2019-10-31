package ie.curiositysoftware.JobEngine.Entities.Job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class VIPAutomationExecutionJobEntity {
    private Long id;

    private Long testSuiteId;

    private Long serverProfileId;

    private String machineKey;

    private String automationType;

    private String runId;

    private List<AutomationExecutionParameter> automationParameters;

    private ServerProcessScopeEnum scope;

    private Boolean sharedJobServer;

    public VIPAutomationExecutionJobEntity()
    {
        automationParameters = new ArrayList<AutomationExecutionParameter>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestSuiteId() {
        return testSuiteId;
    }

    public void setTestSuiteId(Long testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public void setSharedJobServer(Boolean sharedJobServer) {
        this.sharedJobServer = sharedJobServer;
    }

    public Boolean getSharedJobServer() {
        return sharedJobServer;
    }

    public ServerProcessScopeEnum getScope() {
        return scope;
    }

    public void setScope(ServerProcessScopeEnum scope) {
        this.scope = scope;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public void setAutomationParameters(List<AutomationExecutionParameter> automationParameters) {
        this.automationParameters = automationParameters;
    }

    public void setServerProfileId(Long serverProfileId) {
        this.serverProfileId = serverProfileId;
    }

    public void setAutomationType(String automationType) {
        this.automationType = automationType;
    }

    public String getAutomationType() {
        return automationType;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getRunId() {
        return runId;
    }

    public List<AutomationExecutionParameter> getAutomationParameters() {
        return automationParameters;
    }

    public Long getServerProfileId() {
        return serverProfileId;
    }
}
