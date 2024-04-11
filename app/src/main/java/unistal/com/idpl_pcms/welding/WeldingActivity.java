package unistal.com.idpl_pcms.welding;

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
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.BarCodeReaderActivity;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.model.WelderModel;
import unistal.com.idpl_pcms.model.WpsModel;
import unistal.com.idpl_pcms.trenching.TrenchingActivity;

public class WeldingActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edit_LeftPipeNo)
    EditText edit_LeftPipeNo;
    @BindView(R.id.edit_RightPipeNo)
    EditText edit_RightPipeNo;
    @BindView(R.id.imgLeftScan)
    ImageView imgLeftScan;
    @BindView(R.id.imgRightScan)
    ImageView imgRightScan;
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
    @BindView(R.id.edit_rootwelders2)
    Spinner  edRootWelders2;
    @BindView(R.id.edit_hotwelders1)
    Spinner edHotWelders1;
    @BindView(R.id.edit_hotwelders2)
    Spinner edHotWelders2;
    @BindView(R.id.edit_filler1welders1)
    Spinner  edFiller1Welders1;
    @BindView(R.id.edit_filler1welders2)
    Spinner  edFiller1Welders2;
    @BindView(R.id.edit_filler2welders1)
    Spinner  edFiller2Welders1;
    @BindView(R.id.edit_filler2welders2)
    Spinner edFiller2Welders2;
    @BindView(R.id.edit_filler3welders1)
    Spinner  edFiller3Welders1;
    @BindView(R.id.edit_filler3welders2)
    Spinner edFiller3Welders2;
    @BindView(R.id.edit_cappingwelders1)
    Spinner  edCappingWelders1;
    @BindView(R.id.edit_cappingwelders2)
    Spinner edCappingWelders2;

    @BindView(R.id.edit_capping2welders1)
    Spinner  edCapping2Welders1;
    @BindView(R.id.edit_capping2welders2)
    Spinner edCapping2Welders2;


    @BindView(R.id.edelectrode_e6010_dia)
    EditText editelectrode_e6010_dia;
    @BindView(R.id.edelectrode_e6010_batch)
    EditText editelectrode_e6010_batch;
    @BindView(R.id.edelectrode_e8010_dia)
    EditText editelectrode_e8010_dia;
    @BindView(R.id.edelectrode_e8010_batch)
    EditText editelectrode_e8010_batch;
    @BindView(R.id.edelectrode_e9045_dia)
    EditText editelectrode_e9045_dia;
    @BindView(R.id.edelectrode_e9045_batch)
    EditText editelectrode_e9045_batch;
    @BindView(R.id.edelectrode_B221868_dia)
    EditText editelectrode_B221868_dia;
    @BindView(R.id.edelectrode_B221868_batch)
    EditText editelectrode_B221868_batch;
    @BindView(R.id.edelectrode_806012_dia)
    EditText editelectrode_806012_dia;
    @BindView(R.id.edelectrode_806012_batch)
    EditText editelectrode_806012_batch;
    @BindView(R.id.edPipe_dia)
    EditText editPipe_dia;
    @BindView(R.id.edPipe_thick)
    EditText editPipe_thick;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.edChainageFrom)
    EditText editChainageFrom;
    @BindView(R.id.edChainageTo)
    EditText editChainageTo;


    @BindView(R.id.edProcess)
    EditText editProcess;
    @BindView(R.id.edMaterial)
    EditText editMaterial;
    @BindView(R.id.edFitup)
    CheckBox editFitup;
    @BindView(R.id.edWeld_Visual)
    CheckBox editWeld_Visual;





    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image="";

    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};




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
        setContentView(R.layout.activity_welding);
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
        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        spinerJointType.setAdapter(adapter);



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
                try{
                if (editDate.getText().toString().equals("")){
                    Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
                }else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }else if (edit_LeftPipeNo.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Enter Left Pipe No.", Toast.LENGTH_SHORT).show();
                }else if (edit_RightPipeNo.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Enter Right Pipe No.", Toast.LENGTH_SHORT).show();
                }else if (edit_RightPipeNo.getText().toString().trim().equals(edit_LeftPipeNo.getText().toString().trim())){
                    Toast.makeText(context, "Enter Right Pipe No. should not match Left Pipe No.", Toast.LENGTH_SHORT).show();
                }
                else if (edJoint.getText().toString().equals("")){
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
                    params.put("type", "welding");

                    params.put("TR_Welding_Date", editDate.getText().toString());
                    params.put("TR_Report_Number", editReportNo.getText().toString());

                    params.put("JointID", edJoint.getText().toString());
                    params.put("LeftPipeNumber", edit_LeftPipeNo.getText().toString().trim());

                    params.put("RightPipeNumber", edit_RightPipeNo.getText().toString().trim());

                    params.put("WPSNo", WpsModelList.get(spinWPS.getSelectedItemPosition()).getWpsID());
                    params.put("RWelderNumber1", WelderModelList.get(edRootWelders1.getSelectedItemPosition()).getWelderID());
                    params.put("RWelderNumber2", WelderModelList.get(edRootWelders2.getSelectedItemPosition()).getWelderID());
                    params.put("HWelderNumber1", WelderModelList.get(edHotWelders1.getSelectedItemPosition()).getWelderID());
                    params.put("HWelderNumber2", WelderModelList.get(edHotWelders2.getSelectedItemPosition()).getWelderID());
                    params.put("FWelderNumber1", WelderModelList.get(edFiller1Welders1.getSelectedItemPosition()).getWelderID());
                    params.put("FWelderNumber2", WelderModelList.get(edFiller1Welders2.getSelectedItemPosition()).getWelderID());
                    params.put("F2WelderNumber1", WelderModelList.get(edFiller2Welders1.getSelectedItemPosition()).getWelderID());
                    params.put("F2WelderNumber2", WelderModelList.get(edFiller2Welders2.getSelectedItemPosition()).getWelderID());
                    params.put("F3WelderNumber1", WelderModelList.get(edFiller3Welders1.getSelectedItemPosition()).getWelderID());
                    params.put("F3WelderNumber2", WelderModelList.get(edFiller3Welders2.getSelectedItemPosition()).getWelderID());

                    params.put("CWelderNumber1", WelderModelList.get(edCappingWelders1.getSelectedItemPosition()).getWelderID());
                    params.put("CWelderNumber2", WelderModelList.get(edCappingWelders2.getSelectedItemPosition()).getWelderID());
                    params.put("CWelderNumber3", WelderModelList.get(edCapping2Welders1.getSelectedItemPosition()).getWelderID());
                    params.put("CWelderNumber4", WelderModelList.get(edCapping2Welders2.getSelectedItemPosition()).getWelderID());

                    params.put("electrode_e6010_dia", editelectrode_e6010_dia.getText().toString() + "");
                    params.put("electrode_e6010_batch", editelectrode_e6010_batch.getText().toString() + "");
                    params.put("electrode_e8010_dia", editelectrode_e8010_dia.getText().toString() + "");
                    params.put("electrode_e8010_batch", editelectrode_e8010_batch.getText().toString() + "");
                    params.put("electrode_e9045_dia", editelectrode_e9045_dia.getText().toString() + "");
                    params.put("electrode_e9045_batch", editelectrode_e9045_batch.getText().toString() + "");
                    params.put("electrode_B221868_dia", editelectrode_B221868_dia.getText().toString() + "");
                    params.put("electrode_B221868_batch", editelectrode_B221868_batch.getText().toString() + "");
                    params.put("electrode_806012_dia", editelectrode_806012_dia.getText().toString() + "");
                    params.put("electrode_806012_batch", editelectrode_806012_batch.getText().toString() + "");
                    params.put("Pipe_dia", editPipe_dia.getText().toString() + "");
                    params.put("Pipe_thick", editPipe_thick.getText().toString() + "");
                    params.put("Weather", edWeather.getSelectedItem().toString() + "");
                    params.put("ChainageFrom", editChainageFrom.getText().toString() + "");
                    params.put("ChainageTo", editChainageTo.getText().toString() + "");

                    params.put("Process", editProcess.getText().toString() + "");
                    params.put("Material", editMaterial.getText().toString() + "");
                    if (editFitup.isChecked()) {
                        params.put("Fitup", "Ok");
                    } else {
                        params.put("Fitup", "No");
                    }
                    if (editWeld_Visual.isChecked()) {
                        params.put("Weld_Visual", "Ok");
                    } else {
                        params.put("Weld_Visual", "No");
                    }

                    params.put("TR_alignment", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());


                    params.put("TN_Remarks", editRemarks.getText().toString() + "");
                    params.put("Photo","Welding"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");
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

    private void Initialization(){
        edit_LeftPipeNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String s = edit_LeftPipeNo.getText().toString().trim();
                    if(s.contains(","))
                    {
                        String[] str1 = s.split(",");
                        edit_LeftPipeNo.setText(str1[0].trim());}
                    else if(s.contains(" "))
                    {
                        String[] str1 = s.split("\\s+");
                        edit_LeftPipeNo.setText(str1[0].trim());
                    }
                    else {
                        edit_LeftPipeNo.setText(s);
                    }
                }
            }
        });
        edit_RightPipeNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String s = edit_RightPipeNo.getText().toString().trim();
                    if (s.contains(",")) {
                        String[] str1 = s.split(",");
                        edit_RightPipeNo.setText(str1[0].trim());
                    } else if (s.contains(" ")) {
                        String[] str1 = s.split("\\s+");
                        edit_RightPipeNo.setText(str1[0].trim());
                    } else {
                        edit_RightPipeNo.setText(s);
                    }
                }
            }
        });
        imgLeftScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcode = new Intent(WeldingActivity.this, BarCodeReaderActivity.class);
                startActivityForResult(barcode, 101);
            }
        });
        imgRightScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcode = new Intent(WeldingActivity.this, BarCodeReaderActivity.class);
                startActivityForResult(barcode, 102);
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


    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(WeldingActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeldingActivity.this,
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
            Toast.makeText(WeldingActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(WeldingActivity.this, "No WPS number found or Please update WPS number details", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(WeldingActivity.this, "No Welder number found or Please update Welder number details", Toast.LENGTH_SHORT).show();
        }
        spinnerWelderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, welderNumList); //selected item will look like a spinner set from XML
        spinnerWelderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edRootWelders1.setAdapter(spinnerWelderArrayAdapter);
        edRootWelders2.setAdapter(spinnerWelderArrayAdapter);
        edFiller1Welders1.setAdapter(spinnerWelderArrayAdapter);
        edFiller1Welders2.setAdapter(spinnerWelderArrayAdapter);
        edFiller2Welders1.setAdapter(spinnerWelderArrayAdapter);
        edFiller2Welders2.setAdapter(spinnerWelderArrayAdapter);
        edFiller3Welders1.setAdapter(spinnerWelderArrayAdapter);
        edFiller3Welders2.setAdapter(spinnerWelderArrayAdapter);
        edHotWelders1.setAdapter(spinnerWelderArrayAdapter);
        edHotWelders2.setAdapter(spinnerWelderArrayAdapter);
        edCappingWelders1.setAdapter(spinnerWelderArrayAdapter);
        edCappingWelders2.setAdapter(spinnerWelderArrayAdapter);
        edCapping2Welders1.setAdapter(spinnerWelderArrayAdapter);
        edCapping2Welders2.setAdapter(spinnerWelderArrayAdapter);

        spinnerWelderArrayAdapter.notifyDataSetChanged();

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
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == 101 && data != null) {
                String s = data.getExtras().getString("barCode");
                if(s.contains(","))
                {
                    String[] str1 = s.split(",");
                    edit_LeftPipeNo.setText(str1[0].trim());}
                else if(s.contains(" "))
                {
                    String[] str1 = s.split("\\s+");
                    edit_LeftPipeNo.setText(str1[0].trim());
                }
                else {
                    edit_LeftPipeNo.setText(s);
                }
               // edit_LeftPipeNo.setText(data.getExtras().getString("barCode"));


            }
            if (requestCode == 102 && data != null) {
                String s = data.getExtras().getString("barCode");
                if(s.contains(","))
                {
                    String[] str1 = s.split(",");
                    edit_RightPipeNo.setText(str1[0].trim());}
                else if(s.contains(" "))
                {
                    String[] str1 = s.split("\\s+");
                    edit_RightPipeNo.setText(str1[0].trim());
                }
                else {
                    edit_RightPipeNo.setText(s);
                }
               // edit_RightPipeNo.setText(data.getExtras().getString("barCode"));


            }


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
        edit_LeftPipeNo.setText("");
        edit_RightPipeNo.setText("");
        edJoint.setText("");
        editRemarks.setText("");
        image="";

        editChainageFrom.setText("");
        editChainageTo.setText("");

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

    private void getWPSDetail() {
       // if (DialogUtil.checkInternetConnection(this)) {
            progressDialog.show();
           // loadingDialog.onShow();
           /* final ProgressDialog progressDialog = ProgressDialog.show(WeldingActivity.this, "",
                    "Loading. Please wait...", false);*/
            RequestQueue queue = Volley.newRequestQueue(this);

            final String url = AppConstants.APP_BASE_URL + "API/WPS_typeAPI.php?" + "SectionID=" + SessionUtil.getAssignedSection(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                                progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            try {

                                JSONArray joints = new JSONArray(response);

                                dataBaseHelper.deleteAll("tbl_welding_wps_num");
                                for (int i = 0; i < joints.length(); i++) {

                                    JSONObject c = joints.getJSONObject(i);
                                    ContentValues namelist = new ContentValues();
                                    namelist.put("WpsID", c.getString("WpsID"));

                                    namelist.put("WPSName", c.getString("WPSName"));
                                    Toast.makeText(getApplicationContext(), "WPS Number: "+ c.getString("WPSName"), Toast.LENGTH_LONG).show();

                                    dataBaseHelper.insert("tbl_welding_wps_num", namelist);

                                }

                               // dataBaseHelper.close();
                            } catch (Exception ignored) {
                                Toast.makeText(getApplicationContext(), "Exception WPS Number: " + ignored, Toast.LENGTH_LONG).show();
                            }/*finally {

                                dataBaseHelper.close();
                            }*/


                            Toast.makeText(getApplicationContext(), "WPS Number", Toast.LENGTH_LONG).show();
                           // loadingDialog.dismiss();
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getMessage();
                  //  loadingDialog.dismiss();
                    progressDialog.dismiss();
                    Toast.makeText(WeldingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(WeldingActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/

    }
    private void getWelderDetail() {
        // if (DialogUtil.checkInternetConnection(this)) {
        progressDialog.show();
        // loadingDialog.onShow();
           /* final ProgressDialog progressDialog = ProgressDialog.show(WeldingActivity.this, "",
                    "Loading. Please wait...", false);*/
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = AppConstants.APP_BASE_URL + "API/welderapi.php?" + "SectionID=" + SessionUtil.getAssignedSection(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {

                            JSONArray joints = new JSONArray(response);

                            dataBaseHelper.deleteAll("tbl_welding_welder_num");
                            for (int i = 0; i < joints.length(); i++) {

                                JSONObject c = joints.getJSONObject(i);
                                ContentValues namelist = new ContentValues();
                                namelist.put("WelderID", c.getString("WelderID"));

                                namelist.put("WelderName", c.getString("WelderName"));
                                Toast.makeText(getApplicationContext(), "Welder Number: "+ c.getString("WelderName"), Toast.LENGTH_LONG).show();

                                dataBaseHelper.insert("tbl_welding_welder_num", namelist);

                            }

                            // dataBaseHelper.close();
                        } catch (Exception ignored) {
                            Toast.makeText(getApplicationContext(), "Exception Welder Number: " + ignored, Toast.LENGTH_LONG).show();
                        }/*finally {

                                dataBaseHelper.close();
                            }*/


                        Toast.makeText(getApplicationContext(), "Welder Number", Toast.LENGTH_LONG).show();
                        // loadingDialog.dismiss();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                //  loadingDialog.dismiss();
                progressDialog.dismiss();
                Toast.makeText(WeldingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(WeldingActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/

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
