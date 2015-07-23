package com.warriorminds.astropics.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public class Meta {
    private int limit;
    private String next;
    private int offset;
    private String previous;
    @SerializedName("total_count")
    private int totalResults;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
