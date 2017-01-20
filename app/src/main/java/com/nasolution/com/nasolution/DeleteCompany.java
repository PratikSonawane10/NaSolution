package com.nasolution.com.nasolution;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.CompanySpinnerAdapter;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteCompany extends BaseActivity implements View.OnClickListener{

    Spinner spinnerDeleteCompany;
    Button btnSubmitDeleteCompany;
    ProgressDialog deleteProgressBox;

    private String companyID;
    private String companyName;
    private String[] companyArrayList;
    private String companyResponseResult;
    private String companyMethodName = "GetAllClient";
    int saveDeleteCompanyId;
    private String deleteCompanyResponse;
    private String deleteCompanyWebMethod = "DeleteClient";
    private CompanySpinnerAdapter companySpinnerAdapter;
    private List<String> companyList = new ArrayList<String>();
    private List<String> companyIdList = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.deletecompany);

        spinnerDeleteCompany = (Spinner) findViewById(R.id.spinnerDeleteClient);
        btnSubmitDeleteCompany = (Button) findViewById(R.id.btnSubmitDeleteCompany);

        getAllCompany();
        btnSubmitDeleteCompany.setOnClickListener(this);
    }

    private void getAllCompany()  {

        companyArrayList = new String[]{
                "Select Client"
        };
        companyList = new ArrayList<>(Arrays.asList(companyArrayList));
        companySpinnerAdapter = new CompanySpinnerAdapter(this, R.layout.client_spinner_item, companyList);
        // progressDialog = ProgressDialog.show(getActivity(), "", "Fetching Company Details Please Wait...", true);
        fetchAllCompany();
        companySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeleteCompany.setAdapter(companySpinnerAdapter);
        spinnerDeleteCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    companyName = parent.getItemAtPosition(position).toString();
                    companyID = companyIdList.get(position);
                    companySpinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitDeleteCompany){
            if(companyID != null){
                saveDeleteCompanyId = Integer.parseInt(companyID);
                submitCompanyDelete();
            }
        }
    }

    private void fetchAllCompany() {
        AllCompanyAsyncCallWS task = new AllCompanyAsyncCallWS();
        task.execute();
    }


    public class AllCompanyAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            companyResponseResult = GetDataWebService.GetAllClients(companyMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (companyResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Clients ");
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                //  progressDialog.hide();
                try {
                    JSONArray jsonArray = new JSONArray(companyResponseResult);
                    companyIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            companyList.add(obj.getString("fullname"));
                            companyIdList.add(obj.getString("clientMasterId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

           /* if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.hide();
            }*/
        }
    }

    private void submitCompanyDelete() {
        deleteProgressBox = ProgressDialog.show(DeleteCompany.this,"","Deleting company details please wait...");
        DeleteCompanyAsyncCallWS deleteCompany = new DeleteCompanyAsyncCallWS();
        deleteCompany.execute();
    }

    public class DeleteCompanyAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteCompanyWebMethod = DeleteDataWebService.DeleteCompany(deleteCompanyWebMethod,saveDeleteCompanyId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteCompanyWebMethod.equals("Deleted Successfully")){
                deleteProgressBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Company details deleted successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        Intent gotoHome = new Intent(DeleteCompany.this,Home.class);
                        startActivity(gotoHome);
                        alert.dismiss();
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
            else if(deleteCompanyWebMethod.equals("Error occurred")) {
                deleteProgressBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to delete company details please try again.");
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

        }
    }

}
