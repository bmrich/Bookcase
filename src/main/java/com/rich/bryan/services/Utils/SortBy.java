package com.rich.bryan.services.Utils;

import java.util.HashMap;
import java.util.Map;

public enum SortBy {
    AUTHOR_ASC(0),
    AUTHOR_DESC(1),
    PAGE_COUNT_ASC(2),
    PAGE_COUNT_DESC(3),
    DATE_ADDED_ASC(4),
    DATE_ADDED_DESC(5),
    TITLE_ASC(6),
    TITLE_DESC(7);

    private int sortNum;

    private static Map<Integer, SortBy> map = new HashMap<>();

    static {
        for (SortBy sortBy : SortBy.values()) {
            map.put(sortBy.sortNum, sortBy);
        }
    }

    SortBy(final int sortNum) {
        this.sortNum = sortNum;
    }

    public static SortBy valueOf(int sortNum) {
        return map.get(sortNum);
    }
}
