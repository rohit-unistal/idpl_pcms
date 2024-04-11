package unistal.com.idpl_pcms.qc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class QCContractorROW extends AppCompatActivity {

    @BindView(R.id.spinner_reportno)
    Spinner spinnerReportno;
    @BindView(R.id.chb_approve)
    CheckBox checkBoxApprove;
    @BindView(R.id.edit_remark)
    EditText editComment;
    @BindView(R.id.contractorQcList)
    ListView contractorQcList;
    @BindView(R.id.btn_submit)
    TextView btn_submit;


    ProgressDialog progressDialog;
    String [] arrReportNo;
    ArrayList reportName,reportIDName;
    ArrayList<HashMap<String,String>> reportIDList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qccontractor_row);
        context=this;
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        reportIDList=new ArrayList<>();
        reportName=new ArrayList();
        reportIDName=new ArrayList();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();

                params.put("type", "");
                params.put("GroupType", "");
                params.put("QCContractor", "");
                params.put("QCClient", "");
                params.put("QCPMC", "");
                bookTruck(context,params);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        setSpinReportNo();
    }
    @OnItemSelected(R.id.spinner_reportno)
    public void onControlRoomSelected(int position)
    {
        if(position>0) {
            contractorQcList.setVisibility(View.VISIBLE);
            Log.e("reportNameNo",reportName.get(position).toString());
            setListReportNo(reportName.get(position).toString());
        }else{
            contractorQcList.setVisibility(View.GONE);
            //Toast.makeText(QCContractorROW.this, "Select Report No", Toast.LENGTH_SHORT).show();
        }
    }
    public void setSpinReportNo() {
        //if (DialogUtil.checkInternetConnection(this)) {
        //loadingDialog.onShow();
        reportName.clear();
        reportName.add("Select Report No.");
        progressDialog.show();
        final String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = AppConstants.BASE_URL +"?type=roucontractor&SectionID="+"1";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        //if (response.contains("Successful")) {
                            try {
                                //alignmentName.add("Select Alignment");
                                JSONArray arr = new JSONArray(response);
                                //s = new String[arr.length()];
                                if (arr != null) {
                                    for (int i = 0; i < arr.length(); i++) {
                                        reportName.add(arr.getJSONObject(i).getString("ReportNo"));
                                    }
                                    // spinnerArrayAdapter = new CustomAdapter(getApplicationContext(),alignmentNo); //selected item will look like a spinner set from XML
                                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, reportName);
                                    //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    //spinnerArrayAdapter.notifyDataSetChanged();
                                    spinnerReportno.setAdapter(spinnerArrayAdapter);
                                }
                            } catch (JSONException ignored) {

                            }

                        //}
                        //loadingDialog.dismiss();
                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                //loadingDialog.dismiss();
                progressDialog.dismiss();
                Toast.makeText(QCContractorROW.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/
    }


    public void setListReportNo(final String reportNameNo) {

       /* reportIDName.add("Select Report ID");
        areaList.add(new AssetsModel("null","null"));*/
        reportIDList.clear();
        reportIDName.clear();

        //if (DialogUtil.checkInternetConnection(QC_TrenchingActivity.this)) {
            if (!progressDialog.isShowing()) {

                progressDialog.show();
            }
            RequestQueue requestQueue = Volley.newRequestQueue(QCContractorROW.this);
            //final String url = "http://demo.plmis.net/API/contractor_trenching.php?"+"ReportNo="+reportNameNo;
            final String url = AppConstants.BASE_URL+"?type=roucontractorview&ReportNo="+reportNameNo;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response","2"+response);
                    try {
                        //alignmentName.add("Select Alignment");
                        JSONArray jarr = new JSONArray(response);
                        //s = new String[arr.length()];
                        if (jarr != null) {
                            for (int j = 0; j < jarr.length(); j++) {
                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("RouID",jarr.getJSONObject(j).getString("RouID"));
                                hashMap.put("ReportNo",jarr.getJSONObject(j).getString("ReportNo"));
                                hashMap.put("ChainageFrom",jarr.getJSONObject(j).getString("ChainageFrom"));
                                hashMap.put("ChainageTo",jarr.getJSONObject(j).getString("ChainageTo"));
                                hashMap.put("RouLength",jarr.getJSONObject(j).getString("RouLength"));
                                hashMap.put("Alignment_Sheet",jarr.getJSONObject(j).getString("Alignment_Sheet"));
                                hashMap.put("RouDate",jarr.getJSONObject(j).getString("RouDate"));
                                reportIDList.add(hashMap);

                            }
                            MyListAdapter reportAdapter = new MyListAdapter(QCContractorROW.this,reportIDList);
                            reportAdapter.notifyDataSetChanged();
                            contractorQcList.setAdapter(reportAdapter);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(QCContractorROW.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(QCContractorROW.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("ReportNo",reportNameNo);

                    return params;
                }

            };

            requestQueue.add(stringRequest);

        /*} else {
            DialogUtil.showNoConnectionDialog(QC_TrenchingActivity.this);
        }*/





    }

    class MyListAdapter extends BaseAdapter {

        private final Activity context;
        private final ArrayList<HashMap<String,String>> qcContractor ;


        public MyListAdapter(Activity context,ArrayList<HashMap<String, String>> qcContractor) {
            this.context=context;
            this.qcContractor = qcContractor;
        }

        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();

            @SuppressLint("ViewHolder")
            View rowView=inflater.inflate(R.layout.contractor_list_item, null,true);

            LinearLayout layoutListItem =  rowView.findViewById(R.id.layoutListItem);
            TextView txtSN =  rowView.findViewById(R.id.txtSN);
            TextView txtChainageFrom =  rowView.findViewById(R.id.txtChainageFrom);
            TextView txtChainageTo =  rowView.findViewById(R.id.txtChainageTo);
            TextView txtLength =  rowView.findViewById(R.id.txtLength);

            final HashMap<String,String> hashMap=qcContractor.get(position);

            layoutListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailDialog(hashMap);
                }
            });
            /*if(position==0) {
                rowView.setBackgroundColor(Color.LTGRAY);
                titleText.setTextColor(Color.WHITE);
                chainagefromText.setTextColor(Color.WHITE);
                chainagetoText.setTextColor(Color.WHITE);
            }*/
            txtSN.setText(hashMap.get("RouID"));
            txtChainageFrom.setText(hashMap.get("ChainageFrom"));
            txtChainageTo.setText(hashMap.get("ChainageTo"));
            txtLength.setText(hashMap.get("RouLength"));
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


    public void DetailDialog(HashMap<String, String> hashMap) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dailog_layout_details, null);


        final ImageView btnCancel=alertLayout.findViewById(R.id.imgCancel);
        final TextView txtReportNo=alertLayout.findViewById(R.id.txtReportNo);
        final TextView txtChainageFrom=alertLayout.findViewById(R.id.txtChainageFrom);
        final TextView txtChainageTo=alertLayout.findViewById(R.id.txtChainageTo);
        final TextView rouLength=alertLayout.findViewById(R.id.rouLength);
        final TextView rouCreatedDate=alertLayout.findViewById(R.id.rouCreatedDate);
        final TextView txtAlignment_Sheet=alertLayout.findViewById(R.id.txtAlignment_Sheet);


        txtReportNo.setText("Report No. - \t"+hashMap.get("ReportNo"));
        txtChainageFrom.setText(" : "+hashMap.get("ChainageFrom"));
        txtChainageTo.setText(" : "+hashMap.get("ChainageTo"));
        rouLength.setText(" : "+hashMap.get("RouLength"));
        txtAlignment_Sheet.setText(" : "+hashMap.get("Alignment_Sheet"));
        rouCreatedDate.setText(" : "+hashMap.get("RouDate"));
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

    public static void bookTruck(final Context context, final Map<String, String> params) {

        Activity activity = (Activity) context;




        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
               AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {

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

}
