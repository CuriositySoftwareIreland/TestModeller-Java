package ie.curiositysoftware.jobengine.services;

public class ResourceIdentifier {
    private Long id;

    public ResourceIdentifier() {

    }

    public ResourceIdentifier(long id)
    {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
