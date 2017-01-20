package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.MyTask;
import com.nasolution.com.nasolution.MyTask_Details;
import com.nasolution.com.nasolution.StateMaster;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class SaveTaskRemark {

    private static int mytaskAssignMasterId;
    private static String  myTaskstatus;
    private static String  myTaskcount;
    private static String  myTaskremark;
    private static ProgressDialog progressDialogBox;
    private static String addStateResponseResult;
    public static String addStateMethodName = "AssignTaskDetails";
    private static Context context;


    public SaveTaskRemark(MyTask_Details myTask_Details) {
        context = myTask_Details;
    }

    public static void InsertTaskRemark(String taskAssignMasterId, String status, String count, String remark, ProgressDialog progressDialog){

        mytaskAssignMasterId = Integer.parseInt(taskAssignMasterId);
        myTaskstatus = status;
        myTaskcount = count;
        myTaskremark = remark;
        progressDialogBox = progressDialog;
        SaveTaskDetailAsyncCallWS task = new SaveTaskDetailAsyncCallWS();
        task.execute();
    }

    public static class SaveTaskDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            addStateResponseResult = InsertDataWebservice.AssignTaskDetails(addStateMethodName,mytaskAssignMasterId,myTaskstatus,myTaskcount,myTaskremark);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

             if(addStateResponseResult.equals("Task Update")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Details Added Successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        Intent gotoMyTaskList = new Intent(context, MyTask.class);
                        context.startActivity(gotoMyTaskList);
                        alert.dismiss();
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
            else {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Add  Details");
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
        }
    }

}

