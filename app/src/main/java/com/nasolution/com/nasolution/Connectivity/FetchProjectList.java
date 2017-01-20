package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.AddSubStage;
import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.DeleteProject;
import com.nasolution.com.nasolution.DeleteTask;
import com.nasolution.com.nasolution.StageForm;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.DeleteSubStage;
import com.nasolution.com.nasolution.UpdateStageForm;
import com.nasolution.com.nasolution.UpdateSubStage;
import com.nasolution.com.nasolution.UpdateTask;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchProjectList {

    private static Context context;
    private static ProjectSpinnerAdapter spinnerAdapter;
    private static ProgressDialog progressDialogBox;
    private static String projectResponseResult;
    private static String projectWebMethod = "GetAllProject";
    private static List<String> allProjectIdList = new ArrayList<String>();
    private static List<String> allProjectNameList = new ArrayList<String>();

    public FetchProjectList(UpdateProject updateProject) {
        context = updateProject;
    }

    public FetchProjectList(StageForm stageForm) {
        context = stageForm;
    }

    public FetchProjectList(DeleteProject deleteProject) {
        context = deleteProject;
    }

    public FetchProjectList(AddSubStage addSubStage) {
        context = addSubStage;
    }

    public FetchProjectList(DeleteSubStage deleteSubStage) {
        context = deleteSubStage;
    }

    public FetchProjectList(UpdateSubStage updateSubStage) {
        context = updateSubStage;
    }

    public FetchProjectList(AddTask addTask) {
        context = addTask;
    }

    public FetchProjectList(UpdateTask updateTask) {
        context = updateTask;
    }

    public FetchProjectList(DeleteTask deleteTask) {
        context = deleteTask;
    }

    public FetchProjectList(UpdateStageForm updateStageForm) {
        context = updateStageForm;
    }

    public void GetAllProject(List<String> projectNameList, List<String> projectIdList, ProjectSpinnerAdapter projectSpinnerAdapter, ProgressDialog projectDialogBox) {
        allProjectNameList = projectNameList;
        allProjectIdList = projectIdList;
        spinnerAdapter = projectSpinnerAdapter;
        progressDialogBox = projectDialogBox;
        ProjectListAsyncTask  task = new ProjectListAsyncTask();
        task.execute();
    }

    public void GetAllProjectforSubStage(List<String> projectNameList, List<String> projectIdList, ProgressDialog projectDialogBox) {
        allProjectNameList = projectNameList;
        allProjectIdList = projectIdList;
        progressDialogBox = projectDialogBox;
        ProjectListAsyncTask  task = new ProjectListAsyncTask();
        task.execute();
    }

    public static class ProjectListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            projectResponseResult = GetDataWebService.GetAllProjectList(projectWebMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(projectResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch project information");
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
            if(projectResponseResult.equals("No Record Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("project details not found");
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
                progressDialogBox.dismiss();
                try {
                    allProjectIdList.clear();
                    allProjectNameList.clear();
                    JSONArray jsonArray = new JSONArray(projectResponseResult);
                    allProjectNameList.add("Select Project");
                    allProjectIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allProjectIdList.add(obj.getString("projectMasterId"));
                            allProjectNameList.add(obj.getString("title"));
                            spinnerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
}
