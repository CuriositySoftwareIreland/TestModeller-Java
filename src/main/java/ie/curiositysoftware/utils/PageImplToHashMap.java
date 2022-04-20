package ie.curiositysoftware.utils;

import ie.curiositysoftware.datacatalogue.DataListItemDto;
import ie.curiositysoftware.datacatalogue.DataListRowDto;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PageImplToHashMap {

    public PageImplToHashMap()
    {

    }

    public List<HashMap<String, String>> convert(PageImpl<DataListRowDto> pageItems)
    {
        ArrayList<HashMap<String, String>> dataItems = new ArrayList<>();

        if (pageItems.isEmpty())
        {
            return dataItems;
        }

        for (DataListRowDto rowDto : pageItems.toList())
        {
            HashMap<String, String> items = new HashMap<>();

            for (DataListItemDto rowItem : rowDto.getItems())
            {
                if (!items.containsKey(rowItem.getColumnName())) {
                    items.put(rowItem.getColumnName(), rowItem.getValue());
                }
            }

            dataItems.add(items);
        }

        return  dataItems;
    }
}
