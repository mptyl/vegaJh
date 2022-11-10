package it.tylconsulting.vega.util.extjshelpers.sort;

import it.tylconsulting.vega.util.extjshelpers.sort.SortDirection;
import it.tylconsulting.vega.util.extjshelpers.sort.SortInfo;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SortAdapter {

    public static Sort createSort(List<SortInfo> sortInfos) {
        Sort sort = Sort.unsorted();
        boolean alreadyAssigned = false;
        if (sortInfos.size() == 0)
            return sort;
        else
            for (SortInfo si : sortInfos) {
                if (alreadyAssigned) {
                    sort = sort.and(setSort(sort, si));
                } else {
                    sort = setSort(sort, si);
                    alreadyAssigned = true;
                }
            }
        return sort;
    }

    private static Sort setSort(Sort sort, SortInfo si) {
        sort = Sort.by(si.getProperty());
        if (si.getDirection().equals(SortDirection.ASC))
            sort = sort.ascending();
        else
            sort = sort.descending();
        return sort;
    }
}
