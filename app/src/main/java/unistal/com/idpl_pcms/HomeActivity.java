package unistal.com.idpl_pcms;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
/*
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
*/
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import unistal.com.idpl_pcms.backfiling.BackfillingInputForm;
import unistal.com.idpl_pcms.bending.BendingFormActivity;
import unistal.com.idpl_pcms.clearing_and_grading.ClearingGrading2Activity;
import unistal.com.idpl_pcms.database.DataBaseHelper;
import unistal.com.idpl_pcms.hdpe.HDPE_InputForm;
import unistal.com.idpl_pcms.hydrotest.PrehydroTestInput;
import unistal.com.idpl_pcms.joint_coating.JoinCoatingInputForm;
import unistal.com.idpl_pcms.levelling.LevellingActivity;
import unistal.com.idpl_pcms.lowering.LoweringInputForm;
import unistal.com.idpl_pcms.ofc.OFCBlowingInputForm;
import unistal.com.idpl_pcms.radio_graphy.RadioGraphyInput;
import unistal.com.idpl_pcms.receiver.SensorRestarterBroadcastReceiver;
import unistal.com.idpl_pcms.rou.ROU_InputForm;
import unistal.com.idpl_pcms.service.NotifyService;
import unistal.com.idpl_pcms.stringing.Stringing2ndActivity;
import unistal.com.idpl_pcms.stringing.StringingActivity;
import unistal.com.idpl_pcms.trenching.TrenchingActivity;
import unistal.com.idpl_pcms.ut.UTActivity;
import unistal.com.idpl_pcms.welding.WeldingActivity;

/**
 * Created by Unistal on 07-08-2018.
 */
public class HomeActivity extends AppCompatActivity {
   private RightSideMenuHome rightSideMenu;
    private static final int REQUEST_PERMISSIONS = 100;
    private boolean isLocationPermission;
    private ProgressLoading loadingDialog;
    AppUpdateManager appUpdateManager;
    private int UPDATE_REQUEST_CODE = 909;
    Context context;
    DataBaseHelper dataBaseHelper;
  /*  Intent intent ;
    PendingIntent pendingIntent = null;
   */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context=this;
        ButterKnife.bind(this);
        loadingDialog = new ProgressLoading(this);
      /*  intent = new Intent(context, HomeActivity.class);
        pendingIntent = PendingIntent.getActivity(this,0,   intent    , PendingIntent.FLAG_IMMUTABLE);
      */  inAppUpdate();
        checkPermission();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("idpl_pcms_userSessionPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("InsertingStatus","true");
        editor.apply();

        dataBaseHelper = new DataBaseHelper(context);
       getWelderDetail();
        getWPSDetail();
        getAlignmentSheet();
       if ((pref.getString("InsertingStatus", "true").equals("true"))) {
             dataBaseHelper.insert("true");
         SharedPreferences.Editor ee = pref.edit();
            ee.putString("InsertingStatus","false");
            ee.apply();
        }


    }
    @OnClick(R.id.relUT)
    void ut() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, UTActivity.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relLevelling)
    void levelling() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, LevellingActivity.class));
        else
            checkPermission();
    }


    @OnClick(R.id.relJointCoatBlasting)
    void jointCoatingBlasting() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, JoinCoatingInputForm.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relLpt)
    void LPT() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, LPTActivity.class));
        else
            checkPermission();
    }
    /*@OnClick(R.id.relCutPipe)
    void cutPipe() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, CutPipeActivity.class));
        else
            checkPermission();
    }

    @OnClick(R.id.reltieinWelding)
    void tieInWelding() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, TieInWeldingActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relPadding)
    void padding() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, PaddingActivity.class));
        else
            checkPermission();
    }*/

    @OnClick(R.id.relroute_survey)
    void routeSurvey() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, RowFormActivity2.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relroute_handover)
    void routeHandOver() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, ROU_InputForm.class));
        else
            checkPermission();

    }

    @OnClick(R.id.relclear_greading)
    void clearGrading() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, ClearingGrading2Activity.class));
        else
            checkPermission();
    }
  /* @OnClick(R.id.relBedding)
    void bedding() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, BeddingActivity.class));
        else
            checkPermission();
    }*/




    @OnClick(R.id.reltrenching)
    void rel_trenching() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, TrenchingActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relradiography)
    void relRadioGraphy() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, RadioGraphyInput.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relHydro)
    void relHydro() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, PrehydroTestInput.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relStringing)
    void relStringing() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, StringingActivity.class));
        else
            checkPermission();

    }

    @OnClick(R.id.relbending)
    void rel_bending() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, BendingFormActivity.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relbackfiling)
    void rel_backfilling() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, BackfillingInputForm.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relLowering)
    void relLoaring() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, LoweringInputForm.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relHDPE)
    void relHDPE() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, HDPE_InputForm.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relOFCBlowing)
    void relOFCBlowing() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, OFCBlowingInputForm.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relWelding)
    void relWelding() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, WeldingActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relWeldRepair)
    void relWeldRepair() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, WeldRepairActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relHindrance)
    void relHindrance() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, HindranceListActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relSoilResistivity)
    void relSoilResistivity() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, SoilResistivityActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relConcreteCoating)
    void relConcreteCoating() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, ConcreteCoatingActivity.class));
        else
            checkPermission();
    }
/*
    @OnClick(R.id.rel_levelling)
    void rel_levelling() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, LevellingActivity.class));
        else
            checkPermission();
    }
*//*


    @OnClick(R.id.relWeldingQC)
    void relWeldingQC() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this, WeldRepairActivity.class));
        else
            checkPermission();
    }

    @OnClick(R.id.relJointCoatRepair)
    void relJointCoatRepair() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this,JointCoatingRepair.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relDamageInspection)
    void relDamageInspection() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this,DamageInspectionActivity.class));
        else
            checkPermission();
    }
    @OnClick(R.id.relFocConduitLaying)
    void relFOCConduitLaying() {
        if (isLocationPermission)
            startActivity(new Intent(HomeActivity.this,FOCConduitLayingActivity.class));
        else
            checkPermission();
    }
*//*
    *//*

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
    }*/
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermission = true;

                } else {
                    showSettingsAlert();
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    private void checkPermission() {
        if (!hasGPSDevice(HomeActivity.this)) {
            Toast.makeText(HomeActivity.this, "Gps not Supported", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permissionCheck = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermission = true;
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
                }
            } else {
                isLocationPermission = true;
            }
        }
    }

    //TODO Step 1
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers != null && providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                HomeActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("GPS location not Captured");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        HomeActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(HomeActivity.this, "GPS location not Captured", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
    }

    private void setupAlarm() {
        Intent alarm = new Intent(this.context, SensorRestarterBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 2000, pendingIntent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotifyService notifyService=new NotifyService();
        if (!isMyServiceRunning(notifyService.getClass())){
            setupAlarm();
        }
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        NotifyService notifyService=new NotifyService();
        if (!isMyServiceRunning(notifyService.getClass())){
            setupAlarm();
        }
    }*/

    private void getWPSDetail() {
        // if (DialogUtil.checkInternetConnection(this)) {
       if(!loadingDialog.isShowing()) {
           loadingDialog.onShow();
       }
           /* final ProgressDialog progressDialog = ProgressDialog.show(HomeActivity.this, "",
                    "Loading. Please wait.mbpl2_sec7_pcms..", false);*/
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = AppConstants.APP_BASE_URL + "API/WPS_typeAPI.php?" + "SectionID=" + SessionUtil.getAssignedSection(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // progressDialog.dismiss();
                      //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {

                            JSONArray joints = new JSONArray(response);

                            dataBaseHelper.deleteAll("tbl_welding_wps_num");
                            for (int i = 0; i < joints.length(); i++) {

                                JSONObject c = joints.getJSONObject(i);
                                ContentValues namelist = new ContentValues();
                                namelist.put("WpsID", c.getString("WpsID"));

                                namelist.put("WPSName", c.getString("WPSName"));
                            //    Toast.makeText(getApplicationContext(), "WPS Number: "+ c.getString("WPSName"), Toast.LENGTH_LONG).show();

                                dataBaseHelper.insert("tbl_welding_wps_num", namelist);

                            }

                            // dataBaseHelper.close();
                        } catch (Exception ignored) {
                //            Toast.makeText(getApplicationContext(), "Exception WPS Number: " + ignored, Toast.LENGTH_LONG).show();
                        }/*finally {

                                dataBaseHelper.close();
                            }*/


               //         Toast.makeText(getApplicationContext(), "WPS Number", Toast.LENGTH_LONG).show();
                         loadingDialog.dismiss();
                        //progressDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                  loadingDialog.dismiss();
               // progressDialog.dismiss();
               // Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(HomeActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/

    }
    private void getWelderDetail() {
        // if (DialogUtil.checkInternetConnection(this)) {
        //progressDialog.show();
        if(!loadingDialog.isShowing()) {
            loadingDialog.onShow();
        }
           /* final ProgressDialog progressDialog = ProgressDialog.show(WeldingActivity.this, "",
                    "Loading. Please wait.mbpl2_sec7_pcms..", false);*/
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = AppConstants.APP_BASE_URL + "API/welderapi.php?" + "SectionID=" + SessionUtil.getAssignedSection(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                     //   progressDialog.dismiss();
                      //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {

                            JSONArray joints = new JSONArray(response);

                            dataBaseHelper.deleteAll("tbl_welding_welder_num");
                            for (int i = 0; i < joints.length(); i++) {

                                JSONObject c = joints.getJSONObject(i);
                                ContentValues namelist = new ContentValues();
                                namelist.put("WelderID", c.getString("WelderID"));

                                namelist.put("WelderName", c.getString("WelderName"));
                         //       Toast.makeText(getApplicationContext(), "Welder Number: "+ c.getString("WelderName"), Toast.LENGTH_LONG).show();

                                dataBaseHelper.insert("tbl_welding_welder_num", namelist);

                            }

                            // dataBaseHelper.close();
                        } catch (Exception ignored) {
                          //  Toast.makeText(getApplicationContext(), "Exception Welder Number: " + ignored, Toast.LENGTH_LONG).show();
                        }/*finally {

                                dataBaseHelper.close();
                            }*/


                      //  Toast.makeText(getApplicationContext(), "Welder Number", Toast.LENGTH_LONG).show();
                         loadingDialog.dismiss();
                        //progressDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                  loadingDialog.dismiss();
                //progressDialog.dismiss();
             //   Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
             //   Toast.makeText(HomeActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
       /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/

    }
    private void getAlignmentSheet() {
        // if (DialogUtil.checkInternetConnection(this)) {
        if(!loadingDialog.isShowing()) {
            loadingDialog.onShow();
        }
           /* final ProgressDialog progressDialog = ProgressDialog.show(WeldingActivity.this, "",
                    "Loading. Please wait...", false);*/
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = AppConstants.BASE_URL + "?type=align&" + "SectionID=" + SessionUtil.getAssignedSection(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // progressDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Log.e("Response",response);
                        try {

                            JSONArray joints = new JSONArray(response);

                            dataBaseHelper.deleteAll("tbl_alignment_sheet");
                            for (int i = 0; i < joints.length(); i++) {

                                JSONObject c = joints.getJSONObject(i);
                                ContentValues namelist = new ContentValues();
                                namelist.put("AlignmentID", c.getString("AlignmentID"));

                                namelist.put("AlignmentName", c.getString("AlignmentName"));
                                //    Toast.makeText(getApplicationContext(), "WPS Number: "+ c.getString("WPSName"), Toast.LENGTH_LONG).show();
                                Log.e("AlignmentName",c.getString("AlignmentName"));
                                dataBaseHelper.insert("tbl_alignment_sheet", namelist);

                            }

                            // dataBaseHelper.close();
                        } catch (Exception ignored) {
                            //            Toast.makeText(getApplicationContext(), "Exception WPS Number: " + ignored, Toast.LENGTH_LONG).show();
                        }/*finally {

                                dataBaseHelper.close();
                            }*/


                        //         Toast.makeText(getApplicationContext(), "WPS Number", Toast.LENGTH_LONG).show();
                        loadingDialog.dismiss();
                        //progressDialog.dismiss();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                loadingDialog.dismiss();
                // progressDialog.dismiss();
                // Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(HomeActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        /*} else {
            DialogUtil.showNoConnectionDialog(this);
        }*/

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
                                HomeActivity.this,
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


}
