package ie.curiositysoftware.allocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class AllocatedTest {

    private List<AllocatedTestKey> keys;

    private List<AllocatedTestParameter> parameters;

    private Long id;

    private Long poolId;

    private Boolean active;
    private Boolean prepEnvironment;
    private String suiteName;
    private String name;
    private Boolean uniqueFind;
    private Integer howMany;
    private String sourceDatabase;
    private String sourceSchema;
    private String tableName;

    private Long testCriteriaIdCatalogueId;
    private Long testCriteriaId;


    public AllocatedTest()
    {

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

    public Long getTestCriteriaId() {
        return testCriteriaId;
    }

    public void setTestCriteriaId(Long testCriteriaId) {
        this.testCriteriaId = testCriteriaId;
    }

    public void setKeys(List<AllocatedTestKey> keys) {
        this.keys = keys;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTableName() {
        return tableName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public Boolean getPrepEnvironment() {
        return prepEnvironment;
    }

    public Boolean getUniqueFind() {
        return uniqueFind;
    }

    public Integer getHowMany() {
        return howMany;
    }

    public List<AllocatedTestKey> getKeys() {
        return keys;
    }

    public List<AllocatedTestParameter> getParameters() {
        return parameters;
    }

    public Long getPoolId() {
        return poolId;
    }

    public Long getTestCriteriaIdCatalogueId() {
        return testCriteriaIdCatalogueId;
    }

    public String getSourceDatabase() {
        return sourceDatabase;
    }

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setHowMany(Integer howMany) {
        this.howMany = howMany;
    }

    public void setParameters(List<AllocatedTestParameter> parameters) {
        this.parameters = parameters;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public void setPrepEnvironment(Boolean prepEnvironment) {
        this.prepEnvironment = prepEnvironment;
    }

    public void setSourceDatabase(String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    public void setTestCriteriaIdCatalogueId(Long testCriteriaIdCatalogueId) {
        this.testCriteriaIdCatalogueId = testCriteriaIdCatalogueId;
    }

    public void setUniqueFind(Boolean uniqueFind) {
        this.uniqueFind = uniqueFind;
    }
}
