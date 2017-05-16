package com.rich.bryan.services.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Sort {
    DATE_CREATED_DESC(1, "Date Added: New to Old"),
    DATE_CREATED(2, "Date Added: Old to New"),
    TITLE(3, "Title: A to Z"),
    TITLE_DESC(4, "Title: Z to A"),
    LAST_NAME(5, "Author: A to Z"),
    LAST_NAME_DESC(6, "Author: Z to A"),
    DATE_STARTED_DESC(7, "Date Started: New to Old"),
    DATE_STARTED(8, "Date Started: Old to New"),
    DATE_FINISHED_DESC(9, "Date Finished: New to Old"),
    DATE_FINISHED(10, "Date Finished: Old to New"),
    PAGE_COUNT(11, "Page Count: Low to High"),
    PAGE_COUNT_DESC(12, "Page Count: High to Low"),
    CURRENT_PAGE(13, "Current Page: Low to High"),
    CURRENT_PAGE_DESC(14, "Current Page: High to Low"),
    PROGRESS(15, "Progress");


    private int sortNum;
    private String name;

    private static Map<Integer, Sort> map = new HashMap<>();

    static {
        Arrays.stream(Sort.values()).forEach(a -> map.put(a.sortNum, a));
    }

    Sort(final int sortNum, final String name) {
        this.sortNum = sortNum;
        this.name = name;
    }

    public static Sort valueOf(int sortNum) {
        return map.get(sortNum);
    }

    @Override
    public String toString() {
        return name;
    }
}
