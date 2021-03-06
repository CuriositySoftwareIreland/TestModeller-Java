package ie.curiositysoftware.allocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DataCatalogueKey {
    private Long id;

    private Long testCriteriaId;

    private String name;

    private Integer index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public Long getTestCriteriaId() {
        return testCriteriaId;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setTestCriteriaId(Long testCriteriaId) {
        this.testCriteriaId = testCriteriaId;
    }
}
