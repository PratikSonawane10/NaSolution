package com.nasolution.com.nasolution;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.FetchAllUser;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.Connectivity.UpdateTaskDetails;
import com.nasolution.com.nasolution.SessionManager.SessionManager;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class UpdateTask extends BaseActivity implements View.OnClickListener {

    LinearLayout updateTaskfieldLayout;
    Spinner spinnerCompanyList;
    Spinner spinnerUpdateProjectList;
    Button btnUpdateTask;
    ProgressDialog taskDetailDialogBox;
    ProgressDialog submitUpdateDialogBox;

    //company
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

    int k = 0;
    int flag;
    public static LinearLayout layout[] = new LinearLayout[100];
    public static Spinner spinnerUser[] = new Spinner[100];
    public static TextView stageName[] = new TextView[100];
    public static TextView stagePercent[] = new TextView[100];
    public static TextView labelStage[] = new TextView[100];
    public static View dividerLine[] = new View[100];
    public static EditText date[] = new EditText[100];

    public static String tittleStage[] = new String[100];
    public static String stageId[] = new String[100];
    public static String userName[] = new String[100];
    public static String userId[] = new String[100];
    public static String dateValue[] = new String[100];

    private List<String> userNameList = new ArrayList<String>();
    private List<String> userIdList = new ArrayList<String>();
    private ProgressDialog userDialogBox;
    private CustomSpinnerAdapter adapter;

    public StringBuilder strUserId;
    public StringBuilder strStageID;
    public StringBuilder strCountName;
    public StringBuilder strSubmitDate;
    public StringBuilder strAppendUserId;
    public StringBuilder strAppendStageId;
    public StringBuilder strAppendCountName;
    public StringBuilder strAppendSubmitDate;

    String saveUpdateUserId;
    String saveUpdateStageId;
    String saveUpdateCountName;
    String saveUpdateDate;
    int spinnerPosition;
    int addedBy;
    int datePosition;
    public SimpleDateFormat dateFormatter;
    public DatePickerDialog datePickerDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        addedBy = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        spinnerCompanyList = (Spinner) findViewById(R.id.spinnerUpdateTaskCompanyList);
        spinnerUpdateProjectList = (Spinner) findViewById(R.id.spinnerUpdateTaskProjectList);
        updateTaskfieldLayout = (LinearLayout) findViewById(R.id.updateTaskFieldsLayout);
        btnUpdateTask = (Button) findViewById(R.id.btnUpdateTask);

        btnUpdateTask.setOnClickListener(this);
        getAllUser();
        getSpinnerCompanyList();
        getSpinnerProjectList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdateTask){
            if(projectId == null){
                Toast.makeText(this,"Please select project", LENGTH_LONG).show();
            }
            else {
                if (taskResponseResult.equals("Task Details Not Found")) {
                    Toast.makeText(this,"Please fill task details ", LENGTH_LONG).show();
                }
                else {
                    collectAllData();
                    submitUpdateDialogBox = ProgressDialog.show(UpdateTask.this, "", "Updating task details please wait...");
                    UpdateTaskDetails updateTaskDetails = new UpdateTaskDetails(this);
                    updateTaskDetails.UpdateTask(addedBy, saveProjectId, saveUpdateStageId, saveUpdateUserId, saveUpdateDate, saveUpdateCountName, submitUpdateDialogBox);
                }
            }
        }
    }

    private void collectAllData() {

        strUserId = new StringBuilder();
        strStageID = new StringBuilder();
        strSubmitDate = new StringBuilder();
        strCountName = new StringBuilder();

        strAppendUserId = new StringBuilder();
        strAppendStageId = new StringBuilder();
        strAppendSubmitDate = new StringBuilder();
        strAppendCountName = new StringBuilder();

        for (int i = 1; i <= k; i++) {
            if (userId[i] != null ) {
                if(!date[i].getText().toString().isEmpty()){
                    strUserId.append(userId[i] + ",");
                    strStageID.append(stageId[i] + ",");
                    strSubmitDate.append(dateValue[i] + ",");
                    strCountName.append(tittleStage[i]+ ",");
                }
            }
        }
        strAppendUserId = strUserId;
        strAppendStageId = strStageID;
        strAppendSubmitDate = strSubmitDate;
        strAppendCountName = strCountName;

        saveUpdateUserId = strAppendUserId.toString();
        saveUpdateStageId = strAppendStageId.toString();
        saveUpdateDate = strAppendSubmitDate.toString();
        saveUpdateCountName = strAppendCountName.toString();
    }

    private boolean checkAllFieldIsEmpty() {
        boolean textval = false;

        for (int i = 0; i <= k; i++) {
            if (date[i].getText().toString().isEmpty()){
                textval = true;
                break;
            } else {
                textval = false;
            }
        }
        return textval;
    }

    private void getAllUser() {
        userDialogBox = ProgressDialog.show(UpdateTask.this, "", "Getting project list please wait...");
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
                        updateTaskfieldLayout.removeView(layout[i]);
                    }
                    k = 0;
                    getSpinnerProjectList();
                    spinnerUpdateProjectList.setSelection(projectNameList.indexOf(0));
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
        spinnerUpdateProjectList.setAdapter(projectSpinnerAdapter);
        spinnerUpdateProjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int i = 0; i <= k; i++) {
                        updateTaskfieldLayout.removeView(layout[i]);
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
        companyDialogBox = ProgressDialog.show(this, "", "Fetching details Please Wait...", true);
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchAllClient(companyNameList,companyIdList,clientSpinnerAdapter, companyDialogBox);
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchProjectDetails fetchProjectDetails = new FetchProjectDetails(this);
        fetchProjectDetails.FetchProjectDetails(saveCompanyId, projectNameList, projectIdList, projectSpinnerAdapter,projectDialogBox);
    }

    private void fetchTaskDetails() {
        taskDetailDialogBox  = ProgressDialog.show(this, "", "Fetching details Please Wait...", true);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTask.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch details please try again ");
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
            else if (taskResponseResult.equals("Task Details Not Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTask.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch details please try again ");
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
                    JSONArray jsonArray = new JSONArray(taskResponseResult);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            templabelStage = obj.getString("count");
                            tempTaskName = obj.getString("title");
                            tempStagPercent = obj.getString("stagepercent");
                            tempStageId = obj.getString("stageMasterId");
                            tempSubStageId = obj.getString("subStageId");
                            tempUser = obj.getString("username");
                            tempDate= obj.getString("submiton");
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
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Add User");

        try {
            k++;
            flag = k;
            final LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
          //  lparams.setMargins(0,10,0,10);
            layout[flag] = new LinearLayout(UpdateTask.this);
            layout[flag].setLayoutParams(lparams);
            layout[flag].setId(flag);
            layout[flag].setOrientation(LinearLayout.VERTICAL);
            layout[flag].setPadding(0,15,0,10);

            labelStage[flag] = new TextView(UpdateTask.this);
            labelStage[flag].setLayoutParams(lparams);
            labelStage[flag].setText("Title : "+ templabelStage);
            labelStage[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            labelStage[flag].setId(flag);
            labelStage[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            labelStage[flag].setVisibility(View.GONE);
            tittleStage[flag] = templabelStage;
            templabelStage = null;

            stageName[flag] = new TextView(UpdateTask.this);
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

            stagePercent[flag] = new TextView(UpdateTask.this);
            stagePercent[flag].setLayoutParams(lparams);
            stagePercent[flag].setText("Percentage : "+tempStagPercent);
            stagePercent[flag].setId(flag);
            stagePercent[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            stagePercent[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            tempStagPercent = null;

            spinnerUser[flag] = new Spinner(UpdateTask.this);
            spinnerUser[flag].setLayoutParams(lparams);
            spinnerUser[flag].setId(flag);
            if(userNameList != null){
                adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, userNameList);
            }
            else{
                adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, spinnerArray);
            }
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUser[flag].setAdapter(adapter);
            if (tempUser != null) {
                int spinnerStatePosition = adapter.getPosition(tempUser);
                spinnerUser[flag].setSelection(spinnerStatePosition);
                userId[flag] = userIdList.get(spinnerStatePosition);
                adapter.notifyDataSetChanged();
            }
            spinnerUser[flag].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    spinnerPosition = view.getId();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        spinnerUser[spinnerPosition].setOnItemSelectedListener(new UpdateTask.SpinnerClickListner());
                    }
                    return false;
                }
            });

            date[flag] =  new EditText(UpdateTask.this);
            date[flag].setLayoutParams(lparams);
            date[flag].setHint("Select Date");
            date[flag].setId(flag);
            date[flag].setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size));
            date[flag].setTextColor(ContextCompat.getColor(this, R.color.menu_text_color));
            date[flag].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    datePosition = view.getId();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        setDateTimeField();
                        datePickerDialog.show();
                        // date[datePosition].setOnClickListener(new EditTextClick());
                    }
                    return false;
                }
            });
            if (tempDate !=null){
                date[flag].setText(tempDate);
                dateValue[flag] = date[flag].getText().toString();
            }

            dividerLine[flag] = new View(UpdateTask.this);
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
        layout[flag].addView(spinnerUser[flag]);
        layout[flag].addView(date[flag]);
        layout[flag].addView(dividerLine[flag]);
        updateTaskfieldLayout.addView(layout[flag]);
    }

    private class SpinnerClickListner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position > 0) {
                userName[flag] = parent.getItemAtPosition(position).toString();
                userId[spinnerPosition] = userIdList.get(position);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set( year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                String finalDate = dateFormatter.format(newDate.getTime());
                date[datePosition].setText(finalDate);
                dateValue[datePosition] = date[datePosition].getText().toString();
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
