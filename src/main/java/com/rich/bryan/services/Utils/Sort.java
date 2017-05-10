package com.rich.bryan.services.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Sort {
    DATE_CREATED_DESC(1),
    DATE_CREATED(2),
    TITLE(3),
    TITLE_DESC(4),
    LAST_NAME(5),
    LAST_NAME_DESC(6),
    DATE_STARTED_DESC(7),
    DATE_STARTED(8),
    DATE_FINISHED_DESC(9),
    DATE_FINISHED(10),
    PAGE_COUNT(11),
    PAGE_COUNT_DESC(12),
    CURRENT_PAGE(13),
    CURRENT_PAGE_DESC(14),
    PROGRESS(15);


    private int sortNum;

    private static Map<Integer, Sort> map = new HashMap<>();

    static {
        Arrays.stream(Sort.values()).forEach(a -> map.put(a.sortNum, a));
    }

    Sort(final int sortNum) {
        this.sortNum = sortNum;
    }

    public static Sort valueOf(int sortNum) {
        return map.get(sortNum);
    }
}
