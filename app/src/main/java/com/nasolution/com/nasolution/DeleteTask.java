package com.nasolution.com.nasolution;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.DeleteTaskDetails;
import com.nasolution.com.nasolution.Connectivity.FetchAllUser;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.SessionManager.SessionManager;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeleteTask extends BaseActivity implements View.OnClickListener {

    LinearLayout deleteTaskfieldLayout;
    Spinner spinnerDeleteProjectList;
    Spinner spinnerCompanyList;
    Button btnDeleteTask;
    ProgressDialog taskDetailDialogBox;
    ProgressDialog submitDeleteDialogBox;

    private int saveCompanyId;
    private String companyID;
    private String companyName;
    private String[] companyArrayList;
    private ProgressDialog companyDialogBox;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    private List<String> companyNameList = new ArrayList<String>();
    private List<String> companyIdList = new ArrayList<String>();

    //project List
    String projectName;
    String projectId;
    int saveProjectId;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    private List<String> projectIdList = new ArrayList<String>();
    private List<String> projectNameList = new ArrayList<String>();

    private static String taskResponseResult;
    private static String taskDetailMethodName = "GETTaskDetail";
    String templabelStage;
    String tempTaskName;
    String tempStagPercent;
    String tempStageId;
    String tempSubStageId;
    String tempUser;
    String tempDate;
    String tempTaskId;

    int k = 0;
    int flag;
    public static LinearLayout layout[] = new LinearLayout[100];
    public static Spinner spinnerUser[] = new Spinner[100];
    public static TextView stageName[] = new TextView[100];
    public static TextView stagePercent[] = new TextView[100];
    public static TextView lbluser[] = new TextView[100];
    public static TextView labelStage[] = new TextView[100];
    public static View dividerLine[] = new View[100];
    public static CheckBox deleteCheckBox[] = new CheckBox[100];
    public static boolean checkBoxValue[] = new boolean[100];

    public static String tittleStage[] = new String[100];
    public static String stageId[] = new String[100];
    public static String taskId[] = new String[100];
    public static String userId[] = new String[100];
    public static String dateValue[] = new String[100];

    private List<String> userNameList = new ArrayList<String>();
    private List<String> userIdList = new ArrayList<String>();
    private ProgressDialog userDialogBox;
    private CustomSpinnerAdapter adapter;

    public StringBuilder strTaskId;
    public StringBuilder strCountName;

    public StringBuilder strAppendTaskId;
    public StringBuilder strAppendCountName;

    String saveTaskId;
    String saveStageId;
    String saveCountName;
    String saveDate;
    int position;
    int addedBy;
    int datePosition;
    public SimpleDateFormat dateFormatter;
    public DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_task);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        addedBy = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        spinnerCompanyList = (Spinner) findViewById(R.id.spinnerDeleteTaskCompanyList);
        spinnerDeleteProjectList = (Spinner) findViewById(R.id.spinnerDeleteTaskProjectList);
        deleteTaskfieldLayout = (LinearLayout) findViewById(R.id.deleteTaskFieldsLayout);
        btnDeleteTask = (Button) findViewById(R.id.btnDeleteTask);

        btnDeleteTask.setOnClickListener(this);
        getAllUser();
        getSpinnerCompanyList();
        getSpinnerProjectList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDeleteTask){
            collectAllData();
            if(saveTaskId.isEmpty()){
                Toast.makeText(this,"Please select task to delete",Toast.LENGTH_LONG).show();
            }
            else {
                submitDeleteDialogBox  = ProgressDialog.show(this, "", "Deleting task details please wait...");
                DeleteTaskDetails deleteTaskDetails = new DeleteTaskDetails(this);
                deleteTaskDetails.DeleteTask(saveTaskId,saveCountName,submitDeleteDialogBox);
            }
        }
    }

    private void collectAllData() {

        strTaskId = new StringBuilder();
        strCountName = new StringBuilder();

        strAppendTaskId = new StringBuilder();
        strAppendCountName = new StringBuilder();

        for (int i = 1; i <= k; i++) {
                if(checkBoxValue[i] == true){
                    strTaskId.append(taskId[i] + ",");
                    strCountName.append(tittleStage[i]+ ",");
            }
        }
        strAppendTaskId = strTaskId;
        strAppendCountName = strCountName;

        saveTaskId = strAppendTaskId.toString();
        saveCountName = strAppendCountName.toString();
    }

    private void getAllUser() {
        userDialogBox = ProgressDialog.show(DeleteTask.this, "", "Fetching details please wait...");
        FetchAllUser fetchAllUser = new FetchAllUser(this);
        fetchAllUser.GetAllUser(userNameList, userIdList, userDialogBox);
    }

    private void getSpinnerCompanyList() {
        companyArrayList = new String[]{
                "Select Company"
        };
        companyNameList = new ArrayList<>(Arrays.asList(companyArrayList));
        clientSpinnerAdapter = new ClientSpinnerAdapter(this, R.layout.client_spinner_item, companyNameList);
        getCompanyList();
        clientSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompanyList.setAdapter(clientSpinnerAdapter);
        spinnerCompanyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int i = 0; i <= k; i++) {
                        deleteTaskfieldLayout.removeView(layout[i]);
                    }
                    k = 0;
                    getSpinnerProjectList();
                    companyID = null;
                    companyName = parent.getItemAtPosition(position).toString();
                    companyID = companyIdList.get(position);
                    if (companyID != null) {
                        saveCompanyId = Integer.parseInt(companyID);
                        getProjectList();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSpinnerProjectList() {
        projectArrayList = new String[]{
                "Select Project"
        };
        projectNameList = new ArrayList<>(Arrays.asList(projectArrayList));
        projectSpinnerAdapter = new ProjectSpinnerAdapter(this, R.layout.spinner_item, projectNameList);
        projectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeleteProjectList.setAdapter(projectSpinnerAdapter);
        spinnerDeleteProjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int i = 0; i <= k; i++) {
                        deleteTaskfieldLayout.removeView(layout[i]);
                    }
                    k = 0;
                    projectName = parent.getItemAtPosition(position).toString();
                    projectId = projectIdList.get(position);
                    saveProjectId = Integer.parseInt(projectId);
                    if(projectId != null){
                        fetchTaskDetails();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getCompanyList() {
        companyDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchAllClient(companyNameList,companyIdList,clientSpinnerAdapter, companyDialogBox);
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchProjectDetails fetchProjectDetails = new FetchProjectDetails(this);
        fetchProjectDetails.FetchProjectDetails(saveCompanyId, projectNameList, projectIdList, projectSpinnerAdapter,projectDialogBox);
    }

    private void fetchTaskDetails() {
       taskDetailDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
       GetTaskDetailsAsyncall task = new GetTaskDetailsAsyncall();
       task.execute();
    }


    public class GetTaskDetailsAsyncall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            taskResponseResult = GetDataWebService.GetTaskDetail(taskDetailMethodName, saveProjectId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (taskResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteTask.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to detch task details please try again");
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
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(taskResponseResult);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            templabelStage = obj.getString("count");
                            tempTaskName = obj.getString("title");
                            tempStagPercent = obj.getString("stagepercent");
                            tempStageId = obj.getString("stageMasterId");
                            tempSubStageId = obj.getString("subStageId");
                            tempTaskId = obj.getString("TaskAssignMasterId");
                            tempUser = obj.getString("username");

                            addMoreFields();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (taskDetailDialogBox != null && taskDetailDialogBox.isShowing()) {
                taskDetailDialogBox.dismiss();
            }
        }
    }

    private void addMoreFields() {
        try {
            k++;
            flag = k;
            final LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //lparams.setMargins(0,10,0,10);
            layout[flag] = new LinearLayout(this);
            layout[flag].setLayoutParams(lparams);
            layout[flag].setId(flag);
            layout[flag].setOrientation(LinearLayout.VERTICAL);
            layout[flag].setPadding(0,15,0,10);

            taskId[flag] = tempTaskId;

            labelStage[flag] = new TextView(this);
            labelStage[flag].setLayoutParams(lparams);
            labelStage[flag].setText("Title : "+ templabelStage);
            labelStage[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            labelStage[flag].setId(flag);
            labelStage[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            labelStage[flag].setVisibility(View.GONE);
            tittleStage[flag] = templabelStage;
            templabelStage = null;

            stageName[flag] = new TextView(this);
            stageName[flag].setLayoutParams(lparams);
            stageName[flag].setText("StageName : "+tempTaskName);
            stageName[flag].setId(flag);
            stageName[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            stageName[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            if(!tempStageId.equals("0")){
                stageId[flag] = tempStageId;
            }
            else {
                stageId[flag] = tempSubStageId;
            }
            tempTaskName = null;
            tempStageId = null;
            tempSubStageId = null;

            stagePercent[flag] = new TextView(this);
            stagePercent[flag].setLayoutParams(lparams);
            stagePercent[flag].setText("Percentage : "+tempStagPercent);
            stagePercent[flag].setId(flag);
            stagePercent[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            stagePercent[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            tempStagPercent = null;

            lbluser[flag] = new TextView(this);
            lbluser[flag].setLayoutParams(lparams);
            lbluser[flag].setText("UserName : "+ tempUser);
            lbluser[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            lbluser[flag].setId(flag);
            lbluser[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));

            deleteCheckBox[flag] = new CheckBox(this);
            deleteCheckBox[flag].setLayoutParams(lparams);
            deleteCheckBox[flag].setText("Delete this task");
            deleteCheckBox[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            deleteCheckBox[flag].setId(flag);
            deleteCheckBox[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            deleteCheckBox[flag].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    position = view.getId();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        deleteCheckBox[position].setOnClickListener(new checkBoxClickListner());
                    }
                    return false;
                }
            });


            dividerLine[flag] = new View(this);
            dividerLine[flag].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            dividerLine[flag].setBackgroundColor(ContextCompat.getColor(this, R.color.list_internal_divider));
            dividerLine[flag].setId(flag);
            dividerLine[flag].setPadding(0,0,0,5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        layout[flag].addView( labelStage[flag]);
        layout[flag].addView( stageName[flag]);
        layout[flag].addView(stagePercent[flag]);
        layout[flag].addView(lbluser[flag]);
        layout[flag].addView(deleteCheckBox[flag]);
        layout[flag].addView(dividerLine[flag]);
        deleteTaskfieldLayout.addView(layout[flag]);
    }

    private class checkBoxClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
           //int position =  v.getId();
            if(deleteCheckBox[position].isChecked()){
                checkBoxValue[position] = deleteCheckBox[position].isChecked();
            }
            else {
                checkBoxValue[position] = false;
            }
        }
    }
}
