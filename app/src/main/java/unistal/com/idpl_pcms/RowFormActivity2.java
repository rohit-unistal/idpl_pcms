package unistal.com.idpl_pcms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class RowFormActivity2 extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
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
    @BindView(R.id.edit_tp_chainage)
    EditText editTPChainage;
    @BindView(R.id.editTPNo)
    EditText editTPNo;
    @BindView(R.id.edit_tp_remarks)
    EditText editTPRemarks;
    @BindView(R.id.edit_deflection_of_angle)
    EditText editBearingofAngle;
    @BindView(R.id.edit_chainage_done)
    EditText editChainageDone;
    @BindView(R.id.edit_chainage)
    EditText editChainage;

    @BindView(R.id.edit_nameofstructure)
    EditText editNameofStructure;
    @BindView(R.id.edit_detailsofstructure)
    EditText editdetailsofstructure;
    @BindView(R.id.edit_across_row)
    EditText editacross_row;
    @BindView(R.id.edit_details)
    EditText editdetails;
    @BindView(R.id.edit_OtherDetails)
    EditText edit_OtherDetails;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    @BindView(R.id.btn_add)
    TextView btnAdd;
    @BindView(R.id.btn_display)
    TextView btnDisplay;
    @BindView(R.id.edWeather)
    Spinner edWeather;

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;
    private ArrayList<String> tpnolist,tpchainagelist,bearinganglelist;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ProgressDialog progressDialog;
    String[] arrWeather = {"Sunny", "Cloudy", "Rainy"};
    private String image="",image1;
    DataBaseHelper dataBaseHelper;
    Context context;

    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_form2);
        ButterKnife.bind(this);
        context = this;
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        netConnectivity();
        ArrayAdapter weatherAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrWeather);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        edWeather.setAdapter(weatherAdapter);
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(context);
        loadAlignment();
        tpnolist = new ArrayList<>();
        tpchainagelist = new ArrayList<>();
        bearinganglelist = new ArrayList<>();
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                editChainage.setText(sl+"");
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

                editChainage.setText(sl+"");
            }
        });

    }
    @OnClick(R.id.btn_add)
    public void  onAdd()
    {
        tpchainagelist.add(editTPChainage.getText().toString());
        tpnolist.add(editTPNo.getText().toString());
        bearinganglelist.add(editBearingofAngle.getText().toString());
        editTPChainage.setText("");
        editTPNo.setText("");
        editBearingofAngle.setText("");
    }
    @OnClick(R.id.btn_display)
    public void  onDisplay() {
        CustomAdapter adapter=new CustomAdapter(context,tpnolist,tpchainagelist,bearinganglelist);
        Show(context,adapter);
    }
    @OnClick(R.id.btnSubmit)
    public void onSubmit() {
        if (editDate.getText().toString().equals("")) {
            Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
        } else if (editReportNo.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Report No.", Toast.LENGTH_SHORT).show();
        }
                /*else if (edSpread.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Spread No.", Toast.LENGTH_SHORT).show();
                }
                else if (spinAlignment.getSelectedItem().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Select Alignment Sheet No.", Toast.LENGTH_SHORT).show();
                }else if (edWeather.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Weather", Toast.LENGTH_SHORT).show();
                }*/
        else if (editChainageFrom.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Chainage From", Toast.LENGTH_SHORT).show();
        } else if (editChainageTo.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Chainage To", Toast.LENGTH_SHORT).show();
        }
                /*else if (editDistance.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Section Length", Toast.LENGTH_SHORT).show();
                }*/
        else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("type", "rou");
            hashMap.put("UserID", SessionUtil.getUserId(context));
            hashMap.put("SectionID", SessionUtil.getAssignedSection(context));
            // params.put("SectionID", selectedSections.toString());
            // params.put("DeviceID", SessionUtil.getDeviceId(ROWForm.this));
            hashMap.put("MR_Chainage_From", editChainageFrom.getText().toString());
            hashMap.put("MR_Chainage_To", editChainageTo.getText().toString());
            hashMap.put("TR_Report_Number", editReportNo.getText().toString());
            hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID()+"");
            // params.put("Spread", edSpread.getText().toString());
            hashMap.put("Weather", edWeather.getSelectedItem().toString());
            //params.put("spinSheetAlignment","");
            hashMap.put("bearingangle", bearinganglelist.toString().replace("[","").replace("]",""));
          //  hashMap.put("Ipno", tpnolist.toString().replace("[","").replace("]",""));
            hashMap.put("IpChainage", tpchainagelist.toString().replace("[","").replace("]",""));
            hashMap.put("namestructre", editNameofStructure.getText().toString());
          //  hashMap.put("tpremark", editTPRemarks.getText().toString());
            hashMap.put("Ipno", editTPRemarks.getText().toString());
            hashMap.put("tpremark",tpnolist.toString().replace("[","").replace("]",""));
            hashMap.put("terrian", editTerrain.getText().toString());
        //   hashMap.put("chainage", editChainage.getText().toString());
            // params.put("Alignment_Sheet", alignName);
            // params.put("MR_Distance_Cleared", editDistance.getText().toString());
            hashMap.put("TR_Rou_Date", editDate.getText().toString());
            hashMap.put("Others", edit_OtherDetails.getText().toString());
            hashMap.put("TN_Remarks", editRemarks.getText().toString());
            hashMap.put("DetailsStructure", editdetailsofstructure.getText().toString());
            hashMap.put("across_row", editacross_row.getText().toString());
            hashMap.put("Details", editdetails.getText().toString());
            hashMap.put("chainage", editChainageDone.getText().toString());
            hashMap.put("Photo", "RouteSurvey"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".jpeg");
            hashMap.put("ImageData", image);
            Log.e("response params",hashMap.toString());
            if (isOnline()) {
                submitClearing(hashMap);
            } else {
                boolean s = dataBaseHelper.newRowInsert(hashMap);
                if (s) {
                    clear();
                    hashMap.clear();
                    Toast.makeText(context, "Locally Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Not Inserted", Toast.LENGTH_LONG).show();
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
            Toast.makeText(RowFormActivity2.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
        }
        spinnerAlignmentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, alignmentNumList); //selected item will look like a spinner set from XML
        spinnerAlignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlignment.setAdapter(spinnerAlignmentAdapter);
        spinnerAlignmentAdapter.notifyDataSetChanged();

    }

    private void submitClearing(final HashMap<String, String> hashMap) {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.BASE_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        if (response.contains("1")) {
                            clear();
                            hashMap.clear();
                            Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = hashMap;
                return params;
            }
        };
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void clear() {
        editChainageFrom.getText().clear();
        editChainageTo.getText().clear();
        edit_OtherDetails.getText().clear();
        editRemarks.getText().clear();
        editTerrain.getText().clear();
        editTPChainage.getText().clear();
        editTPNo.getText().clear();
        editBearingofAngle.getText().clear();
        editTPRemarks.getText().clear();
        editChainage.getText().clear();
        editNameofStructure.getText().clear();
        tpnolist.clear();
        tpchainagelist.clear();
        bearinganglelist.clear();
       image="";
    }

    public void netConnectivity() {
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
    public class CustomAdapter extends BaseAdapter {
        ArrayList<String> tpnolist;
        ArrayList<String> tpchainagelist;
        ArrayList<String> bearinganglelist;


        Context context;
        public  CustomAdapter(Context mContext, ArrayList<String> tpnolist, ArrayList<String> tpchainagelist, ArrayList<String> bearinganglelist) {
            this.tpnolist=tpnolist;
            this.tpchainagelist=tpchainagelist;
            this.bearinganglelist=bearinganglelist;

            this.context=mContext;

        }

        @Override
        public int getCount() {
            return tpnolist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            @SuppressLint("ViewHolder")
            final View view =  LayoutInflater.from(context).inflate(R.layout.list_view_item_resource, parent,false);

            TextView txtView=view.findViewById(R.id.txtViewItem);
            final LinearLayout layoutDelete=view.findViewById(R.id.layoutDelete);



            txtView.setText(" tp_no="+tpnolist.get(position)+": tp chainage=[ "+tpchainagelist.get(position)+" ]"+",bearingangle=["+bearinganglelist.get(position)+"]");


            layoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setMessage("Do you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tpnolist.remove(position);
                                    tpchainagelist.remove(position);
                                    bearinganglelist.remove(position);

                                    view.setVisibility(View.GONE);
                                    // (context).Remo

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
            });
            return view;
        }
    }
    public static void Show(Context context, CustomAdapter customAdapter) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertLayout = inflater.inflate(R.layout.list_view_resource, null);
        ListView listView=alertLayout.findViewById(R.id.listView);
        String strTitle="Dialogue";



        listView.setAdapter(customAdapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(strTitle);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();
    }
    @OnClick(R.id.imgPhoto)
    public void captureImage() {
        if (ContextCompat.checkSelfPermission(RowFormActivity2.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RowFormActivity2.this,
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
}