package com.nasolution.com.nasolution;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.RegisterSpinnerItemsAdapter;
import com.nasolution.com.nasolution.Connectivity.AddUserDetails;
import com.nasolution.com.nasolution.CropImage.CropImage;
import com.nasolution.com.nasolution.InternetConnectivity.CheckInternetConnection;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddUser extends BaseActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int PIC_CAMERA_CROP = 3;
    private static final int PIC_GALLERY_CROP = 4;
    private static final int CAMERA_PERMISSION_REQUEST = 5;
    private static final int READ_STORAGE_PERMISSION_REQUEST = 6;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST = 7;

    RelativeLayout relativeLyoutForRegister;
    CardView cardView;
    TextView labelEmpCode;
    TextView txtEmpCode;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextMobileNo;
    EditText editTextUserName;
    EditText editTextPassword;
    EditText editTextWorkFor;
    EditText editTextDOB;
    EditText editTextAddress;
    Spinner spinnerUserRole;
    LinearLayout imageViewLinearLayout;
    Button btnSubmit;
    ProgressDialog progressDialog = null;
    public String workForDefaultValue = "NA Solution";

    public String name;
    public String empcode;
    public String email;
    public String mobileno;
    public String username;
    public String password;
    public String worksfor;
    public String dob;
    public String address;
    public String userrole;
    public String userRoleMethod = "GetRole";
    public String userRoleResponseResult;
    public String empCodeMethod = "GetEmpCode";
    public String EmpCodeResponseResult;

    private List<String> userRoleList = new ArrayList<String>();
    private RegisterSpinnerItemsAdapter spinnerAdapter;
    private String[] userRoleArrayList;

    public AlertDialog registerAlertDialog;
    public ArrayAdapter<String> registerDialogAdapter;
    Button btnUploadProfileImage;
    Button btnUploadIdProofImage;
    ImageView profileImageView;
    ImageView idProofImageView;
    String currentImagePath;

    Bitmap imageToShow;
    Bitmap imageToShow2;
    String timeStamp;
    File storageDir;
    File originalFile;
    File originalFile2;
    File cropFile;
    String imageBase64String;
    String imageBase64String2;
    String profileImagePath = "";
    String idProofImagePath = "";
    String profileImageName = "";
    String idProofImageName = "";

    public SimpleDateFormat dateFormatter;
    public DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        EmpCodeAsyncCallWS empcodetask = new EmpCodeAsyncCallWS();
        empcodetask.execute();

        labelEmpCode = (TextView) findViewById(R.id.labelEmpcodeAdd);
        txtEmpCode = (TextView) findViewById(R.id.txtEmpCodeAdd);
        labelEmpCode.setVisibility(View.GONE);
        txtEmpCode.setVisibility(View.GONE);

        cardView = (CardView) findViewById(R.id.registrationCard_viewAdd);
        spinnerUserRole = (Spinner) findViewById(R.id.spinnerAssignRoleAdd);
        editTextName = (EditText) findViewById(R.id.txtNameAdd);
        editTextEmail = (EditText) findViewById(R.id.txtEmailAdd);
        editTextMobileNo = (EditText) findViewById(R.id.txtMobileNoAdd);
        editTextUserName = (EditText) findViewById(R.id.txtUserNameAdd);
        editTextPassword = (EditText) findViewById(R.id.txtpasswordAdd);
        editTextWorkFor = (EditText) findViewById(R.id.txtWorkForAdd);
        editTextDOB = (EditText) findViewById(R.id.txtDateofBirthAdd);
        editTextAddress = (EditText) findViewById(R.id.txtAddressAdd);
        btnSubmit = (Button) findViewById(R.id.btnSubmitRegisterAdd);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        editTextDOB.setInputType(InputType.TYPE_NULL);
        editTextWorkFor.setText(workForDefaultValue);

        relativeLyoutForRegister = (RelativeLayout) findViewById(R.id.relativeLayoutForRegisterAdd);

        userRoleArrayList = new String[]{
                "Select User Role"
        };
        userRoleList = new ArrayList<>(Arrays.asList(userRoleArrayList));
        spinnerAdapter = new RegisterSpinnerItemsAdapter(this, R.layout.district_spinneritem, userRoleList);
        UserRoleAsyncCallWS task = new UserRoleAsyncCallWS();
        task.execute();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserRole.setSelection(userRoleList.indexOf(0));
        spinnerUserRole.setAdapter(spinnerAdapter);
        spinnerUserRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                if(position > 0) {
                    userrole = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnUploadProfileImage = (Button) this.findViewById(R.id.uploadProfileButtonAdd);
        btnUploadIdProofImage = (Button) this.findViewById(R.id.uploadIdProofButtonAdd);
        imageViewLinearLayout = (LinearLayout) findViewById(R.id.imageViewLinearLayoutAdd);
        imageViewLinearLayout.setVisibility(View.GONE);
        profileImageView = (ImageView) this.findViewById(R.id.profileImageAdd);
        idProofImageView = (ImageView) this.findViewById(R.id.idProofImageAdd);

        //     setDateTimeField();
        editTextDOB.setOnClickListener(this);
        btnUploadProfileImage.setOnClickListener(this);
        btnUploadIdProofImage.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        idProofImageView.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        editTextDOB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    setDateTimeField();
                    datePickerDialog.show();
                }
                return false;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.uploadProfileButtonAdd || v.getId() == R.id.uploadIdProofButtonAdd ) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestWriteStoragePermission();
            }
            else {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestReadStoragePermission();
                }
                else {
                    createImageFromSelectImageDialogChooser();
                }
            }
        }/*
        else if(v.getId() == R.id.txtDateofBirthAdd){
            setDateTimeField();
            datePickerDialog.show();
        }*/
        else if(v.getId()==R.id.btnSubmitRegisterAdd){
            if (CheckInternetConnection.getInstance(this).isOnline()) {
                ButtonSubmit();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Internet connection!");
                builder.setMessage("Please check your internet connection.");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set( year, monthOfYear, dayOfMonth);
                String finalDate = dateFormatter.format(newDate.getTime());
                editTextDOB.setText(finalDate);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public class EmpCodeAsyncCallWS extends AsyncTask<String, Void, Void> {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch EmpCode ");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    empcode = EmpCodeResponseResult;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void createImageFromSelectImageDialogChooser() {
        registerDialogAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.alertDialogListNames));
                return view;
            }
        };
        registerDialogAdapter.add("Take from Camera");
        registerDialogAdapter.add("Select from Gallery");
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle("Select Image");
        builder.setAdapter(registerDialogAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    registerAlertDialog.dismiss();
                    if(ActivityCompat.checkSelfPermission(AddUser.this, android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestCameraPermission();
                    }
                    else {
                        new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                    }
                } else if (which == 1) {
                    registerAlertDialog.dismiss();
                    new SelectGalleryImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                }
            }
        });
        registerAlertDialog = builder.create();
        registerAlertDialog.show();
    }

    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            super.onActivityResult(requestCode, resultCode, intent);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == CAMERA_REQUEST) {
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), bmOptions);

                    doCropping(originalFile, PIC_CAMERA_CROP);
                }
                else if(requestCode == GALLERY_REQUEST) {
                    Uri uri = intent.getData();
                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    currentImagePath = cursor.getString(columnIndex);
                    cursor.close();
                    originalFile2 = saveBitmapToFile(new File(currentImagePath));
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    this.imageToShow2 = BitmapFactory.decodeFile(originalFile2.getAbsolutePath(), bmOptions);

                    doCropping(originalFile2, PIC_GALLERY_CROP);
                }
                else if(requestCode == PIC_CAMERA_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    String filename = currentImagePath.substring(currentImagePath.lastIndexOf("/")+1);
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveCameraCropBitmap(filename, imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String);
                }
                else if(requestCode == PIC_GALLERY_CROP) {
                    Bundle extras = intent.getExtras();
                    this.imageToShow = extras.getParcelable("data");
                    originalFile = saveBitmapToFile(new File(currentImagePath));
                    imageBase64String = createBase64StringFromImageFile(originalFile);
                    this.imageToShow = saveGalleryCropBitmap(imageToShow);
                    setBitmapToImage(imageToShow, imageBase64String2);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(AddUser.this, "Did not taken any image!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(e.getMessage(), "Error");
            Toast.makeText(AddUser.this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    private String createBase64StringFromImageFile(File originalFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encodedImage;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setBitmapToImage(Bitmap imageToShow, String imageBase64String) {
        imageViewLinearLayout.setVisibility(View.VISIBLE);
        if(Objects.equals(profileImagePath, "")) {
            profileImageView.setImageBitmap(imageToShow);
            profileImagePath = imageBase64String;
            profileImageName = splitImageName(currentImagePath);
        }
        else if(!Objects.equals(profileImagePath, "")) {
            idProofImageView.setImageBitmap(imageToShow);
            idProofImagePath = imageBase64String;
            idProofImageName = splitImageName(currentImagePath);
        }
    }

    private String splitImageName(String currentImagePath) {
        String imageName = currentImagePath.substring(currentImagePath.lastIndexOf("/") + 1);
        return imageName;
    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void doCropping(File image, int request_code) {

        Intent cropIntent = new Intent(this, CropImage.class);

        cropIntent.putExtra("image-path", currentImagePath);
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);

        try {
            startActivityForResult(cropIntent, request_code);
        } catch (Exception e) {
            Toast.makeText(AddUser.this, "Crop Error", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap saveCameraCropBitmap(String filename, Bitmap imageToShow) {
        FileOutputStream outStream = null;

        cropFile = new File(currentImagePath);
        if (cropFile.exists()) {
            cropFile.delete();
            cropFile = new File(storageDir + ".png");
        }
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private Bitmap saveGalleryCropBitmap(Bitmap imageToShow) {
        FileOutputStream outStream = null;

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        cropFile = new File(storageDir + ".png");
        try {
            outStream = new FileOutputStream(cropFile);
            imageToShow.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageToShow;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = new File(storageDir + ".png");
        try {
            currentImagePath = image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    // All Permissions
    private void requestReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(AddUser.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestWriteStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(AddUser.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(AddUser.this,
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }

    public class SelectCameraImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
            return null;
        }
    }

    public class SelectGalleryImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SelectCameraImage().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            } else {
                Toast.makeText(AddUser.this, "CAMERA permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == WRITE_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestReadStoragePermission();
            } else {
                Toast.makeText(AddUser.this, "Write storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == READ_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createImageFromSelectImageDialogChooser();
            } else {
                Toast.makeText(AddUser.this, "Read storage permission was NOT granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Fetch Role from server
    public class UserRoleAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            userRoleResponseResult = GetDataWebService.GetUserRole(userRoleMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(userRoleResponseResult.isEmpty()) {
                progressDialog.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to Generate EmpCode ");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else {
                try {
                    JSONArray jsonArray = new JSONArray(userRoleResponseResult);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            userRoleList.add(obj.optString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        spinnerAdapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static boolean isValidEmail(String emailForValidation) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailForValidation).matches();
    }

    // submit all user details to server
    private void ButtonSubmit() {
        empcode = txtEmpCode.getText().toString();
        name = editTextName.getText().toString();
        email = editTextEmail.getText().toString();
        mobileno = editTextMobileNo.getText().toString();
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        worksfor = editTextWorkFor.getText().toString();
        dob = editTextDOB.getText().toString();
        address = editTextAddress.getText().toString();

        if (txtEmpCode.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty()
                || editTextEmail.getText().toString().isEmpty() || editTextMobileNo.getText().toString().isEmpty()
                || editTextUserName.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()
                || editTextDOB.getText().toString().isEmpty() || editTextAddress.getText().toString().isEmpty()
                || profileImageName == null || idProofImageName == null) {

            Toast.makeText(AddUser.this, "All Details are neccessory", Toast.LENGTH_LONG).show();
        }
        else {
            if (!isValidEmail(email)) {
                Toast.makeText(AddUser.this, "Email is not valid", Toast.LENGTH_LONG).show();
            }
            else if ((mobileno.length() < 6 || mobileno.length() > 13)) {
                Toast.makeText(AddUser.this, "Mobile No is not valid", Toast.LENGTH_LONG).show();
            }
            else if (spinnerUserRole.getSelectedItemPosition() <= 0) {
                Toast.makeText(AddUser.this, "Select Role", Toast.LENGTH_LONG).show();
            }
            else {
                progressDialog = ProgressDialog.show(AddUser.this,"","adding user details  please wait...");
                AddUserDetails addUserDetails = new AddUserDetails(this);
                addUserDetails.AddUser(name, address, email, worksfor, mobileno, empcode, username, password, profileImageName, idProofImageName, userrole, dob,progressDialog);
            }
        }
    }

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
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent gotoLogin = new Intent(AddUser.this,Login.class);
        startActivity(gotoLogin);
    }
}
