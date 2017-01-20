package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.AddSubStage;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddSubStageDetails {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String subStageResponseResult;
    private static String addSubStageMethod = "InsertSubStage";
    private static String allSubStagePercent;
    private static String  allSubStageName;
    private static int  stageId;
    private static int  projectId;
    private static int  userId;
    public AddSubStageDetails(AddSubStage addSubStage) {
        context = addSubStage;
    }

    public void AddSubStage(int saveProjectId, int saveStageId, String saveAllStageName, String saveAllStagePercent, ProgressDialog subStageDialogBox, int saveUserId) {
        projectId = saveProjectId;
        stageId = saveStageId;
        allSubStageName = saveAllStageName;
        allSubStagePercent = saveAllStagePercent;
        progressDialogbox = subStageDialogBox;
        userId = saveUserId;
        AddSubStageAsyncCallWS  task = new AddSubStageAsyncCallWS();
        task.execute();
    }

    public static class AddSubStageAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            subStageResponseResult = InsertDataWebservice.InsertSubStage(addSubStageMethod, projectId ,stageId,allSubStageName,allSubStagePercent,userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(subStageResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to add sub-stage");
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
            else if(subStageResponseResult.equals("Insert Sub-Stage")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Sub Stage Added successfully");
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
