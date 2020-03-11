package ie.curiositysoftware.allocation.engine;

public class DataAllocationCriteria {
    private String parameterName;

    private String parameterValue;

    public DataAllocationCriteria(String name, String value)
    {
        parameterName = name;

        parameterValue = value;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
