package com.woodain.corona.constants;

/** 검사현황 상태 상수*/
public enum TestStatusEnum {

    testCnt("testCnt"), //검사수
    testCompleteCnt("testCompleteCnt"), //검사완료수
    confirmRatio("confirmRatio"); //확진률

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
