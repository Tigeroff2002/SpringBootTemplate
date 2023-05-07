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

    private String type;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    private String status;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    private String liked;

    public String getLiked(){
        return liked;
    }

    public void setLiked(String liked){
        this.liked = liked;
    }

    private String unliked;

    public String getUnliked(){
        return unliked;
    }

    public void setUnliked(String unliked){
        this.unliked = unliked;
    }

    private String viewed;

    public String getViewed(){
        return viewed;
    }

    public void setViewed(String viewed){
        this.viewed = viewed;
    }

    private String Sorter;

    public String getSorter() {
        return Sorter;
    }

    public void setSorter(String sorter) {
        Sorter = sorter;
    }

    private boolean isAuthUser;

    public boolean isAuthUser() {
        return isAuthUser;
    }

    public WholeFilterSet(String RowToFind, String Filter, String Sorter){
        this.RowToFind = RowToFind;
        this.Filter = Filter;
        this.Sorter = Sorter;
    }

    public WholeFilterSet(String RowToFind, String type, String status, String Sorter){
        this.RowToFind = RowToFind;
        this.type = type;
        this.status = status;
        this.Sorter = Sorter;
        this.isAuthUser = false;
    }

    public WholeFilterSet(String RowToFind, String type, String status, String liked, String viewed, String Sorter){
        this.RowToFind = RowToFind;
        this.type = type;
        this.status = status;
        this.liked = liked;
        this.viewed = viewed;
        this.Sorter = Sorter;
        this.isAuthUser = true;
    }

    public WholeFilterSet(){
        this.RowToFind = "empty";
        this.Filter = "default_filter";
        this.Sorter = "default_sort";
        this.type = "All";
        this.status = "All";
        this.liked = "All";
        this.viewed = "All";
    }

    public WholeFilterSet(String RowToFind){
        this.RowToFind = RowToFind;
        this.Filter = "default_filter";
        this.Sorter = "default_sort";
        this.type = "All";
        this.status = "All";
        this.liked = "All";
        this.viewed = "All";
    }
}
