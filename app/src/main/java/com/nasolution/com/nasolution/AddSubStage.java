package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StageListAdapter;
import com.nasolution.com.nasolution.Connectivity.AddSubStageDetails;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.Connectivity.FetchProjectStage;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddSubStage extends BaseActivity implements View.OnClickListener, TextWatcher {

    private String userId;
    private String addSubStage;
    Spinner spinnerStageProjectList;
    Spinner spinnerCompanyList;
    Spinner spinnerStageList;
    EditText edittxtNoOfSubStage;
    LinearLayout subStageLayout;
    Button btnSubmitAddSubStage;
    ProgressDialog subStageDialogBox;

    //project list
    String projectName;
    String projectId;
    int saveProjectId;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    private List<String> projectIdList = new ArrayList<String>();
    private List<String> projectNameList = new ArrayList<String>();

    //company
    private int saveCompanyId;
    private String companyID;
    private String companyName;
    private String[] companyArrayList;
    private ProgressDialog companyDialogBox;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    private List<String> companyNameList = new ArrayList<String>();
    private List<String> companyIdList = new ArrayList<String>();


    //project stage
    String stageName;
    String stageId;
    int saveStageId;
    private String[] projectStageArrayList;
    private ProgressDialog stageDialogBox;
    private StageListAdapter stageListAdapter;
    private List<String> stageIdList = new ArrayList<String>();
    private List<String> stageNameList = new ArrayList<String>();
    private List<String> stagePercentList = new ArrayList<String>();

    public String saveAllStageName;
    public String saveAllStagePercent;

    public StringBuilder strStageName;
    public StringBuilder strStagePercent;
    public StringBuilder strAppendName;
    public StringBuilder strAppendPercent;
    private LinearLayout stageLinearLayout;
    private int id;
    public  int k = -1;
    public int flag;
    public static LinearLayout layout[] = new LinearLayout[100];
    public static EditText txtStageName[] = new EditText[100];
    public static EditText txtStagePercent[] = new EditText[100];
    public int totalPercent;
    public int countEditText;
    public int lastTextBox;
    public float result;
    public int convertFloatResult;
    public int remainingPercent;
    public int lastTextValue;
    Button btnUpdateProjectSubStage;

    int idOfEditText;
    int afterTextChanged;
    int totalPercentForUpdate;
    int countEditTextFor;
    String saveNoOfStage;
    int saveTxtCount;
    String beforeTextChangeValue;
    int beforeTxtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub_stage);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId = user.get(SessionManager.KEY_USER_ID);

        addSubStage = getIntent().getStringExtra("Add SubStage");

        spinnerCompanyList = (Spinner) findViewById(R.id.spinnerSubStageCompanyList);
        spinnerStageProjectList = (Spinner) findViewById(R.id.spinnerSubStageProjectList);
        spinnerStageList = (Spinner) findViewById(R.id.spinnerStageList);
        edittxtNoOfSubStage = (EditText) findViewById(R.id.txtNoOfSubStage);
        subStageLayout = (LinearLayout) findViewById(R.id.subStageLayout);
        btnSubmitAddSubStage = (Button) findViewById(R.id.btnSubmitAddSubStage);
        btnUpdateProjectSubStage = (Button) findViewById(R.id.btnUpdateProjectSubStageAdd);

        edittxtNoOfSubStage.addTextChangedListener(this);
        btnSubmitAddSubStage.setOnClickListener(this);
        btnUpdateProjectSubStage.setOnClickListener(this);

        btnUpdateProjectSubStage.setVisibility(View.GONE);
        btnSubmitAddSubStage.setVisibility(View.VISIBLE);
        edittxtNoOfSubStage.setVisibility(View.GONE);

        getSpinnerCompanyList();
        getSpinnerProjectList();
        getSpinnerProjectStageList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmitAddSubStage){
            collectAllData();
            submitSubStageDetails();
        }
        else if(v.getId() == R.id.btnUpdateProjectSubStageAdd){
            int currentIdOfEditText = idOfEditText;
            int newPercentValue = afterTextChanged;

            if (countEditText == 1) {
                if (newPercentValue != totalPercent) {
                    Toast.makeText(AddSubStage.this, "You Need To add More sub-stages", Toast.LENGTH_LONG).show();
                    btnUpdateProjectSubStage.setVisibility(View.VISIBLE);
                    btnSubmitAddSubStage.setVisibility(View.GONE);
                    txtStagePercent[0].setText(totalPercent);
                } else {
                    btnUpdateProjectSubStage.setVisibility(View.GONE);
                    btnSubmitAddSubStage.setVisibility(View.VISIBLE);
                }
            } else {
                if (afterTextChanged > totalPercent) {
                    Toast.makeText(AddSubStage.this, "Please Enter Value Below "+totalPercent+".", Toast.LENGTH_LONG).show();
                } else {
                    totalPercentForUpdate = totalPercent - newPercentValue;
                    countEditTextFor = countEditText - 1;
                    calculateUpdatedPercentage(countEditTextFor, totalPercentForUpdate, currentIdOfEditText, newPercentValue);
                }
            }
        }
    }

    public void calculateUpdatedPercentage(int countEditTextFor, int totalPercentForUpdate, int currentIdOfEditText, int newPercentValue) {

        int idOfEditedEditText = currentIdOfEditText;
        int valueOfEditedEditText = newPercentValue;
        btnUpdateProjectSubStage.setVisibility(View.GONE);
        btnSubmitAddSubStage.setVisibility(View.VISIBLE);
        k = flag;
        result = totalPercentForUpdate / countEditTextFor;
        convertFloatResult = Math.round(result);
        remainingPercent = totalPercentForUpdate - (convertFloatResult * countEditTextFor);
        lastTextValue = convertFloatResult + remainingPercent;

        try {
            if (remainingPercent == 0) {

                for (int j = 0; j <= lastTextBox; j++) {
                    if (j != idOfEditedEditText) {
                        txtStagePercent[j].setText(String.valueOf(convertFloatResult));
                    } else {
                        if (idOfEditedEditText == lastTextBox) {
                            txtStagePercent[lastTextBox - 1].setText(String.valueOf(lastTextValue));
                        } else {
                            txtStagePercent[idOfEditedEditText].setText(String.valueOf(valueOfEditedEditText));
                        }
                    }
                }
            } else {
                for (int j = 0; j < lastTextBox; j++) {

                    if (j == idOfEditedEditText) {
                        txtStagePercent[idOfEditedEditText].setText(String.valueOf(valueOfEditedEditText));
                    } else {
                        txtStagePercent[j].setText(String.valueOf(convertFloatResult));
                    }

                    if (idOfEditedEditText == lastTextBox) {
                        txtStagePercent[idOfEditedEditText - 1].setText(String.valueOf(lastTextValue));
                    } else {
                        txtStagePercent[lastTextBox].setText(String.valueOf(lastTextValue));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitSubStageDetails() {
        if (edittxtNoOfSubStage.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else if (checkAllField() == true) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else {
            if (projectId == null) {
                Toast.makeText(AddSubStage.this, "Please select project ", Toast.LENGTH_LONG).show();
            }
            else if (stageId == null) {
                Toast.makeText(AddSubStage.this, "Please select stage ", Toast.LENGTH_LONG).show();
            } else {
                int saveUserId = Integer.parseInt(userId);
                saveStageId = Integer.parseInt(stageId);
                subStageDialogBox = ProgressDialog.show(AddSubStage.this, "", "Adding sub stage information please wait...", true);
                AddSubStageDetails addSubStageDetails = new AddSubStageDetails(this);
                addSubStageDetails.AddSubStage(saveProjectId, saveStageId, saveAllStageName, saveAllStagePercent, subStageDialogBox,saveUserId);
            }
        }
    }

    private void collectAllData() {

        strStageName = new StringBuilder();
        strStagePercent = new StringBuilder();
        strAppendName = new StringBuilder();
        strAppendPercent = new StringBuilder();

        for (int i = 0; i <= k; i++) {

            if (!txtStageName[i].getText().toString().equals("null") || !txtStagePercent[i].getText().toString().equals("null")) {
                strStageName.append(txtStageName[i].getText().toString() + ",");
                strStagePercent.append(txtStagePercent[i].getText().toString() + ",");
            }
        }
        strAppendName = strStageName;
        strAppendPercent = strStagePercent;

        saveAllStageName = strAppendName.toString();
        saveAllStagePercent = strAppendPercent.toString();
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
        spinnerStageProjectList.setAdapter(projectSpinnerAdapter);
        spinnerStageProjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int i = 0; i <= k; i++) {
                        subStageLayout.removeView(layout[i]);
                        txtStagePercent[i].setHint("");
                    }
                    k = -1;
                    edittxtNoOfSubStage.setText("");
                    projectName = parent.getItemAtPosition(position).toString();
                    projectId = projectIdList.get(position);
                    if(projectId != null){
                        saveProjectId = Integer.parseInt(projectId);
                        stageDialogBox = ProgressDialog.show(AddSubStage.this,"","Fetching stage list details please wait...");
                        getProjectStage();
                    }
                    spinnerStageList.setSelection(stageNameList.indexOf(0));
                    stageListAdapter.notifyDataSetChanged();
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
                    for(int i=0; i<= k;i++){
                        subStageLayout.removeView(layout[i]);
                        txtStagePercent[i].setHint("");
                    }
                    edittxtNoOfSubStage.setVisibility(View.VISIBLE);
                    edittxtNoOfSubStage.setText("");
                    stageName = parent.getItemAtPosition(position).toString();
                    stageId = stageIdList.get(position);
                    saveStageId = Integer.parseInt(stageId);
                    totalPercent = Integer.parseInt(stagePercentList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(s.toString().trim().length()== 0){
            return;
        }
       beforeTextChangeValue = edittxtNoOfSubStage.getText().toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().trim().length()== 0){
            return;
        }
        if(beforeTextChangeValue != null ){
            removeOldViews();
        }
        saveNoOfStage = edittxtNoOfSubStage.getText().toString();
        if(saveNoOfStage.equals("0")) {
            Toast.makeText(AddSubStage.this,"No of Stage should be greater than 0",Toast.LENGTH_LONG).show();
        }
        else if(saveNoOfStage!= null) {
            saveTxtCount = Integer.parseInt(saveNoOfStage);
            for (int c = 0; c < saveTxtCount; c++) {
                addMoreFields();
            }
        }
    }

    private void removeOldViews() {
        beforeTxtCount = Integer.parseInt(beforeTextChangeValue);
        for (int a = 0; a < beforeTxtCount; a++) {
            layout[a].removeView(txtStageName[a]);
            layout[a].removeView(txtStagePercent[a]);
            subStageLayout.removeView(layout[a]);
            k = k-1;
        }
    }

    private void addMoreFields() {
        try {
            k++;
            flag = k;
            int a = flag + 1;
            final LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layout[flag] = new LinearLayout(this);
            layout[flag].setLayoutParams(lparams);
            layout[flag].setId(flag);

            txtStageName[flag] = new EditText(this);
            txtStageName[flag].setLayoutParams(lparams);
            txtStageName[flag].setHint("stage " + a + "");
            txtStageName[flag].setId(flag);

            txtStagePercent[flag] = new EditText(this);
            txtStagePercent[flag].setLayoutParams(lparams);
            txtStagePercent[flag].setHint("%");
            txtStagePercent[flag].setId(flag);
            //txtStagePercent[flag].setInputType(InputType.TYPE_CLASS_NUMBER);

            countEditText = saveTxtCount;
            calculatePercentage(countEditText,flag);

//            txtStagePercent[flag].setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        idOfEditText = v.getId();
//                        txtStagePercent[idOfEditText].addTextChangedListener(stagePercentChangeListener);
//                        btnUpdateProjectSubStage.setVisibility(View.VISIBLE);
//                        btnSubmitAddSubStage.setVisibility(View.GONE);
//                    }
//                }
//            });

            txtStagePercent[flag].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        idOfEditText = view.getId();
                        txtStagePercent[idOfEditText].addTextChangedListener(stagePercentChangeListener);
                        btnUpdateProjectSubStage.setVisibility(View.VISIBLE);
                        btnSubmitAddSubStage.setVisibility(View.GONE);
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        layout[flag].addView(txtStageName[flag]);
        layout[flag].addView(txtStagePercent[flag]);
        subStageLayout.addView(layout[flag]);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode==KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return true;
        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }

    private TextWatcher stagePercentChangeListener = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 0) {
                return;
            }
            String newValue = String.valueOf(s);
            afterTextChanged = Integer.parseInt(newValue);
        }
    };

    private void calculatePercentage(int countEditText,int flag) {
        k = flag;
        lastTextBox = countEditText - 1;
        result = totalPercent / countEditText;
        convertFloatResult = Math.round(result);
        remainingPercent = totalPercent - (convertFloatResult * countEditText);
        lastTextValue = convertFloatResult + remainingPercent;

        try {
            if (remainingPercent == 0) {
                for (int j = 0; j <= lastTextBox; j++) {
                    txtStagePercent[j].setText(String.valueOf(convertFloatResult));
                }
                txtStagePercent[lastTextBox].setText(String.valueOf(lastTextValue));

            } else {
                for (int j = 0; j < lastTextBox; j++) {
                    txtStagePercent[j].setText(String.valueOf(convertFloatResult));
                }
                txtStagePercent[lastTextBox].setText(String.valueOf(lastTextValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkAllField() {
        boolean textval = false;

        for (int i = 0; i <= k; i++) {
            if (TextUtils.isEmpty( txtStageName[i].getText().toString()) || TextUtils.isEmpty(txtStagePercent[i].getText().toString())) {
                textval = true;
                break;
            } else {
                textval = false;
            }
        }
        return textval;
    }

    private void getCompanyList() {
        companyDialogBox = ProgressDialog.show(this, "", "Fetching Company Details Please Wait...", true);
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchAllClient(companyNameList,companyIdList,clientSpinnerAdapter, companyDialogBox);
    }


    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(this, "", "Getting project list please wait...");
        FetchProjectDetails fetchProjectDetails = new FetchProjectDetails(this);
        fetchProjectDetails.FetchProjectDetails(saveCompanyId, projectNameList, projectIdList, projectSpinnerAdapter,projectDialogBox);
    }

    private void getProjectStage() {
        FetchProjectStage fetchProjectStage = new FetchProjectStage(this);
        fetchProjectStage.ProjectStageWithPercent(stageIdList, stageNameList,saveProjectId,stagePercentList,stageDialogBox,stageListAdapter);
    }
}
