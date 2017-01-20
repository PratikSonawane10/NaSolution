package com.nasolution.com.nasolution.Connectivity;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Model.AllProjectItems;
import com.nasolution.com.nasolution.View_All_Project;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fetch_AllProjects {

    private static Context context;
    private static String displayText;
    private static String webMethodName ="GetAllProject";
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<AllProjectItems> allProjectssItemsArrayForAsyncTask;
    private static int projectID;



    public Fetch_AllProjects(View_All_Project view_All_Project) {
        context = view_All_Project;

    }

    public void Fetch_AllProjects(List<AllProjectItems> allProjectsItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter) {

        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        allProjectssItemsArrayForAsyncTask = allProjectsItems;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = GetDataWebService.GetAllProjectList(webMethodName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("No Record Found") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Projects Not Found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            AllProjectItems allProjectsItems = new AllProjectItems();
                            allProjectsItems.setprojectMasterId(obj.getString("projectMasterId"));
                            allProjectsItems.settitle(obj.getString("title"));
                            allProjectsItems.setdescription(obj.getString("description"));
                            allProjectsItems.setProjectArea(obj.getString("ProjectArea"));
                            allProjectsItems.setProjectCode(obj.getString("ProjectCode"));
                            allProjectsItems.setfileNumber(obj.getString("fileNumber"));
                            allProjectsItems.setNewSurveyNo(obj.getString("NewSurveyNo"));
                            allProjectsItems.setoldSurveyNo(obj.getString("oldSurveyNo"));
                            allProjectsItems.setVillageName(obj.getString("VillageName"));
                            allProjectssItemsArrayForAsyncTask.add(allProjectsItems);
                            adapterForAsyncTask.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

}
