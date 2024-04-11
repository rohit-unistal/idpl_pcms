package unistal.com.idpl_pcms;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class NewRowFormActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
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

    @BindView(R.id.edit_chainage)
    EditText editChainage;
    @BindView(R.id.edit_nameofstructure)
    EditText editNameofStructure;

    @BindView(R.id.edit_OtherDetails)
    EditText edit_OtherDetails;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
@BindView(R.id.spin_alignment)
Spinner spinAlignment;
    private ArrayList<AlignmentModel> AlignmentModelList;
    private ArrayList<String> alignmentNumList, alignmentIDList;
    private ArrayAdapter spinnerAlignmentAdapter;

    ProgressDialog progressDialog;



    DataBaseHelper dataBaseHelper;
    Context context;

    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_row_form);
        ButterKnife.bind(this);
        context = this;
        init();
    }
    private void init()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        netConnectivity();
        AlignmentModelList = new ArrayList<>();
        alignmentIDList = new ArrayList<>();
        alignmentNumList = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(context);
        loadAlignment();
        setRouteSurveyDate();
    }


    @OnClick(R.id.btnSubmit)
    public void onSubmit() {
           if (editDate.getText().toString().equals("")){
                Toast.makeText(context, "Choose Date", Toast.LENGTH_SHORT).show();
            }else if (editReportNo.getText().toString().equals("")){
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
            else if (editChainageFrom.getText().toString().equals("")){
                Toast.makeText(context, "Enter Chainage From", Toast.LENGTH_SHORT).show();
            }else if (editChainageTo.getText().toString().equals("")){
                Toast.makeText(context, "Enter Chainage To", Toast.LENGTH_SHORT).show();
            }
                /*else if (editDistance.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Section Length", Toast.LENGTH_SHORT).show();
                }*/
            else{
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("type", "rou");
                hashMap.put("UserID", SessionUtil.getUserId(context));
                hashMap.put("SectionID", SessionUtil.getAssignedSection(context));
                // params.put("SectionID", selectedSections.toString());
                // params.put("DeviceID", SessionUtil.getDeviceId(ROWForm.this));
                hashMap.put("MR_Chainage_From", editChainageFrom.getText().toString());
                hashMap.put("MR_Chainage_To", editChainageTo.getText().toString());
                hashMap.put("TR_Report_Number", editReportNo.getText().toString());
                hashMap.put("Alignment_Sheet", AlignmentModelList.get(spinAlignment.getSelectedItemPosition()).getAlignmentID());
                // params.put("Spread", edSpread.getText().toString());
                // params.put("Weather", edWeather.getText().toString());
                //params.put("spinSheetAlignment","");
                hashMap.put("namestructre", editNameofStructure.getText().toString());
               hashMap.put("tpremark", editTPRemarks.getText().toString());
               hashMap.put("bearingangle", editBearingofAngle.getText().toString());
               hashMap.put("Ipno", editTPNo.getText().toString());
               hashMap.put("IpChainage", editTPChainage.getText().toString());
               hashMap.put("terrian", editTerrain.getText().toString());
               hashMap.put("chainage", editChainage.getText().toString());
                // params.put("Alignment_Sheet", alignName);
                // params.put("MR_Distance_Cleared", editDistance.getText().toString());
                hashMap.put("TR_Rou_Date", editDate.getText().toString());
                hashMap.put("Others", edit_OtherDetails.getText().toString());
                hashMap.put("TN_Remarks", editRemarks.getText().toString());
                hashMap.put("Photo","photo");

                if (isOnline()){
                    submitClearing(hashMap);
                }else{
                    boolean s=dataBaseHelper.newRowInsert(hashMap);
                    if (s){
                        clear();
                        hashMap.clear();
                        Toast.makeText(context, "Locally Saved", Toast.LENGTH_LONG).show();
                    }else {
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
            Toast.makeText(NewRowFormActivity.this, "No Alignment number found or Please update Alignment number details", Toast.LENGTH_SHORT).show();
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
                        if(response.contains("1")) {
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
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    private void clear(){
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
