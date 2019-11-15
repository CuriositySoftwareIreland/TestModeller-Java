package ie.curiositysoftware.jobengine.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UploadFileResponse {
    private String fileName;

    private String downloadUri;

    private String fileType;

    private Long fileSize;

    public UploadFileResponse()
    {

    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public Long getFileSize() {
        return fileSize;
    }
}
