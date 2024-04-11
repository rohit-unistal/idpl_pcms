package unistal.com.idpl_pcms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.provider.MediaStore;
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
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.model.WelderModel;
import unistal.com.idpl_pcms.model.WpsModel;
import unistal.com.idpl_pcms.welding.WeldingActivity;

public class WeldRepairActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.spinSegment)
    Spinner spinSegment;
    @BindView(R.id.spinRepairStatus)
    Spinner spinRepair;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.edpipe_dia)
    EditText edPipe_dia;
    @BindView(R.id.edChainageFrom)
    EditText editChainageFrom;
    @BindView(R.id.edChainageTo)
    EditText editChainageTo;

    @BindView(R.id.netstatus)
    ImageView netStatus;
    @BindView(R.id.edJoint)
    EditText edJoint;
    @BindView(R.id.edKM)
    EditText edKM;
    @BindView(R.id.spinerJointType)
    Spinner spinerJointType;
    @BindView(R.id.edJointNo)
    EditText edJointNo;
    @BindView(R.id.edSuffix)
    EditText edSuffix;

    @BindView(R.id.spinWPS)
    Spinner spinWPS;
    @BindView(R.id.edit_rootwelders1)
    Spinner  edRootWelders1;

    @BindView(R.id.edelectrode_e6010_dia)
    EditText editelectrode_e6010_dia;

    @BindView(R.id.edelectrode_e8010_dia)
    EditText editelectrode_e8010_dia;
    @BindView(R.id.edelectrode_e9045)
    EditText editelectrode_e9045;
    @BindView(R.id.edelectrode_Er70S)
    EditText editelectrode_Er70S;
    @BindView(R.id.edpreheat_temp)
    EditText edpre_heating;
    @BindView(R.id.edvisual)
    EditText editvisual;
    @BindView(R.id.edit_typeofDefect)
    EditText edittypeofDefect;



    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    String[] arrSegment = {"Segment1", "Segment2", "Segment3", "Segment4"};
    String[] arrRepair = {"R1", "R2", "R3"};
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image;

    private NetworkStateReceiver networkStateReceiver;

    Context context;
    private ArrayList<WpsModel> WpsModelList;
    private ArrayList<String> wpsNumList, wpsIDList;

    private ArrayList<WelderModel> WelderModelList;
    private ArrayList<String> welderNumList, welderIDList;

    String[] arrJointType={"Select Type","M","T","FT","GT","FTR","TR","MTR"};

    String strKM="K";
    String strJointType;
    String strJointNo;
    String strSuffix;
    ProgressDialog progressDialog;

    DataBaseHelper dataBaseHelper;
    private ArrayAdapter<WpsModel> spinnerArrayAdapter;
    private ArrayAdapter<WelderModel> spinnerWelderArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weld_repair);
        context=this;
        ButterKnife.bind(this);
        netConnectivity();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        dataBaseHelper=new DataBaseHelper(context);
        WpsModelList= new ArrayList<>();
        wpsNumList= new ArrayList<>();
        wpsIDList= new ArrayList<>();
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        loadAlignment();
        WelderModelList= new ArrayList<>();
        welderNumList= new ArrayList<>(); welderIDList= new ArrayList<>();
        //getWPSDetail();
        //getWelderDetail();
        loadWPS();

        loadWelder();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter segmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrSegment);
        segmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinSegment.setAdapter(segmentAdapter);
        ArrayAdapter repairAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrRepair);
        repairAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinRepair.setAdapter(repairAdapter);
        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        spinerJointType.setAdapter(adapter);



        Initialization();}

    private void Initialization(){
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
            }
        });
edKM.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strKM="K";
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()==1){
                strKM=strKM+"0"+s;
            }/*else if (s.length()==2){
                    strKM=strKM+"0"+s;
                }*/else {
                strKM= strKM+s;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            edJoint.setText(strKM);
        }
    });

        edSuffix.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strSuffix="";
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            strSuffix= ""+ s;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            edJoint.setText(strKM+strJointType+strJointNo+strSuffix);
        }
    });

        edJointNo.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strJointNo="";
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()==1){
                strJointNo="0"+s;
            }else {
                strJointNo= ""+ s;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            //StrJointNumber=StrJointNumber+strKM;
            edJoint.setText(strKM+strJointType+strJointNo);
        }
    });

        spinerJointType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position>0) {
                strJointType=arrJointType[position];
                edJoint.setText(strKM+strJointType);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (editDate.getText().toString().equals("")){
                        Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
                    }else if (editReportNo.getText().toString().equals("")) {
                        Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
                    }else if (edJoint.getText().toString().equals("")){
                            Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
                        }else if (edKM.getText().toString().equals("")){
                            Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
                        }else if (spinerJointType.getSelectedItem().toString().equals("Select Type")){
                            Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
                        }else if (edJointNo.getText().toString().equals("")){
                            Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();

                        }else {
                            final HashMap<String, String> params = new HashMap<>();

                            params.put("UserID", SessionUtil.getUserId(context));
                            params.put("SectionID", SessionUtil.getAssignedSection(context));
                            params.put("type", "weldrepair");

                            params.put("WeldTestDate", editDate.getText().toString());
                            params.put("TR_Report_Number", editReportNo.getText().toString());

                            params.put("JointID", edJoint.getText().toString());

                            params.put("WPSNo", WpsModelList.get(spinWPS.getSelectedItemPosition()).getWpsID());
                            params.put("WelderID", WelderModelList.get(edRootWelders1.getSelectedItemPosition()).getWelderID());

                            params.put("electrode_E6010", editelectrode_e6010_dia.getText().toString() + "");
                            params.put("electrode_E8010P1", editelectrode_e8010_dia.getText().toString() + "");
                             params.put("electrode_E9045P2", editelectrode_e9045.getText().toString() + "");
                            params.put("electrode_Er70S", editelectrode_Er70S.getText().toString() + "");
                            params.put("electrode_E81T1M21A8",  "");
                            params.put("ChainageFrom", editChainageFrom.getText().toString() + "");
                            params.put("ChainageTo", editChainageTo.getText().toString() + "");
                            params.put("electrode_E81T1M21A8",  "");
                            params.put("Pipe_size", edPipe_dia.getText().toString() + "");

                            params.put("Weather", edWeather.getSelectedItem().toString() + "");
                            params.put("ChainageFrom", editChainageFrom.getText().toString() + "");
                            params.put("ChainageTo", editChainageTo.getText().toString() + "");
                            params.put("Remarks", editRemarks.getText().toString() + "");
                            params.put("visual_exam", editvisual.getText().toString() + "");
                            params.put("defect_typ", edittypeofDefect.getText().toString() + "");
                            params.put("Pipe_Material",  "");
                            params.put("segname", spinSegment.getSelectedItem().toString() + "");
                            params.put("Result", spinRepair.getSelectedItem().toString() + "");
                            params.put("temp", edpre_heating.getText().toString() + "");
                            params.put("TR_alignment", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());


                            params.put("TN_Remarks", editRemarks.getText().toString() + "");
                        params.put("Photo","WeldRepair"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");
                        params.put("ImageData",image + "");


                        Log.d("Response", "Details = " + params.toString());
                            if (isOnline()) {
                                InsertData(context, params);
                            } else {
                                boolean s = dataBaseHelper.WeldingInsert(params);

                                if (s) {
                                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                                    Clear();


                                    params.clear();
                                } else {
                                    Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(WeldRepairActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeldRepairActivity.this,
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

    public void InsertData(final Context context, final Map<String, String> params) {

        progressDialog.show();
        Activity activity = (Activity) context;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.e("Response", "InsertMethod = " + stringResponse);
                        progressDialog.dismiss();
                        try {
                            if (stringResponse.contains("1")) {
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                Clear();
                                params.clear();
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.e("API_ERROR", error.toString());
                            progressDialog.dismiss();
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
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

        edKM.getText().clear();
        edJointNo.getText().clear();
        edSuffix.getText().clear();
        image="";
        edJoint.setText("");
        editRemarks.setText("");

        editChainageFrom.setText("");
        editChainageTo.setText("");

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
            Toast.makeText(WeldRepairActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }


    private void loadWPS() {
        // dataBaseHelper.open();

        wpsIDList.clear();
        wpsNumList.clear();
        Cursor cur = dataBaseHelper.getAll("SELECT WpsID,WPSName" +
                " FROM tbl_welding_wps_num ");
        // wpsNumList.add("WPS Number");
        System.out.println("count---" + cur.getCount());
        if (cur.getCount() > 0) {
            //   wpsIDList.add("");
            //sectionIDList.add("");
            if (cur.moveToFirst()) {
                do {
                    WpsModel jointModel = new WpsModel();
                    jointModel.setWpsID(cur.getString(0));

                    jointModel.setWPSName(cur.getString(1));


                    wpsIDList.add(cur.getString(0));
                    wpsNumList.add(cur.getString(1));

                    WpsModelList.add(jointModel);
                } while (cur.moveToNext());
            }
        } else {
            Toast.makeText(WeldRepairActivity.this, "No WPS number found or Please update WPS number details", Toast.LENGTH_SHORT).show();
        }
        spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, wpsNumList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWPS.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

    }

    private void loadWelder() {
        // dataBaseHelper.open();

        welderIDList.clear();
        welderNumList.clear();
        WelderModelList.clear();
        WelderModel welderModel1 = new WelderModel();
        welderModel1.setWelderID("0");

        welderModel1.setWelderName("Select");

        WelderModelList.add(welderModel1);
        welderIDList.add("0");
        welderNumList.add("Select");
        Cursor cur = dataBaseHelper.getAll("SELECT WelderID,WelderName" +
                " FROM tbl_welding_welder_num ");
        // welderNumList.add("Welder Number");
        System.out.println("count---" + cur.getCount());
        if (cur.getCount() > 0) {
            //  welderIDList.add("");
            //sectionIDList.add("");
            if (cur.moveToFirst()) {
                do {
                    WelderModel welderModel = new WelderModel();
                    welderModel.setWelderID(cur.getString(0));

                    welderModel.setWelderName(cur.getString(1));


                    welderIDList.add(cur.getString(0));
                    welderNumList.add(cur.getString(1));

                    WelderModelList.add(welderModel);
                } while (cur.moveToNext());
            }
        } else {
            Toast.makeText(WeldRepairActivity.this, "No Welder number found or Please update Welder number details", Toast.LENGTH_SHORT).show();
        }
        spinnerWelderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, welderNumList); //selected item will look like a spinner set from XML
        spinnerWelderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edRootWelders1.setAdapter(spinnerWelderArrayAdapter);

        spinnerWelderArrayAdapter.notifyDataSetChanged();

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