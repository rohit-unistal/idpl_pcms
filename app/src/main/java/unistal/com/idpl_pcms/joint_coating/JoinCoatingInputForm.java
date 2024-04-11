package unistal.com.idpl_pcms.joint_coating;

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
/*import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;*/
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import unistal.com.idpl_pcms.clearing_and_grading.ClearingGrading2Activity;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.radio_graphy.RadioGraphyInput;

public class JoinCoatingInputForm extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.edcoating_type)
    EditText editcoating_type;
    @BindView(R.id.edsleeve_option)
    EditText editsleeve_option;
    @BindView(R.id.edrelative_humidity)
    EditText editrelative_humidity;

    @BindView(R.id.edJointNumber)
    EditText edJointNumber;
    @BindView(R.id.edKM)
    EditText edKM;
    @BindView(R.id.spinerJointType)
    Spinner spinerJointType;
    @BindView(R.id.edJointNo)
    EditText edJointNo;
    @BindView(R.id.edSuffix)
    EditText edSuffix;
    @BindView(R.id.edChainageFrom)
    EditText edChainageFrom;
    @BindView(R.id.edChainageTo)
    EditText edChainageTo;
    @BindView(R.id.rdGroupVisual)
    RadioGroup rdGroupVisual;
    @BindView(R.id.rdGroupHoliday)
    RadioGroup rdGroupHoliday;

    RadioButton rdVisual,rdHoliday,rdPeel;


    @BindView(R.id.edPipeCoating)
    EditText edPipeCoat;
    @BindView(R.id.edJointCoating)
    EditText edJointCoatThick;
    @BindView(R.id.edPeelTest)
    EditText edPeelTest;

    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;

    @BindView(R.id.edit_remark)
    EditText edit_remark;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.edpipe_dia)
    EditText edpipe_dia;
    @BindView(R.id.edpipe_thick)
    EditText edpipe_thick;
    @BindView(R.id.edbatch_no)
    EditText edbatch_no;
    @BindView(R.id.edroughness)
    EditText edroughness;
    @BindView(R.id.eddust_con)
    EditText eddust_con;
    @BindView(R.id.edprimer)
    EditText edprimer;

    @BindView(R.id.edprimer_b)
    EditText edprimerb;
    @BindView(R.id.spinnerAlgnSheetNo)
    Spinner spinAlignment;
    /*@BindView(R.id.edon_body)
    EditText edon_body;
    @BindView(R.id.edon_seam)
    EditText edon_seam;*/
    @BindView(R.id.edmaterial)
    EditText edmaterial;

    @BindView(R.id.edvisual)
    EditText edvisual;
    @BindView(R.id.edpre_heating)
    EditText edpre_heating;

    @BindView(R.id.edholiday_tester_no)
    EditText edholiday_tester_no;

    @BindView(R.id.edelcometer)
    EditText edelcometer;
    @BindView(R.id.edwftprimer)
    EditText edwftprimer;

    @BindView(R.id.edit_calibration)
    EditText edit_calibration;
    @BindView(R.id.rdGroupPeel)
    RadioGroup rdGroupPeel;
    @BindView(R.id.edit_manpower)
    EditText edit_manpower;
    @BindView(R.id.edit_machineries)
    EditText edit_machineries;

    @BindView(R.id.edDSPosition3)
    EditText edDSPosition3;
    @BindView(R.id.edDSPosition6)
    EditText edDSPosition6;
    @BindView(R.id.edDSPosition9)
    EditText edDSPosition9;
    @BindView(R.id.edDSPosition12)
    EditText  edDSPosition12;
    @BindView(R.id.edDSPositionAvg)
    EditText  edDSPositionAvg;
    @BindView(R.id.edUSPosition3)
    EditText edUSPosition3;
    @BindView(R.id.edUSPosition6)
    EditText edUSPosition6;
    @BindView(R.id.edUSPosition9)
    EditText edUSPosition9;
    @BindView(R.id.edUSPosition12)
    EditText  edUSPosition12;
    @BindView(R.id.edUSPositionAvg)
    EditText  edUSPositionAvg;

    @BindView(R.id.edWeldPosition3)
    EditText edWeldPosition3;
    @BindView(R.id.edWeldPosition6)
    EditText edWeldPosition6;
    @BindView(R.id.edWeldPosition9)
    EditText edWeldPosition9;
    @BindView(R.id.edWeldPosition12)
    EditText  edWeldPosition12;
    @BindView(R.id.edWeldPositionAvg)
    EditText  edWeldPositionAvg;


    @BindView(R.id.netstatus)
    ImageView netStatus;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image = "";

    private NetworkStateReceiver networkStateReceiver;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    ProgressDialog progressDialog;
    Context context;
    String [] arrJointType={"Select Type","M","T","FT","GT","GTR","FTR","TR","MTR"};

    String strKM="K";
    String strJointType="";
    String strJointNo="";
    String strSuffix="";
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    DataBaseHelper DataBaseHelper;

    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage="";
    private String pictureFilePath;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_coating_input_form);

        context=this;
        ButterKnife.bind(this);
        netConnectivity();
        DataBaseHelper = new DataBaseHelper(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);

        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerJointType.setAdapter(adapter);
        loadAlignment();
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
            }
        });
        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
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
                }
                /*else if (s.length()==2){
                    strKM=strKM+"0"+s;
                }*/
                else {
                    strKM= strKM+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointNumber.setText(strKM+strJointType+strJointNo+strSuffix);
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
                edJointNumber.setText(strKM+strJointType+strJointNo+strSuffix);
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
                edJointNumber.setText(strKM+strJointType+strJointNo+strSuffix);
            }
        });



    }
    private void loadAlignment() {
        // dataBaseHelper.open();

        alignmentIDList.clear();
        alignmentNumList.clear();
        Cursor cur = DataBaseHelper.getAll("SELECT AlignmentID,AlignmentName" +
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
            Toast.makeText(JoinCoatingInputForm.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }

    @OnItemSelected(R.id.spinerJointType)
    public void onControlRoomSelected(int position)
    {
        if(position>0) {
            strJointType=arrJointType[position];
            edJointNumber.setText(strKM+strJointType+strJointNo+strSuffix);
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
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(JoinCoatingInputForm.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(JoinCoatingInputForm.this,
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
    private void validateFields(){

        String strDate=editDate.getText().toString();
        String strReportNo=editReportNo.getText().toString();
        String strRemark=edit_remark.getText().toString();
        String StrJointNumber=edJointNumber.getText().toString();

        int selectedVisualId=rdGroupVisual.getCheckedRadioButtonId();
        int selectedHolidayId=rdGroupHoliday.getCheckedRadioButtonId();
        int selectedPeelId=rdGroupPeel.getCheckedRadioButtonId();
        rdVisual=findViewById(selectedVisualId);
        rdHoliday=findViewById(selectedHolidayId);
        rdPeel=findViewById(selectedVisualId);
        //Toast.makeText(context,rdVisual.getText().toString()+"\n"+rdHoliday.getText().toString(),Toast.LENGTH_LONG).show();

        if (strDate.equals("")){
            Toast.makeText(context, "Select Date", Toast.LENGTH_LONG).show();
        }else if (strReportNo.equals("")){
            Toast.makeText(context, "Enter Report No.", Toast.LENGTH_LONG).show();
        }else if (edKM.getText().toString().equals("")){
            Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_LONG).show();
        }else if (spinerJointType.getSelectedItem().toString().equals("Select Type")){
            Toast.makeText(context, "Select Joint Type", Toast.LENGTH_LONG).show();
        }else if (edJointNo.getText().toString().equals("")){
            Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(context, "Inserted", Toast.LENGTH_LONG).show();
            HashMap<String,String> hashMap=new HashMap<>();

            hashMap.put("UserID", SessionUtil.getUserId(context));
            hashMap.put("SectionID",SessionUtil.getAssignedSection(context));
            hashMap.put("type","jointcoating");

            hashMap.put("JointCoatingRepNo",strReportNo);
            hashMap.put("JointDate",strDate);
            hashMap.put("JointID",StrJointNumber);
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");

            hashMap.put("Visuals",rdVisual.getText().toString());
            hashMap.put("Holiday",rdHoliday.getText().toString());
            hashMap.put("pre_heating",edpre_heating.getText().toString());
            hashMap.put("PipeCoatingThickness",edPipeCoat.getText().toString());
            hashMap.put("JointCoatingThickness",edJointCoatThick.getText().toString());
            hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());


            hashMap.put("relative_humidity",editrelative_humidity.getText().toString());
          //  hashMap.put("coating_type",editcoating_type.getText().toString());
           // hashMap.put("sleeve_option",editsleeve_option.getText().toString());

            hashMap.put("Remarks",strRemark);

            hashMap.put("pipe_dia",edpipe_dia.getText().toString());
            hashMap.put("pipe_thickness",edpipe_thick.getText().toString());
        //    hashMap.put("batch_no",edbatch_no.getText().toString());
            hashMap.put("roughness",edroughness.getText().toString());
            hashMap.put("dust_containment",eddust_con.getText().toString());
            hashMap.put("Coating_Thick","");
            hashMap.put("primer",edprimer.getText().toString());
            hashMap.put("primer_b",edprimerb.getText().toString());
            hashMap.put("Pipe_Dia",edpipe_dia.getText().toString());
            hashMap.put("pipe_thickness",edpipe_thick.getText().toString());
            hashMap.put("Thickness",edpipe_thick.getText().toString());
            hashMap.put("Primer_A_Batch",edprimer.getText().toString());
            hashMap.put("Primer_B_Batch",edprimerb.getText().toString());
            hashMap.put("Holiday_Tester", edholiday_tester_no.getText().toString());
            hashMap.put("Type_Coating", editcoating_type.getText().toString());
            hashMap.put("Sleave",editsleeve_option.getText().toString());
            hashMap.put("Callibration",edit_calibration.getText().toString());
            hashMap.put("BatchNo",edbatch_no.getText().toString());
            hashMap.put("ChainageFrom",edChainageFrom.getText().toString());
            hashMap.put("ChainageTo",edChainageTo.getText().toString());
            hashMap.put("Material",edmaterial.getText().toString());
            hashMap.put("TN_RH",editrelative_humidity.getText().toString());
            hashMap.put("TN_Elcometer", edelcometer.getText().toString());
            hashMap.put("TN_PeelTest", rdPeel.getText().toString());
            hashMap.put("Roughness",edroughness.getText().toString());
            hashMap.put("Dust",eddust_con.getText().toString());
            hashMap.put("Profile",edvisual.getText().toString());
            hashMap.put("WFT",edwftprimer.getText().toString());
            hashMap.put("TN_Manpower",edit_manpower.getText().toString());
            hashMap.put("TN_Machineries",edit_machineries.getText().toString());
            hashMap.put("body_3",edDSPosition3.getText().toString());
            hashMap.put("body_6",edDSPosition6.getText().toString());
            hashMap.put("body_9",edDSPosition9.getText().toString());
            hashMap.put("body_12",edDSPosition12.getText().toString());
            hashMap.put("body_avg",edDSPositionAvg.getText().toString());

            hashMap.put("bodyu_3",edUSPosition3.getText().toString());
            hashMap.put("bodyu_6",edUSPosition6.getText().toString());
            hashMap.put("bodyu_9",edUSPosition9.getText().toString());
            hashMap.put("bodyu_12",edUSPosition12.getText().toString());
            hashMap.put("bodyu_avg",edUSPositionAvg.getText().toString());

            hashMap.put("joint_3",edWeldPosition3.getText().toString());
            hashMap.put("joint_6",edWeldPosition6.getText().toString());
            hashMap.put("joint_9",edWeldPosition9.getText().toString());
            hashMap.put("joint_12",edWeldPosition12.getText().toString());
            hashMap.put("joint_avg",edWeldPositionAvg.getText().toString());
            hashMap.put("Photo","JointCoating"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");



            if (isOnline()){
                InsertData(context,hashMap);
            }else {
                boolean s=new DataBaseHelper(context).InsertJointCoating(hashMap);
                if (s){
                    Clear();
                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error To Inserted Locally!", Toast.LENGTH_SHORT).show();
                }
            }

        }

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
                        Log.e("Response", stringResponse);
                        progressDialog.dismiss();
                        /*try {

                            JSONObject jsonObject = new JSONObject(stringResponse);

                            if (jsonObject.getString("Status") == null ||
                                    !jsonObject.getString("Status").equalsIgnoreCase("Success")) {
                                Toast.makeText(context, jsonObject.getString("Messege"), Toast.LENGTH_LONG).show();
                                Log.e("Response", jsonObject.toString());
                                return;
                            }

                            JSONObject response = new JSONObject(stringResponse);
                            Log.e("Response", response.toString());*/
                            if (stringResponse.contains("1")) {
                                Clear();
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
        edSuffix.getText().clear();
        edit_remark.getText().clear();
        edKM.getText().clear();
        edJointNo.getText().clear();
        edPeelTest.getText().clear();
        edJointCoatThick.getText().clear();
        edPipeCoat.getText().clear();
        strImage="";image="";
       edpipe_dia.getText().clear();
        edpipe_thick.getText().clear();
       edbatch_no.getText().clear();
       edroughness.getText().clear();
       eddust_con.getText().clear();
      edprimer.getText().clear();
      edprimerb.getText().clear();

        editsleeve_option.getText().clear();
        editcoating_type.getText().clear();
        editrelative_humidity.getText().clear();


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
