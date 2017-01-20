package com.nasolution.com.nasolution.Model;




public class MyTaskListItems {

    public String taskAssignMasterId;
    public String clientname;
    public String title;
    public String projectname;
    public String stagepercent;
    public String setstageMasterId;
    public String assignby;
    public String status;
    public String submiton;

    public String subStageId;
    public String count;

    public MyTaskListItems() {
    }

    public MyTaskListItems(String taskAssignMasterId,String clientname, String title  ,String projectname ,String stagepercent ,String setstageMasterId, String assignby,String subStageId,String count, String status) {

        this.taskAssignMasterId = taskAssignMasterId;
        this.title = title;
        this.clientname = clientname;
        this.projectname = projectname;
        this.stagepercent = stagepercent;
        this.setstageMasterId = setstageMasterId;
        this.assignby = assignby;
        this.subStageId = subStageId;
        this.count = subStageId;
        this.status = status;

    }


    public String gettaskAssignMasterId() {
        return taskAssignMasterId;
    }

    public void settaskAssignMasterId(String taskAssignMasterId) {
        this.taskAssignMasterId = taskAssignMasterId;
    }

    public String getclientname() {
        return clientname;
    }

    public void setclientname(String clientname) {
        this.clientname = clientname;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getprojectname() {
        return projectname;
    }
    public void setprojectname(String projectname) {
        this.projectname = projectname;
    }

    public String getstagepercent() {
        return stagepercent;
    }
    public void setstagepercent(String stagepercent) {
        this.stagepercent = stagepercent;
    }

    public String getstageMasterId() {
        return setstageMasterId;
    }
    public void setstageMasterId(String setstageMasterId) {
        this.setstageMasterId = setstageMasterId;
    }

    public String getassignby() {
        return assignby;
    }
    public void setassignby(String assignby) {
        this.assignby = assignby;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getsubmiton() {
        return submiton;
    }
    public void setsubmiton(String submiton) {
        this.submiton = submiton;
    }


    public String getsubStageId() {
        return subStageId;
    }
    public void setsubStageId(String subStageId) {
        this.subStageId = subStageId;
    }

    public String getcount() {
        return count;
    }
    public void setcount(String count) {
        this.count = count;
    }



}
