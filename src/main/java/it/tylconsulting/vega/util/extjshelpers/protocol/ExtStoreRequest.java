package it.tylconsulting.vega.util.extjshelpers.protocol;

import it.tylconsulting.vega.util.extjshelpers.filters.FilterParam;
import it.tylconsulting.vega.util.extjshelpers.sort.SortInfo;

import java.util.List;

public class ExtStoreRequest {
    private Integer _dc;
    private Integer page;
    private Integer start;
    private Integer limit;

    private List<SortInfo> sort;
    private List<FilterParam> filter;

    public Integer get_dc() {
        return _dc;
    }

    public void set_dc(Integer _dc) {
        this._dc = _dc;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<SortInfo> getSort() {
        return sort;
    }

    public void setSort(List<SortInfo> sort) {
        this.sort = sort;
    }

    public List<FilterParam> getFilter() {
        return filter;
    }

    public void setFilter(List<FilterParam> filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "ExtStoreRequest{" +
            "_dc=" + _dc +
            ", page=" + page +
            ", start=" + start +
            ", limit=" + limit +
            ", sort=" + sort +
            ", filter=" + filter +
            '}';
    }
}
