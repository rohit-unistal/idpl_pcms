package unistal.com.idpl_pcms.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
//import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unistal.com.idpl_pcms.AppConstants;
import unistal.com.idpl_pcms.database.DataBaseHelper;


public class NotifyService extends Service{

    boolean isRuning=false;
    DataBaseHelper DbHelper;

    ArrayList<HashMap<String,String>> rouArrayList = null;
    ArrayList<HashMap<String,String>> rowArrayList= null;
    ArrayList<HashMap<String,String>> newrowArrayList= null;
    ArrayList<HashMap<String,String>> cngArrayList= null;
    ArrayList<HashMap<String,String>> stringingArrList= null;
    ArrayList<HashMap<String,String>> bendindArrayList= null;
    ArrayList<HashMap<String,String>> hdpeArrList= null;
    ArrayList<HashMap<String,String>> weldingArrList= null;
    ArrayList<HashMap<String,String>> trenchingArrList= null;
    ArrayList<HashMap<String,String>> ofcLayingArrList= null;
    ArrayList<HashMap<String,String>> radiographyArrList= null;
    ArrayList<HashMap<String,String>> utArrList= null;

    ArrayList<HashMap<String,String>> backFillingArrList= null;

    ArrayList<HashMap<String,String>> dryingArrList= null;
    ArrayList<HashMap<String,String>> restorationArrList= null;

    ArrayList<HashMap<String,String>> loweringArrList= null;
    ArrayList<HashMap<String,String>> levellingArrList= null;
    ArrayList<HashMap<String,String>> jointCoatingArrList= null;
    ArrayList<HashMap<String,String>> hydrotestArrList= null;


    Context context;
    String TableName="";
    private static int delay=3000;
    public NotifyService() {
        super();
        //context = applicationContext;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isRuning){


            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    String CHANNEL_ID = "my_channel_01";
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

                    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setContentTitle("")
                            .setContentText("").build();

                    startForeground(1, notification);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(battery_receiver, filter);


     }
    }

    private BroadcastReceiver battery_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            DbHelper=new DataBaseHelper(context);
            if(!isRuning )
            {
                isRuning=true;
                DbHelper.update("true");
            }


            try{
                rouArrayList=DbHelper.getRouData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                rowArrayList=DbHelper.getRowData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                newrowArrayList=DbHelper.getnewRowData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                cngArrayList=DbHelper.getCNGData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                stringingArrList=DbHelper.getStringingData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                hdpeArrList=DbHelper.getHDPEData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                weldingArrList=DbHelper.getWeldingData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                trenchingArrList=DbHelper.getTrenchingData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                ofcLayingArrList=DbHelper.getOFCData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                bendindArrayList= DbHelper.getBendingData();
            }catch (Exception e){
              e.printStackTrace();
            }
            try{
                radiographyArrList=DbHelper.getRadiographyData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                utArrList= DbHelper.getUTData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                dryingArrList=DbHelper.getDryingData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                restorationArrList=DbHelper.getRestorationData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                backFillingArrList=DbHelper.getBackfillingData();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                loweringArrList=DbHelper.getLoweringData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                levellingArrList=DbHelper.getLevellingData();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                jointCoatingArrList=DbHelper.getJointCoatingData();
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                hydrotestArrList=DbHelper.getHydroTestingData();
            }catch (Exception e){
                e.printStackTrace();
            }

            if (isOnline()){
                boolean running=DbHelper.getData();
                Log.e("ServiceRunning",String.valueOf(running)+"\n"+cngArrayList.toString());
                if (running){

                    if (rouArrayList !=null && rouArrayList.size()>0){
                        TableName="rou_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<rouArrayList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) == rouArrayList.size()){
                                        DbHelper.update("true");
                                    }
                                    //boolean s=DbHelper.DeleteRow(rouArrayList.get(finalI).get("COLUMN_ID"),TableName);
                                    //Log.e("Sevice", s+" = "+rouArrayList.get(finalI).toString());
                                    InsertData(context,rouArrayList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (rowArrayList!= null && rowArrayList.size()>0){
                        TableName="ROW_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<rowArrayList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==rowArrayList.size()){
                                        DbHelper.update("true");
                                    }
                                    //boolean s=DbHelper.DeleteRow(rowArrayList.get(finalI).get("COLUMN_ID"),TableName);
                                    //Log.e("Sevice", s+" = "+rowArrayList.get(finalI).toString());
                                    InsertData(context,rowArrayList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (newrowArrayList!= null && newrowArrayList.size()>0){
                        TableName="NEW_ROW_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<newrowArrayList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==newrowArrayList.size()){
                                        DbHelper.update("true");
                                    }
                                    //boolean s=DbHelper.DeleteRow(rowArrayList.get(finalI).get("COLUMN_ID"),TableName);
                                    //Log.e("Sevice", s+" = "+rowArrayList.get(finalI).toString());
                                    InsertData(context,newrowArrayList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (cngArrayList!= null && cngArrayList.size()>0){
                        TableName="CNG_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<cngArrayList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if ((finalI+1) ==cngArrayList.size()) {
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,cngArrayList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (stringingArrList != null && stringingArrList.size()>0){
                        TableName="STRINGING_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<stringingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==stringingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    //boolean s=DbHelper.DeleteRow(rowArrayList.get(finalI).get("COLUMN_ID"),TableName);
                                    //Log.e("Sevice", s+" = "+rowArrayList.get(finalI).toString());
                                    InsertData(context,stringingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (weldingArrList != null && weldingArrList.size()>0){
                        TableName="WELDING_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<weldingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==weldingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,weldingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (bendindArrayList != null && bendindArrayList.size()>0){
                        TableName="bending_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<bendindArrayList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==bendindArrayList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,bendindArrayList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (hdpeArrList !=null && hdpeArrList.size()>0){
                        TableName="HDPE_TABLE";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<hdpeArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==hdpeArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,hdpeArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (trenchingArrList!=null && trenchingArrList.size()>0){
                        TableName="trenching_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<trenchingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==trenchingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,trenchingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (ofcLayingArrList!=null && ofcLayingArrList.size()>0){
                        TableName="ofcblowing_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<ofcLayingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==ofcLayingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,ofcLayingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (radiographyArrList!=null && radiographyArrList.size()>0){
                        TableName="radio_graphy_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<radiographyArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==radiographyArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,radiographyArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (utArrList!=null && utArrList.size()>0){
                        TableName="ut_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<utArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==utArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,utArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (backFillingArrList!=null && backFillingArrList.size()>0){
                        TableName="backfilling_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<backFillingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==backFillingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,backFillingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (dryingArrList!=null && dryingArrList.size()>0){
                        TableName="drying_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<dryingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==dryingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,dryingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (restorationArrList!=null && restorationArrList.size()>0){
                        TableName="restoration_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<restorationArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==restorationArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,restorationArrList.get(finalI));

                                }
                            },delay);
                        }
                    }

                    else if (loweringArrList!=null && loweringArrList.size()>0){
                        TableName="Lowering_Table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<loweringArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==loweringArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,loweringArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (levellingArrList!=null && levellingArrList.size()>0){
                        TableName="levelling";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<levellingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==levellingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,levellingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }
                    else if (jointCoatingArrList!=null && jointCoatingArrList.size()>0){
                        TableName="joint_coating";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<jointCoatingArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==jointCoatingArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,jointCoatingArrList.get(finalI));

                                }
                            },delay);
                        }
                    }

                    else if (hydrotestArrList!=null && hydrotestArrList.size()>0){
                        TableName="hydro_testing_table";
                        DbHelper.update("false");
                        Handler handler=new Handler();
                        int i;
                        for (i=0;i<hydrotestArrList.size();i++){
                            final int finalI = i;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if ((finalI+1) ==hydrotestArrList.size()){
                                        DbHelper.update("true");
                                    }
                                    InsertData(context,hydrotestArrList.get(finalI));

                                }
                            },delay);
                        }
                    }




                }
            }

        }
    };

    public void InsertData(final Context context, final Map<String, String> params) {


        Log.e("Response", "InsertData Method Call = "+params.get("COLUMN_ID")+"\t"+TableName);
        RequestQueue requestQueue = null;
        try{
            requestQueue = Volley.newRequestQueue(context);
        }catch (Exception e){
            Log.e("Response", "InsertData Method Exception = "+e.toString());
        }
        StringRequest featureRequest = new StringRequest(Request.Method.POST,
                AppConstants.BASE_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String stringResponse) {
                        try {
                            //if (stringResponse.equalsIgnoreCase("0")) {
                            DbHelper.DeleteRow(params.get("COLUMN_ID"),TableName);
                            Log.e("Response", stringResponse);
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response","API_ERROR : "+ error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(featureRequest);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stoptimertask();
        unregisterReceiver(battery_receiver);
    }


}