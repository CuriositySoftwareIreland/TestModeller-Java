package ie.curiositysoftware.allocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class AllocatedTestParameter {
    private Long id;

    private Long testId;

    private Long criteriaParameterId;

    private String value;

    private String criteriaParameterName;

    public AllocatedTestParameter()
    {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getCriteriaParameterId() {
        return criteriaParameterId;
    }

    public Long getTestId() {
        return testId;
    }

    public String getValue() {
        return value;
    }

    public void setCriteriaParameterId(Long criteriaParameterId) {
        this.criteriaParameterId = criteriaParameterId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCriteriaParameterName() {
        return criteriaParameterName;
    }

    public void setCriteriaParameterName(String criteriaParameterName) {
        this.criteriaParameterName = criteriaParameterName;
    }
}
