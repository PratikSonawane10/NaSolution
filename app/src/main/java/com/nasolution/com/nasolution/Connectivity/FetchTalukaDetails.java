package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchTalukaDetails {

    private static int districtId;
    private static Context context;
    private static ProgressDialog talukaDialogBox;
    private static TalukaSpinnerAdapter talukaAdapter;
    private static String talukaResponseResult;
    private static String talukaMethodName = "SelectTaluka";
    private static List<String> talukaNameList = new ArrayList<String>();
    private static List<String> talukaIdList = new ArrayList<String>();

    public FetchTalukaDetails(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public FetchTalukaDetails(AddProject addProject) {
        context = addProject;
    }

    public FetchTalukaDetails(UpdateProject updateProject) {
        context = updateProject;
    }

    public void FetchTaluka(List<String> nameList, List<String> idList, int saveDistrictID, TalukaSpinnerAdapter talukaSpinnerAdapter, ProgressDialog progressDialog) {
        talukaDialogBox = progressDialog;
        TalukaAsyncCallWS stateTask = new TalukaAsyncCallWS();
        stateTask.execute();
        talukaNameList = nameList;
        talukaIdList = idList;
        districtId = saveDistrictID;
        talukaAdapter  = talukaSpinnerAdapter;
    }

    public static class TalukaAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            talukaResponseResult = GetDataWebService.GetAllTaluka(talukaMethodName, districtId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(talukaResponseResult.equals("Taluka Not Found")) {
                talukaNameList.clear();
                talukaIdList.clear();
                talukaNameList.add("Select Taluka");
                talukaIdList.add("0");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Taluka Not Found");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                try {
                    talukaNameList.clear();
                    talukaIdList.clear();
                    JSONArray jsonArray = new JSONArray(talukaResponseResult);
                    talukaNameList.add("Select Taluka");
                    talukaIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            talukaNameList.add(obj.getString("talukaName"));
                            talukaIdList.add(obj.getString("taluka_MasterId"));
                            talukaAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            if(talukaDialogBox != null && talukaDialogBox.isShowing())
            {
                talukaDialogBox.dismiss();
            }
        }
    }
}
