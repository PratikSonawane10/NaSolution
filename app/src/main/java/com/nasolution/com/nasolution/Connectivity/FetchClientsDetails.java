package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.AddSubStage;
import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.ClientContact;
import com.nasolution.com.nasolution.DeleteTask;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.UpdateSubStage;
import com.nasolution.com.nasolution.UpdateTask;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchClientsDetails {

    private static Context context;
    private static int clientId;
    private static ProgressDialog progressDialogBox;
    private static String clientResponseResult;
    private static String clientMethodName = "GetClient";
    private static String allClientMethodName = "GetAllClient";
    private static ClientSpinnerAdapter clientAdapter;
    private static List<String> allClientsName = new ArrayList<String>();
    private static List<String> allClienstId = new ArrayList<String>();

    public FetchClientsDetails(ClientContact clientContact) {
        context = clientContact;
    }

    public FetchClientsDetails(AddProject addProject) {
        context = addProject;
    }

    public FetchClientsDetails(UpdateProject updateProject) {
        context = updateProject;
    }

    public FetchClientsDetails(AddTask addTask) {
        context = addTask;
    }

    public FetchClientsDetails(DeleteTask deleteTask) {
        context = deleteTask;
    }

    public FetchClientsDetails(UpdateTask updateTask) {
        context = updateTask;
    }

    public FetchClientsDetails(AddSubStage addSubStage) {
        context =  addSubStage;
    }

    public FetchClientsDetails(UpdateSubStage updateSubStage) {
        context = updateSubStage;
    }

    public void FetchClient(List<String> clientNameList,int saveClientID, List<String> clientIdList, ClientSpinnerAdapter clientSpinnerAdapter, ProgressDialog progressDialog) {
        allClientsName = clientNameList;
        clientId = saveClientID;
        allClienstId = clientIdList;
        clientAdapter = clientSpinnerAdapter;
        progressDialogBox = progressDialog;
        ClientByIdAsyncCallWS task = new ClientByIdAsyncCallWS();
        task.execute();
    }

    public void FetchAllClient(List<String> companyNameList, List<String> companyIdList, ClientSpinnerAdapter clientSpinnerAdapter, ProgressDialog companyDialogBox) {
        allClientsName = companyNameList;
        allClienstId = companyIdList;
        clientAdapter = clientSpinnerAdapter;
        progressDialogBox = companyDialogBox;
        AllClientAsyncCallWS task = new AllClientAsyncCallWS();
        task.execute();
    }

    public static class ClientByIdAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            clientResponseResult = GetDataWebService.GetClients(clientMethodName,clientId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (clientResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch details please try again later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent intent = new Intent(context,ClientContact.class);
                        context.startActivity(intent);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(clientResponseResult);
                    allClienstId.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allClientsName.add(obj.getString("title"));
                            allClienstId.add(obj.getString("clientMasterId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (progressDialogBox != null && progressDialogBox.isShowing()) {
                progressDialogBox.dismiss();
            }
        }
    }

    public static class AllClientAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            clientResponseResult = GetDataWebService.GetAllClients(allClientMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (clientResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Clients ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent intent = new Intent(context,ClientContact.class);
                        context.startActivity(intent);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(clientResponseResult);
                    allClienstId.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allClientsName.add(obj.getString("fullname"));
                            allClienstId.add(obj.getString("clientMasterId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (progressDialogBox != null && progressDialogBox.isShowing()) {
                progressDialogBox.dismiss();
            }
        }
    }
}
