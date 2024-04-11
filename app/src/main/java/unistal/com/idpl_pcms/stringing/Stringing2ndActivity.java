package unistal.com.idpl_pcms.stringing;

//import android.support.v7.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import unistal.com.idpl_pcms.BarCodeReaderActivity;
import unistal.com.idpl_pcms.DialogUtil;
import unistal.com.idpl_pcms.GPSTracker;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.trenching.TrenchingActivity;

public class Stringing2ndActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener  {

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
    @BindView(R.id.edit_typeofCorrosion)
    EditText edit_typeofCorrosion;
    @BindView(R.id.edit_damage_coating)
    EditText edit_damage_coating;
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

    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    Context context;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;


    String image="";
    ProgressDialog progressDialog;
    GPSTracker gps;
    double latitude,longitude;
    DataBaseHelper dataBaseHelper;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    private ArrayAdapter<String> adapterbarcode;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    private AlertDialog.Builder ad;
    ArrayList<String> existPipeNo;
  //  int pipeNoSize =0;
   // int pipeNo=0;
    boolean submitClick = false;
    private ArrayList<String> barcodeNo;
  //  private ArrayList<LoweringDataModel> barcodeList;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stringing2nd);
        ButterKnife.bind(this);
        context=this;


        netConnectivity();
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        barcodeNo = new ArrayList<>();
        dataBaseHelper=new DataBaseHelper(context);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        existPipeNo = new ArrayList<>();
     //   barcodeList = new ArrayList<>();
        ListView lvBarCode = findViewById(R.id.lvBarCode);
        adapterbarcode = new ArrayAdapter<>(context, R.layout.barcode_list_data, R.id.txtBarcode, barcodeNo);
        lvBarCode.setAdapter(adapterbarcode);
        loadAlignment();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
            }
        });
        findViewById(R.id.add_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_PipeNo.getText().toString().trim().isEmpty())
                {  addPipe();}

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!submitClick){validateFields();}
            }
        });
        gps = new GPSTracker(context);

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
                Intent barcode = new Intent(context, BarCodeReaderActivity.class);
                startActivityForResult(barcode, 101);
            }
        });
        setReportDate();
    }
    private void setReportDate()
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        editDate.setText(mYear  + "-" + (mMonth + 1) + "-" + mDay );
    }
    private void validateFields(){
        submitClick =true;
      /*  pipeNoSize =0;
        pipeNoSize = barcodeNo.size();*/
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

        else if (strChainage.equals("")){
            Toast.makeText(context, "Enter Chainage Value", Toast.LENGTH_SHORT).show();
        }else {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("UserID", SessionUtil.getUserId(context));
                hashMap.put("SectionID", SessionUtil.getAssignedSection(context));
                hashMap.put("type", "stringing");
                hashMap.put("TR_Stringing_Date", strDate);
                hashMap.put("TR_Report_Number", strReportNo);
                 hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
               // hashMap.put("Alignment_Sheet", "0");
                hashMap.put("Weather", edWeather.getSelectedItem().toString() + "");
                hashMap.put("MR_Chainage_From", strChainage);
              hashMap.put("PipeDia", edit_pipeDiameter.getText().toString().trim());
                hashMap.put("PipeMaterial", edit_pipeMaterial.getText().toString().trim());

                hashMap.put("PipeID", barcodeNo.toString().replace("[","").replace("]",""));
                hashMap.put("latitude", latitude + "");
                hashMap.put("longitude", longitude + "");
            hashMap.put("TN_Damage",edit_damage_coating.getText().toString().trim()+"");
            hashMap.put("CorrosionType",edit_typeofCorrosion.getText().toString().trim()+"");
                if (radioAccept.isChecked()) {
                    hashMap.put("ConcreteCoating", "Yes");
                } else if (radioReject.isChecked()) {
                    hashMap.put("ConcreteCoating", "No");
                } else {
                    hashMap.put("ConcreteCoating", "");
                }
                hashMap.put("TN_Remarks", strRemark);
            hashMap.put("Photo","Stringing"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");

                if (isOnline()) {

                    InsertData(context, hashMap);

                } else {

                    boolean s = dataBaseHelper.StringingInsert(hashMap);
                    Clear();
                    hashMap.clear();
                    if (s) {
                        Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                    }
                }
            submitClick =false;

        }
    }
    void Clear()
    {
        edit_chainage.setText("");
        barcodeNo.clear();
        adapterbarcode.notifyDataSetChanged();
        edit_PipeNo.setText("");
        edit_remark.setText("");
        image = "";
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
                      //  Toast.makeText(context, stringResponse, Toast.LENGTH_SHORT).show();
                        Log.e("Response", stringResponse);
                        if(stringResponse.contains("1")) {
                            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            Clear();
                        } else {
                            Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                        }
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
                            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_LONG).show();

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

                Log.e("request", params.toString());

                return params;
            }
        };
        requestQueue.add(featureRequest);
    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(Stringing2ndActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Stringing2ndActivity.this,
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

        }    if (data != null && resultCode == RESULT_OK) {
            if (requestCode == 101 && data != null) {

                edit_PipeNo.setText(data.getExtras().getString("barCode"));


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
            Toast.makeText(context, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
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
    public void alreadyExistingPipe() {

        if (DialogUtil.checkInternetConnection(this)) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            existPipeNo.clear();
            //  final String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            RequestQueue queue = Volley.newRequestQueue(this);
            //  final String url = AppConstants.APP_BASE_URL + "API/Duplicate_pipe_store.php";
            final String url = AppConstants.APP_BASE_URL + "API/get_pipe.php";
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            {
                                Log.e("response", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    response = jsonObject.optString("msg");
                                    Toast toast = Toast.makeText(context, response, Toast.LENGTH_LONG);
                                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                    if (!jsonObject.optString("Status").contains("Successful")) {
                                        v.setTextColor(Color.RED);
                                    } else {
                                        v.setTextColor(Color.GREEN);
                                    }
                                    toast.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                            addPipe();
                            progressDialog.dismiss();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getMessage();
                    addPipe();
                    progressDialog.dismiss();
                    Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("PipeNo", edit_PipeNo.getText().toString().trim());

                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } else {
            DialogUtil.showNoConnectionDialog(this);
        }
    }
    public void addPipe() {
        {
            try {
                String barcodeValue = edit_PipeNo.getText().toString().trim();
                if (existPipeNo.contains(barcodeValue)) {

                    ad.setMessage("Pipe already exist");
                    ad.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Pipe already exist", Toast.LENGTH_LONG).show();
                    AlertDialog alert = ad.create();
                    alert.show();
                } else {
                    if (!barcodeValue.equalsIgnoreCase("")) {
                      /*  LoweringDataModel dd = new LoweringDataModel();

                        dd.setBarcode(barcodeValue);

                        barcodeList.add(dd);*/
                        barcodeNo.add(barcodeValue);
                        barcodeValue = "";
                        edit_PipeNo.setText("");

                        // adapter.notifyDataSetChanged();
                        adapterbarcode.notifyDataSetChanged();
                        //  alreadyExistingPipe();
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }
}