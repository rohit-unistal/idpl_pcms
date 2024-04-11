package unistal.com.idpl_pcms.bending;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import unistal.com.idpl_pcms.SessionUtil;

public class BendingQC extends AppCompatActivity {

    @BindView(R.id.spinner_reportno)
    Spinner spinnerReportno;

    @BindView(R.id.chb_approve)
    RadioButton checkBoxApprove;
    @BindView(R.id.chb_reject)
    RadioButton checkBoxReject;
    @BindView(R.id.layoutRemark)
    LinearLayout llremark;
    @BindView(R.id.edit_remark)
    EditText editComment;
    @BindView(R.id.ROU_QcList)
    ListView bendigQcList;
    @BindView(R.id.btn_submit)
    TextView btn_submit;

    @BindView(R.id.txtEmptyList)
    TextView txtEmptyList;
    /*@BindView(R.id.layoutRemark)
    LinearLayout layoutRemark;*/
    @BindView(R.id.spinner_client)
    Spinner spinnerClient;
    @BindView(R.id.llclient)
    LinearLayout llclient;
    ArrayList clientName,clientID;
    String selectedClientID="0";
    ProgressDialog progressDialog;
    String [] arrReportNo;
    ArrayList reportName,reportIDName;
    ArrayList<HashMap<String,String>> reportIDList;
    Context context;
    String type;
    String strApprove="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bending_qc);


        context=this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        bendigQcList.setVisibility(View.GONE);
        txtEmptyList.setVisibility(View.VISIBLE);

        reportIDList=new ArrayList<>();
        reportName=new ArrayList();
        reportIDName=new ArrayList();
        clientID= new ArrayList();clientName = new ArrayList();
        checkBoxApprove.setChecked(true);llremark.setVisibility(View.INVISIBLE);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "bendingcontractorupdate";
                if(SessionUtil.getGroupType(context).contains("QCPMC"))
                { type = "bendingpmcupdate";}
                if(SessionUtil.getGroupType(context).contains("QCClient"))
                { type = "bendingclientupdate";}

                HashMap<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("GroupType",SessionUtil.getGroupType(context));
                params.put("UserID", SessionUtil.getUserId(context));
                params.put("SectionID", SessionUtil.getAssignedSection(context));
                params.put("ReportNo", spinnerReportno.getSelectedItem().toString());

                params.put("Uremarks", editComment.getText().toString().trim());

                params.put("approveconstruct", strApprove);
                params.put("ClientID", selectedClientID);

                if (isOnline()){
                    if (!spinnerReportno.getSelectedItem().toString().equals("Select Report No."))
                        submitDetails(context,params);//bendinging
                    else {
                        Toast.makeText(context, "Select Report No.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        checkBoxApprove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strApprove="1";
                    llremark.setVisibility(View.INVISIBLE);
                }
            }
        });
        checkBoxReject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strApprove="2";
                    llremark.setVisibility(View.VISIBLE);
                }
            }
        });
        setSpinReportNo();
        setClientNo();
    }
    @OnItemSelected(R.id.spinner_reportno)
    public void onControlRoomSelected(int position)
    {
        if(position>0) {
            bendigQcList.setVisibility(View.VISIBLE);
            txtEmptyList.setVisibility(View.GONE);
            Log.e("reportNameNo",reportName.get(position).toString());
            setListReportNo(reportName.get(position).toString());
        }else{
            bendigQcList.setVisibility(View.GONE);
            txtEmptyList.setVisibility(View.VISIBLE);
        }

    }
    public void setClientNo() {
        clientName.add("Select");clientID.add("0");
        if(SessionUtil.getGroupType(context).contains("QCContractor"))
        { llclient.setVisibility(View.VISIBLE);}
        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = AppConstants.APP_QCCLIENT_URL+"SectionID="+SessionUtil.getAssignedSection(context);

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
                                    clientName.add(arr.getJSONObject(i).getString("FullName"));
                                    clientID.add(arr.getJSONObject(i).getString("UserID"));
                                }

                                ArrayAdapter spinnerClientArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, clientName);
                                spinnerClientArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                spinnerClient.setAdapter(spinnerClientArrayAdapter);
                                spinnerClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedClientID =  clientID.get(position).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        } catch (JSONException ignored) {

                        }
                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        Log.e("Request", stringRequest.toString());
        queue.add(stringRequest);
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

            @SuppressLint("ViewHolder")
            View rowView=inflater.inflate(R.layout.bending_qc_list_item, null,true);

            LinearLayout layoutListItem =  rowView.findViewById(R.id.layoutListItem);
            TextView txtAlignment_SheetNo =  rowView.findViewById(R.id.txtAlignment_SheetNo);
            TextView txtChainageFrom =  rowView.findViewById(R.id.txtChainageFrom);
            TextView txtChainageTo =  rowView.findViewById(R.id.txtChainageTo);
            TextView txtLength =  rowView.findViewById(R.id.txtLength);
            TextView txtTPNo =  rowView.findViewById(R.id.txtTPNo);

            TextView txtView1 =  rowView.findViewById(R.id.txtView1);
            TextView txtView2 =  rowView.findViewById(R.id.txtView2);
            TextView txtView3 =  rowView.findViewById(R.id.txtView3);
            TextView txtView4 =  rowView.findViewById(R.id.txtView4);

            final HashMap<String,String> hashMap=qcContractor.get(position);

            layoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DetailDialog(hashMap);
                }
            });


            txtView1.setText("Bend PipeNo");
            txtView2.setText("Bend Chainage");
            txtView3.setText("Bend Angle");
            txtView4.setText("");
            txtAlignment_SheetNo.setText(hashMap.get("BendID"));
            txtChainageFrom.setText(hashMap.get("BendChainage"));
            txtChainageTo.setText(hashMap.get("BendAngleDeg")+"° "+hashMap.get("BendAngleMin")+"' "+hashMap.get("BendSecond"));
            txtLength.setVisibility(View.GONE);
            txtView4.setVisibility(View.GONE);
            txtTPNo.setText(hashMap.get("TP Number"));
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
        RequestQueue queue = Volley.newRequestQueue(context);

        String type = "bendingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "bendingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "bendingclient";
        }

//        final String url = AppConstants.BASE_URL+"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
        final String url = AppConstants.BASE_URL+"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
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
                        progressDialog.dismiss();
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, reportName);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerReportno.setAdapter(spinnerArrayAdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public static void submitDetails(final Context context, final Map<String, String> params) {

        Activity activity = (Activity) context;



        //HttpsTrustManager.allowAllSSL();


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        Log.e("response","3 "+stringResponse);
                        try {
                            /*Toast.makeText(context, stringResponse, Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(stringResponse);

                            if (jsonObject.getString("Status") == null ||
                                    !jsonObject.getString("Status").equalsIgnoreCase("Success")) {
                                Toast.makeText(context, jsonObject.getString("Messege"), Toast.LENGTH_SHORT).show();
                                Log.e("Response", jsonObject.toString());
                                return;
                            }

                            JSONObject response = new JSONObject(stringResponse);
                            Log.e("Response", response.toString());
                            if (response.getString("Status").equalsIgnoreCase("Success")) {

                            } else {
                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
                            }*/

                            if (stringResponse.equalsIgnoreCase("0")) {
                                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, stringResponse, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, "Somthing went wrong.", Toast.LENGTH_SHORT).show();
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
    public void DetailDialog(HashMap<String, String> hashMap) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_layout_bending, null);


        final ImageView btnCancel=alertLayout.findViewById(R.id.imgCancel);
        final TextView txtReportNo=alertLayout.findViewById(R.id.txtReportNo);
        final TextView txtChainageFrom=alertLayout.findViewById(R.id.txtChainage);
        final TextView txtPipeNo=alertLayout.findViewById(R.id.txtPipeNo);
        final TextView bendType=alertLayout.findViewById(R.id.bendType);
        final TextView txtTPNo=alertLayout.findViewById(R.id.txtTPNo);
        final TextView txtBendDegree=alertLayout.findViewById(R.id.txtBendDegree);
        final TextView txtBendMin=alertLayout.findViewById(R.id.txtBendMin);
        final TextView txtBendSec=alertLayout.findViewById(R.id.txtBendSec);
        final TextView txtVisualCheck=alertLayout.findViewById(R.id.txtVisualCheck);
        final TextView txtGuagingCheck=alertLayout.findViewById(R.id.txtGuaging);
        final TextView txtDisbendingSec=alertLayout.findViewById(R.id.txtDisbendingSec);
        final TextView txtOvalityCheck=alertLayout.findViewById(R.id.txtOvalityCheck);
        final TextView txtHolidayCheck=alertLayout.findViewById(R.id.txtHolidayCheck);
        final TextView txtRemark=alertLayout.findViewById(R.id.txtRemark);


        txtReportNo.setText("Report No. - \t"+hashMap.get("ReportNo"));
        txtChainageFrom.setText(" : "+hashMap.get("ChainageFrom"));
        txtPipeNo.setText(" : "+hashMap.get("ChainageTo"));
        bendType.setText(" : "+hashMap.get("RouLength"));
        txtBendDegree.setText(" : "+hashMap.get("Alignment_Sheet"));
        txtTPNo.setText(" : "+hashMap.get("RouDate"));
        txtBendMin.setText(" : "+hashMap.get("RouDate"));
        txtBendSec.setText(" : "+hashMap.get("RouDate"));
        txtVisualCheck.setText(" : "+hashMap.get("RouDate"));
        txtGuagingCheck.setText(" : "+hashMap.get("RouDate"));
        txtDisbendingSec.setText(" : "+hashMap.get("RouDate"));
        txtOvalityCheck.setText(" : "+hashMap.get("RouDate"));
        txtHolidayCheck.setText(" : "+hashMap.get("RouDate"));
        txtRemark.setText(" : "+hashMap.get("RouDate"));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
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
        String type = "bendingcontractorview";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "bendingpmcview";}
        if(SessionUtil.getGroupType(context).contains("QCClient"))
        { type = "bendingclientview";}

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = AppConstants.BASE_URL+"?type="+type+"&ReportNo="+reportNameNo;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response","2"+response);
                try {
                    JSONArray jarr = new JSONArray(response);
                    if (jarr != null) {
                        for (int j = 0; j < jarr.length(); j++) {
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("BendID",jarr.getJSONObject(j).getString("PipeNumber"));
                            hashMap.put("ReportNo",jarr.getJSONObject(j).getString("ReportNo"));
                            hashMap.put("BendChainage",jarr.getJSONObject(j).getString("BendChainage"));
                            hashMap.put("BendAngleDeg",jarr.getJSONObject(j).getString("BendAngleDeg"));
                            hashMap.put("BendAngleMin",jarr.getJSONObject(j).getString("BendAngleMin"));
                            hashMap.put("BendSecond",jarr.getJSONObject(j).getString("BendSecond"));
                            hashMap.put("BendDate",jarr.getJSONObject(j).getString("BendDate"));
                            hashMap.put("TP Number",jarr.getJSONObject(j).getString("TP Number"));
                            reportIDList.add(hashMap);

                        }
                        MyListAdapter reportAdapter = new MyListAdapter(BendingQC.this,reportIDList);
                        reportAdapter.notifyDataSetChanged();
                        bendigQcList.setAdapter(reportAdapter);
                    }
                } catch (JSONException e) {
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
