package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.StageForm;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddProjectDetail {


    private static int userId;
    private static int companyId;
    private static int serviceTypeId;
    private static String projectName;
    private static String oldSurvey;
    private static String newSurvey;
    private static int districtId;
    private static int talukaId;
    private static String fileNumber;
    private static String villageName;
    private static String projectArea;
    private static String multipleSelectedValue;
    private static String projectDescription;

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String projectResponseResult;
    private static String addProjectWebMethod = "AddProject";

    public AddProjectDetail(AddProject addProject) {
        context = addProject;
    }

    public void AddProject(int saveCompanyId, String saveProjectName, String saveProjectDescription, int saveServiceTypeId,
                           String saveOldSurvey, String saveNewSurvey, int saveTalukaID, int saveDistrictID, String saveFileNumber,
                           String saveVillageName, String saveProjectArea, int saveUserId, ProgressDialog projectDailogbox, String architectureValues) {
        companyId = saveCompanyId;
        projectName = saveProjectName;
        projectDescription = saveProjectDescription;
        serviceTypeId = saveServiceTypeId;
        oldSurvey = saveOldSurvey;
        newSurvey = saveNewSurvey;
        talukaId = saveTalukaID;
        districtId = saveDistrictID;
        fileNumber = saveFileNumber;
        villageName = saveVillageName;
        projectArea = saveProjectArea;
        userId = saveUserId;
        progressDialogbox = projectDailogbox;
        multipleSelectedValue = architectureValues;
        AddProjectAsyncCallWS  task = new AddProjectAsyncCallWS();
        task.execute();

    }

    public static class AddProjectAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            projectResponseResult = InsertDataWebservice.InsertProject(addProjectWebMethod,companyId, projectName,projectDescription,
                    serviceTypeId, oldSurvey, newSurvey, talukaId, districtId, fileNumber, villageName, projectArea, userId,multipleSelectedValue);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(projectResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to add new project please try again.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                progressDialogbox.dismiss();
                final String projectId = projectResponseResult;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Project added successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent stageIntent = new Intent(context, StageForm.class);
                        stageIntent.putExtra("ProjectId",projectId);
                        context.startActivity(stageIntent);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
