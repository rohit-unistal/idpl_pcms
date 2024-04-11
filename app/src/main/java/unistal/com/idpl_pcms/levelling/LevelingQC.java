package unistal.com.idpl_pcms.levelling;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

public class LevelingQC extends AppCompatActivity {

    @BindView(R.id.spinner_reportno)
    Spinner spinnerReportno;
    @BindView(R.id.chb_approve)
    RadioButton checkBoxApprove;
    @BindView(R.id.chb_reject)
    RadioButton checkBoxReject;
    @BindView(R.id.layoutRemark)
    LinearLayout llRemark;
    @BindView(R.id.edit_remark)
    EditText editComment;
    @BindView(R.id.QcList)
    ListView QcList;
    @BindView(R.id.btn_submit)
    TextView btn_submit;

    @BindView(R.id.txtEmptyList)
    TextView txtEmptyList;

    @BindView(R.id.spinner_client)
    Spinner spinnerClient;
    @BindView(R.id.llclient)
    LinearLayout llclient;
    ArrayList clientName,clientID;
    String selectedClientID="0";
    ProgressDialog progressDialog;
    String strApprove="1";
    ArrayList reportName,reportIDName;
    ArrayList<HashMap<String,String>> reportIDList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leveling_qc);

        context=this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        reportIDList=new ArrayList<>();
        reportName=new ArrayList();
        reportIDName=new ArrayList();
        clientID= new ArrayList();clientName = new ArrayList();
        checkBoxApprove.setChecked(true);llRemark.setVisibility(View.INVISIBLE);


        QcList.setVisibility(View.GONE);
        txtEmptyList.setVisibility(View.VISIBLE);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "levelcontractorupdate";
                if(SessionUtil.getGroupType(context).contains("QCPMC"))
                { type = "levelpmcupdate";}
                if(SessionUtil.getGroupType(context).contains("QCClient"))
                { type = "levelclientupdate";}


                HashMap<String, String> params = new HashMap<>();
                params.put("type", type);
                params.put("GroupType",SessionUtil.getGroupType(context));
                params.put("UserID", SessionUtil.getUserId(context));
                params.put("SectionID", SessionUtil.getAssignedSection(context));
                params.put("ReportNo", spinnerReportno.getSelectedItem().toString());
                params.put("Uremarks", editComment.getText().toString().trim());
                params.put("ClientID", selectedClientID);
                params.put("approveconstruct", strApprove);

                if (!spinnerReportno.getSelectedItem().toString().equals("Select Report No."))
                    submitDetails(context,params);
                else {
                    Toast.makeText(context, "Select Report No.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkBoxApprove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strApprove="1";
                    llRemark.setVisibility(View.INVISIBLE);
                }
            }
        });
        checkBoxReject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    strApprove="2";
                    llRemark.setVisibility(View.VISIBLE);
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
            QcList.setVisibility(View.VISIBLE);
            txtEmptyList.setVisibility(View.GONE);
            Log.e("reportNameNo",reportName.get(position).toString());
            setListReportNo(reportName.get(position).toString());
        }else{
            QcList.setVisibility(View.GONE);
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

        private final Context context;
        private final ArrayList<HashMap<String,String>> qcContractor ;


        public MyListAdapter(Context context,ArrayList<HashMap<String, String>> qcContractor) {
            this.context=context;
            this.qcContractor = qcContractor;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();

            @SuppressLint("ViewHolder")
            View rowView=inflater.inflate(R.layout.qc_list_item, null,true);

            LinearLayout layoutListItem =  rowView.findViewById(R.id.layoutListItem);
            LinearLayout layoutOthers =  rowView.findViewById(R.id.layoutOthers);
            TextView txtAlignment_SheetNo =  rowView.findViewById(R.id.txtAlignment_SheetNo);
            TextView txtChainageFrom =  rowView.findViewById(R.id.txtChainageFrom);
            TextView txtChainageTo =  rowView.findViewById(R.id.txtChainageTo);
            TextView txtLength =  rowView.findViewById(R.id.txtLength);
            TextView txtOther =  rowView.findViewById(R.id.txtOther);

            TextView txtView1 =  rowView.findViewById(R.id.txtView1);
            TextView txtView2 =  rowView.findViewById(R.id.txtView2);
            TextView txtView3 =  rowView.findViewById(R.id.txtView3);
            TextView txtView4 =  rowView.findViewById(R.id.txtView4);
            TextView txtView5 =  rowView.findViewById(R.id.txtView5);

            final HashMap<String,String> hashMap=qcContractor.get(position);

            layoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DetailDialog(hashMap);
                }
            });

            layoutOthers.setVisibility(View.VISIBLE);
            txtView1.setText("Report Date");
            txtView2.setText("Joint ID");
            txtView3.setText("Cover");
            txtView4.setVisibility(View.GONE);
            txtView5.setText("Remarks");

            txtAlignment_SheetNo.setText(": "+hashMap.get("ReportDate"));
            txtChainageFrom.setText(": "+hashMap.get("Joint"));
            txtChainageTo.setText(": "+hashMap.get("Cover"));
            txtLength.setVisibility(View.GONE);
            txtOther.setText(": "+hashMap.get("Remarks"));


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
        String type = "levelcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "levelpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "levelclient";
        }
        final String url = AppConstants.BASE_URL+ "?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
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
                        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, reportName);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void submitDetails(final Context context, final Map<String, String> params) {

        progressDialog.show();
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

    public void setListReportNo(final String reportNameNo) {
        reportIDList.clear();
        reportIDName.clear();

        if (!progressDialog.isShowing()) {

            progressDialog.show();
        }

        String type = "levelcontractorview";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "levelpmcview";}
        if(SessionUtil.getGroupType(context).contains("QCClient"))
        { type = "levelclientview";}

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

                            hashMap.put("LevellingID",jarr.getJSONObject(j).getString("LevellingID"));
                            hashMap.put("ReportDate",jarr.getJSONObject(j).getString("ReportDate"));

                            hashMap.put("Joint",jarr.getJSONObject(j).getString("Joint"));
                            hashMap.put("Cover",jarr.getJSONObject(j).getString("Cover"));
                            hashMap.put("ReportNo",jarr.getJSONObject(j).getString("ReportNo"));
                          //  hashMap.put("Holiday",jarr.getJSONObject(j).getString("Holiday"));
                           hashMap.put("Remarks",jarr.getJSONObject(j).getString("Remarks"));

                            reportIDList.add(hashMap);

                        }
                        MyListAdapter reportAdapter = new MyListAdapter(context,reportIDList);
                        reportAdapter.notifyDataSetChanged();
                        QcList.setAdapter(reportAdapter);
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
}
