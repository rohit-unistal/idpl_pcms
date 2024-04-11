package unistal.com.idpl_pcms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unistal.com.idpl_pcms.clearing_and_grading.ClearingGrading2Activity;
import unistal.com.idpl_pcms.database.DataBaseHelper;

public class SoilResistivityActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_distance_cleared)
    TextView editDistance;
    @BindView(R.id.edit_soilresistivity)
    EditText edit_soilresistivity;
    @BindView(R.id.edit_instrument)
    EditText edit_instrument;
    @BindView(R.id.edit_location)
    EditText edit_location;
    @BindView(R.id.edit_depth)
    EditText edit_depth;
    @BindView(R.id.edit_resistance)
    EditText edit_resistance;

    @BindView(R.id.edit_chainage)
    EditText editChainage;
    @BindView(R.id.edit_multiplier)
    EditText edit_multiplier;

    @BindView(R.id.edit_resistivity)
    EditText edit_resistivity;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    String image = "";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    Context context;
    private ProgressLoading loadingDialog;
    @BindView(R.id.netstatus)
    ImageView netStatus;

  //  ProgressDialog progressDialog;
    DataBaseHelper dataBaseHelper;
    private NetworkStateReceiver networkStateReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_resistivity);
        ButterKnife.bind(this);
        context = this;
        init();
    }
    private void init()
    {
        loadingDialog = new ProgressLoading(this);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        netConnectivity();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editDate.getText().toString().equals("")) {
                    Toast.makeText(SoilResistivityActivity.this, "Choose Date", Toast.LENGTH_SHORT).show();
                } else if (editReportNo.getText().toString().equals("")) {
                    Toast.makeText(SoilResistivityActivity.this, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }
                /*else if (edSpread.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Spread No.", Toast.LENGTH_SHORT).show();
                }
                else if (spinAlignment.getSelectedItem().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Select Alignment Sheet No.", Toast.LENGTH_SHORT).show();
                }else if (edWeather.getText().toString().equals("")){
                    Toast.makeText(ClearingGrading2Activity.this, "Enter Weather", Toast.LENGTH_SHORT).show();
                }*/
                else if (editChainageFrom.getText().toString().equals("")) {
                    Toast.makeText(SoilResistivityActivity.this, "Enter Chainage From", Toast.LENGTH_SHORT).show();
                } else if (editChainageTo.getText().toString().equals("")) {
                    Toast.makeText(SoilResistivityActivity.this, "Enter Chainage To", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("type", "soilresistivity");
                    params.put("UserID", SessionUtil.getUserId(SoilResistivityActivity.this));
                    params.put("SectionID", SessionUtil.getAssignedSection(SoilResistivityActivity.this));

                    params.put("TR_Soil_Date", editDate.getText().toString());
                    params.put("TR_Report_Number", editReportNo.getText().toString());
                    params.put("MR_Chainage_From", editChainageFrom.getText().toString());
                    params.put("MR_Chainage_To", editChainageTo.getText().toString());
                    params.put("MR_Distance_Cleared", editDistance.getText().toString());

                    params.put("instrument_name", edit_instrument.getText().toString());
                    params.put("soil_resistivity", edit_soilresistivity.getText().toString());
                    params.put("location", edit_location.getText().toString());
                    params.put("depth", edit_depth.getText().toString());
                    params.put("Resistance", edit_resistance.getText().toString());
                    params.put("MR_Chainage", editChainage.getText().toString());

                    params.put("Multiplier", edit_multiplier.getText().toString());


                    params.put("Resistivity", edit_resistivity.getText().toString());
                    params.put("TR_Clearing_Date", editDate.getText().toString());
                    params.put("TN_Remarks", editRemarks.getText().toString());
                    params.put("Length", editDistance.getText().toString());
                    params.put("Photo", "sr" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpeg");//
                    params.put("ImageData", image + "");
                    Log.e("params", params.toString());
                    if (isOnline()) {
                        submitClearing(params);
                    }else {
                        boolean s=dataBaseHelper.ClearingGradingInsert(params);
                        if (s){
                            clear();
                            params.clear();
                            Toast.makeText(SoilResistivityActivity.this, "Locally saved", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SoilResistivityActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
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

        image = "";

        editRemarks.getText().clear();

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
        if (ContextCompat.checkSelfPermission(SoilResistivityActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SoilResistivityActivity.this,
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
                            Toast.makeText(SoilResistivityActivity.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        }
                        loadingDialog.dismiss();


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.getMessage();
                loadingDialog.dismiss();
                Toast.makeText(SoilResistivityActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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