package com.rich.bryan.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private Integer id;
    private Integer version;
    private String title;
    private Integer pageCount;
    private String publisher;
    private String datePublished;
    private String description;
    private String isbn13;
    private String isbn10;
    private String imageUrl;
    private String author;
    private List<DtoAuthor> dtoAuthorList = new ArrayList<>();

    public SearchResult() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor() {
        if (dtoAuthorList.size() > 1) {
            StringBuilder sb = new StringBuilder();
            for (DtoAuthor a : dtoAuthorList) {
                sb.append(a.getName()).append(", ");
            }
            this.author = sb.substring(0, sb.lastIndexOf(", "));
        } else if (dtoAuthorList.size() == 1) {
            this.author = dtoAuthorList.get(0).getName();
        } else {
            this.author = null;
        }
    }

    public List<DtoAuthor> getDtoAuthorList() {
        return dtoAuthorList;
    }

    public void setDtoAuthorList(List<DtoAuthor> dtoAuthorList) {
        this.dtoAuthorList = dtoAuthorList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "id=" + id +
                ", version=" + version +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", publisher='" + publisher + '\'' +
                ", datePublished='" + datePublished + '\'' +
                ", description='" + description + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dtoAuthorList=" + dtoAuthorList +
                '}';
    }
}
