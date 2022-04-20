package ie.curiositysoftware.datacatalogue;

public class DataListItemDto
{
    private Long id;
    private String value;
    private Long columnId;
    private String columnName;
    private Long rowId;

    public DataListItemDto() {
    }

    public Long getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public Long getColumnId() {
        return this.columnId;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public Long getRowId() {
        return this.rowId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setColumnId(final Long columnId) {
        this.columnId = columnId;
    }

    public void setColumnName(final String columnName) {
        this.columnName = columnName;
    }

    public void setRowId(final Long rowId) {
        this.rowId = rowId;
    }
}
