package ie.curiositysoftware.datacatalogue;

import java.util.List;

public class DataListRowDto
{
    private Long id;
    private Integer index;
    private Long listId;
    private List<DataListItemDto> items;

    public DataListRowDto() {
    }

    public Long getId() {
        return this.id;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Long getListId() {
        return this.listId;
    }

    public List<DataListItemDto> getItems() {
        return this.items;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }

    public void setListId(final Long listId) {
        this.listId = listId;
    }

    public void setItems(final List<DataListItemDto> items) {
        this.items = items;
    }
}
