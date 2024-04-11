package unistal.com.idpl_pcms.clearing_and_grading;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.ProgressLoading;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.rou.ROU_InputForm;

public class ClearingGrading2Activity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;

    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_ipnofrom)
    EditText editIPNofrom;
    @BindView(R.id.edit_ipnoto)
    EditText editIPNoTo;

    @BindView(R.id.edit_tpnofrom)
    EditText editTPNoFrom;
    @BindView(R.id.edit_tpnoto)
    EditText editTPNoto;
    @BindView(R.id.edit_nameofstructure)
    EditText edit_nameofstructure;
    @BindView(R.id.edit_chainage)
    EditText edit_chainage;

    @BindView(R.id.edit_OtherDetails)
    EditText editOtherDetails;
    /*@BindView(R.id.edit_surveycheck)
    CheckBox editSurveyCheck;
    @BindView(R.id.edit_markerpost)
    CheckBox editMarkerPost;
    @BindView(R.id.edit_row)
    EditText editROW;*/
    @BindView(R.id.edit_markeriptp)
    EditText editMarkerIPTP;
    @BindView(R.id.edit_row)
    EditText editROW;
    @BindView(R.id.edit_rowStatus)
    EditText editROWStatus;
    @BindView(R.id.edit_manpower)
    EditText editmanpower;
    @BindView(R.id.edit_machineries)
    EditText editMachineries;
    @BindView(R.id.edit_sec_length)
    TextView editDistance;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.edit_work)
    EditText editWork;
    String alignment;
    @BindView(R.id.spinnerAlgnSheetNo)
    Spinner spinAlignment;
    @BindView(R.id.editGround)
    Spinner spinGround;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    /* @BindView(R.id.lvPipeLine)
     ListView lvPipeline;*/
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.netstatus)
    ImageView netStatus;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;

    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    private  String image;

    private NetworkStateReceiver networkStateReceiver;

    /*String s[];
    ArrayList<AlignmentModel> alignmentNo;
    ArrayList<String> alignmentName;//,pipeLineName,pipeLineID,selectedSections;
    String alignName;*/
    // AlignmentModel alignmentModel;
    private ProgressLoading loadingDialog;
    private ArrayAdapter spinnerArrayAdapter;
    //ArrayAdapter<SectionModel> lvPipelineAdapter;
    // ArrayList<SectionModel> sectionList;
    // private CustomAdapter spinnerArrayAdapter ;


    ArrayList<String> categories;

    String[] arrGround = {"Normal", "Rock", "Seismic"};
    DataBaseHelper dataBaseHelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clearing_and_grading);
        ButterKnife.bind(this);
        context = this;
        netConnectivity();
        categories = new ArrayList<String>();
        categories.add("alignment");
        categories.add("alignment1");
        categories.add("alignment2");
        categories.add("alignment3");
        categories.add("alignment4");
        dataBaseHelper=new DataBaseHelper(getApplicationContext());
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        loadAlignment();

        //  spinAlignment = (Spinner) findViewById(R.id.spin_alignment);
        // alignmentModel = new AlignmentModel();
       // alignmentNo = new ArrayList<>();
       // alignmentName = new ArrayList<String>();
       /* pipeLineID = new ArrayList<>();
        pipeLineName = new ArrayList<>();
        sectionList = new ArrayList<>();
        selectedSections = new ArrayList<>();*/
        // AlignmentIDList = new ArrayList<>();
        loadingDialog = new ProgressLoading(this);
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);

        ArrayAdapter groundAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrGround);
        groundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinGround.setAdapter(groundAdapter);
       // sheetAlignment();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editDate.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Choose Date", Toast.LENGTH_SHORT).show();
                }else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }
                /*else if (edSpread.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Spread No.", Toast.LENGTH_SHORT).show();
                }
                else if (spinAlignment.getSelectedItem().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Select Alignment Sheet No.", Toast.LENGTH_SHORT).show();
                }else if (edWeather.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Weather", Toast.LENGTH_SHORT).show();
                }*/
                else if (editChainageFrom.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Chainage From", Toast.LENGTH_SHORT).show();
                }else if (editChainageTo.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Chainage To", Toast.LENGTH_SHORT).show();
                }else{
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "clearing");
                params.put("UserID", SessionUtil.getUserId(ClearingGrading2Activity.this));
                params.put("SectionID", SessionUtil.getAssignedSection(ClearingGrading2Activity.this));

                    params.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
                    params.put("Weather",edWeather.getSelectedItem().toString()+"");

                    params.put("MR_Chainage_From", editChainageFrom.getText().toString());
                params.put("MR_Chainage_To", editChainageTo.getText().toString());

                    params.put("IPFrom", editIPNofrom.getText().toString());
                    params.put("IPTo", editIPNoTo.getText().toString());
                    params.put("TPFrom", editTPNoFrom.getText().toString());
                    params.put("TPTo", editTPNoto.getText().toString());
                    params.put("namestructure", edit_nameofstructure.getText().toString());
                    params.put("chainage", edit_chainage.getText().toString());

                    params.put("spinGround", spinGround.getSelectedItem().toString());

                params.put("TR_Report_Number", editReportNo.getText().toString());
                params.put("DetailsStructure", editOtherDetails.getText().toString());
                params.put("TR_Clearing_Date", editDate.getText().toString());
                params.put("TN_Remarks", editRemarks.getText().toString());
                    params.put("work_interruption", editWork.getText().toString());
                    params.put("Length", editDistance.getText().toString());
                    params.put("Description", editOtherDetails.getText().toString());
                    params.put("IP_No", editMarkerIPTP.getText().toString());
                    params.put("Row", editROW.getText().toString());

                    params.put("TN_RowStatus", editROWStatus.getText().toString());
                    params.put("TN_Manpower", editmanpower.getText().toString());
                    params.put("TN_Machineries", editMachineries.getText().toString());
                    params.put("Photo","Clearing"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
                    params.put("ImageData",image + "");
                    Log.e("params",params.toString());
                if (isOnline()){
                    submitClearing(params);
                }else {
                    boolean s=dataBaseHelper.ClearingGradingInsert(params);
                    if (s){
                        clear();
                        params.clear();
                        Toast.makeText(ClearingGrading2Activity.this, "Locally saved", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ClearingGrading2Activity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }}
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate();
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
    private void clear(){
        editChainageFrom.getText().clear();
        editChainageTo.getText().clear();
        editIPNofrom.getText().clear();
        editIPNoTo.getText().clear();
        editTPNoFrom.getText().clear();
        editTPNoto.getText().clear();
        image = "";
        editOtherDetails.getText().clear();
        editRemarks.getText().clear();

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
            Toast.makeText(ClearingGrading2Activity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
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
    private void submitClearing( final Map<String, String> hashMap) {
        //getSelectedSections();
        RequestQueue queue = Volley.newRequestQueue(this);
//        final String url = "http://mbpl2sf.plims.net/API/mobileapis.php";
       // final String url = AppConstants.APP_BASE_URL + AppConstants.ADD_CLEARING_GRANDING;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Response", response);
                        if(response.contains("1")) {
                            clear();
                            hashMap.clear();
                            Toast.makeText(ClearingGrading2Activity.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        }
                        loadingDialog.dismiss();


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.getMessage();
                loadingDialog.dismiss();
                Toast.makeText(ClearingGrading2Activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error", error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                return hashMap;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

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
    protected void onResume() {
        super.onResume();
    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(ClearingGrading2Activity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ClearingGrading2Activity.this,
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image

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

