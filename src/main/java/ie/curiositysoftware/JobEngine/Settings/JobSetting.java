package ie.curiositysoftware.JobEngine.Settings;

import java.util.ArrayList;
import java.util.List;

public class JobSetting {
    private String urlField;

    private Long maximumtimeField;

    private String apikeyField;

    private String machinekeyField;

    private Long severprofileidField;

    private String automationtypeField;

    private Long testsuiteidField;

    private List<JobSettingParameter> parametersField;

    public JobSetting()
    {
        parametersField = new ArrayList<JobSettingParameter>();
    }

    public Long getMaximumtimeField() {
        return maximumtimeField;
    }

    public List<JobSettingParameter> getParametersField() {
        return parametersField;
    }

    public Long getSeverprofileidField() {
        return severprofileidField;
    }

    public String getApikeyField() {
        return apikeyField;
    }

    public String getAutomationtypeField() {
        return automationtypeField;
    }

    public String getMachinekeyField() {
        return machinekeyField;
    }

    public String getUrlField() {
        return urlField;
    }

    public void setApikeyField(String apikeyField) {
        this.apikeyField = apikeyField;
    }

    public void setAutomationtypeField(String automationtypeField) {
        this.automationtypeField = automationtypeField;
    }

    public void setMachinekeyField(String machinekeyField) {
        this.machinekeyField = machinekeyField;
    }

    public void setMaximumtimeField(Long maximumtimeField) {
        this.maximumtimeField = maximumtimeField;
    }

    public void setParametersField(List<JobSettingParameter> parametersField) {
        this.parametersField = parametersField;
    }

    public void setSeverprofileidField(Long severprofileidField) {
        this.severprofileidField = severprofileidField;
    }

    public void setUrlField(String urlField) {
        this.urlField = urlField;
    }

    public Long getTestsuiteidField() {
        return testsuiteidField;
    }

    public void setTestsuiteidField(Long testsuiteidField) {
        this.testsuiteidField = testsuiteidField;
    }
}
