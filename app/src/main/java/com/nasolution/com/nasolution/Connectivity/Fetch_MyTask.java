package com.nasolution.com.nasolution.Connectivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.MyTask;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;
import com.nasolution.com.nasolution.Model.MyTaskListItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class Fetch_MyTask {

    private static Context context;
    private static final String url="";
    private static String displayText;
    private static String webMethodName ="GETUserTask";
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<MyTaskListItems> MyTaskListItemsArrayForAsyncTask;
    private static int userID;


    
    public Fetch_MyTask(MyTask myTask) {
        context = myTask;

    }

    public void Fetch_MyTaskItems(List<MyTaskListItems> myTaskListItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter, int user_id) {

        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        MyTaskListItemsArrayForAsyncTask = myTaskListItems;
        userID = user_id;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = GetDataWebService.MyTaskDetails(webMethodName,userID);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Task Details Not Found") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Task Not Found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            MyTaskListItems myTaskListItems = new MyTaskListItems();
                            myTaskListItems.settaskAssignMasterId(obj.getString("TaskAssignMasterId"));
                            myTaskListItems.setclientname(obj.getString("clientname"));
                            myTaskListItems.settitle(obj.getString("title"));
                            myTaskListItems.setprojectname(obj.getString("projectname"));
                            myTaskListItems.setstagepercent(obj.getString("stagepercent"));
                            myTaskListItems.setstageMasterId(obj.getString("stageMasterId"));
                            myTaskListItems.setassignby(obj.getString("assignby"));
                            myTaskListItems.setsubmiton(obj.getString("submiton"));
                            myTaskListItems.setsubStageId(obj.getString("subStageId"));
                            myTaskListItems.setcount(obj.getString("count"));
                            myTaskListItems.setstatus(obj.getString("status"));

                            MyTaskListItemsArrayForAsyncTask.add(myTaskListItems);
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
