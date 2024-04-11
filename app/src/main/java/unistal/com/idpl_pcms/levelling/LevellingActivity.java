package unistal.com.idpl_pcms.levelling;

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
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.GPSTracker;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.lowering.LoweringInputForm;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class LevellingActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.spinnerAlgnSheetNo)
    Spinner spinAlignment;

    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_pipedia)
    EditText editPipeDia;

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

    @BindView(R.id.edJointNumberFrom)
    EditText edJointNumberFrom;
    @BindView(R.id.edKMFrom)
    EditText edKMFrom;
    @BindView(R.id.spinerJointTypeFrom)
    Spinner spinerJointTypeFrom;
    @BindView(R.id.edJointNoFrom)
    EditText edJointNoFrom;
    @BindView(R.id.edSuffixFrom)
    EditText edSuffixFrom;

    @BindView(R.id.edJointNumberTo)
    EditText edJointNumberTo;
    @BindView(R.id.edKMTo)
    EditText edKMTo;
    @BindView(R.id.spinerJointTypeTo)
    Spinner spinerJointTypeTo;
    @BindView(R.id.edJointNoTo)
    EditText edJointNoTo;
    @BindView(R.id.edSuffixTo)
    EditText edSuffixTo;

    @BindView(R.id.edCover)
    EditText edCover;
    @BindView(R.id.edit_pipethick)
    EditText edpipe_thick;
    @BindView(R.id.edit_tpno)
    EditText edTPNo;
    @BindView(R.id.edit_lowered_sec_length)
    EditText edit_lowered_sec_length;

    @BindView(R.id.edEasting)
    EditText edEasting;
    @BindView(R.id.edNorthing)
    EditText edNorthing;
    @BindView(R.id.edPipeTop)
    EditText edPipeTop;
    @BindView(R.id.edNGL)
    EditText edNGL;
    @BindView(R.id.accuracytv)
    TextView acctv;

    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;

    @BindView(R.id.edit_remark)
    EditText edit_remark;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.btnNGL)
    TextView btnNGL;
    @BindView(R.id.btnLocation)
    TextView btnLocation;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image = "";
    ProgressDialog progressDialog;
    Context context;

    DataBaseHelper dbHelper;

    String[] arrJointType = {"Select Type", "M", "T", "FT", "GT", "FTR", "TR", "MTR"};

    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage = "";
    private String pictureFilePath;

    String strKM = "K";
    String strJointType= "";
    String strJointNo = "";
    String strSuffix= "";
    String strKMFrom = "K";
    String strJointTypeFrom= "";
    String strJointNoFrom = "";
    String strSuffixFrom= "";
    String strKMTo = "K";
    String strJointTypeTo= "";
    String strJointNoTo = "";
    String strSuffixTo= "";

    GPSTracker gps;
    double latitude, longitude, altitude;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String strAccuracy;
    private String strLantitude, strLongitude, strAltitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelling);
        context = this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        netConnectivity();
        dbHelper = new DataBaseHelper(context);
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, arrJointType);
        spinerJointType.setAdapter(adapter);spinerJointTypeFrom.setAdapter(adapter);spinerJointTypeTo.setAdapter(adapter);
        acctv.setText("Accuracy = " + strAccuracy);

        edKM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKM = "K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strKM = strKM + "0" + s;
                }
                /*else if (s.length()==2){
                    strKMFrom=strKMFrom+"0"+s;
                }*/
                else {
                    strKM = strKM + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointNumber.setText(strKM + strJointType + strJointNo + strSuffix);
            }
        });
        edJointNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNo = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strJointNo = "0" + s;
                } else {
                    strJointNo = "" + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointNumber.setText(strKM + strJointType + strJointNo + strSuffix);
            }
        });
        edSuffix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffix = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffix = "" + s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointNumber.setText(strKM + strJointType+ strJointNo + strSuffix);
            }
        });
        edKMFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKMFrom = "K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strKMFrom = strKMFrom + "0" + s;
                }
                /*else if (s.length()==2){
                    strKMFrom=strKMFrom+"0"+s;
                }*/
                else {
                    strKMFrom = strKMFrom + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointNumberFrom.setText(strKMFrom + strJointTypeFrom + strJointNoFrom + strSuffixFrom);
            }
        });
        edJointNoFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNoFrom = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strJointNoFrom = "0" + s;
                } else {
                    strJointNoFrom = "" + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointNumberFrom.setText(strKMFrom + strJointTypeFrom + strJointNoFrom + strSuffixFrom);
            }
        });
        edSuffixFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffixFrom = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffixFrom = "" + s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointNumberFrom.setText(strKMFrom + strJointTypeFrom+ strJointNoFrom + strSuffixFrom);
            }
        });

        edKMTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKMTo = "K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strKMTo = strKMTo + "0" + s;
                }
                /*else if (s.length()==2){
                    strKMFrom=strKMFrom+"0"+s;
                }*/
                else {
                    strKMTo = strKMTo + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointNumberTo.setText(strKMTo + strJointTypeTo + strJointNoTo + strSuffixTo);
            }
        });
        edJointNoTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNoTo = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    strJointNoTo = "0" + s;
                } else {
                    strJointNoTo = "" + s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointNumberTo.setText(strKMTo + strJointTypeTo + strJointNoTo + strSuffixTo);
            }
        });
        edSuffixTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffixTo = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffixTo = "" + s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointNumberTo.setText(strKMTo + strJointTypeTo+ strJointNoTo + strSuffixTo);
            }
        });




        gps = new GPSTracker(LevellingActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            altitude = gps.getAltitude();
            // \n is for new line
            Log.e("Your Location is", " \nLat: " +
                    latitude + "\nLong: " + longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
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
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNorthing.setText(strLantitude + "");
                edEasting.setText(strLongitude + "");
              //  edPipeTop.setText(strAltitude + "");
            }
        });
        btnNGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNGL.setText(strAltitude);
               // edEasting.setText(strLongitude + "");
                //  edPipeTop.setText(strAltitude + "");
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        edCover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double chf ,cht;
                if (edCover.getText().toString().trim().isEmpty())
                {chf=0.0;}else{chf = Double.valueOf(edCover.getText().toString());}
                if (edNGL.getText().toString().trim().isEmpty())
                {cht=0.0;}else{cht= Double.valueOf(edNGL.getText().toString());}
                Double sl =(double) (Math.round((cht-chf) * 100.000) / 100.000) ;
                edPipeTop.setText(sl+"");
            }
        });
        edNGL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double chf ,cht;
                if (edCover.getText().toString().trim().isEmpty())
                {chf=0.0;}else{chf = Double.valueOf(edCover.getText().toString());}
                if (edNGL.getText().toString().trim().isEmpty())
                {cht=0.0;}else{cht= Double.valueOf(edNGL.getText().toString());}
                Double sl =(double) (Math.round((cht-chf) * 100.000) / 100.000) ;
                edPipeTop.setText(sl+"");
            }
        });

        loadAlignment();
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
            Toast.makeText(LevellingActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }


    @OnItemSelected(R.id.spinerJointType)
    public void onControlRoomSelected(int position) {
        if (position > 0) {
            strJointType = arrJointType[position];
            edJointNumber.setText(strKM + strJointType + strJointNo + strSuffix);
        }
    }
    @OnItemSelected(R.id.spinerJointTypeFrom)
    public void onControlRoomSelectedFrom(int position) {
        if (position > 0) {
            strJointTypeFrom = arrJointType[position];
            edJointNumberFrom.setText(strKMFrom + strJointTypeFrom + strJointNoFrom + strSuffixFrom);
        }
    }

    @OnItemSelected(R.id.spinerJointTypeTo)
    public void onControlRoomSelectedTo(int position) {
        if (position > 0) {
            strJointTypeTo = arrJointType[position];
            edJointNumberTo.setText(strKMTo + strJointTypeTo + strJointNoTo + strSuffixTo);
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

    private void validateFields() {
        String strDate = editDate.getText().toString();
        String strReportNo = editReportNo.getText().toString();
        String strRemark = edit_remark.getText().toString();


        if (strDate.equals("")) {
            Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
        } else if (strReportNo.equals("")) {
            Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
        } else if (edKM.getText().toString().equals("")) {
            Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
        } else if (spinerJointType.getSelectedItem().toString().equals("Select Type")) {
            Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
        } else if (edJointNo.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
        } else if (edCover.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Cover ", Toast.LENGTH_SHORT).show();
        }else if (edEasting.getText().toString().trim().equals("")) {
            Toast.makeText(context, "Enter Coordinates ", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("UserID", SessionUtil.getUserId(context));
            hashMap.put("SectionID", SessionUtil.getAssignedSection(context));
            hashMap.put("type", "levelling");

            hashMap.put("LevellingRepNo", strReportNo);
            hashMap.put("LevellingDate", strDate);
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");

            hashMap.put("JointID", edJointNumber.getText().toString());

            hashMap.put("Cover", ""+edCover.getText().toString());
            hashMap.put("XCord", "" +  edNorthing.getText().toString()+"");
            hashMap.put("YCord", "" +  edEasting.getText().toString()+"");
            hashMap.put("ZCord", "");
            hashMap.put("NGL", "" + edNGL.getText().toString()+"");
            hashMap.put("Remarks", strRemark);
            hashMap.put("Alignment_Sheet",AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());

            hashMap.put("ChFrom", editChainageFrom.getText().toString());
            hashMap.put("ChTo", editChainageTo.getText().toString());
            hashMap.put("Elevation", edPipeTop.getText().toString());
            hashMap.put("PipeDia", editPipeDia.getText().toString());

            hashMap.put("JointFrom", edJointNumberFrom.getText().toString());
            hashMap.put("JointTo", edJointNumberTo.getText().toString());
            hashMap.put("PipeThik", edpipe_thick.getText().toString());
            hashMap.put("TpNo", edTPNo.getText().toString());
            hashMap.put("SectionLength", edit_lowered_sec_length.getText().toString());
            hashMap.put("Photo","Levelling"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");
            if (isOnline()) {
                InsertData(context, hashMap);
            } else {
                boolean s = dbHelper.LevellingInsert(hashMap);
                if (s) {
                    Clear();
                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error To Inserted Locally!", Toast.LENGTH_SHORT).show();
                }

            }

        }


    }

    private void Clear(){
        edKM.getText().clear();
        edJointNumber.getText().clear();
        edSuffix.getText().clear();
        edJointNo.getText().clear();
        edit_remark.getText().clear();
        edCover.getText().clear();
        edPipeTop.getText().clear();
        edEasting.getText().clear();
        edNorthing.getText().clear();
        edNGL.getText().clear();
        image = "";

    }
    protected boolean isOnline () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void InsertData ( final Context context, final Map<String, String> params){

        progressDialog.show();
        Activity activity = (Activity) context;


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.e("Response", stringResponse);
                        progressDialog.dismiss();
                       /* try {

                            JSONObject jsonObject = new JSONObject(stringResponse);

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
                            }
*/
                            if (stringResponse.contains("1")) {
                                Clear();
                                params.clear();
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                           } /* else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
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
        if (ContextCompat.checkSelfPermission(LevellingActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LevellingActivity.this,
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

        }    if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
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
    private File getPictureFile () throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "MBPL_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }
    private Bitmap decodeFile (File f){
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
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat")
        String imgName = "img_" + new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()) + ".jpg";
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
        Log.e("ImagePath", destFile.getAbsolutePath() + "\t" + String.valueOf(deleted));
        //image.setImageBitmap(b);
        strImage = BitMapToString(b);

        return b;
    }
    public String BitMapToString (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }
    @Override
    protected void onStart() {
        super.onStart();
        startLocationListener();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SmartLocation.with(context).location().stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationListener();
    }

    private void startLocationListener() {

        long mLocTrackingInterval = 1000 * 1*15; // 5 sec
        float trackingDistance = 0;

        // LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;
        LocationAccuracy trackingAccuracy = LocationAccuracy.HIGH;

        LocationParams.Builder builder = new LocationParams.Builder()
                .setAccuracy(trackingAccuracy)
                .setDistance(trackingDistance)
                .setInterval(mLocTrackingInterval);

        SmartLocation.with(this)
                .location()
                .continuous()
                .config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        // textView.setText("Accuracy(Estimated Position Error): "+location.getAccuracy()+"\n\nLat: "+location.getLatitude()+"\n\nLong: "+location.getLongitude());
                        strLongitude = location.getLongitude() + "";
                        strLantitude = location.getLatitude() + "";
                        strAccuracy = location.getAccuracy() + "";
                        strAltitude = location.getAltitude() + "";
                        acctv.setText("Accuracy = " + strAccuracy);
                    }
                });
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