package it.tylconsulting.vega.util.extjshelpers.protocol;

import java.util.List;

public class ExtStoreListRequest<T> {
    List<T> records;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "ExtStoreListRequest{" +
            "records=" + records +
            '}';
    }
}
