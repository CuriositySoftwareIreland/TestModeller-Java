package ie.curiositysoftware.pageobjects.Identifier;

import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.pageobjects.dto.PageObjectEntity;
import ie.curiositysoftware.pageobjects.services.PageObjectService;

public class ModellerObjectIdentifier {
    private Integer pageObjectId;

    private String pageObjectName;

    private PageObjectEntity pageObjectEntity;

    public ModellerObjectIdentifier(String pageObjectName)
    {
        this.pageObjectName = pageObjectName;

        this.pageObjectId = null;

        this.pageObjectEntity = null;
    }

    public ModellerObjectIdentifier(int pageObjectId)
    {
        this.pageObjectId = pageObjectId;

        this.pageObjectEntity = null;
    }

    public int getPageObjectId() {
        return pageObjectId;
    }

    public void setPageObjectId(int pageObjectId) {
        this.pageObjectId = pageObjectId;
    }

    public PageObjectEntity getPageObjectEntity(ConnectionProfile conProfile) {
        if (pageObjectEntity == null) {
            retrieveAndAssignPageObjectEntity(conProfile);
        }

        return pageObjectEntity;
    }

    public void setPageObjectEntity(PageObjectEntity pageObjectEntity) {
        this.pageObjectEntity = pageObjectEntity;
    }

    private void retrieveAndAssignPageObjectEntity(ConnectionProfile conProfile)
    {
        PageObjectService poService = new PageObjectService(conProfile);

        if (this.pageObjectId != null) {
            this.pageObjectEntity = poService.GetPageObject(this.pageObjectId);
        } else {
            this.pageObjectEntity = poService.GetPageObject(this.pageObjectName);

            this.pageObjectId = (int) this.pageObjectEntity.getId();
        }
    }

    @Override
    public String toString() {
        return pageObjectName.toString();
    }
}
