package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.DeleteTask;
import com.nasolution.com.nasolution.UpdateTask;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchAllUser {

    private static Context context;
    private static ProgressDialog progressDialogBox;
    private static String userResponseResult;
    private static List<String> allUserIdList = new ArrayList<String>();
    private static List<String> allUserNameList = new ArrayList<String>();
    private static String getUserWebMethod = "GetAllUser";

    public FetchAllUser(AddTask addTask) {
        context = addTask;
    }

    public FetchAllUser(UpdateTask updateTask) {
        context = updateTask;
    }

    public FetchAllUser(DeleteTask deleteTask) {
        context = deleteTask;
    }

    public void GetAllUser(List<String> userNameList, List<String> userIdList, ProgressDialog userDialogBox) {
        allUserNameList = userNameList;
        allUserIdList = userIdList;
        progressDialogBox  = userDialogBox;
        UserListAsyncTask  task = new UserListAsyncTask();
        task.execute();
    }

    public static class UserListAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            userResponseResult = GetDataWebService.GetAllUser(getUserWebMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(userResponseResult.equals("No Record Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("User details not found");
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
                try {
                    allUserIdList.clear();
                    allUserNameList.clear();
                    JSONArray jsonArray = new JSONArray(userResponseResult);
                    allUserNameList.add("Select User");
                    allUserIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allUserIdList.add(obj.getString("user_MasterId"));
                            allUserNameList.add(obj.getString("username"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
                progressDialogBox.dismiss();
            }
        }
    }
}
