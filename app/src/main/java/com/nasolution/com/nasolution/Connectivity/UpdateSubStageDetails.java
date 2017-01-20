package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.UpdateSubStage;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateSubStageDetails {
    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String subStageResponseResult;
    private static String updateSubStageMethod = "UpdateSubStage";
    private static String allSubStagePercent;
    private static String  allSubStageName;
    private static int  stageId;
    private static int  projectId;
    private static int  userId;

    public UpdateSubStageDetails(UpdateSubStage updateSubStage) {
        context = updateSubStage;
    }

    public void UpdateSubStage(int saveProjectId, int saveStageId, String saveAllSubStageName, String saveAllSubStagePercent, ProgressDialog subStageDialogBox, int clientId) {
        projectId = saveProjectId;
        stageId = saveStageId;
        allSubStageName = saveAllSubStageName;
        allSubStagePercent = saveAllSubStagePercent;
        userId = clientId;
        progressDialogbox = subStageDialogBox;
        UpdateSubStageAsyncCallWS  task = new UpdateSubStageAsyncCallWS();
        task.execute();
    }

    public static class UpdateSubStageAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            subStageResponseResult = UpdateDataWebservice.UpdateSubStage(updateSubStageMethod, projectId ,stageId,allSubStageName,allSubStagePercent,userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(subStageResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update sub-stage");
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
            else if(subStageResponseResult.equals("Update Sub-Stage")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Sub Stage Updated successfully");
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
