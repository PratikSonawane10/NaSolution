package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.UpdateTask;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateTaskDetails {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String taskResponseResult;
    private static String taskWebMethod = "UpdateTask";
    private static String stageId;
    private static String userId;
    private static String date;
    private static String count;
    private static int  addedBy;
    private static int  projectId;


    public UpdateTaskDetails(UpdateTask updateTask) {
        context = updateTask;
    }

    public void UpdateTask(int saveAddedBy, int saveProjectId, String saveUpdateStageId, String saveUpdateUserId, String saveUpdateDate, String saveUpdateCountName, ProgressDialog submitUpdateDialogBox) {
        addedBy = saveAddedBy;
        projectId = saveProjectId;
        stageId = saveUpdateStageId;
        userId = saveUpdateUserId;
        date = saveUpdateDate;
        count = saveUpdateCountName;
        progressDialogbox = submitUpdateDialogBox;
        UpdateTaskAsynCall updateTaskAsynCall = new  UpdateTaskAsynCall();
        updateTaskAsynCall.execute();
    }

    public static class UpdateTaskAsynCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            taskResponseResult = UpdateDataWebservice.UpdateTaskDetail(taskWebMethod, projectId,userId,stageId,addedBy,date,count);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(taskResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to updated task details");
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
            else if(taskResponseResult.equals("Task Updated")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Task details updated  successfully");
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
