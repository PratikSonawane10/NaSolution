package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.AddUser;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddUserDetails {

    private static Context context;
    private static String savename;
    private static String saveempcode;
    private static String saveemail;
    private static String savemobileno;
    private static String saveusername;
    private static String savepassword;
    private static String saveworksfor;
    private static String savedob;
    private static String saveaddress;
    private static String saveuserrole;
    private static String saveprofileImageName;
    private static String saveidProofImageName;
    private static ProgressDialog progressDialogBox;
    private static String createUsersMethod = "InsertUser";
    public String addUserResponseResult;

    public AddUserDetails(AddUser addUser) {
        context = addUser;
    }

    public void AddUser(String name, String address, String email, String worksfor, String mobileno, String empcode, String username, String password, String profileImageName, String idProofImageName, String userrole, String dob, ProgressDialog progressDialog) {

        savename = name ;
        saveempcode = empcode;
        saveemail  = email;
        savemobileno = mobileno;
        saveusername = username;
        savepassword = password;
        saveworksfor = worksfor;
        savedob = dob;
        saveaddress = address;
        saveprofileImageName = profileImageName;
        saveidProofImageName = idProofImageName;
        saveuserrole = userrole;
        progressDialogBox = progressDialog;

        uploadUserDetailAsyncCallWS task = new uploadUserDetailAsyncCallWS();
        task.execute();
    }

    public class uploadUserDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            addUserResponseResult = InsertDataWebservice.AddUser(createUsersMethod, savename, saveaddress, saveemail, saveworksfor, savemobileno, saveempcode, saveusername,
                    savepassword, saveprofileImageName, saveidProofImageName, saveuserrole, savedob);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(addUserResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to add user please try again.");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("User added successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(context, Home.class);
                        context.startActivity(gotohome);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
