package com.travel.domain;

import java.util.List;

public class PageBean <T> {
    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currentPage;
    private List<T> dataList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
    }

    public Integer getTotalPage() {
        return totalPage;
    }


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
