package unistal.com.idpl_pcms.qc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.R;
import unistal.com.idpl_pcms.SessionUtil;

public class ROWay_QC extends AppCompatActivity {
    @BindView(R.id.spinner_reportno)
    Spinner spinnerReportno;
    @BindView(R.id.chb_approve)
    RadioButton checkBoxApprove;
    @BindView(R.id.chb_reject)
    RadioButton checkBoxReject;
    @BindView(R.id.edit_remark)
    EditText editComment;
    @BindView(R.id.ROU_QcList)
    ListView ROU_QcList;
    @BindView(R.id.btn_submit)
    TextView btn_submit;

    @BindView(R.id.txtEmptyList)
    TextView txtEmptyList;
    @BindView(R.id.spinner_client)
    Spinner spinnerClient;
    @BindView(R.id.llclient)
    LinearLayout llclient;
    @BindView(R.id.llremark)
    LinearLayout llremark;
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
        setContentView(R.layout.activity_roway__qc);

        context=this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        reportIDList=new ArrayList<>();
        reportName=new ArrayList();
        reportIDName=new ArrayList();
        clientName = new ArrayList();
        clientID = new ArrayList();
        checkBoxApprove.setChecked(true);llremark.setVisibility(View.INVISIBLE);
        ROU_QcList.setVisibility(View.GONE);
        txtEmptyList.setVisibility(View.VISIBLE);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "roucontractorupdate";
                if(SessionUtil.getGroupType(context).contains("QCPMC"))
                { type = "roupmcupdate";}
                if(SessionUtil.getGroupType(context).contains("QCClient"))
                { type = "rouclientupdate";}
                HashMap<String, String> params = new HashMap<>();

                params.put("type", type);
                params.put("GroupType",SessionUtil.getGroupType(context));
                params.put("UserID", SessionUtil.getUserId(context));
                params.put("SectionID", SessionUtil.getAssignedSection(context));
                params.put("ReportNo", spinnerReportno.getSelectedItem().toString());
               params.put("Uremarks", editComment.getText().toString().trim());
                params.put("approveconstruct", strApprove);
                params.put("ClientID", selectedClientID);
                Log.e("params", params.toString());
                submitDetails(context,params);

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

            @SuppressLint("ViewHolder")
            View rowView=inflater.inflate(R.layout.rou_qc_list_item, null,true);

            LinearLayout layoutListItem =  rowView.findViewById(R.id.layoutListItem);
            TextView txtDate =  rowView.findViewById(R.id.txtDate);
            TextView txtChainageFrom =  rowView.findViewById(R.id.txtChainageFrom);
            TextView txtChainageTo =  rowView.findViewById(R.id.txtChainageTo);
            TextView txtLength =  rowView.findViewById(R.id.txtLength);
            TextView txtAlignmentSheet =  rowView.findViewById(R.id.txtalignmentsheet);
            TextView txtNameofStructure =  rowView.findViewById(R.id.txtnameofstructure);

            TextView txtDetailsofStructure =  rowView.findViewById(R.id.txtdetailsofstructure);

            TextView txtTpChainage =  rowView.findViewById(R.id.txttpchainage);
            TextView txtTerrain =  rowView.findViewById(R.id.txtTerrain);
            TextView txtBearingAngle =  rowView.findViewById(R.id.txtbearingangle);
            TextView txtTPRemarks =  rowView.findViewById(R.id.txt_tp_remarks);
            TextView txtRemarks =  rowView.findViewById(R.id.txt_remarks);




            LinearLayout layouttp =  rowView.findViewById(R.id.layouttp);
            LinearLayout layoutchainage =  rowView.findViewById(R.id.layoutchainage);
            LinearLayout layoutrow = rowView.findViewById(R.id.linlayrow);
            layouttp.setVisibility(View.VISIBLE);
            layoutchainage.setVisibility(View.VISIBLE);
            layoutrow.setVisibility(View.VISIBLE);
            TextView txttpno=rowView.findViewById(R.id.txttpno);
            TextView txtchainage=rowView.findViewById(R.id.txt_chainage);
            final HashMap<String,String> hashMap=qcContractor.get(position);

            layoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailDialog(hashMap);
                }
            });

            txtDate.setText(hashMap.get("RouDate"));
            txtChainageFrom.setText(hashMap.get("ChainageFrom"));
            txtChainageTo.setText(hashMap.get("ChainageTo"));
           txtLength.setText(hashMap.get("RouLength"));
            txttpno.setText(hashMap.get("Tp"));
            txtchainage.setText(hashMap.get("Chainage"));
            txtAlignmentSheet.setText(hashMap.get("Alignment Sheet"));

            txtTerrain.setText(hashMap.get("Terrian"));
            txtDetailsofStructure.setText(hashMap.get("Details Of Structures"));
            txtTpChainage.setText(hashMap.get("TPIP Chainage"));
            txtBearingAngle.setText(hashMap.get("BearingDeflection Of Angle"));
            txtNameofStructure.setText(hashMap.get("Name Of Structure"));
            txtTPRemarks.setText(hashMap.get("TP Remarks"));
            txtRemarks.setText(hashMap.get("Remarks"));



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

    public void setSpinReportNo() {

        reportName.clear();
        reportName.add("Select Report No.");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "roucontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "roupmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "rouclient";
        }
            final String url = AppConstants.BASE_URL+"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);

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

                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, reportName);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                spinnerReportno.setAdapter(spinnerArrayAdapter);
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
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                //?type=roucontractor&SectionID=1
                params.put("type","roucontractor");
                params.put("SectionID","1");

                return params;
            }

        };
        // Add the request to the RequestQueue.
        Log.e("Request", stringRequest.toString());
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
                        Toast.makeText(context, stringResponse, Toast.LENGTH_SHORT).show();
                        ROU_QcList.setVisibility(View.GONE);
                        txtEmptyList.setVisibility(View.VISIBLE);
                        Log.e("Response",stringResponse);
                        progressDialog.dismiss();
                        try {

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
    public void DetailDialog(HashMap<String, String> hashMap) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_layout_rou, null);


        final ImageView btnCancel=alertLayout.findViewById(R.id.imgCancel);
        final TextView txtReportNo=alertLayout.findViewById(R.id.txtReportNo);
        final TextView txtDate=alertLayout.findViewById(R.id.txtDate);
        final TextView txtChainageFrom=alertLayout.findViewById(R.id.txtChainageFrom);
        final TextView txtChainageTo=alertLayout.findViewById(R.id.txtChainageTo);
        final TextView txtLength=alertLayout.findViewById(R.id.txtLength);



        txtReportNo.setText(" : "+spinnerReportno.getSelectedItem().toString());
        txtDate.setText(" : "+hashMap.get("RouDate"));
        txtChainageFrom.setText(" : "+hashMap.get("ChainageFrom"));
        txtChainageTo.setText(" : "+hashMap.get("ChainageTo"));
        txtLength.setText(" : "+hashMap.get("RouLength"));



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
        String type = "roucontractorview";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "roupmcview";}
        if(SessionUtil.getGroupType(context).contains("QCClient"))
        { type = "rouclientview";}
        final String url = AppConstants.BASE_URL+ "?type="+type+"&ReportNo="+reportNameNo;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                try {
                    JSONArray jarr = new JSONArray(response);
                    if (jarr != null) {
                        for (int j = 0; j < jarr.length(); j++) {
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("RouID",jarr.getJSONObject(j).getString("RouID"));
                            hashMap.put("RouDate",jarr.getJSONObject(j).getString("RouDate"));

                            hashMap.put("ChainageFrom",jarr.getJSONObject(j).getString("ChainageFrom"));
                            hashMap.put("ChainageTo",jarr.getJSONObject(j).getString("ChainageTo"));
                           hashMap.put("RouLength",jarr.getJSONObject(j).getString("RouLength"));
                            hashMap.put("Tp",jarr.getJSONObject(j).getString("TPIP Nos."));
                            hashMap.put("Chainage",jarr.getJSONObject(j).getString("Chainage"));
                          hashMap.put("Remarks",jarr.getJSONObject(j).getString("Remarks"));
                            hashMap.put("Details Of Structures",jarr.getJSONObject(j).getString("Details Of Structures"));
                            hashMap.put("Terrian",jarr.getJSONObject(j).getString("Terrian"));
                            hashMap.put("TPIP Chainage",jarr.getJSONObject(j).getString("TPIP Chainage"));
                            hashMap.put("BearingDeflection Of Angle",jarr.getJSONObject(j).getString("BearingDeflection Of Angle"));
                            hashMap.put("TP Remarks",jarr.getJSONObject(j).getString("TP Remarks"));
                            hashMap.put("Name Of Structure",jarr.getJSONObject(j).getString("Name Of Structure"));
                            hashMap.put("Alignment Sheet",jarr.getJSONObject(j).getString("Alignment Sheet"));

                            reportIDList.add(hashMap);

                        }
                        MyListAdapter reportAdapter = new MyListAdapter(ROWay_QC.this,reportIDList);
                        reportAdapter.notifyDataSetChanged();
                        ROU_QcList.setAdapter(reportAdapter);
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
