package unistal.com.idpl_pcms.rou;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.R;

public class ROU_QC extends AppCompatActivity {

    @BindView(R.id.spinner_reportno)
    Spinner spinnerReportno;
    @BindView(R.id.chb_approve)
    CheckBox checkBoxApprove;
    @BindView(R.id.edit_remark)
    EditText editComment;
    @BindView(R.id.ROU_QcList)
    ListView ROU_QcList;
    @BindView(R.id.btn_submit)
    TextView btn_submit;

    @BindView(R.id.txtEmptyList)
    TextView txtEmptyList;

    @BindView(R.id.layoutRemark)
    LinearLayout layoutRemark;

    ProgressDialog progressDialog;
    String strApprove="0";
    ArrayList reportName,reportIDName;
    ArrayList<HashMap<String,String>> reportIDList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rou__qc);


        context=this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        reportIDList=new ArrayList<>();
        reportName=new ArrayList();
        reportIDName=new ArrayList();

        ROU_QcList.setVisibility(View.GONE);
        txtEmptyList.setVisibility(View.VISIBLE);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> params = new HashMap<>();
                params.put("type", "rouhandcontractorupdate");
                params.put("GroupType", "QCContractor");
                params.put("UserID", "24");
                params.put("SectionID", "1");
                params.put("ReportNo", spinnerReportno.getSelectedItem().toString());
                if (strApprove.equals("0")){
                    params.put("Uremarks", editComment.getText().toString().trim());
                }
                params.put("approveconstruct", strApprove);

                if (!spinnerReportno.getSelectedItem().toString().equals("Select Report No.")){
                    submitDetails(context,params);
                }else {
                    Toast.makeText(context, "Select valid report no.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkBoxApprove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strApprove="1";
                    layoutRemark.setVisibility(View.GONE);
                }else {
                    strApprove = "0";
                    layoutRemark.setVisibility(View.VISIBLE);
                }
            }
        });

        setSpinReportNo();
    }



    @OnItemSelected(R.id.spinner_reportno)
    public void onControlRoomSelected(int position)
    {
        if(position>0) {
            ROU_QcList.setVisibility(View.VISIBLE);
            txtEmptyList.setVisibility(View.GONE);
            Log.e("reportNameNo",reportName.get(position).toString());
            setListReportNo(reportName.get(position).toString());
        }else{
            ROU_QcList.setVisibility(View.GONE);
            txtEmptyList.setVisibility(View.VISIBLE);
        }
    }

    class MyListAdapter extends BaseAdapter {

        private final Activity context;
        private final ArrayList<HashMap<String,String>> qcContractor ;


        public MyListAdapter(Activity context,ArrayList<HashMap<String, String>> qcContractor) {
            this.context=context;
            this.qcContractor = qcContractor;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            Log.e("response", String.valueOf(position)+"\t"+qcContractor.size());
            View rowView=inflater.inflate(R.layout.rou_qc_list_item, parent,false);

            LinearLayout layoutListItem =  rowView.findViewById(R.id.layoutListItem);
            TextView txtDate            =  rowView.findViewById(R.id.txtDate);
            TextView txtChainageFrom    =  rowView.findViewById(R.id.txtChainageFrom);
            TextView txtChainageTo      =  rowView.findViewById(R.id.txtChainageTo);
            TextView txtLength          =  rowView.findViewById(R.id.txtLength);

            final HashMap<String,String> hashMap=qcContractor.get(position);

            layoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DetailDialog(hashMap);
                }
            });

            txtDate.setText(": "+hashMap.get("RouHandOverDate"));
            txtChainageFrom.setText(": "+hashMap.get("ChainageFrom"));
            txtChainageTo.setText(": "+hashMap.get("ChainageTo"));
            txtLength.setText(": "+hashMap.get("DistanceCleared"));
            return rowView;

        };

        @Override
        public int getCount() {
            return qcContractor.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
    public void setSpinReportNo() {

        reportName.clear();
        reportName.add("Select Report No.");
        progressDialog.show();
        if (isOnline()){
            RequestQueue queue = Volley.newRequestQueue(context);

            final String url = AppConstants.BASE_URL +"?type=rouhandovercontractor&SectionID=1";
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response","1 "+response);
                            try {
                                JSONArray arr = new JSONArray(response);
                                if (arr != null) {
                                    for (int i = 0; i < arr.length(); i++) {
                                        reportName.add(arr.getJSONObject(i).getString("ReportNo"));
                                    }
                                }
                            } catch (JSONException ignored) {

                            }
                            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, reportName);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinnerReportno.setAdapter(spinnerArrayAdapter);
                            progressDialog.dismiss();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getMessage();
                    progressDialog.dismiss();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
            /*@Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("type","rouhandovercontractor");
                params.put("SectionID","1");
                //params.put("type","");
                return params;
            }*/

            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }else {
            progressDialog.dismiss();
            Toast.makeText(context, "Enable Internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }
    public void submitDetails(final Context context, final Map<String, String> params) {

        progressDialog.show();
        Log.d("URL ", "My  URl: " + params.toString());
        if (isOnline()){
            Activity activity = (Activity) context;

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            StringRequest featureRequest = new StringRequest(Request.Method.POST,
                  AppConstants.BASE_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String stringResponse) {
                            Log.e("response","3 "+stringResponse);
                            progressDialog.dismiss();
                            try {

                            /*JSONObject jsonObject = new JSONObject(stringResponse);

                            if (jsonObject.getString("Status") == null ||
                                    !jsonObject.getString("Status").equalsIgnoreCase("Success")) {
                                Toast.makeText(context, jsonObject.getString("Messege"), Toast.LENGTH_SHORT).show();
                                Log.e("Response", jsonObject.toString());
                                return;
                            }

                            JSONObject response = new JSONObject(stringResponse);
                            Log.e("Response", response.toString());*/
                                if (stringResponse.equalsIgnoreCase("0")) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(featureRequest);
        }else {
            progressDialog.dismiss();
            Toast.makeText(context, "Enable Internet Connetion", Toast.LENGTH_SHORT).show();
        }

    }
    public void DetailDialog(HashMap<String, String> hashMap) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_layout_rou, null);


        final ImageView btnCancel=alertLayout.findViewById(R.id.imgCancel);
        final TextView txtReportNo=alertLayout.findViewById(R.id.txtReportNo);
        final TextView txtDate=alertLayout.findViewById(R.id.txtDate);
        final TextView txtChainageFrom=alertLayout.findViewById(R.id.txtChainageFrom);
        final TextView txtChainageTo=alertLayout.findViewById(R.id.txtChainageTo);
        final TextView txtLength=alertLayout.findViewById(R.id.txtLength);
        final TextView txtVillage=alertLayout.findViewById(R.id.txtVillage);
        final TextView txtTehsil=alertLayout.findViewById(R.id.txtTehsil);
        final TextView txtDistrict=alertLayout.findViewById(R.id.txtDistrict);
        final TextView txtRemark=alertLayout.findViewById(R.id.txtRemark);


        txtReportNo.setText(" : "+spinnerReportno.getSelectedItem().toString());
        txtDate.setText(" : "+hashMap.get("RouHandOverDate"));
        txtChainageFrom.setText(" : "+hashMap.get("ChainageFrom"));
        txtChainageTo.setText(" : "+hashMap.get("ChainageTo"));
        txtLength.setText(" : "+hashMap.get("DistanceCleared"));
        txtVillage.setText(" : "+hashMap.get("Village"));
        txtTehsil.setText(" : "+hashMap.get("Tehsil"));
        txtDistrict.setText(" : "+hashMap.get("District"));
        txtRemark.setText(" : "+hashMap.get("txtRemark"));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        final AlertDialog dialog = alert.create();
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void setListReportNo(final String reportNameNo) {
        reportIDList.clear();
        reportIDName.clear();

        if (!progressDialog.isShowing()) {

            progressDialog.show();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = AppConstants.BASE_URL+ "?type=rouhandovercontractorview&ReportNo="+reportNameNo;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response","2"+response);
                try {
                    JSONArray jarr = new JSONArray(response);
                    if (jarr != null) {
                        for (int j = 0; j < jarr.length(); j++) {
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("RouHandOverID",jarr.getJSONObject(j).getString("RouHandOverID"));
                            hashMap.put("RouHandOverDate",jarr.getJSONObject(j).getString("RouHandOverDate"));

                            hashMap.put("ChainageFrom",jarr.getJSONObject(j).getString("ChainageFrom"));
                            hashMap.put("ChainageTo",jarr.getJSONObject(j).getString("ChainageTo"));
                            hashMap.put("DistanceCleared",jarr.getJSONObject(j).getString("DistanceCleared"));
//                            hashMap.put("Village",jarr.getJSONObject(j).getString("Village"));
//                            hashMap.put("Tehsil",jarr.getJSONObject(j).getString("Tehsil"));
//                            hashMap.put("District",jarr.getJSONObject(j).getString("District"));
//                            hashMap.put("Unapprove_remarks",jarr.getJSONObject(j).getString("Unapprove_remarks"));
                            reportIDList.add(hashMap);

                        }
                        Log.e("response","2"+reportIDList.toString());
                        MyListAdapter reportAdapter = new MyListAdapter(ROU_QC.this,reportIDList);
                        //reportAdapter.notifyDataSetChanged();
                        ROU_QcList.setAdapter(reportAdapter);
                    }
                } catch (JSONException e) {
                    Log.e("response","Error = "+e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
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
