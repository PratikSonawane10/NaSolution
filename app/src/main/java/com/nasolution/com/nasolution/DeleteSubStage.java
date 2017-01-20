package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StageListAdapter;
import com.nasolution.com.nasolution.Connectivity.DeleteSubStageDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.Connectivity.FetchProjectStage;
import com.nasolution.com.nasolution.Connectivity.FetchSubStage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteSubStage extends BaseActivity  implements View.OnClickListener{

    private String userId;
    private String deleteSubStage;
    Spinner spinnerProjectList;
    Spinner spinnerStageList;
    Spinner spinnerSubStageList;
    Button btnSubmitDeleteSubStage;
    ProgressDialog deleteSubStageDialogBox;

    //company
    private int saveCompanyId;
    private String companyID;
    private String companyName;
    private String[] companyArrayList;
    private ProgressDialog companyDialogBox;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    private List<String> companyNameList = new ArrayList<String>();
    private List<String> companyIdList = new ArrayList<String>();


    //project list
    String projectName;
    String projectId;
    int saveProjectId;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    private List<String> projectIdList = new ArrayList<String>();
    private List<String> projectNameList = new ArrayList<String>();

    //project stage
    String stageName;
    String stageId;
    int saveStageId;
    private String[] projectStageArrayList;
    private ProgressDialog stageDialogBox;
    private StageListAdapter stageListAdapter;
    private List<String> stageIdList = new ArrayList<String>();
    private List<String> stageNameList = new ArrayList<String>();

    //project stage
    String subStageName;
    String subStageId;
    int saveSubStageId;
    private String[] subStageArrayList;
    private ProgressDialog subStageDialogBox;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private List<String> subStageIdList = new ArrayList<String>();
    private List<String> subStageNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_sub_stage);

        spinnerProjectList = (Spinner) findViewById(R.id.deleteSubStage_ProjectList);
        spinnerStageList = (Spinner) findViewById(R.id.deleteSubStage_StageList);
        spinnerSubStageList = (Spinner) findViewById(R.id.spinnerDeleteSubStageList);
        btnSubmitDeleteSubStage = (Button) findViewById(R.id.btnSubmitDeleteSubStage);
        getSpinnerProjectList();
        getSpinnerProjectStageList();
        getSpinnerSubStageList();
        btnSubmitDeleteSubStage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmitDeleteSubStage){
            submitDeleteSubStage();
        }
    }

    private void submitDeleteSubStage() {
        if (projectId == null) {
            Toast.makeText(this, "Please select project ", Toast.LENGTH_LONG).show();
        }
        else if (stageId == null) {
            Toast.makeText(this, "Please select stage ", Toast.LENGTH_LONG).show();
        }
        else if (subStageId == null) {
            Toast.makeText(this, "Please select sub-stage ", Toast.LENGTH_LONG).show();
        }
        else {
            saveSubStageId = Integer.parseInt(subStageId);
            subStageDialogBox = ProgressDialog.show(DeleteSubStage.this, "", "Deleting sub stage information please wait...", true);
            DeleteSubStageDetails deleteSubStageDetails = new DeleteSubStageDetails(this);
            deleteSubStageDetails.DeleteSubStage(saveSubStageId,subStageDialogBox);
        }
    }

    private void getSpinnerProjectList() {
        projectArrayList = new String[]{
                "Select Project"
        };
        projectNameList = new ArrayList<>(Arrays.asList(projectArrayList));
        projectSpinnerAdapter = new ProjectSpinnerAdapter(this, R.layout.spinner_item, projectNameList);
        getProjectList();
        projectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectList.setAdapter(projectSpinnerAdapter);
        spinnerProjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    projectName = parent.getItemAtPosition(position).toString();
                    projectId = projectIdList.get(position);
                    if(projectId != null){
                        saveProjectId = Integer.parseInt(projectId);
                        getProjectStage();
                    }
                    if (subStageNameList != null){
                        subStageNameList.clear();
                        subStageIdList.clear();
                        getSpinnerSubStageList();
                    }
                    spinnerStageList.setSelection(stageNameList.indexOf(0));
                    stageListAdapter.notifyDataSetChanged();
                    spinnerSubStageList.setSelection(subStageNameList.indexOf(0));
                    customSpinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSpinnerProjectStageList() {
        projectStageArrayList = new String[]{
                "Select Stage"
        };
        stageNameList = new ArrayList<>(Arrays.asList(projectStageArrayList));
        stageListAdapter = new StageListAdapter(this, R.layout.spinner_item, stageNameList);
        stageListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStageList.setAdapter(stageListAdapter);
        spinnerStageList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stageName = parent.getItemAtPosition(position).toString();
                    stageId = stageIdList.get(position);
                    saveStageId = Integer.parseInt(stageId);
                    if(stageId != null){
                        getSubStage();
                    }
                    spinnerSubStageList.setSelection(subStageNameList.indexOf(0));
                    customSpinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSpinnerSubStageList() {
        subStageArrayList = new String[]{
                "Select Sub Stage"
        };
        subStageNameList = new ArrayList<>(Arrays.asList(subStageArrayList));
        customSpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item, subStageNameList);
        customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubStageList.setAdapter(customSpinnerAdapter);
        spinnerSubStageList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    subStageName = parent.getItemAtPosition(position).toString();
                    subStageId = subStageIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(DeleteSubStage.this, "", "Getting project list please wait...");
        FetchProjectList fetchProjectList = new FetchProjectList(this);
        fetchProjectList.GetAllProject(projectNameList, projectIdList, projectSpinnerAdapter, projectDialogBox);
    }

    private void getProjectStage() {
        stageDialogBox = ProgressDialog.show(DeleteSubStage.this,"","Fetching stage list details please wait...");
        FetchProjectStage fetchProjectStage = new FetchProjectStage(this);
        fetchProjectStage.ProjectStage(stageIdList, stageNameList,saveProjectId,stageDialogBox,stageListAdapter);
    }

    private void getSubStage() {
        subStageDialogBox = ProgressDialog.show(DeleteSubStage.this,"","Fetching sub-stage details please wait...");
        FetchSubStage fetchSubStage = new FetchSubStage(this);
        fetchSubStage.GetSubStage(subStageNameList,subStageIdList,saveStageId,subStageDialogBox);
    }
}
