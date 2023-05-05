package ru.vlsu.ispi.beans.extrabeans;

public class WholeFilterSet {

    private String RowToFind;

    public String getRowToFind() {
        return RowToFind;
    }

    public void setRowToFind(String rowToFind) {
        RowToFind = rowToFind;
    }

    private String Filter;

    public String getFilter() {
        return Filter;
    }

    public void setFilter(String filter) {
        Filter = filter;
    }

    private String FilterByType;

    public String getFilterByType(){
        return FilterByType;
    }

    public void setFilterByType(String filterByType){
        FilterByType = filterByType;
    }

    private String FilterByStatus;

    public String getFilterByStatus(){
        return FilterByStatus;
    }

    public void setFilterByStatus(String filterByStatus){
        FilterByStatus = filterByStatus;
    }

    private String FilterByLikedStatus;

    public String getFilterByLikedStatus(){
        return FilterByLikedStatus;
    }

    public void setFilterByLikedStatus(String filterByLikedStatus){
        FilterByLikedStatus = filterByLikedStatus;
    }

    private String FilterByViewedStatus;

    public String getFilterByViewedStatus(){
        return FilterByViewedStatus;
    }

    public void setFilterByViewedStatus(String filterByViewedStatus){
        FilterByViewedStatus = filterByViewedStatus;
    }

    private String Sorter;

    public String getSorter() {
        return Sorter;
    }

    public void setSorter(String sorter) {
        Sorter = sorter;
    }

    public WholeFilterSet(String RowToFind, String Filter, String Sorter){
        this.RowToFind = RowToFind;
        this.Filter = Filter;
        this.Sorter = Sorter;
    }

    public WholeFilterSet(){
        this.RowToFind = "empty";
        this.Filter = "default_filter";
        this.Sorter = "default_sort";
    }

    public WholeFilterSet(String RowToFind){
        this.RowToFind = RowToFind;
        this.Filter = "default_filter";
        this.Sorter = "default_sort";
    }
}
