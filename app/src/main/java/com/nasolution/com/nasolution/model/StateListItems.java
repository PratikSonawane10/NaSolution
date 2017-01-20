package com.nasolution.com.nasolution.Model;

public class StateListItems {

    private String name;
    private int stateMasterId;


    public StateListItems(String name){
        this.name = name;
    }

    public StateListItems() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateMasterId() {
        return stateMasterId;
    }

    public void setStateMasterId(int stateMasterId) {
        this.stateMasterId = stateMasterId;
    }
}
