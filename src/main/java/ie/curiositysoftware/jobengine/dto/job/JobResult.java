package ie.curiositysoftware.jobengine.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class JobResult {
    private Long id;

    private byte[] resultObject;

    private String fileName;

    private String resultServerLocation;

    private Long job;

    private String resultValue;

    public JobResult()
    {

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

    public byte[] getResultObject() {
        return resultObject;
    }

    public void setResultObject(byte[] resultObject) {
        this.resultObject = resultObject;
    }

    public void setResultServerLocation(String resultServerLocation) {
        this.resultServerLocation = resultServerLocation;
    }

    public String getResultServerLocation() {
        return resultServerLocation;
    }

    public void setJob(Long job) {
        this.job = job;
    }

    public Long getJob() {
        return job;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }
}
