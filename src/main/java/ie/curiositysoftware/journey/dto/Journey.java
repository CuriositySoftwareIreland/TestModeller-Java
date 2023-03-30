package ie.curiositysoftware.journey.dto;

public class Journey {
    private Long id;

    private String title;

    private String description;

    private String sourceLocation;

    private Integer fromId;

    private Integer toId;

    private String stateDescription;

    private Boolean executable;

    private Long journeyPool;

    private Long release;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getExecutable() {
        return executable;
    }

    public Integer getFromId() {
        return fromId;
    }

    public Long getJourneyPool() {
        return journeyPool;
    }

    public Integer getToId() {
        return toId;
    }

    public Long getRelease() {
        return release;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setExecutable(Boolean executable) {
        this.executable = executable;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public void setJourneyPool(Long journeyPool) {
        this.journeyPool = journeyPool;
    }

    public void setRelease(Long release) {
        this.release = release;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }
}
