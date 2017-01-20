package com.nasolution.com.nasolution.Connectivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Model.AllClientsItems;
import com.nasolution.com.nasolution.Model.AllUsersItems;
import com.nasolution.com.nasolution.View_All_Clients;
import com.nasolution.com.nasolution.View_All_Users;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fetch_AllUsers {

    private static Context context;
    private static String displayText;
    private static String webMethodName ="GETAllUser";
    private static RecyclerView.Adapter adapterForAsyncTask;
    private static RecyclerView recyclerViewForAsyncTask;
    private static List<AllUsersItems> allUserssItemsArrayForAsyncTask;
    private static int userID;



    public Fetch_AllUsers(View_All_Users view_All_Users) {
        context = view_All_Users;

    }

    public void Fetch_AllUsersDetails(List<AllUsersItems> allUsersItems, RecyclerView recyclerView, RecyclerView.Adapter reviewAdapter) {

        adapterForAsyncTask= reviewAdapter;
        recyclerViewForAsyncTask= recyclerView;
        allUserssItemsArrayForAsyncTask = allUsersItems;

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private static class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            displayText = GetDataWebService.GetAllUser(webMethodName);
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            if(displayText.equals("Users Details Not Found") || displayText.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Users Not Found");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(displayText);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            AllUsersItems allUsersItems = new AllUsersItems();
                            allUsersItems.setuserMasterId(obj.getString("user_MasterId"));
                            allUsersItems.setusername(obj.getString("name"));
                            allUsersItems.setaddres(obj.getString("addres"));
                            allUsersItems.setemail(obj.getString("email"));
                            allUsersItems.setemp_code(obj.getString("emp_code"));
                            allUsersItems.setmobile(obj.getString("mobile"));
                            allUserssItemsArrayForAsyncTask.add(allUsersItems);
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