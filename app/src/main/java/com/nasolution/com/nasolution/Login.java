package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.WebServices.GetDataWebService;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;
import com.nasolution.com.nasolution.InternetConnectivity.CheckInternetConnection;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private long TIME = 5000;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView lbljoinUs;
    private TextView txtRegister;
    private TextView txtForgotPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;

    private String email;
    private String password;
    private String method = "CreateLogin";
    public String loginResponseResult;
    public String empCodeMethod = "GetEmpCode";
    public String EmpCodeResponseResult;
    public String saveEmpcode;

    SessionManager sessionManager;
    public  String saveUserMasterId;
    public  String saveEmail;
    public String saveName;
    public String savePassword;

    public String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextEmail = (EditText) findViewById(R.id.txtLoginEmail);
        editTextPassword = (EditText) findViewById(R.id.txtLoginpassword);
        txtForgotPassword = (TextView) findViewById(R.id.lblforgetpassword);
        txtRegister = (TextView) findViewById(R.id.lblregister);
        btnSignIn = (Button) findViewById(R.id.btnlogin);

        txtForgotPassword.setVisibility(View.GONE);
        btnSignIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

        if (CheckInternetConnection.getInstance(this).isOnline()) {
            if (v.getId() == R.id.btnlogin) {
                v.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, TIME);
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (editTextEmail.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter your Email Id", Toast.LENGTH_LONG).show();
                } else if (editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please enter Password", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Loging In...");
                    progressDialog.show();
                    LoginAsyncCallWS task = new LoginAsyncCallWS();
                    task.execute();
                }
            } else if (v.getId() == R.id.lblforgetpassword) {

            } else if (v.getId() == R.id.lblregister) {
                RegisterAsyncCallWS task = new RegisterAsyncCallWS();
                task.execute();
            }
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet connection!");
            builder.setMessage("Please check your internet connection.");
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public class LoginAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            loginResponseResult = InsertDataWebservice.CreateLogin(email,password,method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(loginResponseResult.equals("Invalid UserName & Password")) {
                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage(loginResponseResult);
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
            else if(loginResponseResult.equals("No Network Found")) {
                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to login. please try again later.");
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
                progressDialog.hide();
                sessionManager = new SessionManager(Login.this);
                try {
                    JSONArray jArr = new JSONArray(loginResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        saveUserMasterId = obj.getString("user_MasterId");
                        saveName = obj.getString("name");
                        saveEmail = obj.getString("email");
                        savePassword = obj.getString("pasword");
                        sessionManager.createUserLoginSession(saveUserMasterId,saveName,saveEmail,savePassword);
                        editTextEmail.setText("");
                        editTextPassword.setText("");
                        Intent gotoHome = new Intent(Login.this,Home.class);
                        startActivity(gotoHome);
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class RegisterAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            EmpCodeResponseResult = GetDataWebService.GetEmpCode(empCodeMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(EmpCodeResponseResult.isEmpty()) {
                progressDialog.hide();
                editTextEmail.setText("");
                editTextPassword.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to get employee code please try again later");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
            else {
                try {
                    saveEmpcode = EmpCodeResponseResult;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setTitle("Result");
                    builder.setMessage("User added successfully");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int which) {
                            alert.dismiss();
                            Intent gotoRegister = new Intent(Login.this,Registration.class);
                            gotoRegister.putExtra("EmpCode",saveEmpcode);
                            startActivity(gotoRegister);
                        }
                    });
                    AlertDialog alertBox = builder.create();
                    alertBox.show();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

    @Override
    protected void onResume() {
        super.onResume();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
