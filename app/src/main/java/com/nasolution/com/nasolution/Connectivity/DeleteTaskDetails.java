package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.DeleteTask;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;

public class DeleteTaskDetails {

    private static Context context;
    private static ProgressDialog progressDialogBox;
    private static String taskResponseResult;
    private static String taskWebMethod = "DeleteTask";
    private static String taskId;
    private static String countName;

    public DeleteTaskDetails(DeleteTask deleteTask) {
        context = deleteTask;
    }

    public void DeleteTask(String saveTaskId, String saveCountName, ProgressDialog submitDeleteDialogBox) {
        taskId = saveTaskId;
        countName = saveCountName;
        progressDialogBox = submitDeleteDialogBox;
        DeleteTaskAsynTaskCallWS task = new DeleteTaskAsynTaskCallWS();
        task.execute();
    }


    public static class DeleteTaskAsynTaskCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            taskResponseResult = DeleteDataWebService.DeleteTask(taskWebMethod, taskId, countName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(taskResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to delete task details");
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
            else if(taskResponseResult.equals("Task Delete")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Task deleted sucessfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(context, Home.class);
                        context.startActivity(gotohome);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }
}
