package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.StageForm;
import com.nasolution.com.nasolution.UpdateStageForm;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddStage {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String stageResponseResult;
    private static String addStageMethod = "AddStage";
    private static String allStagePercent;
    private static String  allStageName;
    private static int  projectId;
    private static int  clientID;

    public AddStage(StageForm stageForm) {
        context = stageForm;
    }

    public AddStage(UpdateStageForm updateStageForm) {
        context = updateStageForm;
    }

    public void insertAddSatges(int idOfProject, int idOfClient, String saveAllStageName, String saveAllStagePercent, ProgressDialog projectDetailDaialogBox) {
        projectId = idOfProject;
        allStageName = saveAllStageName;
        allStagePercent = saveAllStagePercent;
        clientID = idOfClient;
        progressDialogbox = projectDetailDaialogBox;
        AddStage.AddStageAsyncCallWS task = new AddStage.AddStageAsyncCallWS();
        task.execute();
    }

    public static class AddStageAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            stageResponseResult = InsertDataWebservice.InsertStage(addStageMethod, projectId ,allStageName,allStagePercent,clientID);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(stageResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to add stage");
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
            else if(stageResponseResult.equals("Stage Added")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Stage Added successfully");
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
