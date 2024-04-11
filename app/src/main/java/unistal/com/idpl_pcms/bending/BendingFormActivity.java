package unistal.com.idpl_pcms.bending;

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
/*import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;*/
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.BarCodeReaderActivity;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.clearing_and_grading.ClearingGrading2Activity;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class BendingFormActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    /*@BindView(R.id.edSpread)
    EditText edSpread;

    @BindView(R.id.edWeather)
    EditText edWeather;*/
    @BindView(R.id.edit_chainage)
    EditText edit_chainage;

    @BindView(R.id.edit_PipeNo)
    EditText edit_PipeNo;
    @BindView(R.id.edit_bendno)
    EditText edit_BendNo;
    @BindView(R.id.imgScan)
    ImageView imgScan;
    /*@BindView(R.id.edit_sec_length)
    EditText edit_sec_length;*/

    @BindView(R.id.editBendDegree)
    EditText editBendDegree;
    @BindView(R.id.editMinute)
    EditText editMinute;
    @BindView(R.id.editSecond)
    EditText editSecond;
    @BindView(R.id.editTPNo)
    EditText editTPNo;


    @BindView(R.id.spinnerBendType)
    Spinner spinnerBendType;
    @BindView(R.id.edit_remark)
    EditText edit_remark;

    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.txtTakePhoto)
    TextView txtTakePhoto;

    @BindView(R.id.spinner_visual)
    Spinner spinnerVisual;
    @BindView(R.id.spinnerGuaging)
    Spinner spinnerGuaging;
    @BindView(R.id.spinnerOvality)
    Spinner spinnerOvality;
    @BindView(R.id.spinnerDisbonding)
    Spinner spinnerDisbonding;
    @BindView(R.id.spinnerHoliday)
    Spinner spinnerHoliday;
    @BindView(R.id.edWeather)
    Spinner edWeather;
    @BindView(R.id.edit_manpower)
    EditText editmanpower;
    @BindView(R.id.edit_machineries)
    EditText editMachineries;
    @BindView(R.id.netstatus)
    ImageView netStatus;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;

    private NetworkStateReceiver networkStateReceiver;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String [] arrHolidayChecks={"At time of Lowering","Ok","Not Ok"};
    String [] arrChecks={"Ok","Not Ok"};
    String [] arrBendType={"Sag","Over","HR","HL","Hot Bend"};
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};

    ArrayList<String> alignmentName;
    ProgressDialog progressDialog;
    ArrayList<AlignmentModel> alignmentNo;
    Context context;
    DataBaseHelper dbHelper;

    static final int REQUEST_PICTURE_CAPTURE = 1;
    String strImage="",image="";
    private String pictureFilePath;

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bending_form);

        context=this;
        ButterKnife.bind(this);
        netConnectivity();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();

        dbHelper=new DataBaseHelper(context);
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        ArrayAdapter adapterBendType=new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,arrBendType);
        spinnerBendType.setAdapter(adapterBendType);

        ArrayAdapter adapter=new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,arrChecks);
        ArrayAdapter adapterholiday=new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,arrHolidayChecks);

        loadAlignment();
        spinnerVisual.setAdapter(adapter);
        spinnerDisbonding.setAdapter(adapter);
        spinnerGuaging.setAdapter(adapter);
        spinnerOvality.setAdapter(adapter);
        spinnerHoliday.setAdapter(adapterholiday);


//        if (txtDistbonding != null) {
//            txtDistbonding.setChecked(false);
//            txtDistbonding.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
//
//            txtDistbonding.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    txtDistbonding.setChecked(!txtDistbonding.isChecked());
//                    txtDistbonding.setCheckMarkDrawable(txtDistbonding.isChecked() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
//                    String msg = (txtDistbonding.isChecked() ? "Checked" : "unchecked");
//                    Toast.makeText(BendingFormActivity.this, msg, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        if (txtOvality != null) {
//            txtOvality.setChecked(false);
//            txtOvality.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
//
//            txtOvality.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    txtOvality.setChecked(!txtOvality.isChecked());
//                    txtOvality.setCheckMarkDrawable(txtOvality.isChecked() ? android.R.drawable.checkbox_on_background : android.R.drawable.checkbox_off_background);
//
//                    String msg = (txtOvality.isChecked() ? "Checked" : "unchecked");
//                    Toast.makeText(BendingFormActivity.this, msg, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
        edit_PipeNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barcode = new Intent(BendingFormActivity.this, BarCodeReaderActivity.class);
                startActivityForResult(barcode, 101);

               // initialiseDetectorsAndSources();
                /*try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
                    startActivityForResult(intent, 0);

                } catch (Exception e) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
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
//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }else
                {
                    sendTakePictureIntent();
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                new AlertDialog.Builder(context).setMessage(contents).show();
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }*/
    @Override
    protected void onStart() {
        super.onStart();
        try{
            if (alignmentNo.size()<=0){
                //sheetAlignment();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void validateFields(){

        String strDate=editDate.getText().toString();
        String strReportNo=editReportNo.getText().toString();

        String strChainage=edit_chainage.getText().toString();
        String strPipeNo=edit_PipeNo.getText().toString();
        String strBendNo=edit_BendNo.getText().toString();
        String strBendDegree=editBendDegree.getText().toString();
        String strMin=editMinute.getText().toString();
        String strSec=editSecond.getText().toString();
        String strTPno=editTPNo.getText().toString();
        String strTypeOfBend=spinnerBendType.getSelectedItem().toString();
        String strVisual=spinnerVisual.getSelectedItem().toString();
        String strOvality=spinnerOvality.getSelectedItem().toString();
        String strDisBonding=spinnerDisbonding.getSelectedItem().toString();
        String strGuaging=spinnerGuaging.getSelectedItem().toString();
        String strHoliday=spinnerHoliday.getSelectedItem().toString();

        String strRemark=edit_remark.getText().toString();

        if (strDate.equals("")){
            Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
        }else if (strReportNo.equals("")){
            Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
        }

        else if (strChainage.equals("")){
            Toast.makeText(context, "Enter Chainage Value", Toast.LENGTH_SHORT).show();
        }else if (strPipeNo.equals("")){
            Toast.makeText(context, "Enter Pipe No Value", Toast.LENGTH_SHORT).show();
        }else if (strBendNo.equals("")){
        Toast.makeText(context, "Enter Bend No Value", Toast.LENGTH_SHORT).show();
    }else if (strBendDegree.equals("")){
            Toast.makeText(context, "Enter Bend Degree Value", Toast.LENGTH_SHORT).show();
        }else if (strMin.equals("")){
            Toast.makeText(context, "Enter Minute Value", Toast.LENGTH_SHORT).show();
        }else if (strSec.equals("")){
            Toast.makeText(context, "Enter Second Value", Toast.LENGTH_SHORT).show();
        }else if (strTPno.equals("")){
            Toast.makeText(context, "Enter TP No.", Toast.LENGTH_SHORT).show();
        }else {
            final HashMap<String,String> hashMap=new HashMap<>();

            hashMap.put("UserID", SessionUtil.getUserId(context));
            hashMap.put("SectionID",SessionUtil.getAssignedSection(context));
            hashMap.put("type","bending");
            hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
            hashMap.put("Weather",edWeather.getSelectedItem().toString()+"");

            hashMap.put("TR_Date",strDate);
            hashMap.put("TR_Report_Number",strReportNo);
            hashMap.put("TN_Chainage",strChainage);
            hashMap.put("Pipe_Number",strPipeNo);
            hashMap.put("Bend_Number",strBendNo);
            hashMap.put("TN_Degree",strBendDegree);
            hashMap.put("TR_BEND",strTypeOfBend);
            hashMap.put("TN_Min",strMin);
            hashMap.put("TN_Sec",strSec);
            hashMap.put("TN_TP",strTPno);
            hashMap.put("TN_Visual",strVisual);
            hashMap.put("TN_Disbonding",strDisBonding);
            hashMap.put("TN_Gauging",strGuaging);
            hashMap.put("TN_Holiday",strHoliday);
            hashMap.put("TN_Ovality",strOvality);
            hashMap.put("TN_Remarks",strRemark);
            hashMap.put("TN_Manpower", editmanpower.getText().toString());
            hashMap.put("TN_Machineries", editMachineries.getText().toString());
            hashMap.put("latitude", "0.0");
            hashMap.put("longitude", "0.0");
            hashMap.put("Photo","Bending"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");//
            hashMap.put("ImageData",image + "");

            Log.e("Details",hashMap.toString());


            if (isOnline()){
                InsertData(context,hashMap);
            }else {
                boolean s=dbHelper.BendingInsert(hashMap);
                if (s){
                    Toast.makeText(context, "Locally saved", Toast.LENGTH_SHORT).show();
                    Clear();
                    hashMap.clear();
                }else {
                    Toast.makeText(context, "Not Inserted!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(BendingFormActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BendingFormActivity.this,
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
            Toast.makeText(BendingFormActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

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
                       /* try {*/
                            Log.e("Response", stringResponse);
                            if (stringResponse.contains("1")) {
                               Clear();
                                params.clear();
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        /*} catch (Exception e) {
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
                15000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(featureRequest);
    }
    private void Clear(){
        edit_chainage.getText().clear();
        edit_PipeNo.getText().clear();
        edit_BendNo.getText().clear();
        editBendDegree.getText().clear();
        editMinute.getText().clear();
        editSecond.getText().clear();
        editTPNo.getText().clear();
        edit_remark.getText().clear();
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
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == 101 && data != null) {
                String s = data.getExtras().getString("barCode");
                if (s.contains(",")) {
                    String[] str1 = s.split(",");
                    edit_PipeNo.setText(str1[0].trim());
                } else if (s.contains(" ")) {
                    String[] str1 = s.split("\\s+");
                    edit_PipeNo.setText(str1[0].trim());
                } else {
                    edit_PipeNo.setText(s);
                }
                //  edit_PipeNo.setText(data.getExtras().getString("barCode"));


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
            b.compress(Bitmap.CompressFormat.JPEG, 30, out);
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
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(BendingFormActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    Log.e("BarcodeScanner",barcodes.valueAt(0).displayValue);
                    /*txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                                btnAction.setText("LAUNCH URL");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);

                            }
                        }
                    });*/

                }
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
