package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.UpdateStageForm;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateProjectDetail {

    private static int userId;
    private static int ProjectId;
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
    private static String projectDescription;
    private static String multipleSelectedValue;

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String projectResponseResult;

    private static String updateProjectWebMethod = "UpdateProject";

    public UpdateProjectDetail(UpdateProject updateProject) {
        context = updateProject;
    }

    public void UpdateProject(int updateProjectId,int saveCompanyId, String saveProjectName, String saveProjectDescription, int saveServiceTypeId,
            String saveOldSurvey, String saveNewSurvey, int saveTalukaID, int saveDistrictID, String saveFileNumber,
            String saveVillageName, String saveProjectArea, int saveUserId, ProgressDialog projectDailogbox, String architectureValues) {


        ProjectId = updateProjectId;
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
        UpdateProjectDetail.UpdateProjectAsyncCallWS task = new UpdateProjectDetail.UpdateProjectAsyncCallWS();
        task.execute();

    }

    public static class UpdateProjectAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            projectResponseResult = UpdateDataWebservice.updateProject(updateProjectWebMethod,ProjectId,companyId, projectName,projectDescription,
                    serviceTypeId, oldSurvey, newSurvey, talukaId, districtId, fileNumber, villageName, projectArea, userId,multipleSelectedValue);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(projectResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to Add New Project");
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
                builder.setMessage("Project Updated successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent stageIntent = new Intent(context, UpdateStageForm.class);
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
