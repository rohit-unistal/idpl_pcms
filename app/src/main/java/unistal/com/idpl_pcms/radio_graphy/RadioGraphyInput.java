package unistal.com.idpl_pcms.radio_graphy;

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
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.DefaultRetryPolicy;
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
import butterknife.OnItemSelected;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.bending.BendingFormActivity;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.model.WelderModel;
import unistal.com.idpl_pcms.stringing.StringingActivity;
import unistal.com.idpl_pcms.welding.WeldingActivity;

public class RadioGraphyInput extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edit_observe)
    EditText editObservation;
    @BindView(R.id.edit_observe2)
    EditText editObservation2;
    @BindView(R.id.edit_observe3)
    EditText editObservation3;
    @BindView(R.id.edit_observe4)
    EditText editObservation4;

    @BindView(R.id.edWeather)
    Spinner edWeather;

    @BindView(R.id.edJointNumber)
    EditText edJointNumber;
    @BindView(R.id.edKM)
    EditText edKMFrom;
    @BindView(R.id.spinerJointType)
    Spinner spinerJointTypeFrom;
    @BindView(R.id.edJointNo)
    EditText edJointNoFrom;
    @BindView(R.id.edSuffixFrom)
    EditText edSuffixFrom;

    @BindView(R.id.rgSegment1)
    RadioGroup rgSegment1;
    @BindView(R.id.rgSegment2)
    RadioGroup rgSegment2;
    @BindView(R.id.rgSegment3)
    RadioGroup rgSegment3;
    @BindView(R.id.rgSegment4)
    RadioGroup rgSegment4;



    @BindView(R.id.txtResult)
    CheckedTextView txtResult;
    @BindView(R.id.spinfilmtype)
    Spinner spinfilmtype;

    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;

    @BindView(R.id.edit_penetrameter)
    EditText editpenetrameter;
    @BindView(R.id.edit_gray_value)
    EditText editgray_value;

    @BindView(R.id.edit_thickness)
    EditText editthickness;
    @BindView(R.id.edit_source)
    EditText editsource;
    @BindView(R.id.edit_sensitivity)
    EditText editsensitivity;
    @BindView(R.id.edit_ChainageFrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_ChainageTo)
    EditText editChainageTo;

    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image;


    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    @BindView(R.id.edit_remark)
    EditText edit_remark;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    @BindView(R.id.edRootRepair)
    EditText editRootRepair1;
    @BindView(R.id.edit_rootwelders1)
    Spinner spinRootWelders1;
    @BindView(R.id.edHotPass)
    EditText editHotPass1;
    @BindView(R.id.edit_hotwelders1)
    Spinner spinHotWelders1;
    @BindView(R.id.edFiller1)
    EditText editFiller1;
    @BindView(R.id.edit_fillerwelders1)
    Spinner spinFillerWelders1;
    @BindView(R.id.edcapping)
    EditText editCapping1;
    @BindView(R.id.edit_cappingwelders1)
    Spinner spincappingwelders1;
    @BindView(R.id.edRootRepair2)
    EditText editRootRepair2;
    @BindView(R.id.edit_rootwelders2)
    Spinner spinRootWelders2;
    @BindView(R.id.edHotPass2)
    EditText editHotPass2;
    @BindView(R.id.edit_hotwelders2)
    Spinner spinHotWelders2;
    @BindView(R.id.edFiller2)
    EditText editFiller2;
    @BindView(R.id.edit_fillerwelders2)
    Spinner spinFillerWelders2;
    @BindView(R.id.edcapping2)
    EditText editCapping2;
    @BindView(R.id.edit_cappingwelders2)
    Spinner spincappingwelders2;

    @BindView(R.id.edRootRepair3)
    EditText editRootRepair3;
    @BindView(R.id.edit_rootwelders3)
    Spinner spinRootWelders3;
    @BindView(R.id.edHotPass3)
    EditText editHotPass3;
    @BindView(R.id.edit_hotwelders3)
    Spinner spinHotWelders3;
    @BindView(R.id.edFiller3)
    EditText editFiller3;
    @BindView(R.id.edit_fillerwelders3)
    Spinner spinFillerWelders3;
    @BindView(R.id.edcapping3)
    EditText editCapping3;
    @BindView(R.id.edit_cappingwelders3)
    Spinner spincappingwelders3;
    @BindView(R.id.edRootRepair4)
    EditText editRootRepair4;
    @BindView(R.id.edit_rootwelders4)
    Spinner spinRootWelders4;
    @BindView(R.id.edHotPass4)
    EditText editHotPass4;
    @BindView(R.id.edit_hotwelders4)
    Spinner spinHotWelders4;
    @BindView(R.id.edFiller4)
    EditText editFiller4;
    @BindView(R.id.edit_fillerwelders4)
    Spinner spinFillerWelders4;
    @BindView(R.id.edcapping4)
    EditText editCapping4;
    @BindView(R.id.edit_cappingwelders4)
    Spinner spincappingwelders4;




    private NetworkStateReceiver networkStateReceiver;

    ProgressDialog progressDialog;
    Context context;
    private ArrayList<WelderModel> WelderModelList;
    private ArrayList<String> welderNumList, welderIDList;
    private ArrayAdapter<WelderModel> spinnerWelderArrayAdapter;
    DataBaseHelper dbHelper;

    String[] arrJointType={"Select Type","M","T","FT","GT","FTR","TR","MTR"};
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    String[] arrFilmType = {"Digital X-Ray", "External X-Ray", "Gamma Ray"};

    static final int REQUEST_PICTURE_CAPTURE = 1;

    String strImage="";
    private String pictureFilePath;

    String strKMFrom="K";
    String strJointTypeFrom="";
    String strJointNoFrom="";
    String strSuffixFrom="";
    String strResult="Reject";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_graphy_input);

        context=this;
        ButterKnife.bind(this);
        netConnectivity();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        dbHelper=new DataBaseHelper(context);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        WelderModelList= new ArrayList<>();
        welderNumList= new ArrayList<>(); welderIDList= new ArrayList<>();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter filmTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrFilmType);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinfilmtype.setAdapter(filmTypeAdapter);

        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        spinerJointTypeFrom.setAdapter(adapter);


        if (txtResult != null) {
            txtResult.setChecked(false);
            txtResult.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);

            txtResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtResult.setChecked(!txtResult.isChecked());
                    txtResult.setCheckMarkDrawable(txtResult.isChecked() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
                    strResult = (txtResult.isChecked() ? "Accept" : "Reject");

                }
            });
        }

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
                    strKMFrom=strKMFrom+s+"";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointNumber.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
                edJointNumber.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
                edJointNumber.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
            }
        });

        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTakePictureIntent();
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
        loadAlignment();
        loadWelder();
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
        Cursor cur = dbHelper.getAll("SELECT WelderID,WelderName" +
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
            Toast.makeText(RadioGraphyInput.this, "No Welder number found or Please update Welder number details", Toast.LENGTH_SHORT).show();
        }
        spinnerWelderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, welderNumList); //selected item will look like a spinner set from XML
        spinnerWelderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRootWelders1.setAdapter(spinnerWelderArrayAdapter);
        spinRootWelders2.setAdapter(spinnerWelderArrayAdapter);
        spinRootWelders3.setAdapter(spinnerWelderArrayAdapter);
        spinRootWelders4.setAdapter(spinnerWelderArrayAdapter);
        spinHotWelders1.setAdapter(spinnerWelderArrayAdapter);
        spinHotWelders2.setAdapter(spinnerWelderArrayAdapter);
        spinHotWelders3.setAdapter(spinnerWelderArrayAdapter);
        spinHotWelders4.setAdapter(spinnerWelderArrayAdapter);
        spinFillerWelders1.setAdapter(spinnerWelderArrayAdapter);
        spinFillerWelders2.setAdapter(spinnerWelderArrayAdapter);
        spinFillerWelders3.setAdapter(spinnerWelderArrayAdapter);
        spinFillerWelders4.setAdapter(spinnerWelderArrayAdapter);
        spincappingwelders1.setAdapter(spinnerWelderArrayAdapter);
        spincappingwelders2.setAdapter(spinnerWelderArrayAdapter);
        spincappingwelders3.setAdapter(spinnerWelderArrayAdapter);
        spincappingwelders4.setAdapter(spinnerWelderArrayAdapter);
        spinnerWelderArrayAdapter.notifyDataSetChanged();

    }

    @OnItemSelected(R.id.spinerJointType)
    public void onControlRoomSelected(int position) {
        if(position>0) {
            strJointTypeFrom=arrJointType[position];
            edJointNumber.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
            Toast.makeText(RadioGraphyInput.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }
    private void validateFields(){

        RadioButton rdSeg1;//=findViewById(idSegment1);
        RadioButton rdSeg2;//=findViewById(idSegment2);
        RadioButton rdSeg3;//=findViewById(idSegment3);
        RadioButton rdSeg4;//=findViewById(idSegment4);

        String strSegment1="";//=rdSeg1.getText().toString();
        String strSegment2="";//rdSeg2.getText().toString();
        String strSegment3="";//rdSeg3.getText().toString();
        String strSegment4="";//rdSeg4.getText().toString();







/*
        int idSegment1=rgSegment1.getCheckedRadioButtonId();
        int idSegment2=rgSegment2.getCheckedRadioButtonId();
        int idSegment3=rgSegment3.getCheckedRadioButtonId();
        int idSegment4=rgSegment4.getCheckedRadioButtonId();

        RadioButton rdSeg1=findViewById(idSegment1);
        RadioButton rdSeg2=findViewById(idSegment2);
        RadioButton rdSeg3=findViewById(idSegment3);
        RadioButton rdSeg4=findViewById(idSegment4);


        String strSegment1=rdSeg1.getText().toString();
        String strSegment2=rdSeg2.getText().toString();
        String strSegment3=rdSeg3.getText().toString();
        String strSegment4=rdSeg4.getText().toString();*/


        String strDate=editDate.getText().toString();
        String strReportNo=editReportNo.getText().toString();
        String strRemark=edit_remark.getText().toString();



        if (strDate.equals("")){
            Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
        }else if (strReportNo.equals("")){
            Toast.makeText(context, "Enter Spread Value", Toast.LENGTH_SHORT).show();
        }else if (edKMFrom.getText().toString().equals("")){
            Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
        }else if (spinerJointTypeFrom.getSelectedItem().toString().equals("Select Type")){
            Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
        }else if (edJointNoFrom.getText().toString().equals("")){
            Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
        } else {
            if ((rgSegment1.getCheckedRadioButtonId() != -1 )){
                int idSegment1=rgSegment1.getCheckedRadioButtonId();
                rdSeg1=findViewById(idSegment1);
                strSegment1=rdSeg1.getText().toString(); }
            if ((rgSegment2.getCheckedRadioButtonId() != -1 )) {
                int idSegment2=rgSegment2.getCheckedRadioButtonId();
                rdSeg2=findViewById(idSegment2);
                strSegment2=rdSeg2.getText().toString();}
            if ((rgSegment3.getCheckedRadioButtonId() != -1 )){

                int idSegment3=rgSegment3.getCheckedRadioButtonId();
                rdSeg3=findViewById(idSegment3);
                strSegment3=rdSeg3.getText().toString();
            }
            if ((rgSegment4.getCheckedRadioButtonId() != -1 )){
                int idSegment4=rgSegment4.getCheckedRadioButtonId();
                rdSeg4=findViewById(idSegment4);
                strSegment4=rdSeg4.getText().toString();
            }


            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("UserID", SessionUtil.getUserId(context));
            hashMap.put("SectionID",SessionUtil.getAssignedSection(context));
            hashMap.put("type","Radiography");
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");

            hashMap.put("ReportNo",strReportNo);
            hashMap.put("WeldTestDate",strDate);
            if(txtResult.isChecked()){
            hashMap.put("Result","Accept");}else{
                hashMap.put("Result","Reject");
            }
            hashMap.put("observation",editObservation.getText().toString());
            hashMap.put("observation2",editObservation2.getText().toString());
            hashMap.put("observation3",editObservation3.getText().toString());
            hashMap.put("observation4",editObservation4.getText().toString());
            hashMap.put("JointId",edJointNumber.getText().toString());
            hashMap.put("segment1",strSegment1);
            hashMap.put("segment2",strSegment2);
            hashMap.put("segment3",strSegment3);
            hashMap.put("segment4",strSegment4);

            hashMap.put("RootRepair",editRootRepair1.getText().toString());
            hashMap.put("RWelder",WelderModelList.get(spinRootWelders1.getSelectedItemPosition()).getWelderID());
            hashMap.put("HotPass",editHotPass1.getText().toString());
            hashMap.put("HWelder",WelderModelList.get(spinHotWelders1.getSelectedItemPosition()).getWelderID());
            hashMap.put("Filler",editFiller1.getText().toString());
            hashMap.put("FWelder",WelderModelList.get(spinFillerWelders1.getSelectedItemPosition()).getWelderID());
            hashMap.put("Capping",editCapping1.getText().toString());
            hashMap.put("CWelder",WelderModelList.get(spincappingwelders1.getSelectedItemPosition()).getWelderID());

            hashMap.put("RootRepair2",editRootRepair2.getText().toString());
            hashMap.put("RWelder2",WelderModelList.get(spinRootWelders2.getSelectedItemPosition()).getWelderID());
            hashMap.put("HotPass2",editHotPass2.getText().toString());
            hashMap.put("HWelder2",WelderModelList.get(spinHotWelders2.getSelectedItemPosition()).getWelderID());
            hashMap.put("Filler2",editFiller2.getText().toString());
            hashMap.put("FWelder2",WelderModelList.get(spinFillerWelders2.getSelectedItemPosition()).getWelderID());
            hashMap.put("Capping2",editCapping2.getText().toString());
            hashMap.put("CWelder2",WelderModelList.get(spincappingwelders2.getSelectedItemPosition()).getWelderID());

            hashMap.put("RootRepair3",editRootRepair3.getText().toString());
            hashMap.put("RWelder3",WelderModelList.get(spinRootWelders3.getSelectedItemPosition()).getWelderID());
            hashMap.put("HotPass3",editHotPass3.getText().toString());
            hashMap.put("HWelder3",WelderModelList.get(spinHotWelders3.getSelectedItemPosition()).getWelderID());
            hashMap.put("Filler3",editFiller3.getText().toString());
            hashMap.put("FWelder3",WelderModelList.get(spinFillerWelders3.getSelectedItemPosition()).getWelderID());
            hashMap.put("Capping3",editCapping3.getText().toString());
            hashMap.put("CWelder3",WelderModelList.get(spincappingwelders3.getSelectedItemPosition()).getWelderID());

            hashMap.put("RootRepair4",editRootRepair4.getText().toString());
            hashMap.put("RWelder4",WelderModelList.get(spinRootWelders4.getSelectedItemPosition()).getWelderID());
            hashMap.put("HotPass4",editHotPass4.getText().toString());
            hashMap.put("HWelder4",WelderModelList.get(spinHotWelders4.getSelectedItemPosition()).getWelderID());
            hashMap.put("Filler4",editFiller4.getText().toString());
            hashMap.put("FWelder4",WelderModelList.get(spinFillerWelders4.getSelectedItemPosition()).getWelderID());
            hashMap.put("Capping4",editCapping4.getText().toString());
            hashMap.put("CWelder4",WelderModelList.get(spincappingwelders4.getSelectedItemPosition()).getWelderID());


            hashMap.put("penetrameter",editpenetrameter.getText().toString());
            hashMap.put("film_type",editsource.getText().toString());
            hashMap.put("sensitivity",editsensitivity.getText().toString());
            hashMap.put("gray_value",editgray_value.getText().toString());
            hashMap.put("thickness",editthickness.getText().toString());
            hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
            hashMap.put("ChainageFrom",editChainageFrom.getText().toString());
            hashMap.put("ChaninageTo",editChainageTo.getText().toString());
            hashMap.put("source",spinfilmtype.getSelectedItem().toString());


            hashMap.put("Remarks",strRemark);
            hashMap.put("Photo","Radiography"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");
            Log.e("response params",hashMap.toString());
            if (isOnline()){
                InsertData(context,hashMap);
            }else {
                boolean s=dbHelper.RadioGraphyInsert(hashMap);
                if (s){
                    Clear();
                    hashMap.clear();
                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error To Inserted Locally!", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    private void Clear(){
        edKMFrom.getText().clear();
        edJointNumber.getText().clear();
        edSuffixFrom.getText().clear();
        edit_remark.getText().clear();
        txtResult.setChecked(false);
        strResult="Reject";
        image = "";
        editObservation.getText().clear();
        editObservation2.getText().clear();
        editObservation3.getText().clear();
        editObservation4.getText().clear();
        rgSegment1.clearCheck();
        rgSegment2.clearCheck();
        rgSegment3.clearCheck();
        rgSegment4.clearCheck();
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

    public void InsertData(final Context context, final Map<String, String> params) {

        progressDialog.show();
        Activity activity = (Activity) context;
        //HttpsTrustManager.allowAllSSL();


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.e("Response",stringResponse);
                        progressDialog.dismiss();
                       // try {

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

                            if (stringResponse.contains("1")) {
                                Clear();
                                params.clear();
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        /*} catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("API_ERROR", error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        featureRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(featureRequest);
        //requestQueue.add(featureRequest);
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
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(RadioGraphyInput.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RadioGraphyInput.this,
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
        //image.setImageBitmap(b);
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
