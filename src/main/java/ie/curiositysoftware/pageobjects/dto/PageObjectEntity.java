package ie.curiositysoftware.pageobjects.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class PageObjectEntity
{
    private long id;

    private String name;

    private String shadowRoot;

    private String docName;

    private String docDescription;

    private PageObjectTypeEnum  objectType;

    private String dataType;

    private String iframeXPath;

    private String dataAttributes;

    private String customObjectType;

    private List<PageObjectParameterEntity> parameters ;

    private List<PageObjectHistoryEntity> pageObjectHistory;

    private Long parent;

    private List<PageObjectTypeEntity> pageObjectTypes;

    public void setShadowRoot(String shadowRoot) {
        this.shadowRoot = shadowRoot;
    }

    public String getShadowRoot() {
        return shadowRoot;
    }

    public void setIframeXPath(String iframeXPath) {
        this.iframeXPath = iframeXPath;
    }

    public String getIframeXPath() {
        return iframeXPath;
    }

    public void setPageObjectTypes(List<PageObjectTypeEntity> pageObjectTypes) {
        this.pageObjectTypes = pageObjectTypes;
    }

    public String getCustomObjectType() {
        return customObjectType;
    }

    public void setCustomObjectType(String customObjectType) {
        this.customObjectType = customObjectType;
    }

    public List<PageObjectTypeEntity> getPageObjectTypes() {
        return pageObjectTypes;
    }

    public PageObjectEntity()
    {
        parameters = new ArrayList<PageObjectParameterEntity>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataAttributes(String dataAttributes) {
        this.dataAttributes = dataAttributes;
    }

    public String getDataAttributes() {
        return dataAttributes;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setObjectType(PageObjectTypeEnum objectType) {
        this.objectType = objectType;
    }

    public PageObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setParameters(List<PageObjectParameterEntity> parameters) {
        this.parameters = parameters;
    }

    public List<PageObjectParameterEntity> getParameters() {
        return parameters;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getParent() {
        return parent;
    }

    public void setPageObjectHistory(List<PageObjectHistoryEntity> pageObjectHistory) {
        this.pageObjectHistory = pageObjectHistory;
    }

    public List<PageObjectHistoryEntity> getPageObjectHistory() {
        return pageObjectHistory;
    }
}
