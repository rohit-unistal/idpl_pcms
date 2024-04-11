package unistal.com.idpl_pcms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HindranceUpdateActivity extends AppCompatActivity {
    @BindView(R.id.backarrow)
    ImageView backArrow;
    @BindView(R.id.edit_activityeffected)
    EditText editActivityEffected;
    @BindView(R.id.edit_reportno)
    EditText editReportNo;
    @BindView(R.id.edit_date)
    TextView editDate;
    @BindView(R.id.edit_chaingefrom)
    EditText editChainageFrom;
    @BindView(R.id.edit_chainageto)
    EditText editChainageTo;
    @BindView(R.id.edit_areahold)
    EditText editAreaHold;
    @BindView(R.id.spinDescription)
    EditText spinDescription;
    @BindView(R.id.edit_datefrom)
    TextView editDateFrom;
    @BindView(R.id.edit_dateto)
    TextView editDateTo;
    @BindView(R.id.edit_weather)
    EditText editWeather;
    @BindView(R.id.edit_remark)
    EditText editRemarks;
    @BindView(R.id.edit_responsibility)
    EditText editResponsibility;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    Context context;
    String id;
    DateFormat inputFormat,outputFormat;

HindranceDetail valueHindrance;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindrance_update);
        ButterKnife.bind(this);
        context = this;
        Intent intent=getIntent();
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        if(intent!=null) {
            try{
            valueHindrance = (HindranceDetail) intent.getSerializableExtra("hind");
            Toast.makeText(getApplicationContext(),valueHindrance.getReportNo(), Toast.LENGTH_SHORT).show();
            editActivityEffected.setText(valueHindrance.getActivityAffected());
            editReportNo.setText(valueHindrance.getReportNo());

            editChainageFrom.setText(valueHindrance.getChainageFrom());
            editChainageTo.setText(valueHindrance.getChainageTo());
            editAreaHold.setText(valueHindrance.getAreaHold());
          //  editDateFrom.setText(outputFormat.format( inputFormat.parse(valueHindrance.getDateFrom())));
            spinDescription.setText(valueHindrance.getDescription());
            editDateFrom.setText(valueHindrance.getDateFrom());
            editWeather.setText(valueHindrance.getWeather());
            editRemarks.setText(valueHindrance.getRemarks());
            editResponsibility.setText(valueHindrance.getResponsibility());
                id = valueHindrance.getId();
            }catch (Exception e)
            {

            }
        }
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    public void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    setReportDate();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    editDateTo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setToDate();
        }
    });
    }
    private void setReportDate()
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        editDate.setText(mDay  + "-" + (mMonth + 1) + "-" +mYear  );
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
    private void validateFields() {
        if (editChainageFrom.getText().toString().equals("")){
            Toast.makeText(HindranceUpdateActivity.this, "Enter Chainage From", Toast.LENGTH_SHORT).show();
        }else if (editChainageTo.getText().toString().equals("")){
            Toast.makeText(HindranceUpdateActivity.this, "Enter Chainage To", Toast.LENGTH_SHORT).show();
        }else if (editDateFrom.getText().toString().equals("")) {
            Toast.makeText(HindranceUpdateActivity.this, "Enter Date From", Toast.LENGTH_SHORT).show();
        }else{
                HashMap<String, String> hashMap=new HashMap<>();

                hashMap.put("UserID", SessionUtil.getUserId(context));
                hashMap.put("SectionID",SessionUtil.getAssignedSection(context));
                hashMap.put("type","EditHindranceData");
                hashMap.put("activity_affected",editActivityEffected.getText().toString());
                hashMap.put("report_date",editDate.getText().toString());
            hashMap.put("ReportNo",editReportNo.getText().toString());
            hashMap.put("id",id);
                hashMap.put("chainage_from",editChainageFrom.getText().toString());
                hashMap.put("chainage_to",editChainageTo.getText().toString());

                hashMap.put("description",spinDescription.getText().toString());
                hashMap.put("date_from",editDateFrom.getText().toString());
                hashMap.put("date_to",editDateTo.getText().toString().trim());
                hashMap.put("weather",editWeather.getText().toString());
                hashMap.put("remarks",editRemarks.getText().toString());
                // hashMap.put("duration",editDuration.getText().toString());
                hashMap.put("responsibility",editResponsibility.getText().toString());
                //  hashMap.put("status",editStatus.getText().toString());

                if (isOnline()){
                    Log.e("Params", hashMap.toString());
                    InsertData(context,hashMap);
                }else {
                    Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();

                }
            }}
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
                            finish();
                            params.clear();

                            Toast.makeText(context, stringResponse, Toast.LENGTH_LONG).show();
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
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
