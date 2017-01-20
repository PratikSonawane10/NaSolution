package com.nasolution.com.nasolution.Model;

public class DistrictListItems {

    private String districtName;
    private int districtMasterId;
    private int stateId;
    private String districtStateName;
    private String[] hintNameforState  = new String [] {"Select State"};

    public DistrictListItems(String districtName){
        this.districtName = districtName;
    }

    public DistrictListItems() {

    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getDistrictMasterId() {
        return districtMasterId;
    }

    public void setDistrictMasterId(int districtMasterId) {
        this.districtMasterId = districtMasterId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getDistrictStateName() {
        return districtStateName;
    }

    public void setDistrictStateName(String districtStateName) {
        this.districtStateName = districtStateName;
    }

    public String [] getStateHintName() {
        return hintNameforState;
    }

}
