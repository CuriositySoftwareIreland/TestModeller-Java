package ie.curiositysoftware.pageobjects.dto;

public class PageObjectParameterEntity
{
    private Long id;

    private String name;

    private VipAutomationSelectorEnum paramType;

    private Boolean preferred;

    private String paramValue;

    private double confidence;

    private String objectName;

    private String moduleName;

    private Long pageObject;

    private Integer index;

    private Boolean shadowRequired;

    private PageObjectParameterStateEnum parameterState;

    public PageObjectParameterEntity()
    {

    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getConfidence() {
        return confidence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getObjectName() {
        return objectName;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public String getParamValue() {
        return paramValue;
    }

    public VipAutomationSelectorEnum getParamType() {
        return paramType;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setParamType(VipAutomationSelectorEnum paramType) {
        this.paramType = paramType;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public void setPageObject(Long pageObject) {
        this.pageObject = pageObject;
    }

    public Long getPageObject() {
        return pageObject;
    }

    public PageObjectParameterStateEnum getParameterState() {
        return parameterState;
    }

    public void setParameterState(PageObjectParameterStateEnum parameterState) {
        this.parameterState = parameterState;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public Boolean getShadowRequired() {
        return shadowRequired;
    }

    public void setShadowRequired(Boolean shadowRequired) {
        this.shadowRequired = shadowRequired;
    }
}
