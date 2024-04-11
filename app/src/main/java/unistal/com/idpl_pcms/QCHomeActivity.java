package unistal.com.idpl_pcms;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
/*import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;*/
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import unistal.com.idpl_pcms.backfiling.BackFillingQC;
import unistal.com.idpl_pcms.bending.BendingQC;
import unistal.com.idpl_pcms.clearing_and_grading.ClearingandGradingQCActivity;
import unistal.com.idpl_pcms.hdpe.HDPE_QC;
import unistal.com.idpl_pcms.hydrotest.Hydrotest_QC;
import unistal.com.idpl_pcms.joint_coating.JointCoatingQC;
import unistal.com.idpl_pcms.levelling.LevelingQC;
import unistal.com.idpl_pcms.lowering.Lowering_QC;
import unistal.com.idpl_pcms.ofc.OFC_QC;
import unistal.com.idpl_pcms.qc.ROU_QC;
import unistal.com.idpl_pcms.qc.ROWay_QC;
import unistal.com.idpl_pcms.radio_graphy.RadiographyQC;
import unistal.com.idpl_pcms.stringing.StringingQCActivity;
import unistal.com.idpl_pcms.trenching.qc.TrenchingQC;
import unistal.com.idpl_pcms.ut.UT_QCActivity;
import unistal.com.idpl_pcms.welding.WeldingQCActivity;

public class QCHomeActivity extends AppCompatActivity {
@BindView(R.id.tvTitle)
    TextView tvTitle;
  /*  @BindView(R.id.relHydroTest)
    TextView tvHydrotest;*/
    @BindView(R.id.txtNotification)
    TextView tvNotific;
    @BindView(R.id.txtNotifyhand)
    TextView tvNotifyhand;
    @BindView(R.id.txtNotifycng)
    TextView tvNotifycng;
    @BindView(R.id.txtNotifytrench)
    TextView tvNotifytrench;
    @BindView(R.id.txtNotifystring)
    TextView tvNotifystring;
    @BindView(R.id.txtNotifybend)
    TextView tvNotifybend;
    @BindView(R.id.txtNotifyWeld)
    TextView tvNotifyweld;
    @BindView(R.id.txtNotifyWeldRepair)
    TextView tvNotifyweldrepair;
    @BindView(R.id.txtNotifyjoint)
    TextView tvNotifyjoint;
    @BindView(R.id.txtNotifyback)
    TextView tvNotifyback;
    @BindView(R.id.txtNotifylower)
    TextView tvNotifylower;
    @BindView(R.id.txtNotifylevel)
    TextView tvNotifylevel;
    @BindView(R.id.txtNotifyhydrotest)
    TextView tvNotifyhydro;
    @BindView(R.id.txtNotifyhdpe)
    TextView tvNotifyhdpe;

    @BindView(R.id.txtNotifylpt)
    TextView tvNotifylpt;

    @BindView(R.id.txtNotifyut)
    TextView tvNotifyut;
    @BindView(R.id.txtNotifyofc)
    TextView tvNotifyofc;
    @BindView(R.id.txtNotifyradio)
    TextView tvNotifyradio;
    @BindView(R.id.txtNotifysoil)
    TextView tvNotifysoil;
    @BindView(R.id.txtNotifyconcrete)
    TextView tvNotifyconcrete;

    private RightSideMenuHome rightSideMenu;
    Context context;
    ProgressLoading progressDialog;
    AppUpdateManager appUpdateManager;
    private int UPDATE_REQUEST_CODE = 909;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qchome);
        ButterKnife.bind(this);
        tvTitle.setText("idpl_pcms - "+SessionUtil.getGroupType(QCHomeActivity.this));
        inAppUpdate();
        context = this;
        progressDialog = new ProgressLoading(context);
      //  tvHydrotest.setVisibility(View.GONE);

    }
    @OnClick(R.id.txtMenu)
    void txtMenu() {
        rightSideMenu();
    }

    private void rightSideMenu() {
        if (rightSideMenu != null) {
            rightSideMenu.dismiss();
        }
        rightSideMenu = new RightSideMenuHome();
        rightSideMenu.show(getFragmentManager(), "");
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this)
                .setTitleText("Are you sure?")
                .setContentText("Want to quit")
                .setConfirmText("ok")
                .setCancelText("Cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })

                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                }).show();
    }
    @OnClick(R.id.relroute_survey)
    void row()
    {
        startActivity(new Intent(QCHomeActivity.this, ROWay_QC.class));
    }

    @OnClick(R.id.relroute_handover)
    void routeHandOver() {
            startActivity(new Intent(QCHomeActivity.this, ROU_QC.class));

    }
    @OnClick(R.id.relclear_greading)
    void clearing()
    {
        startActivity(new Intent(QCHomeActivity.this, ClearingandGradingQCActivity.class));
    }

    @OnClick(R.id.relStringing)
    void stringing() {
        startActivity(new Intent(QCHomeActivity.this, StringingQCActivity.class));

    }

    @OnClick(R.id.relWelding)
    void welding() {
        startActivity(new Intent(QCHomeActivity.this, WeldingQCActivity.class));

    }
    @OnClick(R.id.relWeldRepair)
    void weldRepair() {
        startActivity(new Intent(QCHomeActivity.this, WeldRepairQCActivity.class));

    }
    @OnClick(R.id.relLpt)
    void LPT() {

            startActivity(new Intent(QCHomeActivity.this, LPTQCActivity.class));

    }
    @OnClick(R.id.relHDPE)
    void relHDPE() {

            startActivity(new Intent(QCHomeActivity.this, HDPE_QC.class));

    }
    @OnClick(R.id.relOFCBlowing)
    void relOFCBlowing() {

            startActivity(new Intent(QCHomeActivity.this, OFC_QC.class));

    }
    @OnClick(R.id.reltrenching)
    void rel_trenching() {

            startActivity(new Intent(QCHomeActivity.this, TrenchingQC.class));

    }


    @OnClick(R.id.relbending)
    void rel_bending() {

            startActivity(new Intent(QCHomeActivity.this, BendingQC.class));

    }
    @OnClick(R.id.relradiography)
    void rel_Radio() {

        startActivity(new Intent(QCHomeActivity.this, RadiographyQC.class));

    }
    @OnClick(R.id.relUT)
    void rel_UT() {

        startActivity(new Intent(QCHomeActivity.this, UT_QCActivity.class));

    }
    @OnClick(R.id.relJointCoating)
    void rel_JointCoating() {

        startActivity(new Intent(QCHomeActivity.this, JointCoatingQC.class));

    }
    @OnClick(R.id.relbackfiling)
    void rel_backfilling() {

        startActivity(new Intent(QCHomeActivity.this, BackFillingQC.class));

    }
    @OnClick(R.id.relLowering)
    void rel_lowering() {

        startActivity(new Intent(QCHomeActivity.this, Lowering_QC.class));

    }
    @OnClick(R.id.relLeveling)
    void rel_leveling() {

        startActivity(new Intent(QCHomeActivity.this, LevelingQC.class));

    }
    @OnClick(R.id.relHydroTest)
    void rel_hydro() {

        startActivity(new Intent(QCHomeActivity.this, Hydrotest_QC.class));

    }
    @OnClick(R.id.relHDPE)
    void rel_hdpe() {

        startActivity(new Intent(QCHomeActivity.this, HDPE_QC.class));

    }

    @OnClick(R.id.relConcreteCoating)
    void rel_cc() {

        startActivity(new Intent(QCHomeActivity.this, ConcreteCoatingQCActivity.class));

    }
    @OnClick(R.id.relSoilResistivity)
    void rel_sr() {

        startActivity(new Intent(QCHomeActivity.this, SoilResistivityQCActivity.class));

    }
    @OnClick(R.id.relOFCBlowing)
    void rel_ofc() {

        startActivity(new Intent(QCHomeActivity.this, OFC_QC.class));

    }
    private void inAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode()+"");
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Request the update.

                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                AppUpdateType.IMMEDIATE,
                                // The current activity making the update request.
                                QCHomeActivity.this,
                                // Include a request code to later monitor this update request.
                                UPDATE_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException ignored) {

                    }
                }
            }
        });

        appUpdateManager.registerListener(installStateUpdatedListener);

    }
    //lambda operation used for below listener
    InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        } else
            Log.e("UPDATE", "Not downloaded yet");
    };


    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Update almost finished!",
                        Snackbar.LENGTH_INDEFINITE);
        //lambda operation used for below action
        snackbar.setAction("restart", view ->
                appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }
    @Override
    protected void onResume() {
        super.onResume();

        getReportStatus();
    }

    public void getReportStatus() {
        setSpinRouSurveyReportNo();
        setSpinRouHandOverReportNo();
        setSpinCNGReportNo();
        setSpintrenchReportNo();
        setSpinStringReportNo();
        setSpinBendReportNo();
        setSpinWeldReportNo();
        setSpinRadioReportNo();
        setSpinJointReportNo();
        setSpinbackReportNo();
        setSpinLowerReportNo();
        setSpinlevelReportNo();
        setSpinhydroReportNo();
        setSpinHDPEReportNo();setSpinLPTReportNo();setSpinUTReportNo();
        setSpinOFCReportNo();
        setSpinWeldRepairReportNo();
        setSpinSoilResitivityReportNo();setSpinConcreteCoatingReportNo();

    }
    public void setSpinRouSurveyReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
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
                                tvNotific.setText(arr.length() + "");
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
                params.put("type","roucontractor");
                params.put("SectionID",SessionUtil.getAssignedSection(context));

                return params;
            }

        };

        Log.e("Request", stringRequest.toString());
        queue.add(stringRequest);
    }
    public void setSpinRouHandOverReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "rouhandovercontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "rouhandoverpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "rouhandoverclient";
        }
        final String url = AppConstants.BASE_URL+ "?type="+type+"&SectionID="+ SessionUtil.getAssignedSection(context);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);
                            if (arr != null) {
                                tvNotifyhand.setText(arr.length() + "");
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

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("type","rouhandovercontractor");
                params.put("SectionID","1");
                //params.put("type","");
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setSpinCNGReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "clearingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "clearingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "clearingclient";
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
                                tvNotifycng.setText(arr.length() + "");
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
    public void setSpintrenchReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }

        String type = "trenchingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "trenchingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "trenchingclient";
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = AppConstants.BASE_URL+"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);
                            if (arr != null) {
                                tvNotifytrench.setText(arr.length() + "");
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

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setSpinStringReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "stringingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "stringingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "stringingclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);

                            if (arr != null) {
                                tvNotifystring.setText(arr.length() + "");
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

    public void setSpinRadioReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "radiographycontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "radiographypmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "radiographyclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);

                            if (arr != null) {
                                tvNotifyradio.setText(arr.length() + "");
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
    public void setSpinSoilResitivityReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "soilresistivitycontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "soilresistivitypmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "soilresistivityclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);

                            if (arr != null) {
                                tvNotifysoil.setText(arr.length() + "");
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
    public void setSpinConcreteCoatingReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "concretecontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "concretepmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "concreteclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);

                            if (arr != null) {
                                tvNotifyconcrete.setText(arr.length() + "");
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
    public void setSpinBendReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
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
                                tvNotifybend.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinWeldReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "weldingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "weldingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "weldingclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);
                            if (arr != null) {
                                tvNotifyweld.setText(arr.length() + "");
                            }
                        } catch (JSONException ignored) {

                        }
                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // error.getMessage();
                progressDialog.dismiss();

            }
        });
        // Add the request to the RequestQueue.
        Log.e("Request", stringRequest.toString());
        queue.add(stringRequest);
    }
    public void setSpinWeldRepairReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "weldrepaircontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "weldrepairpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "weldrepairclient";
        }
        final String url = AppConstants.BASE_URL +"?type="+type+"&SectionID="+SessionUtil.getAssignedSection(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","1 "+response);
                        try {
                            JSONArray arr = new JSONArray(response);
                            if (arr != null) {
                                tvNotifyweldrepair.setText(arr.length() + "");
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

            }
        });
        // Add the request to the RequestQueue.
        Log.e("Request", stringRequest.toString());
        queue.add(stringRequest);
    }
    public void setSpinJointReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "jointcoatingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "jointcoatingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "jointcoatingclient";
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
                                tvNotifyjoint.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinbackReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "backfillingcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "backfillingpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "backfillingclient";
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
                                tvNotifyback.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinLowerReportNo() {


        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "loweringcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "loweringpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "loweringclient";
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
                                tvNotifylower.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinlevelReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
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
                                tvNotifylevel.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinhydroReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "hydrotestcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "hydrotestpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "hydrotestclient";
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
                                tvNotifyhydro.setText(arr.length() + "");
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
        queue.add(stringRequest);
    }
    public void setSpinHDPEReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "hdpecontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "hdpepmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "hdpeclient";
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
                                tvNotifyhdpe.setText(arr.length() + "");
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
    }
    public void setSpinLPTReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "lptcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "lptpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "lptclient";
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
                                tvNotifylpt.setText(arr.length() + "");
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
    }
    public void setSpinUTReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "manualcontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "manualpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "manualclient";
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
                                tvNotifyut.setText(arr.length() + "");
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
    }








    public void setSpinOFCReportNo() {

        if(!progressDialog.isShowing()) {
            progressDialog.onShow();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        String type = "ofccontractor";
        if(SessionUtil.getGroupType(context).contains("QCPMC"))
        { type = "ofcpmc";}
        if(SessionUtil.getGroupType(context).contains("QCClient")) {
            type = "ofcclient";
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
                                tvNotifyofc.setText(arr.length() + "");
                            }
                        } catch (JSONException ignored) { }


                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                progressDialog.dismiss();
            //    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
