package ie.curiositysoftware.JobEngine.Entities.Job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AutomationExecutionParameter {
    private Long id;

    private String var;

    private String value;

    private int paramIndex;

    public AutomationExecutionParameter()
    {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
