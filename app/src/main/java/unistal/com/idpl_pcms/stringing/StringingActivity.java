package unistal.com.idpl_pcms.stringing;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.BarCodeReaderActivity;
import unistal.com.idpl_pcms.GPSTracker;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.backfiling.BackfillingInputForm;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class StringingActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    @BindView(R.id.edit_chainage)
    EditText edit_chainage;
    @BindView(R.id.edit_pipeDiameter)
    EditText edit_pipeDiameter;
    @BindView(R.id.edit_pipeMaterial)
    EditText edit_pipeMaterial;
    @BindView(R.id.edWeather)
    Spinner edWeather;

    @BindView(R.id.edit_PipeNo)
    EditText edit_PipeNo;
    @BindView(R.id.imgScan)
    ImageView imgScan;
    @BindView(R.id.edit_remark)
    EditText edit_remark;
    @BindView(R.id.radioAccept)
    RadioButton radioAccept;
    @BindView(R.id.radioReject)
    RadioButton radioReject;
    @BindView(R.id.edit_typeofCorrosion)
    EditText edit_typeofCorrosion;
    @BindView(R.id.edit_damage_coating)
    EditText edit_damage_coating;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    Context context;
    ProgressDialog progressDialog;
    GPSTracker gps;
    double latitude,longitude;
    DataBaseHelper dataBaseHelper;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image = "";


    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stringing);
        ButterKnife.bind(this);
        context=this;


        netConnectivity();
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();

        dataBaseHelper=new DataBaseHelper(context);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        loadAlignment();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        edit_PipeNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if(!hasFocus)
                {
                    String s = edit_PipeNo.getText().toString().trim();
                    if(s.contains(","))
                    {
                        String[] str1 = s.split(",");
                        edit_PipeNo.setText(str1[0].trim());}
                    else if(s.contains(" "))
                    {
                        String[] str1 = s.split("\\s+");
                        edit_PipeNo.setText(str1[0].trim());
                    }
                    else {
                        edit_PipeNo.setText(s);
                    }
                }
            }
        });
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        gps = new GPSTracker(StringingActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Log.e("Your Location is", " \nLat: " +
                    latitude + "\nLong: " + longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcode = new Intent(StringingActivity.this, BarCodeReaderActivity.class);
                startActivityForResult(barcode, 101);
            }
        });
    }
    private void validateFields(){

        String strDate=editDate.getText().toString();
        String strReportNo=editReportNo.getText().toString();
//
        String strChainage=edit_chainage.getText().toString();
        String strPipeNo=edit_PipeNo.getText().toString();
//

        String strRemark=edit_remark.getText().toString();

        if (strDate.equals("")){
            Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
        }else if (strReportNo.equals("")){
            Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
        }

       else if (strPipeNo.equals("")){
            Toast.makeText(context, "Enter Pipe No Value", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("UserID", SessionUtil.getUserId(StringingActivity.this));
            hashMap.put("SectionID",SessionUtil.getAssignedSection(StringingActivity.this));
            hashMap.put("type","stringing");
            hashMap.put("TR_Stringing_Date",strDate);
            hashMap.put("TR_Report_Number",strReportNo);
            hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");

            hashMap.put("MR_Chainage_From",strChainage);
            hashMap.put("PipeDia",edit_pipeDiameter.getText().toString().trim());
            hashMap.put("PipeMaterial",edit_pipeMaterial.getText().toString().trim());
            hashMap.put("PipeID",strPipeNo);
            hashMap.put("latitude",latitude+"");
            hashMap.put("longitude",longitude+"");
            hashMap.put("TN_Damage",edit_damage_coating.getText().toString().trim()+"");
            hashMap.put("CorrosionType",edit_typeofCorrosion.getText().toString().trim()+"");
            if(radioAccept.isChecked())
            { hashMap.put("ConcreteCoating","Yes");}
            else if(radioReject.isChecked())
            { hashMap.put("ConcreteCoating","No");}
            else
            {
                hashMap.put("ConcreteCoating","");
            }
             hashMap.put("TN_Remarks",strRemark);
            hashMap.put("Photo","Stringing"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");
            if (isOnline()){

                InsertData(StringingActivity.this,hashMap);

            }else {

                boolean s=dataBaseHelper.StringingInsert(hashMap);
                Clear();
                hashMap.clear();
                if (s){
                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    void Clear()
    {
      edit_chainage.setText("");
      edit_PipeNo.setText("");
      edit_remark.setText("");
      image="";
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
    public void InsertData(final Context context, final Map<String, String> params) {

        Activity activity = (Activity) context;


        progressDialog.show();




        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {

                        progressDialog.dismiss();
                  //      Toast.makeText(context, stringResponse, Toast.LENGTH_SHORT).show();
                        Log.e("Response", stringResponse);
                        /*try {

                            JSONObject jsonObject = new JSONObject(stringResponse);

                            if (jsonObject.getString("Status") == null ||
                                    !jsonObject.getString("Status").equalsIgnoreCase("Success")) {
                                Toast.makeText(context, jsonObject.getString("Messege"), Toast.LENGTH_SHORT).show();
                                Log.e("Response", jsonObject.toString());
                                return;
                            }

                            JSONObject response = new JSONObject(stringResponse);
                            Log.e("Response", response.toString());*/
                           if(stringResponse.contains("1")) {
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                               Clear();
                            } else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                            }/*
                        } catch (Exception e) {
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
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(StringingActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StringingActivity.this,
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

        }     if (data != null && resultCode == RESULT_OK) {
            if (requestCode == 101 && data != null) {

                String s = data.getExtras().getString("barCode");
                if (s.contains(",")) {
                    String[] str1 = s.split(",");
                    edit_PipeNo.setText(str1[0].trim());
                } else if (s.contains(" ")) {
                    String[] str1 = s.split("\\s+");
                    edit_PipeNo.setText(str1[0].trim());
                } else {
                    edit_PipeNo.setText(s);
                }
                //  edit_PipeNo.setText(data.getExtras().getString("barCode"));


            }

        }
    }
    private void loadAlignment() {
        // dataBaseHelper.open();

        alignmentIDList.clear();
        alignmentNumList.clear();
        Cursor cur = dataBaseHelper.getAll("SELECT AlignmentID,AlignmentName" +
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
            Toast.makeText(StringingActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

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
