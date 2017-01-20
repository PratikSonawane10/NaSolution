package com.nasolution.com.nasolution.Connectivity;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Model.AllClientsItems;
import com.nasolution.com.nasolution.Model.MyTaskListItems;
import com.nasolution.com.nasolution.MyTask;
import com.nasolution.com.nasolution.View_All_Clients;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fetch_AllClients {

    private static Context context;
    private static final String url="";
    private static String displayText;
    private static String webMethodName ="GetAllClient";
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<AllClientsItems> allClientsItemsArrayForAsyncTask;
    private static int userID;



    public Fetch_AllClients(View_All_Clients view_All_Clients) {
        context = view_All_Clients;

    }

    public void Fetch_AllClientsDetails(List<AllClientsItems> allClientsItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter) {

        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        allClientsItemsArrayForAsyncTask = allClientsItems;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = GetDataWebService.GetAllClients(webMethodName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Clients Details Not Found") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Clients Not Found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            AllClientsItems allClientsItems = new AllClientsItems();
                            allClientsItems.setclientMasterId(obj.getString("clientMasterId"));
                            allClientsItems.setclientname(obj.getString("fullname"));
                            allClientsItems.setaddres(obj.getString("addres"));
                            allClientsItems.setcity(obj.getString("city"));
                            allClientsItems.setemail(obj.getString("email"));
                            allClientsItemsArrayForAsyncTask.add(allClientsItems);
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
