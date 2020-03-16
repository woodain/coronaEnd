package com.woodain.corona.constants;

/** 환자현황 상태 상수*/
public enum PatientStatusEnum {

    confirm("confirm"), //확진
    fullRecovery( "fullRecovery") ,//완치
    cure("cure"),  //치료
    death( "death"); //사망

    String title;

    PatientStatusEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
