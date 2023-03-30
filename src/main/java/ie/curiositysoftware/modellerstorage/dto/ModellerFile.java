package ie.curiositysoftware.modellerstorage.dto;

public class ModellerFile {
    public enum FileTypeEnum {
        Model,
        PageObject,
        PageModule,
        TestInsightsPool,
        GlobalVariables,
        RawFile,
        DataActivity;
    }

    public enum RawFileTypeEnum {
        Basic,
        DataSheet,
        Config;
    }

    protected Long id;

    protected String fileName;

    protected String displayName;

    protected FileTypeEnum fileType;

    protected RawFileTypeEnum rawFileType;

    protected Long fileResourceId;

    protected String assigneeUserId;

    protected String state;

    protected String priority;

    protected Integer complexity;

    protected Long parentFolder;

    protected Long release;

    public void setRelease(Long release) {
        this.release = release;
    }

    public Long getRelease() {
        return release;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public FileTypeEnum getFileType() {
        return fileType;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public Long getFileResourceId() {
        return fileResourceId;
    }

    public Long getParentFolder() {
        return parentFolder;
    }

    public RawFileTypeEnum getRawFileType() {
        return rawFileType;
    }

    public String getAssigneeUserId() {
        return assigneeUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPriority() {
        return priority;
    }

    public String getState() {
        return state;
    }

    public void setAssigneeUserId(String assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setFileResourceId(Long fileResourceId) {
        this.fileResourceId = fileResourceId;
    }

    public void setFileType(FileTypeEnum fileType) {
        this.fileType = fileType;
    }

    public void setParentFolder(Long parentFolder) {
        this.parentFolder = parentFolder;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setRawFileType(RawFileTypeEnum rawFileType) {
        this.rawFileType = rawFileType;
    }

    public void setState(String state) {
        this.state = state;
    }
}
