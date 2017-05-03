package com.rich.bryan.services.Utils;

public class SortMethod {

    public static String getSortMethod(Sort sortMethod){
        String sort;

        switch (sortMethod){
            case DATE_CREATED_DESC:
            default:
                sort = "bu.dateCreated desc";
                break;
            case DATE_CREATED:
                sort = "bu.dateCreated";
                break;
            case TITLE:
                sort = "b.title";
                break;
            case TITLE_DESC:
                sort = "b.title desc";
                break;
            case LAST_NAME:
                sort = "a.lastName";
                break;
            case LAST_NAME_DESC:
                sort = "a.lastName desc";
                break;
            case DATE_STARTED:
                sort = "bu.dateStarted";
                break;
            case DATE_STARTED_DESC:
                sort = "bu.dateStarted desc";
                break;
            case DATE_FINISHED:
                sort = "bu.dateFinished";
                break;
            case DATE_FINISHED_DESC:
                sort = "bu.dateFinished desc";
                break;
            case PAGE_COUNT:
                sort = "b.pageCount";
                break;
            case PAGE_COUNT_DESC:
                sort = "b.pageCount desc";
                break;
            case CURRENT_PAGE:
                sort = "bu.currentPage";
                break;
            case CURRENT_PAGE_DESC:
                sort = "bu.currentPage desc";
                break;
        }

        return sort;
    }
}
