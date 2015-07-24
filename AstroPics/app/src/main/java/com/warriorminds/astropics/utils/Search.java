package com.warriorminds.astropics.utils;

/**
 * Created by rodrigo.guerrero on 6/29/2015.
 */
public class Search {
    private int limit;
    private int offset;
    private String searchTerm;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
