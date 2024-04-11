package unistal.com.idpl_pcms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
/*import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;*/
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
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HindranceActivity extends AppCompatActivity {
    @BindView(R.id.backarrow)
    ImageView backArrow;
    @BindView(R.id.edit_activityeffected)
    Spinner editActivityEffected;
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_areahold)
    EditText editAreaHold;
    @BindView(R.id.spinDescription)
    Spinner spinDescription;
    @BindView(R.id.edit_datefrom)
    TextView editDateFrom;
    @BindView(R.id.edit_dateto)
    TextView editDateTo;
    @BindView(R.id.edit_weather)
    Spinner editWeather;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.edit_responsibility)
    Spinner editResponsibility;
   /* @BindView(R.id.edit_status)
    EditText editStatus;
    @BindView(R.id.edit_duration)
    EditText editDuration;*/
    @BindView(R.id.txtTakePhoto)
   TextView txtTakePhoto;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage = "";
    private String pictureFilePath;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    String[] arrActivity = {"All Activity","HDD Activity","OFC Works","Instrumentation",
            "Hydro testing",
            "ROU Handover","Route Survey","Clearing & Grading","Bending","Stringing","Welding","Tie-in Joint",
            "NDT","Field Joint Coating","Trenching","Lowering","HDPE Duct Laying","Backfilling","civil","Mechanical","Electrical"};
    String[] arrResponsibility = {"Client","Contractor"};

    String[] arrHindrance = { "ROU ISSUE-Compensation issue", "ROU ISSUE-Land Owners not allowing",
            "ROU ISSUE-Resources captured by Land owners","ROU ISSUE-Crops Cutting is pending","ROU ISSUE-Width of ROU is less"
            ,"ROU ISSUE-Work Front is not available","ROU Issue-Others"
            ,"NON ROU ISSUE-Statutory Permission is pending"
            ,"NON ROU ISSUE-Heavy Rain or Flood like situation"
            ,"NON ROU ISSUE-Water Logging"
            ,"NON ROU ISSUE-Resources are unable to reach"
            ,"NON ROU ISSUE-Equipment Breakdown"
            ,"NON ROU ISSUE-Vehicles Strike"
            ,"NON ROU ISSUE-Workers Strike","NON ROU ISSUE-Equipment Operator unavailable","NON ROU ISSUE-Material unavailability"
            ,"NON ROU ISSUE-Consumables unavailability","NON ROU ISSUE-Diesel unavailability"
            ,"NON ROU ISSUE-Drawing isn’t available","NON ROU ISSUE-Work Procedure is pending"
            ,"Non ROU Issue-Others"};

    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindrance);
        ButterKnife.bind(this);
        context = this;
        init();
    }

    public void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ArrayAdapter activityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrActivity);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editActivityEffected.setAdapter(activityAdapter);
        ArrayAdapter responsibilityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrResponsibility);
        responsibilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editResponsibility.setAdapter(responsibilityAdapter);
        ArrayAdapter descAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrHindrance);
        descAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinDescription.setAdapter(descAdapter);
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editWeather.setAdapter(weatherAdapter);
        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        sendTakePictureIntent();
                    }
                } else {
                    sendTakePictureIntent();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        setReportDate();
        editDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFromDate();
            }
        });
        editDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToDate();
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setReportDate()
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        editDate.setText(mDay  + "-" + (mMonth + 1) + "-" + mYear );
    }
    private void setFromDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editDateFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" +year );

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    private void setToDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
                        "unistal.com.idpl_pcms.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
        String pictureFile = "NMPPL_PCMS" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }

    private void validateFields() {
        if (editChainageFrom.getText().toString().equals("")){
            Toast.makeText(HindranceActivity.this, "Enter Chainage From", Toast.LENGTH_SHORT).show();
        }else if (editChainageTo.getText().toString().equals("")){
            Toast.makeText(HindranceActivity.this, "Enter Chainage To", Toast.LENGTH_SHORT).show();
        }else if (editDateFrom.getText().toString().equals("")){
            Toast.makeText(HindranceActivity.this, "Enter Date From", Toast.LENGTH_SHORT).show();
        }        else {
        String strDate = editDate.getText().toString();
            BigDecimal chfrom = new BigDecimal(editChainageFrom.getText().toString().trim());
            BigDecimal chto = new BigDecimal(editChainageTo.getText().toString().trim());

         Double areaHold = Double.valueOf(String.valueOf(chto.subtract(chfrom)));
         if(areaHold<0)
         {
             Toast.makeText(HindranceActivity.this, "Chainage to should be greater than Chainage from ", Toast.LENGTH_SHORT).show();
         }else{
        HashMap<String, String> hashMap=new HashMap<>();

        hashMap.put("UserID", SessionUtil.getUserId(context));
        hashMap.put("SectionID",SessionUtil.getAssignedSection(context));
        hashMap.put("type","AddHindranceData");
        hashMap.put("activity_affected",editActivityEffected.getSelectedItem().toString());
        hashMap.put("report_date",editDate.getText().toString());
        hashMap.put("chainage_from",editChainageFrom.getText().toString());
        hashMap.put("chainage_to",editChainageTo.getText().toString());
        hashMap.put("area_hold",areaHold+"");
        hashMap.put("description",spinDescription.getSelectedItem().toString());
        hashMap.put("date_from",editDateFrom.getText().toString());
        hashMap.put("date_to",editDateTo.getText().toString());
        hashMap.put("weather",editWeather.getSelectedItem().toString());
        hashMap.put("remarks",editRemarks.getText().toString());
       // hashMap.put("duration",editDuration.getText().toString());
        hashMap.put("responsibility",editResponsibility.getSelectedItem().toString());
      //  hashMap.put("status",editStatus.getText().toString());
        hashMap.put("imageData",strImage+ "");
        if (isOnline()){
            Log.e("Params", hashMap.toString());
            InsertData(context,hashMap);
        }else {
            Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();

        }
    }}}
    public void InsertData(final Context context, final Map<String, String> params) {

        Activity activity = (Activity) context;
        progressDialog.show();
        String url = AppConstants.APP_BASE_URL+"API_NEW/get_all_activity_type.php";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.e("Params", params.toString());
                        Log.e("Response", stringResponse);
                        progressDialog.dismiss();


                        if (stringResponse.contains("Success")) {
                            Clear();
                            params.clear();
                            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, stringResponse, Toast.LENGTH_LONG).show();
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
        featureRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 600000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(featureRequest);
    }


    private void Clear() {

        editChainageFrom.setText("");
        editChainageTo.setText("");
        editAreaHold.setText("");
        editDateTo.setText("");
        editDateFrom.setText("");
        strImage="";
        editRemarks.setText("");
       // editStatus.setText("");
       // editResponsibility.setText("");
       // editDuration.setText("");
    }

}