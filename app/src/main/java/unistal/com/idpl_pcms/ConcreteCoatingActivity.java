package unistal.com.idpl_pcms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.provider.MediaStore;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import unistal.com.idpl_pcms.levelling.LevellingActivity;
import unistal.com.idpl_pcms.model.AlignmentModel;
import unistal.com.idpl_pcms.stringing.StringingActivity;

public class ConcreteCoatingActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.spinResistivity)
    Spinner spinResistivity;
    @BindView(R.id.edit_chainage)
    EditText edit_chainage;
    @BindView(R.id.edit_PipeNo)
    EditText edit_PipeNo;
    @BindView(R.id.edit_concretecoatingthickness)
    EditText edit_concretecoatingthickness;
    @BindView(R.id.edit_concretecoatinglength)
    EditText edit_concretecoatinglength;
    @BindView(R.id.edit_holiday_test)
    EditText edit_holiday_test;
    @BindView(R.id.edit_megger_test)
    EditText edit_megger_test;
    @BindView(R.id.edit_remark)
    EditText edit_remark;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    DataBaseHelper dataBaseHelper;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};


    String[] arrResistivity = {"Pass", "Fail"};
    ProgressDialog progressDialog;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String image = "";
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_coating);
        context = this;
        ButterKnife.bind(this);
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
        ArrayAdapter resistAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrResistivity);
        resistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinResistivity.setAdapter(resistAdapter);
      /*  edit_PipeNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });*/
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
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ConcreteCoatingActivity.this,
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
            hashMap.put("UserID", SessionUtil.getUserId(ConcreteCoatingActivity.this));
            hashMap.put("SectionID",SessionUtil.getAssignedSection(ConcreteCoatingActivity.this));
            hashMap.put("type","concrete");
            hashMap.put("TR_Date",strDate);
            hashMap.put("TR_Report_Number",strReportNo);
            hashMap.put("TR_alignment", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");
            hashMap.put("Resistivity",spinResistivity.getSelectedItem().toString()+"");
            hashMap.put("Chainage",strChainage);
            hashMap.put("Holiday_Test",edit_holiday_test.getText().toString().trim());
            hashMap.put("Megger",edit_megger_test.getText().toString().trim());
            hashMap.put("TR_Pipe_Number",strPipeNo);
            hashMap.put("MR_CCLength",edit_concretecoatinglength.getText().toString());
            hashMap.put("MR_CCThickness",edit_concretecoatingthickness.getText().toString());

            hashMap.put("TN_Remarks",strRemark);
            hashMap.put("Photo","ConcreteCoat"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");
            hashMap.put("ImageData", image+ "");
            if (isOnline()){

                InsertData(ConcreteCoatingActivity.this,hashMap);

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
            Toast.makeText(ConcreteCoatingActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }
    void Clear()
    {
        edit_chainage.setText("");
        edit_PipeNo.setText("");
        edit_remark.setText("");
        image = "";
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