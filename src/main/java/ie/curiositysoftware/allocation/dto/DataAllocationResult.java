package ie.curiositysoftware.allocation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<Object> getValuesByColumn(String colName)
    {
        List<Object> objects = new ArrayList<Object>();

        for (DataAllocationRow row : DataRows) {
            objects.add(row.get(colName));
        }

        return objects;
    }

    public Object getValueByColumn(String colName)
    {
        return DataRows.get(0).get(colName);
    }

    public Object getValueByColumnIndex(int index)
    {
        return (new ArrayList<Object>(DataRows.get(0).values())).get(index);
    }

    public Object getValueByRowAndColumnIndex(int row, int index)
    {
        return (new ArrayList<Object>(DataRows.get(row).values())).get(index);
    }

    public Object getValueByRowAndColumnName(int row, String colName)
    {
        return DataRows.get(row).get(colName);
    }

    @Override
    public String toString() {
        if (getDataRows().isEmpty()) {
            return "No data rows present";
        }

        // Headers
        Set<String> headers = getDataRows().get(0).keySet();
        String returnStr = (String.join(", ", headers)) + "\r\n";

        for (DataAllocationRow row : getDataRows()) {
            ArrayList<String> values = new ArrayList<String>();

            for (String header : headers) {
                values.add(row.get(header).toString());
            }

            returnStr = returnStr + (String.join(", ", values)) + "\r\n";
        }

        return returnStr;
    }
}
