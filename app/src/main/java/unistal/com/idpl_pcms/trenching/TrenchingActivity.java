package unistal.com.idpl_pcms.trenching;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
/*
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
*/
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.bending.BendingFormActivity;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class TrenchingActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_bottomWidth)
    EditText editBottomWidth;




    @BindView(R.id.edJointFrom)
    EditText edJointFrom;
    @BindView(R.id.edKM)
    EditText edKMFrom;
    @BindView(R.id.spinerJointType)
    Spinner spinerJointTypeFrom;
    @BindView(R.id.edJointNo)
    EditText edJointNoFrom;
    @BindView(R.id.edSuffixFrom)
    EditText edSuffixFrom;


    @BindView(R.id.edJointTo)
    EditText edJointTo;
    @BindView(R.id.edKM_to)
    EditText edKMTo;
    @BindView(R.id.spinerJointTypeTo)
    Spinner spinerJointTypeTo;
    @BindView(R.id.edJointNoTo)
    EditText edJointNoTo;
    @BindView(R.id.edSuffixTo)
    EditText edSuffixTo;

    @BindView(R.id.edit_sec_length)
    EditText editDistance;
    @BindView(R.id.editDepth)
    EditText trenchDepth;
    @BindView(R.id.edit_Trench_Width)
    EditText trenchWidth;

    @BindView(R.id.edit_typeofterrain)
    EditText edittypeOfTerrain;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.spin_arable_soil)
    Spinner spin_arable_soil;
    @BindView(R.id.spin_verification_area)
    Spinner spin_verification_area;
    @BindView(R.id.spin_provision_sign)
    Spinner spin_provision_sign;
    @BindView(R.id.spin_trench_profile)
    Spinner spin_trench_profile;
    @BindView(R.id.ed_avability_cover)
   EditText edavability_cover;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    ArrayList<String> alignmentName;
    ProgressDialog progressDialog;
    ArrayList<AlignmentModel> alignmentNo;
    Context context;
    DataBaseHelper dbHelper;

    String[] arrJointType={"Select Type","M","T","FT","GT","FTR","TR","MTR"};

    String strKMFrom="K";
    String strJointTypeFrom;
    String strJointNoFrom;
    String strSuffixFrom;

    String strKMTo="K";
    String strJointTypeTo;
    String strJointNoTo;
    String strSuffixTo;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    String[] arrseperation_soil = {"Yes", "Not Applicable"};
    String[] arrverification_area = {"Yes", "Not Applicable"};
    String[] arrtrench_profile = {"Yes", "Not Applicable"};
    String[] arrprovision_sign = {"Yes", "No"};
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String image="";
    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage="";
    private String pictureFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trenching);

        context=this;
        ButterKnife.bind(this);
        netConnectivity();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        dbHelper=new DataBaseHelper(context);

        alignmentNo=new ArrayList<>();
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        loadAlignment();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter sepaarationsoilAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrseperation_soil);
        sepaarationsoilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_arable_soil.setAdapter(sepaarationsoilAdapter);
        ArrayAdapter provision_signAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrprovision_sign);
        provision_signAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_provision_sign.setAdapter(provision_signAdapter);
        ArrayAdapter verification_areaAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrverification_area);
        verification_areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_verification_area.setAdapter(verification_areaAdapter);
        ArrayAdapter trench_profileAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrtrench_profile );
        trench_profileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_trench_profile.setAdapter(trench_profileAdapter);
        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        spinerJointTypeFrom.setAdapter(adapter);
        spinerJointTypeTo.setAdapter(adapter);


        Initialization();
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDate.getText().toString().equals("")){
                    Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
                }else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }
                else if (editChainageFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Chainage From", Toast.LENGTH_SHORT).show();
                }
                else if (editChainageTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Chainage To", Toast.LENGTH_SHORT).show();
                }
                else if (edJointFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Spread No.", Toast.LENGTH_SHORT).show();
                }else if (edKMFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
                }else if (spinerJointTypeFrom.getSelectedItem().toString().equals("Select Type")){
                    Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
                }else if (edJointNoFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
                }else if (edJointTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint To Field", Toast.LENGTH_SHORT).show();
                }else if (edKMTo.getText().toString().equals("")){
                    Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
                }else if (spinerJointTypeTo.getSelectedItem().toString().equals("Select Type")){
                    Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
                }else if (edJointNoTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
                }else if (editDistance.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Length", Toast.LENGTH_SHORT).show();
                }else if (trenchDepth.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Depth", Toast.LENGTH_SHORT).show();
                }/*else if (trenchWidth.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Trench Width", Toast.LENGTH_SHORT).show();
                }*/else{
                    final HashMap<String,String> params=new HashMap<>();

                    params.put("UserID", SessionUtil.getUserId(context));
                    params.put("SectionID",SessionUtil.getAssignedSection(context));
                    params.put("type","trenching");

                    params.put("TR_Trenching_Date",editDate.getText().toString());
                    params.put("TR_Report_Number",editReportNo.getText().toString());

                    params.put("ChainageFrom",editChainageFrom.getText().toString());
                    params.put("ChainageTo",editChainageTo.getText().toString());
                    params.put("TN_Trenching_Lower_Width",editBottomWidth.getText().toString());
                    params.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());



                    params.put("TN_JointNumber_From",edJointFrom.getText().toString());
                    params.put("TN_JointNumber_To",edJointTo.getText().toString());

                    params.put("MR_Distance_Cleared",editDistance.getText().toString());
                    params.put("MN_Trenching_Depth",trenchDepth.getText().toString());
                    params.put("MN_Trenching_UpperWidth",trenchWidth.getText().toString());
                    params.put("TypeofTerrain",edittypeOfTerrain.getText().toString());
                    params.put("Weather",edWeather.getSelectedItem().toString()+"");

                    params.put("TN_Remarks",editRemarks.getText().toString());

                    params.put("seperation_soil",spin_arable_soil.getSelectedItem().toString()+"");
                    params.put("provision_sign",spin_provision_sign.getSelectedItem().toString()+"");
                    params.put("verification_area",spin_verification_area.getSelectedItem().toString()+"");
                    params.put("avability_cover",edavability_cover.getText().toString()+"");
                    params.put("trench_profile",spin_trench_profile.getSelectedItem().toString()+"");
                    params.put("Photo","Trench"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
                    params.put("ImageData",image + "");







                    Log.e("Responce", "Details = " + params.toString());
                    if (isOnline()){
                        InsertData(context,params);
                    }else {
                        boolean s=dbHelper.TrenchinInsert(params);
                        if (s){
                            Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                            Clear();
                            params.clear();
                        }else {
                            Toast.makeText(context, "Data Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {sendTakePictureIntent();
                    }
                }else
                {
                    sendTakePictureIntent();
                }
            }
        });
    }

    private void Initialization(){
        edKMFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKMFrom="K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strKMFrom=strKMFrom+"0"+s;
                }
                /*else if (s.length()==2){
                    strKMFrom=strKMFrom+"0"+s;
                }*/
                else {
                    strKMFrom= strKMFrom+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointFrom.setText(strKMFrom);
            }
        });
        edJointNoFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNoFrom="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strJointNoFrom="0"+s;
                }else {
                    strJointNoFrom= ""+ s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom);
            }
        });
        edSuffixFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffixFrom="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffixFrom= ""+ s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
            }
        });

        edKMTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKMTo="K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strKMTo=strKMTo+"0"+s;
                }
                /*else if (s.length()==2){
                    strKMTo=strKMTo+"0"+s;
                }*/
                else {
                    strKMTo= strKMTo+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointTo.setText(strKMTo);
            }
        });
        edJointNoTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNoTo="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strJointNoTo="0"+s;
                }else {
                    strJointNoTo= ""+ s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo);
            }
        });
        edSuffixTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffixTo="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffixTo= ""+ s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo+strSuffixTo);
            }
        });


        spinerJointTypeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    strJointTypeFrom=arrJointType[position];
                    edJointFrom.setText(strKMFrom+strJointTypeFrom);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinerJointTypeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    strJointTypeTo=arrJointType[position];
                    edJointTo.setText(strKMTo+strJointTypeTo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editChainageFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double chf ,cht;
                if ( editChainageFrom.getText().toString().trim().isEmpty())
                {chf=0;}else{chf = Double.valueOf( editChainageFrom.getText().toString());}
                if (editChainageTo.getText().toString().trim().isEmpty())
                {cht=0;}else{cht=Double.valueOf(editChainageTo.getText().toString());}
                double sl = cht-chf;


                editDistance.setText(sl+"");
            }
        });
        editChainageTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double chf ,cht;
                if ( editChainageFrom.getText().toString().trim().isEmpty())
                {chf=0;}else{chf = Double.valueOf( editChainageFrom.getText().toString());}
                if (editChainageTo.getText().toString().trim().isEmpty())
                {cht=0;}else{cht=Double.valueOf(editChainageTo.getText().toString());}
                double sl = cht-chf;

                editDistance.setText(sl+"");
            }
        });

    }

    private void setRouteSurveyDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void InsertData(final Context context, final Map<String, String> params) {

        progressDialog.show();
        Activity activity = (Activity) context;




        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.d("Response", "InsertMethod = " + stringResponse);
                        progressDialog.dismiss();
                       /* try {*/
                        if (stringResponse.contains("1")){
                               Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                           Clear();
                            }/*else {
                                Toast.makeText(context, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                            }*/

                            /*JSONObject jsonObject = new JSONObject(stringResponse);

                            if (jsonObject.getString("Status") == null ||
                                    !jsonObject.getString("Status").equalsIgnoreCase("Success")) {
                                Toast.makeText(context, jsonObject.getString("Messege"), Toast.LENGTH_SHORT).show();
                                Log.e("Response", jsonObject.toString());
                                return;
                            }

                            JSONObject response = new JSONObject(stringResponse);
                            Log.e("Response", response.toString());
                            if (response.getString("Status").equalsIgnoreCase("Success")) {

                            } else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                            }*/
                        /*} catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API_ERROR", error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(featureRequest);
    }
    private void Clear(){

        edKMTo.getText().clear();
        edJointNoTo.getText().clear();
        edSuffixTo.getText().clear();
        edKMFrom.getText().clear();
        edJointNoFrom.getText().clear();
        edSuffixFrom.getText().clear();
        strImage="";image="";
        editDistance.setText("");
        trenchDepth.setText("");
        trenchWidth.setText("");
        editChainageFrom.getText().clear();
        editChainageTo.getText().clear();
        editBottomWidth.getText().clear();
        edittypeOfTerrain.getText().clear();
        imageView.setVisibility(View.GONE);
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(TrenchingActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TrenchingActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    100);

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            //  intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }


    }

    public void sendTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();

            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "unistal.com.bppls1_pcms.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Toast.makeText(context, "Image Captured!", Toast.LENGTH_SHORT).show();

                // imgPhoto.setImageBitmap(photo);
                image = getStringImage(photo);
                // image1 = geoTag(image,77.385,28.611,"Route Survey");
                // successfully captured the image
                // launching upload activity
                //launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(context,
                                "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(context,
                                "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(pictureFilePath);
            if (imgFile.exists()) {
                Toast.makeText(context, "Image Captured!", Toast.LENGTH_SHORT).show();
                decodeFile(imgFile);
                //image.setImageBitmap(decodeFile(imgFile));
                //image.setImageURI(Uri.fromFile(imgFile));
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "MBPL_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }
    private Bitmap decodeFile(File f) {
        Bitmap b = null;
        File destFile;
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try{
            if (!file.exists()) {
                file.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat")
        String imgName="img_" + new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date())+ ".jpg";
        destFile = new File(file, imgName);
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            b.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File deleteFile = new File(pictureFilePath);
        boolean deleted = deleteFile.delete();
        Log.e("ImagePath",destFile.getAbsolutePath()+"\t"+String.valueOf(deleted));
        imageView.setImageBitmap(b);
//        imageView.setVisibility(View.VISIBLE);
        strImage=BitMapToString(b);

        return b;
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }

    private void loadAlignment() {
        // dataBaseHelper.open();

        alignmentIDList.clear();
        alignmentNumList.clear();
        Cursor cur = dbHelper.getAll("SELECT AlignmentID,AlignmentName" +
                " FROM tbl_alignment_sheet ");
        // wpsNumList.add("WPS Number");
        System.out.println("count---" + cur.getCount());
        if (cur.getCount() > 0) {
            //   wpsIDList.add("");
            //sectionIDList.add("");
            if (cur.moveToFirst()) {
                do {
                    AlignmentModel jointModel = new AlignmentModel();
                    jointModel.setAlignmentID(cur.getString(0));

                    jointModel.setAlignmentName(cur.getString(1));


                    alignmentIDList.add(cur.getString(0));
                    alignmentNumList.add(cur.getString(1));

                    AlignmentModelList.add(jointModel);
                } while (cur.moveToNext());
            }
        } else {
            Toast.makeText(TrenchingActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }




    public void netConnectivity()
    {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
    }

    @Override
    public void networkAvailable() {
        netStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icons8_cloud_50));
    }

    @Override
    public void networkUnavailable() {
        netStatus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icons8_cloud_cross_50));

    }


}
