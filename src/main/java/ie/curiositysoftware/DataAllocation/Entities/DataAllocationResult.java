package ie.curiositysoftware.DataAllocation.Entities;

import java.util.ArrayList;
import java.util.List;

public class DataAllocationResult {
    private List<DataAllocationRow> DataRows;

    public DataAllocationResult()
    {
        DataRows = new ArrayList<DataAllocationRow>();
    }

    public List<DataAllocationRow> getDataRows() {
        return DataRows;
    }

    public void setDataRows(List<DataAllocationRow> m_DataRows) {
        this.DataRows = m_DataRows;
    }

    public List<Object> GetValuesByColumn(String colName)
    {
        List<Object> objects = new ArrayList<Object>();

        for (DataAllocationRow row : DataRows) {
            objects.add(row.get(colName));
        }

        return objects;
    }

    public Object GetValueByColumn(String colName)
    {
        return DataRows.get(0).get(colName);
    }

    public Object GetValueByColumnIndex(int index)
    {
        return (new ArrayList<Object>(DataRows.get(0).values())).get(index);
    }
}
