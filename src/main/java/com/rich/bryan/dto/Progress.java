package com.rich.bryan.dto;

public class Progress {
    private Integer pageCount;
    private Integer currentPage;
    private String title;

    public Progress(Integer pageCount, Integer currentPage, String title) {
        this.pageCount = pageCount;
        this.currentPage = currentPage;
        this.title = title;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
