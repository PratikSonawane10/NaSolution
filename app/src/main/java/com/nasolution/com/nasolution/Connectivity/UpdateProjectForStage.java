package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.UpdateStageForm;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateProjectForStage {
    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String stageResponseResult;
    private static String addStageMethod = "UpdateProjectForStage";
    private static int  projectId;
    private static int  projectIdForStage;
    private static int  userId;

    public UpdateProjectForStage(UpdateStageForm updateStageForm) {
        context = updateStageForm;
    }

    public void updateProjectForSatges(int idOfProject, int idOfProjectForStage, int clientID, ProgressDialog projectDetailDaialogBox) {
        projectId = idOfProject;
        projectIdForStage = idOfProjectForStage;
        userId = clientID;
        progressDialogbox = projectDetailDaialogBox;
        UpdateStageAsyncCallWS task = new UpdateStageAsyncCallWS();
        task.execute();
    }

    public static class UpdateStageAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            stageResponseResult = UpdateDataWebservice.UpdateProjectForStage(addStageMethod, projectId, userId,projectIdForStage);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(stageResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update stage");
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
            else if(stageResponseResult.equals("Update Project")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Stage Updated successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(context, Home.class);
                        context.startActivity(gotohome);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}

