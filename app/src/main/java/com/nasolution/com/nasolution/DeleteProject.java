package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteProject extends BaseActivity implements View.OnClickListener {

    Spinner spinnershowProject;
    Button btnSubmitDeleteProject;

    int saveDeleteProjectId;
    //project Spinner Items
    String projectName;
    String projectId;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private ProgressDialog deleteProgressDialog;
    private String projectDeleteResponseResult;
    private ProgressDialog projectDetailDaialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    private String delteProjectWebMethod = "DeleteProject";
    public List<String> projectIdList = new ArrayList<String>();
    public List<String> projectNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_project);

        spinnershowProject = (Spinner)findViewById(R.id.spinnerDeleteProject);
        btnSubmitDeleteProject = (Button) findViewById(R.id.btnSubmitDeleteProject);
        btnSubmitDeleteProject.setOnClickListener(this);

        getProjectSpinnerItem();
    }

    private void getProjectSpinnerItem() {
        projectArrayList = new String[]{
                "Select Project"
        };
        projectNameList = new ArrayList<>(Arrays.asList(projectArrayList));
        projectSpinnerAdapter = new ProjectSpinnerAdapter(this, R.layout.spinner_item, projectNameList);
        getProjectList();
        projectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershowProject.setAdapter(projectSpinnerAdapter);
        spinnershowProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    projectName = parent.getItemAtPosition(position).toString();
                    projectId = projectIdList.get(position);
                    saveDeleteProjectId = Integer.parseInt(projectId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmitDeleteProject){
            if(projectId == null){
                Toast.makeText(DeleteProject.this,"Please select project",Toast.LENGTH_LONG).show();
            }
            else {
                saveDeleteProjectId = Integer.parseInt(projectId);
                deleteProgressDialog = ProgressDialog.show(DeleteProject.this,"","Deleting project details please wait...");
                DeleteProjectDetails();
            }

        }
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(DeleteProject.this,"","Fetching details please wait...");
        FetchProjectList fetchProjectList = new FetchProjectList(this);
        fetchProjectList.GetAllProject(projectNameList,projectIdList,projectSpinnerAdapter, projectDialogBox);
    }

    private void DeleteProjectDetails() {
        DeletetProjectAsyncCallWS task = new DeletetProjectAsyncCallWS();
        task.execute();
    }

    public  class DeletetProjectAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            projectDeleteResponseResult = DeleteDataWebService.DeleteProject(delteProjectWebMethod, saveDeleteProjectId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(projectDeleteResponseResult.equals("Error occured")) {
                deleteProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteProject.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to delete project information. please try again.");
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
            else if(projectDeleteResponseResult.equals("Project Delete")){
                deleteProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteProject.this);
                builder.setTitle("Result");
                builder.setMessage("Project details deleted successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(DeleteProject.this, Home.class);
                        startActivity(gotohome);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
