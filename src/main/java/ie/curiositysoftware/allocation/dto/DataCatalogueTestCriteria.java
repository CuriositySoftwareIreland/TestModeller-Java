package ie.curiositysoftware.allocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties
public class DataCatalogueTestCriteria {
    private Long id;

    private Long catalogueId;

    private String catalogueName;

    private Boolean active;
    private String testType;
    private String testDescription;
    private String sqlCriteria;
    private String groupBy;
    private String orderBy;
    private String tableName;
    private String keyNamesSqlOverride;
    private String uniqueCheckSqlOverride;
    private Integer defaultHowMany;
    private Boolean defaultUnique;
    private String expectedResultsSql;
    private Boolean exposeAsModuleObject;

    private String dataIdentifier;
    private String sourceName;

    private Long connectionId;
    private Long processId;
    private Long makeCriteriaId;


    private Long moduleObjectId;

    private String connectionName;

    private String processName;

    private String makeCriteriaTestType;

    private String makeCriteriaExecutionType;

    private List<DataCatalogueKey> keys;

    private List<DataCatalogueModellerParameter> modellerParameters;

    public HashMap<String, DataCatalogueModellerParameter> getModellerParameterHash()
    {
        HashMap<String, DataCatalogueModellerParameter> paramHash = new HashMap<String, DataCatalogueModellerParameter>();

        for (DataCatalogueModellerParameter param : modellerParameters) {
            paramHash.put(param.getName(), param);
        }

        return paramHash;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    public void setCatalogueId(Long catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public Long getCatalogueId() {
        return catalogueId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getDefaultUnique() {
        return defaultUnique;
    }

    public Boolean getExposeAsModuleObject() {
        return exposeAsModuleObject;
    }

    public Integer getDefaultHowMany() {
        return defaultHowMany;
    }

    public String getDataIdentifier() {
        return dataIdentifier;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public String getExpectedResultsSql() {
        return expectedResultsSql;
    }

    public String getKeyNamesSqlOverride() {
        return keyNamesSqlOverride;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getSqlCriteria() {
        return sqlCriteria;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTestType() {
        return testType;
    }

    public String getUniqueCheckSqlOverride() {
        return uniqueCheckSqlOverride;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setDefaultHowMany(Integer defaultHowMany) {
        this.defaultHowMany = defaultHowMany;
    }

    public void setDataIdentifier(String dataIdentifier) {
        this.dataIdentifier = dataIdentifier;
    }

    public void setDefaultUnique(Boolean defaultUnique) {
        this.defaultUnique = defaultUnique;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    public void setExpectedResultsSql(String expectedResultsSql) {
        this.expectedResultsSql = expectedResultsSql;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public void setExposeAsModuleObject(Boolean exposeAsModuleObject) {
        this.exposeAsModuleObject = exposeAsModuleObject;
    }

    public void setKeyNamesSqlOverride(String keyNamesSqlOverride) {
        this.keyNamesSqlOverride = keyNamesSqlOverride;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setSqlCriteria(String sqlCriteria) {
        this.sqlCriteria = sqlCriteria;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public void setUniqueCheckSqlOverride(String uniqueCheckSqlOverride) {
        this.uniqueCheckSqlOverride = uniqueCheckSqlOverride;
    }

    public Long getMakeCriteriaId() {
        return makeCriteriaId;
    }

    public Long getModuleObjectId() {
        return moduleObjectId;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public String getMakeCriteriaExecutionType() {
        return makeCriteriaExecutionType;
    }

    public String getMakeCriteriaTestType() {
        return makeCriteriaTestType;
    }

    public String getProcessName() {
        return processName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public void setMakeCriteriaExecutionType(String makeCriteriaExecutionType) {
        this.makeCriteriaExecutionType = makeCriteriaExecutionType;
    }

    public void setMakeCriteriaId(Long makeCriteriaId) {
        this.makeCriteriaId = makeCriteriaId;
    }

    public void setMakeCriteriaTestType(String makeCriteriaTestType) {
        this.makeCriteriaTestType = makeCriteriaTestType;
    }

    public void setModuleObjectId(Long moduleObjectId) {
        this.moduleObjectId = moduleObjectId;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<DataCatalogueKey> getKeys() {
        return keys;
    }

    public List<DataCatalogueModellerParameter> getModellerParameters() {
        return modellerParameters;
    }

    public void setKeys(List<DataCatalogueKey> keys) {
        this.keys = keys;
    }

    public void setModellerParameters(List<DataCatalogueModellerParameter> modellerParameters) {
        this.modellerParameters = modellerParameters;
    }
}
