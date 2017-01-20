package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddTaskDetails {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String taskResponseResult;
    private static String taskWebMethod = "InsertTask";
    private static String stageId;
    private static String userId;
    private static String date;
    private static String count;
    private static int  addedBy;
    private static int  projectId;

    public AddTaskDetails(AddTask addTask) {
        context = addTask;
    }

    public void AddTask(int saveAddedBy, int saveProjectId, String saveStageId, String saveUserId, String saveDate, String saveCountName, ProgressDialog addTaskDialogBox) {
        addedBy = saveAddedBy;
        projectId = saveProjectId;
        stageId = saveStageId;
        userId = saveUserId;
        date = saveDate;
        count = saveCountName;
        progressDialogbox = addTaskDialogBox;
        AddTaskAsynCall addTaskAsynCall = new  AddTaskAsynCall();
        addTaskAsynCall.execute();
    }

    public static class AddTaskAsynCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            taskResponseResult = InsertDataWebservice.InsertTaskDetail(taskWebMethod, projectId,userId,stageId,addedBy,date,count);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(taskResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to add task details please try again.");
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
            else if(taskResponseResult.equals("Task Added")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Task details added successfully");
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
