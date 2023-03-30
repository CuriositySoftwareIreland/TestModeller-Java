package ie.curiositysoftware.modellerstorage.dto;

public class ModellerFolder {
    private Long id;

    private String folderName;

    private String folderPath;

    private String displayName;

    private Long parentFolder;

    private Long release;

    public void setParentFolder(Long parentFolder) {
        this.parentFolder = parentFolder;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getParentFolder() {
        return parentFolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelease() {
        return release;
    }

    public void setRelease(Long release) {
        this.release = release;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
