package unistal.com.idpl_pcms.form;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
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
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.NetworkStateReceiver;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.model.AlignmentModel;

public class ROWForm extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edSpread)
    EditText edSpread;
    @BindView(R.id.spin_alignment)
    Spinner spinAlignment;
    @BindView(R.id.edWeather)
    EditText edWeather;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_chainage)
    EditText editChainage;
    @BindView(R.id.editTPNo)
    EditText editTPNo;
    @BindView(R.id.spin_ground)
    Spinner spinGround;
    @BindView(R.id.edit_OtherDetails)
    EditText edit_OtherDetails;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    //private ProgressLoading loadingDialog;
    private ArrayAdapter spinnerArrayAdapter;
    ArrayList<AlignmentModel> alignmentNo;
    ArrayList<String> alignmentName;
    String alignName;
    String [] arrGround={"Normal","Rock","Seismic"};
    ProgressDialog progressDialog;
    DataBaseHelper dataBaseHelper;

    @BindView(R.id.netstatus)
    ImageView netStatus;
    private NetworkStateReceiver networkStateReceiver;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row);
        ButterKnife.bind(this);
        context = this;
        netConnectivity();
        alignmentNo = new ArrayList<>();
        alignmentName = new ArrayList<String>();
        //loadingDialog = new ProgressLoading(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //sheetAlignment();
        dataBaseHelper=new DataBaseHelper(getApplicationContext());
        ArrayAdapter groundAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrGround);
        groundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinGround.setAdapter(groundAdapter);
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
                    Toast.makeText(ROWForm.this, "Choose Date", Toast.LENGTH_SHORT).show();
                }else if (editReportNo.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Report No.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ROWForm.this, "Enter Chainage From", Toast.LENGTH_SHORT).show();
                }else if (editChainageTo.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Chainage To", Toast.LENGTH_SHORT).show();
                }
                /*else if (editDistance.getText().toString().equals("")){
                    Toast.makeText(ROWForm.this, "Enter Section Length", Toast.LENGTH_SHORT).show();
                }*/
                else if (spinGround.getSelectedItem().toString().equals("Select Alignment Sheet No.")){
                    Toast.makeText(ROWForm.this, "Select Ground Type", Toast.LENGTH_SHORT).show();
                }else{
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("type", "rou");
                    hashMap.put("UserID", SessionUtil.getUserId(ROWForm.this));
                    hashMap.put("SectionID", SessionUtil.getAssignedSection(ROWForm.this));
                    // params.put("SectionID", selectedSections.toString());
                    // params.put("DeviceID", SessionUtil.getDeviceId(ROWForm.this));
                    hashMap.put("MR_Chainage_From", editChainageFrom.getText().toString());
                    hashMap.put("MR_Chainage_To", editChainageTo.getText().toString());
                    hashMap.put("TR_Report_Number", editReportNo.getText().toString());
                    hashMap.put("Type_Anode", spinGround.getSelectedItem().toString());
                    hashMap.put("chainage", editChainage.getText().toString());
                    hashMap.put("markers", editTPNo.getText().toString());
                    //params.put("spinSheetAlignment","");
                    hashMap.put("spinGround", spinGround.getSelectedItem().toString());

                    // params.put("Alignment_Sheet", alignName);
                    // params.put("MR_Distance_Cleared", editDistance.getText().toString());
                    hashMap.put("TR_Rou_Date", editDate.getText().toString());
                    hashMap.put("Others", edit_OtherDetails.getText().toString());
                    hashMap.put("TN_Remarks", editRemarks.getText().toString());
                    hashMap.put("Photo","photo");

                    if (isOnline()){
                        submitClearing(hashMap);
                    }else{
                        boolean s=dataBaseHelper.RowInsert(hashMap);
                        if (s){
                            clear();
                            hashMap.clear();
                            Toast.makeText(ROWForm.this, "Locally Saved", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ROWForm.this, "Not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void clear(){
        editChainageFrom.getText().clear();
        editChainageTo.getText().clear();
        edit_OtherDetails.getText().clear();
        editRemarks.getText().clear();
        editChainage.getText().clear();
        editTPNo.getText().clear();
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

    private void submitClearing(final HashMap<String, String> hashMap) {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
         StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.BASE_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        clear();
                        hashMap.clear();
                        Toast.makeText(ROWForm.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
                Toast.makeText(ROWForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
