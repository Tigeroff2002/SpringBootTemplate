package ru.vlsu.ispi.beans.extrabeans;

public class Pagination {

    private int currentPageNumber;

    public int getCurrentPageNumber(){
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber){
        this.currentPageNumber = currentPageNumber;
    }

    private int lastPageNumber;

    public int getLastPageNumber(){
        return lastPageNumber;
    }

    public void setLastPageNumber(int lastPageNumber){
        this.lastPageNumber = lastPageNumber;
    }

    private int elementsPerPage;

    public int getElementsPerPage(){
        return elementsPerPage;
    }

    public void setElementsPerPage(int elementsPerPage){
        this.elementsPerPage = elementsPerPage;
    }

    private final int ELEMENTS_PER_PAGE_DEFAULT = 5;

    public Pagination(){
        this.currentPageNumber = 1;
        this.lastPageNumber = currentPageNumber;
        this.elementsPerPage = ELEMENTS_PER_PAGE_DEFAULT;
    }
}
