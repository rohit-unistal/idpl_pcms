package unistal.com.idpl_pcms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class HindranceListActivity extends AppCompatActivity {
    @BindView(R.id.lvhome)
    ListView listViewHome;
    @BindView(R.id.backarrow)
    ImageView backArrow;
    @BindView(R.id.newHindrance)
    Button btnNewHindrance;
    Context context;
    ProgressLoading progressLoading;
    ArrayList<HindranceDetail> hinderanceDetailArrayList;
    DateFormat inputFormat,outputFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindrance_list);
        ButterKnife.bind(this);
        context = this;
        init();

    }
    private  void init()
    {
        progressLoading = new ProgressLoading(context);
        hinderanceDetailArrayList = new ArrayList();
      //  inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNewHindrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,HindranceActivity.class)) ;
            }
        });
       /* listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"item Click",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void getListHindrance() {
        if (DialogUtil.checkInternetConnection(this)) {
            progressLoading.onShow();
            RequestQueue queue = Volley.newRequestQueue(this);

            final String url = AppConstants.APP_BASE_URL +"API_NEW/get_all_activity_type.php";
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            Log.e("Response ", response);
                            hinderanceDetailArrayList.clear();
                            progressLoading.dismiss();
                            try{
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("code").equalsIgnoreCase("200")) {

                                    try {
                                        JSONObject datajason = jsonObj.getJSONObject("result");
                                        JSONArray jsonArray = datajason.getJSONArray("hindrance_data");

                                        for(int i=0; i<jsonArray.length();i++)
                                        {
                                            JSONObject innerjson_obj=jsonArray.getJSONObject(i);
                                            HindranceDetail hindranceDetail = new HindranceDetail(innerjson_obj.optString("activity_affected")
                                                    ,innerjson_obj.optString("id")
                                                    ,outputFormat.format( inputFormat.parse(innerjson_obj.optString("report_date"))),innerjson_obj.optString("report_no")
                                                    ,innerjson_obj.optString("chainage_from"),innerjson_obj.optString("chainage_to")
                                                    ,innerjson_obj.optString("area_hold"),innerjson_obj.optString("description")
                                                   // ,outputFormat.format( inputFormat.parse(innerjson_obj.optString("date_from")))
                                                    ,innerjson_obj.optString("date_from")
                                                    ,innerjson_obj.optString("date_to")
                                                    ,innerjson_obj.optString("remarks"),innerjson_obj.optString("responsibility")
                                                    ,innerjson_obj.optString("duration"),innerjson_obj.optString("weather")
                                                    ,innerjson_obj.optString("status"),innerjson_obj.optString("imagepath")

                                            );
                                            hinderanceDetailArrayList.add(hindranceDetail);
                                          /*  String taskCode = innerjson_obj.optString("TaskCode");
                                            String taskName = innerjson_obj.optString("TaskName");
                                            String startDistance = innerjson_obj.optString("StartDistance");
                                            String endDistance = innerjson_obj.optString("EndDistance");
                                            String qty = innerjson_obj.optString("Qty");
                                            String qtyUnit = innerjson_obj.optString("QtyUnit");
                                            String startDate = innerjson_obj.optString("StartDate");*/


                                        }
                                    }catch (Exception e)
                                    {

                                    }

                                }
                            }catch (JSONException e)
                            {

                            }
                           CustomListAdapter adapter = new CustomListAdapter(context, hinderanceDetailArrayList);

// get the ListView and attach the adapter

                            listViewHome.setAdapter(adapter);


                        }
                    }
                    , new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressLoading.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("type", "ViewHindranceData");
                    params.put("Status", "Open");

                    params.put("UserID", SessionUtil.getUserId(context));
                    params.put("SectionID", SessionUtil.getAssignedSection(context));
                    Log.e("requests",params.toString());
                    return params;
                }

            };


            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } else {
            DialogUtil.showNoConnectionDialog(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListHindrance();
    }

    @OnItemClick(R.id.lvhome)
    public void onItemClick(AdapterView<?> parent, View view, long id, int position) {
       // Toast.makeText(context,"item Click",Toast.LENGTH_SHORT).show();
        HindranceDetail hinderanceDetail = (HindranceDetail) parent.getItemAtPosition(position);




       if(view.getId()==R.id.btnUpdate)
       {

           Intent intent = new Intent(HindranceListActivity.this,HindranceUpdateActivity.class);

           intent.putExtra("hind",hinderanceDetail);
           startActivity(intent);
          // Toast.makeText(context,"button Click",Toast.LENGTH_SHORT).show();
           //startActivity(new Intent(HindranceListActivity.this,HindranceUpdateActivity.class));
       }else if(view.getId()==R.id.text_view_reportNo && !hinderanceDetail.getImagepath().trim().equals("")) {
           ImageView image = new ImageView(this);
           image.setMaxWidth(200);
           image.setMaxHeight(200);

           image.setPadding(20,40,20,20);
           image.setImageResource(R.drawable.iocl);
           Picasso.get().load(hinderanceDetail.getImagepath()).into(image);
           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
           alertDialogBuilder.setView(image);
           alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() { // define the 'Cancel' button
               public void onClick(DialogInterface dialog, int which) {
                   //Either of the following two lines should work.
                   dialog.cancel();
                   //dialog.dismiss();
               }
           });
           AlertDialog alertDialog = alertDialogBuilder.create();
           alertDialog.show();
           Toast.makeText(context,hinderanceDetail.getReportNo(), Toast.LENGTH_SHORT).show();
       }





    }
    public class CustomListAdapter extends BaseAdapter {
        private Context context; //context
        private ArrayList<HindranceDetail> items; //data source of the list adapter

        //public constructor
        public CustomListAdapter(Context context, ArrayList<HindranceDetail> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size(); //returns total of items in the list
        }

        @Override
        public Object getItem(int position) {
            return items.get(position); //returns list item at the specified position
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.hindrance_list_view_row_items, parent, false);


            }

            // get current item to be displayed
            HindranceDetail currentItem = (HindranceDetail) getItem(position);

            // get the TextView for item name and item description

            TextView textViewactivityAffected = (TextView)
                    convertView.findViewById(R.id.text_view_activityAffected);
            TextView textViewreportDate = (TextView)
                    convertView.findViewById(R.id.text_view_reportDate);
            TextView textViewreportNo = (TextView)
                    convertView.findViewById(R.id.text_view_reportNo);
            TextView textViewchainageFrom = (TextView)
                    convertView.findViewById(R.id.text_view_chainageFrom);
            TextView textViewchainageTo = (TextView)
                    convertView.findViewById(R.id.text_view_chainageTo);
            TextView textViewareaHold = (TextView)
                    convertView.findViewById(R.id.text_view_areaHold);
            TextView textViewdescription = (TextView)
                    convertView.findViewById(R.id.text_view_description);
            TextView textViewdateFrom = (TextView)
                    convertView.findViewById(R.id.text_view_dateFrom);
            TextView textViewdateTo = (TextView)
                    convertView.findViewById(R.id.text_view_dateTo);
            TextView textViewremarks = (TextView)
                    convertView.findViewById(R.id.text_view_remarks);
            TextView textViewresponsibility = (TextView)
                    convertView.findViewById(R.id.text_view_responsibility);


            TextView textViewduration = (TextView)
                    convertView.findViewById(R.id.text_view_duration);
            TextView textViewweather = (TextView)
                    convertView.findViewById(R.id.text_view_weather);

            TextView textViewTaskStatus = (TextView)
                    convertView.findViewById(R.id.text_view_status);
            if(!currentItem.getImagepath().trim().equals(""))
            {
                textViewreportNo.setTextColor(Color.BLUE);

            }
            if(position%2==0)
            {
                convertView.setBackgroundColor( Color.WHITE);
            }else{
                convertView.setBackgroundColor(  getResources().getColor(R.color.fade_color));
            }
            convertView.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });
            convertView.findViewById(R.id.text_view_reportNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });
            /*if (position==0)
            {
                convertView.setBackgroundResource( R.color.colorAccent);
                textViewQty.setTypeface(null, Typeface.BOLD);
                textViewQtyUOM.setTypeface(null, Typeface.BOLD);
                textViewTaskCode.setTypeface(null, Typeface.BOLD);
                textViewTaskName.setTypeface(null, Typeface.BOLD);
                textViewStartDate.setTypeface(null, Typeface.BOLD);
                textViewStartDistance.setTypeface(null, Typeface.BOLD);
                textViewEndDistance.setTypeface(null, Typeface.BOLD);
                textViewQty.setTextColor(getResources().getColor(R.color.text_white));
                textViewQtyUOM.setTextColor(getResources().getColor(R.color.text_white));
                textViewTaskCode.setTextColor(getResources().getColor(R.color.text_white));
                textViewTaskName.setTextColor(getResources().getColor(R.color.text_white));
                textViewStartDate.setTextColor(getResources().getColor(R.color.text_white));
                textViewStartDistance.setTextColor(getResources().getColor(R.color.text_white));
                textViewEndDistance.setTextColor(getResources().getColor(R.color.text_white));

            }*/
            //sets the text for item name and item description from the current item object

            textViewactivityAffected.setText(currentItem.getActivityAffected());
            textViewreportDate.setText(currentItem.getReportDate());
            textViewreportNo.setText(currentItem.getReportNo());
            textViewchainageFrom.setText(currentItem.getChainageFrom());
            textViewchainageTo.setText(currentItem.getChainageTo());
            textViewareaHold.setText(currentItem.getAreaHold());
            textViewdescription.setText(currentItem.getDescription());

            textViewdateFrom.setText(currentItem.getDateFrom());
            textViewdateTo.setText(currentItem.getDateTo());
            textViewremarks.setText(currentItem.getRemarks());
            textViewresponsibility.setText(currentItem.getResponsibility());
            textViewduration.setText(currentItem.getDuration());
            textViewweather.setText(currentItem.getWeather());


            textViewTaskStatus.setText(currentItem.getStatus());

            // returns the view for the current row
            return convertView;
        }
    }






}
