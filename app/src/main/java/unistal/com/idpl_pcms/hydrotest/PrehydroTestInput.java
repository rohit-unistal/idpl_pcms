package unistal.com.idpl_pcms.hydrotest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;

public class PrehydroTestInput extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edWeather)
    Spinner edWeather;

    @BindView(R.id.edJointFrom)
    EditText edJointFrom;
    @BindView(R.id.edKM)
    EditText edKMFrom;
    @BindView(R.id.spinerJointType)
    Spinner spinerJointTypeFrom;
    @BindView(R.id.edJointNo)
    EditText edJointNoFrom;
    @BindView(R.id.edSuffixFrom)
    EditText edSuffixFrom;

    @BindView(R.id.edJointTo)
    EditText edJointTo;
    @BindView(R.id.edKM_to)
    EditText edKMTo;
    @BindView(R.id.spinerJointTypeTo)
    Spinner spinerJointTypeTo;
    @BindView(R.id.edJointNoTo)
    EditText edJointNoTo;
    @BindView(R.id.edSuffixTo)
    EditText edSuffixTo;

    @BindView(R.id.edLength)
    EditText edLength;

    @BindView(R.id.hydroPlanRepoNo)
    EditText hydroPlanRepoNo;
    @BindView(R.id.hydroPlanDate)
    TextView hydroPlanDate;


    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;


    String strKMFrom="K";
    String strJointTypeFrom="";
    String strJointNoFrom="";
    String strSuffixFrom="";

    String strKMTo="K";
    String strJointTypeTo="";
    String strJointNoTo="";
    String strSuffixTo="";

    DataBaseHelper dbHelper;

    ProgressDialog progressDialog;
    Context context;
    String[] arrJointType={"Select Type","M","T","FT","GT","FTR","TR","MTR"};
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};

    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage="";
    private String pictureFilePath;

    boolean aBoolean=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prehydro_test_input);


        context=this;
        ButterKnife.bind(this);
        netConnectivity();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        dbHelper=new DataBaseHelper(context);
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrJointType);
        spinerJointTypeFrom.setAdapter(adapter);
        spinerJointTypeTo.setAdapter(adapter);


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
                    strKMFrom= strKMFrom+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
                edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
                edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
            }
        });

        edKMTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strKMTo="K";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strKMTo=strKMTo+"0"+s;
                }
                /*else if (s.length()==2){
                    strKMTo=strKMTo+"0"+s;
                }*/
                else {
                    strKMTo= strKMTo+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo+strSuffixTo);
            }
        });
        edJointNoTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strJointNoTo="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==1){
                    strJointNoTo="0"+s;
                }else {
                    strJointNoTo= ""+ s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                //StrJointNumber=StrJointNumber+strKM;
                edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo+strSuffixTo);
            }
        });
        edSuffixTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strSuffixTo="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strSuffixTo= ""+ s;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo+strSuffixTo);
            }
        });

        spinerJointTypeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    strJointTypeTo=arrJointType[position];
                    edJointTo.setText(strKMTo+strJointTypeTo+strJointNoTo+strSuffixTo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate(editDate);
            }
        });
        hydroPlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRouteSurveyDate(hydroPlanDate);
            }
        });
        txtTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTakePictureIntent();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //String strPlaneReportNo =hydroPlanRepoNo.getText().toString();
                //String strPlanDate      =hydroPlanDate.getText().toString();


                if (editDate.getText().toString().equals("")){
                    Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
                }else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
                }else if (edJointFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Spread No.", Toast.LENGTH_SHORT).show();
                }else if (edKMFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
                }else if (spinerJointTypeFrom.getSelectedItem().toString().equals("Select Type")){
                    Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
                }else if (edJointNoFrom.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
                }else if (edJointTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint To Field", Toast.LENGTH_SHORT).show();
                }else if (edKMTo.getText().toString().equals("")){
                    Toast.makeText(context, "Fill KM Fields", Toast.LENGTH_SHORT).show();
                }else if (spinerJointTypeTo.getSelectedItem().toString().equals("Select Type")){
                    Toast.makeText(context, "Select Joint Type", Toast.LENGTH_SHORT).show();
                }else if (edJointNoTo.getText().toString().equals("")){
                    Toast.makeText(context, "Enter Joint No.", Toast.LENGTH_SHORT).show();
                }else if(edLength.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Enter HydroTest Length .", Toast.LENGTH_SHORT).show();

                }
                /*else if (strPlaneReportNo.equals("")){
                    Toast.makeText(context, "Enter Hydro Plan Report No.", Toast.LENGTH_SHORT).show();
                }else if (strPlanDate.equals("")){
                    Toast.makeText(context, "Enter Hydro Plane Date", Toast.LENGTH_SHORT).show();
                }*/
                else{
                    final HashMap<String,String> params=new HashMap<>();

                    params.put("UserID", SessionUtil.getUserId(context));
                    params.put("SectionID",SessionUtil.getAssignedSection(context));
                    params.put("type","hydrotest");

                    params.put("ReportDate",editDate.getText().toString());
                    params.put("ReportNo",editReportNo.getText().toString());
                    params.put("Weather",edWeather.getSelectedItem().toString()+"");

                    params.put("JointFrom",edJointFrom.getText().toString());
                    params.put("JointTo",edJointTo.getText().toString());

                    params.put("HydrotestingLength",edLength.getText().toString());

                    /*params.put("Padding",strPlanDate);
                    params.put("Warnning_mat",strPlaneReportNo);*/

                    params.put("Remarks",editRemarks.getText().toString());
                    params.put("ImageData",strImage);

                    Log.d("Response", params.toString());

                    if (isOnline()){
                        InsertData(context,params);
                    }
                    else {
                        boolean s=dbHelper.HydroTestingInsert(params);
                        if (s){
                            Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                            Clear();
                            params.clear();
                        }else {
                            Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                        }
                        /*if (!aBoolean){
                          new AlertDialog.Builder(context)
                                  .setTitle("Alert")
                                  .setMessage("No Internet Connection\nDo you want to save data locally. It will be syc when internet available.")
                                  .setCancelable(false)
                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                          boolean s=dbHelper.HydroTestingInsert(params);
                                          if (s){
                                              aBoolean=true;
                                              Toast.makeText(context, "Successfully Inserted Locally!", Toast.LENGTH_SHORT).show();
                                              Clear();
                                          }else {
                                              Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  })
                                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {

                                      }
                                  }).show();
                        }
                        else {
                            boolean s=dbHelper.HydroTestingInsert(params);
                            if (s){
                                Toast.makeText(context, "Successfully Inserted Locally!", Toast.LENGTH_SHORT).show();
                                Clear();
                            }else {
                                Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                            }
                        }*/
                    }
                }
            }
        });
    }


    @OnItemSelected(R.id.spinerJointType)
    public void onControlRoomSelected(int position) {
        if(position>0) {
            strJointTypeFrom=arrJointType[position];
            edJointFrom.setText(strKMFrom+strJointTypeFrom+strJointNoFrom+strSuffixFrom);
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
    public void InsertData(final Context context, final Map<String, String> params) {

        progressDialog.show();
        Activity activity = (Activity) context;
        //HttpsTrustManager.allowAllSSL();

        //Log.d("URL ", "My  URl: " + url);
        Log.e("Responce","Param = "+params.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {

                        Log.e("Responce","Hydrotesting = "+stringResponse);
                        progressDialog.dismiss();
                        /*try {
                            if (stringResponse.equals("1")){
                          */      Clear();
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                           /* }else {
                                Toast.makeText(context, "Data Not Inserted!", Toast.LENGTH_SHORT).show();
                            }
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
        featureRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(featureRequest);
    }
    private void setRouteSurveyDate(final TextView txtView) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public void Clear(){
        edJointFrom.getText().clear();
        edKMFrom.getText().clear();
        edJointNoFrom.getText().clear();
        edSuffixFrom.getText().clear();
        edJointTo.getText().clear();
        edKMTo.getText().clear();
        edJointNoTo.getText().clear();
        edSuffixTo.getText().clear();
        strImage="";
        edLength.setText("");
        editRemarks.setText("");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            if(imgFile.exists())
            {
                Toast.makeText(context, "Image Captured!", Toast.LENGTH_SHORT).show();
                decodeFile(imgFile);
                //image.setImageBitmap(decodeFile(imgFile));
                //image.setImageURI(Uri.fromFile(imgFile));
            }else {
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
