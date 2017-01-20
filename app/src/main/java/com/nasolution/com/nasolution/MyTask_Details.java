package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Connectivity.Fetch_MyTask;
import com.nasolution.com.nasolution.Connectivity.SaveTaskRemark;

public class MyTask_Details extends BaseActivity implements View.OnClickListener{



    String taskAssignMasterId;
    String clientname;
    String title;
    String projectname;
    String stagepercent;
    String stageMasterId;
    String assignby;
    String assignDate;
    String submitonDate;
    String subStageId;
    String count;
    String status;
    String remark;
    String value;

    TextView txtstagePercent;
    TextView txtassignBy;
    TextView txtcompletionDate;
    TextView txttitle;
    TextView txtclientName;
    TextView txtprojectName;
    TextView txtassignDate;


    RadioGroup rdgTaskStatus;
    RadioButton rdbNew;
    RadioButton rdbinProcess;
    RadioButton rdbComplete;
    EditText txtremark;

    ProgressDialog progressDialog;

    FloatingActionButton taskDetailsSubmit;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_task_details);

        Intent intent = getIntent();
        if (null != intent) {
            taskAssignMasterId = intent.getStringExtra("TaskAssignMasterId");
            stageMasterId = intent.getStringExtra("stageMasterId");
            subStageId = intent.getStringExtra("subStageId");
            clientname = intent.getStringExtra("clientname");
            projectname = intent.getStringExtra("projectname");
            title = intent.getStringExtra("title");
            submitonDate = intent.getStringExtra("submiton");
            assignDate = intent.getStringExtra("submiton");
            stagepercent = intent.getStringExtra("stagepercent");
            assignby = intent.getStringExtra("assignby");
            count = intent.getStringExtra("count");
            status = intent.getStringExtra("status");
        }

        txtremark = (EditText) findViewById(R.id.remark);

        txtprojectName = (TextView) findViewById(R.id.projectName);
        txtclientName = (TextView) findViewById(R.id.clientName);
        txttitle = (TextView) findViewById(R.id.title);
        txtcompletionDate = (TextView) findViewById(R.id.completionDate);
        txtassignDate = (TextView) findViewById(R.id.assignDate);
        txtstagePercent = (TextView) findViewById(R.id.stagePercent);
        txtassignBy = (TextView) findViewById(R.id.assignBy);

        rdgTaskStatus = (RadioGroup) this.findViewById(R.id.rdgTaskStatus);
        rdbNew = (RadioButton) findViewById(R.id.rdbNew);
        rdbinProcess = (RadioButton) findViewById(R.id.rdbinProcess);
        rdbComplete = (RadioButton) findViewById(R.id.rdbComplete);

        taskDetailsSubmit = (FloatingActionButton) this.findViewById(R.id.taskDetailsSubmit);
        taskDetailsSubmit.setOnClickListener(this);

        txtprojectName.setText(projectname);
        txtclientName.setText(clientname);
        txttitle.setText(title);
        txtcompletionDate.setText(submitonDate);
        txtassignDate.setText(assignDate);
        txtstagePercent.setText(stagepercent);
        txtassignBy.setText(assignby);

        if(status.equals("New")){
            rdbNew.setChecked(true);
        }else if(status.equals("In Process")){
            rdbinProcess.setChecked(true);

        }else if(status.equals("Completed")){
            rdbComplete.setChecked(true);
        }

        rdgTaskStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdbNew) {
                    rdbComplete.setChecked(false);
                    rdbinProcess.setChecked(false);
                    status = "New";

                } else  if (checkedId == R.id.rdbinProcess) {
                    rdbComplete.setChecked(false);
                    rdbNew.setChecked(false);
                    status = "In Process";

                } else  if (checkedId == R.id.rdbComplete) {
                    rdbNew.setChecked(false);
                    rdbinProcess.setChecked(false);
                    status = "Completed";

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.taskDetailsSubmit) {
            remark = txtremark.getText().toString();
            progressDialog = ProgressDialog.show(this, "", "Details Adding please wait...", true);

            try {
                SaveTaskRemark saveTaskRemark = new SaveTaskRemark(MyTask_Details.this);
                saveTaskRemark.InsertTaskRemark(taskAssignMasterId,status, count,remark,progressDialog);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

