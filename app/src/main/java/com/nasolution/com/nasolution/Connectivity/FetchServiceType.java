package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ServiceTypeSpinnerAdapter;
import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchServiceType {

    private static Context context;
    private static ServiceTypeSpinnerAdapter adapter;
    private static ProgressDialog progressDialogBox;
    private static String serviceResponseResult;
    private static List<String> allServiceIdList = new ArrayList<String>();
    private static List<String> allServiceNameList = new ArrayList<String>();
    private static String serviceWebMethod = "GetAllServiceType";

    public FetchServiceType(AddProject addProject) {
        context = addProject;
    }

    public FetchServiceType(UpdateProject updateProject) {
        context = updateProject;
    }

    public void GetAllServiceType(List<String> serviceIdList, List<String> serviceNameList, ServiceTypeSpinnerAdapter serviceSpinnerAdapter, ProgressDialog serviceDialogBox) {
        allServiceNameList = serviceNameList;
        allServiceIdList = serviceIdList;
        adapter = serviceSpinnerAdapter;
        progressDialogBox = serviceDialogBox;
        ServiceTYpeListAsyncTask task = new ServiceTYpeListAsyncTask();
        task.execute();
    }

    public static class ServiceTYpeListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            serviceResponseResult = GetDataWebService.GetAllServiceType(serviceWebMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(serviceResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch details please try again later.");
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
            if(serviceResponseResult.equals("No Record Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("ServiceType details not found.");
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
                    allServiceIdList.clear();
                    allServiceNameList.clear();
                    JSONArray jsonArray = new JSONArray(serviceResponseResult);
                    allServiceNameList.add("Select ServiceType");
                    allServiceIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allServiceIdList.add(obj.getString("service_MasterId"));
                            allServiceNameList.add(obj.getString("serviceName"));

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
