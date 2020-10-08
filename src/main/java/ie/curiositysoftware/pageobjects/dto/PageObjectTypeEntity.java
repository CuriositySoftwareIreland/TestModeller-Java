package ie.curiositysoftware.pageobjects.dto;

public class PageObjectTypeEntity {
    private long id;

    private PageObjectTypeEnum objectType;

    private String customObjectType;

    private Long pageObject;

    public void setCustomObjectType(String customObjectType) {
        this.customObjectType = customObjectType;
    }

    public String getCustomObjectType() {
        return customObjectType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPageObject(Long pageObject) {
        this.pageObject = pageObject;
    }

    public Long getPageObject() {
        return pageObject;
    }

    public PageObjectTypeEnum getObjectType() {
        return objectType;
    }

    public void setObjectType(PageObjectTypeEnum objectType) {
        this.objectType = objectType;
    }
}
