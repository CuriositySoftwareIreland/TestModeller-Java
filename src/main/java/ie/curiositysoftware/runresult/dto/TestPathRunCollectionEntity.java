package ie.curiositysoftware.runresult.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TestPathRunCollectionEntity {
    private String guid;

    private String fileName;

    private byte[] fileByte;

    private String name;

    private Date created;

    public TestPathRunCollectionEntity()
    {

    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public String getGuid() {
        return guid;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreated() {
        return created;
    }
}
