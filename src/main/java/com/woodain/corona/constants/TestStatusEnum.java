package com.woodain.corona.constants;

/** 검사현황 상태 상수*/
public enum TestStatusEnum {

    testCnt("testCnt"),
    testCompleteCnt("testCompleteCnt"),
    confirmRatio("confirmRatio"),
    ;
    String title;

    TestStatusEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
