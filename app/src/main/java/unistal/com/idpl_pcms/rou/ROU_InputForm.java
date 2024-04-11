package unistal.com.idpl_pcms.rou;

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
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.RowFormActivity2;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class ROU_InputForm extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{


    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_terrain)
    EditText editTerrain;
    @BindView(R.id.edit_village)
    EditText edvillage;
    @BindView(R.id.edit_tehsil)
    EditText edTehsil;
    @BindView(R.id.edit_district)
    EditText edDistrict;
    @BindView(R.id.edit_remark)
    EditText edRemark;
    @BindView(R.id.edit_chainage_done)
    EditText editChainageDone;
    @BindView(R.id.edit_nameofstructure)
    EditText editNameofStructure;
    @BindView(R.id.edit_detailsofstructure)
    EditText editdetailsofstructure;
    @BindView(R.id.edit_across_row)
    EditText editacross_row;
    @BindView(R.id.edit_details)
    EditText editdetails;
    @BindView(R.id.edit_tp_remarks)
    EditText editTPRemarks;

    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;
    Context context;
    ProgressDialog progressDialog;
    @BindView(R.id.edWeather)
    Spinner edWeather;

    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    DataBaseHelper dbHelper;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rou__input_form);
        netConnectivity();
        context=this;

        dbHelper=new DataBaseHelper(context);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editDate.getText().toString().equals("")){
                    Toast.makeText(ROU_InputForm.this, "Choose Date", Toast.LENGTH_SHORT).show();
                }
                else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }
                else if (editChainageFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Chainage From", Toast.LENGTH_SHORT).show();
                }
                else if (editChainageTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Chainage To", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("type","rowhand");
                    hashMap.put("UserID", SessionUtil.getUserId(context));
                    hashMap.put("SectionID", SessionUtil.getAssignedSection(context));
                    hashMap.put("Weather", edWeather.getSelectedItem().toString());

                    hashMap.put("TR_Rouhandover_Date",editDate.getText().toString());
                    hashMap.put("TR_Report_Number",editReportNo.getText().toString());
                    hashMap.put("MR_Chainage_From",editChainageFrom.getText().toString());
                    hashMap.put("MR_Chainage_To",editChainageTo.getText().toString());
                    hashMap.put("TypeTerrain", editTerrain.getText().toString());
                    hashMap.put("Village",edvillage.getText().toString());
                    hashMap.put("Tehsil",edTehsil.getText().toString());
                    hashMap.put("District",edDistrict.getText().toString());
                    hashMap.put("DetailsStructure", editdetailsofstructure.getText().toString());
                    hashMap.put("across_row", editacross_row.getText().toString());
                    hashMap.put("Details", editdetails.getText().toString());
                    hashMap.put("chainage", editChainageDone.getText().toString());
                    hashMap.put("namestructre", editNameofStructure.getText().toString());
                    //  hashMap.put("tpremark", editTPRemarks.getText().toString());
                    hashMap.put("Ipno", editTPRemarks.getText().toString());
                    hashMap.put("TN_Remarks",edRemark.getText().toString());
                    hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID()+"");

                    hashMap.put("Photo", "RouteHandover"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");
                    hashMap.put("ImageData", image);

                    hashMap.put("Skipping_detail","");
                    hashMap.put("Obstacle","");
                    hashMap.put("RPanchnama","");
                    if (isOnline()){
                        InsertData(context,hashMap);
                    }else {
                        boolean b=dbHelper.RouInsert(hashMap);
                        if (b){
                            editChainageFrom.getText().clear();
                            editChainageTo.getText().clear();
                            edvillage.getText().clear();
                            edTehsil.getText().clear();
                            edDistrict.getText().clear();
                            edRemark.getText().clear();
                            image = "";
                            hashMap.clear();
                            Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Data Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
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
            Toast.makeText(ROU_InputForm.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

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
                        if (stringResponse.equals("1")){
                            editChainageFrom.getText().clear();
                            editChainageTo.getText().clear();
                            edvillage.getText().clear();
                            edTehsil.getText().clear();
                            edDistrict.getText().clear();
                            edRemark.getText().clear();
                            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        }/*else {
                            Toast.makeText(context, "Data not Inserted!", Toast.LENGTH_SHORT).show();
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


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(ROU_InputForm.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ROU_InputForm.this,
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
