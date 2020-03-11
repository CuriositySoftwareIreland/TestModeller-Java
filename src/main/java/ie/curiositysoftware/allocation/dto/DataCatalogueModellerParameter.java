package ie.curiositysoftware.allocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DataCatalogueModellerParameter {
    public enum ModuleParameterDirection {
        In,
        Out,
        InOut
    }

    private Long id;

    private Long testCriteriaId;

    private String name;

    private Integer index;

    private ModuleParameterDirection type;

    public void setTestCriteriaId(Long testCriteriaId) {
        this.testCriteriaId = testCriteriaId;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getTestCriteriaId() {
        return testCriteriaId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(ModuleParameterDirection type) {
        this.type = type;
    }

    public ModuleParameterDirection getType() {
        return type;
    }
}
