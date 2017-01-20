package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddProjectForStage;
import com.nasolution.com.nasolution.Connectivity.AddStage;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StageForm extends BaseActivity implements View.OnClickListener {

    Spinner spinnerProjectStage;
    Spinner spinnerProjectList;
    Button btnAddMoreStage;
    Button btnRemoveStage;
    Button btnSubmitProjectStage;
    Button btnUpdateProjectStage;

    String projectName;
    int projectIdForStage;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private String projectResponseResult;
    private ProgressDialog projectDetailDaialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    public List<String> projectIdList = new ArrayList<String>();
    public List<String> projectNameList = new ArrayList<String>();

    private String stageName;

    private CustomSpinnerAdapter customSpinnerAdapter;
    private List<String> stageNameList = new ArrayList<String>();

    public String saveAllStageName;
    public String saveAllStagePercent;
    private int clientID;
    public StringBuilder strStageName;
    public StringBuilder strStagePercent;
    public StringBuilder strAppendName;
    public StringBuilder strAppendPercent;
    private LinearLayout stageLinearLayout;
    public int k = -1;
    public int flag;
    public static LinearLayout layout[] = new LinearLayout[100];
    public static EditText txtStageName[] = new EditText[100];
    public static EditText txtStagePercent[] = new EditText[100];
    int totalPercent = 100;
    int totalPercentForUpdate;
    int countEditText;
    int countEditTextFor;
    int lastTextBox;
    float result;
    int convertFloatResult;
    int remainingPercent;
    int lastTextValue;
    int afterTextChanged;
    int checked = 0;
    int idOfEditText;
    int saveProjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_form);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        clientID = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        saveProjectId = Integer.parseInt(getIntent().getStringExtra("ProjectId"));
        spinnerProjectStage = (Spinner) findViewById(R.id.spinnerAddProjectStage);
        spinnerProjectList = (Spinner) findViewById(R.id.spinnerProjectList);
        stageLinearLayout = (LinearLayout) findViewById(R.id.stageProjectList);
        btnAddMoreStage = (Button) findViewById(R.id.btnAddMoreStage);
        btnSubmitProjectStage = (Button) findViewById(R.id.btnSubmitProjectStage);
        btnRemoveStage = (Button) findViewById(R.id.btnRemoveStage);
        btnUpdateProjectStage = (Button) findViewById(R.id.btnUpdateProjectStage);

        btnAddMoreStage.setOnClickListener(this);
        btnRemoveStage.setOnClickListener(this);
        btnSubmitProjectStage.setOnClickListener(this);
        btnUpdateProjectStage.setOnClickListener(this);

        btnUpdateProjectStage.setVisibility(View.GONE);
        btnSubmitProjectStage.setVisibility(View.VISIBLE);
        btnRemoveStage.setVisibility(View.GONE);
        btnAddMoreStage.setVisibility(View.GONE);
        spinnerProjectList.setVisibility(View.GONE);

        getProjectStage();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddMoreStage) {
            addMoreFields();
            btnRemoveStage.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.btnRemoveStage) {
            if (countEditText == 1) {
                btnRemoveStage.setVisibility(View.GONE);
            } else {
                stageLinearLayout.removeView(layout[flag]);
                countEditText = countEditText - 1;
                flag = flag - 1;
                if (countEditText == 1) {
                    btnRemoveStage.setVisibility(View.GONE);
                }
                calculatePercentage(countEditText, flag);
            }
        } else if (v.getId() == R.id.btnUpdateProjectStage) {
            int currentIdOfEditText = idOfEditText;
            int newPercentValue = afterTextChanged;

            if (countEditText == 1) {
                if (newPercentValue != 100) {
                    Toast.makeText(StageForm.this, "You Need To add More stages", Toast.LENGTH_LONG).show();
                    btnUpdateProjectStage.setVisibility(View.VISIBLE);
                    btnSubmitProjectStage.setVisibility(View.GONE);
                    txtStagePercent[0].setText("100");
                } else {
                    btnUpdateProjectStage.setVisibility(View.GONE);
                    btnSubmitProjectStage.setVisibility(View.VISIBLE);
                }
            } else {
                if (afterTextChanged > 100) {
                    Toast.makeText(StageForm.this, "Please Enter Value Below Hundred.", Toast.LENGTH_LONG).show();
                } else {
                    totalPercentForUpdate = 100 - newPercentValue;
                    countEditTextFor = countEditText - 1;
                    calculateUpdatedPercentage(countEditTextFor, totalPercentForUpdate, currentIdOfEditText, newPercentValue);
                }
            }
        } else if (v.getId() == R.id.btnSubmitProjectStage) {
            if (checked == 0) {
                try {
                    AddProjectForStage addProjectForStage = new AddProjectForStage(StageForm.this);
                    addProjectForStage.insertProjectForSatges(saveProjectId, projectIdForStage,clientID, projectDetailDaialogBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (afterTextChanged > 100) {
                    Toast.makeText(StageForm.this, "Please Enter Value Below Hundred.", Toast.LENGTH_LONG).show();
                } else {
                    collectAllData();
                }
            }
        }
    }

    public void calculateUpdatedPercentage(int countEditTextFor, int totalPercentForUpdate, int currentIdOfEditText, int newPercentValue) {

        int idOfEditedEditText = currentIdOfEditText;
        int valueOfEditedEditText = newPercentValue;
        btnUpdateProjectStage.setVisibility(View.GONE);
        btnSubmitProjectStage.setVisibility(View.VISIBLE);
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

    private void getProjectStage() {
        stageNameList = new ArrayList<String>();
        stageNameList.add("Select Stage");
        stageNameList.add("Copy From Existing Project");
        stageNameList.add("Enter Manually");
        customSpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item, stageNameList);
        customSpinnerAdapter.notifyDataSetChanged();
        customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectStage.setAdapter(customSpinnerAdapter);
        spinnerProjectStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stageName = (String) parent.getItemAtPosition(position);

                    if (stageName.equals("Copy From Existing Project")) {
                        getProjectStageList();
                        btnAddMoreStage.setVisibility(View.GONE);
                        spinnerProjectList.setVisibility(View.VISIBLE);
                        for (int i = 0; i <= k; i++) {
                            stageLinearLayout.removeView(layout[i]);
                        }
                        k = -1;
                        checked = 0;
                    } else if (stageName.equals("Enter Manually")) {
                        checked = 1;
                        spinnerProjectList.setVisibility(View.GONE);
                        btnAddMoreStage.setVisibility(View.VISIBLE);
                        addMoreFields();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjectStageList() {
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
                    projectIdForStage = Integer.parseInt(projectIdList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(StageForm.this, "", "Getting project details please wait...");
        FetchProjectList fetchProjectList = new FetchProjectList(this);
        fetchProjectList.GetAllProject(projectNameList, projectIdList, projectSpinnerAdapter, projectDialogBox);
    }

    private void collectAllData() {
        int totalpercentOfAllEditText = 0;
        for (int i = 0; i <= k; i++) {
            int valueOfCurrentEditText;
            valueOfCurrentEditText = Integer.parseInt(txtStagePercent[i].getText().toString());
            totalpercentOfAllEditText = totalpercentOfAllEditText + valueOfCurrentEditText;
        }

        if (totalpercentOfAllEditText != 100) {
            Toast.makeText(StageForm.this, "You Are Updating Some Value.", Toast.LENGTH_LONG).show();
        } else {

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
        submitCollectedData();
    }

    public void submitCollectedData() {

        if (checkAllField() == true) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else {
            projectDetailDaialogBox = ProgressDialog.show(StageForm.this, "", "Stages Adding please wait...");
            try {
                AddStage addStage = new AddStage(StageForm.this);
                addStage.insertAddSatges(saveProjectId, clientID, saveAllStageName, saveAllStagePercent, projectDetailDaialogBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkAllField() {
        boolean textval = false;

        for (int i = 0; i <= k; i++) {
            if (TextUtils.isEmpty(txtStageName[i].getText().toString()) || TextUtils.isEmpty(txtStagePercent[i].getText().toString())) {
                textval = true;
                break;
            } else {
                textval = false;
            }
        }
        return textval;
    }

    public void addMoreFields() {
        try {
            k++;
            flag = k;
            int a = flag + 1;
            final LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layout[flag] = new LinearLayout(StageForm.this);
            layout[flag].setLayoutParams(lparams);
            layout[flag].setId(flag);

            txtStageName[flag] = new EditText(StageForm.this);
            txtStageName[flag].setLayoutParams(lparams);
            txtStageName[flag].setHint("stage " + a + "");
            txtStageName[flag].setId(flag);

            txtStagePercent[flag] = new EditText(StageForm.this);
            txtStagePercent[flag].setLayoutParams(lparams);
            txtStagePercent[flag].setHint("in %");
            txtStagePercent[flag].setInputType(InputType.TYPE_CLASS_NUMBER);
            txtStagePercent[flag].setId(flag);

            countEditText = flag + 1;
            calculatePercentage(countEditText, flag);

//            txtStagePercent[flag].setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        idOfEditText = v.getId();
//                        txtStagePercent[idOfEditText].addTextChangedListener(stagePercentChangeListener);
//                        btnUpdateProjectStage.setVisibility(View.VISIBLE);
//                        btnSubmitProjectStage.setVisibility(View.GONE);
//                    }
//                }
//            });

            txtStagePercent[flag].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        idOfEditText = view.getId();
                        txtStagePercent[idOfEditText].addTextChangedListener(stagePercentChangeListener);
                        btnUpdateProjectStage.setVisibility(View.VISIBLE);
                        btnSubmitProjectStage.setVisibility(View.GONE);
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        layout[flag].addView(txtStageName[flag]);
        layout[flag].addView(txtStagePercent[flag]);
        stageLinearLayout.addView(layout[flag]);
    }

    private void calculatePercentage(int countEditText, int flag) {
        k = flag;
        if (flag == 0) {
            lastTextBox = countEditText;
        } else {
            lastTextBox = countEditText - 1;
        }
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
            btnUpdateProjectStage.setVisibility(View.GONE);
            btnSubmitProjectStage.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            String getchangedValue = String.valueOf(s);
            afterTextChanged = Integer.parseInt(getchangedValue);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage("Please Add Stage Details details");
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
