package com.nasolution.com.nasolution.Model;


public class AllProjectItems {

    public String projectMasterId;
    public String title;
    public String description;
    public String ProjectArea;
    public String ProjectCode;
    public String fileNumber;
    public String NewSurveyNo;
    public String oldSurveyNo;
    public String VillageName;


    public AllProjectItems() {
    }

    public AllProjectItems(String projectMasterId, String title, String description, String ProjectArea, String ProjectCode, String fileNumber, String NewSurveyNo, String oldSurveyNo, String VillageName) {

        this.projectMasterId = projectMasterId;
        this.title = title;
        this.description = description;
        this.ProjectArea = ProjectArea;
        this.ProjectCode = ProjectCode;
        this.fileNumber = fileNumber;
        this.NewSurveyNo = NewSurveyNo;
        this.oldSurveyNo = oldSurveyNo;
        this.VillageName = VillageName;
    }

    public String getprojectMasterId() {
        return projectMasterId;
    }

    public void setprojectMasterId(String projectMasterId) {
        this.projectMasterId = projectMasterId;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }



    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }



    public String getProjectArea() {
        return ProjectArea;
    }

    public void setProjectArea(String ProjectArea) {
        this.ProjectArea = ProjectArea;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String ProjectCode) {
        this.ProjectCode = ProjectCode;
    }
    public String getfileNumber() {
        return fileNumber;
    }

    public void setfileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }
    public String getNewSurveyNo() {
        return NewSurveyNo;
    }

    public void setNewSurveyNo(String NewSurveyNo) {
        this.NewSurveyNo = NewSurveyNo;
    }
    public String getoldSurveyNo() {
        return oldSurveyNo;
    }

    public void setoldSurveyNo(String oldSurveyNo) {
        this.oldSurveyNo = oldSurveyNo;
    }
 public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String VillageName) {
        this.VillageName = VillageName;
    }

}

