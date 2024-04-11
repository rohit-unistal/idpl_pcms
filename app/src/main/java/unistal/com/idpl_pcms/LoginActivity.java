package unistal.com.idpl_pcms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.enter_user_name)
    EditText userName;
    @BindView(R.id.enter_password)
    EditText passWord;
    @BindView(R.id.btn_log_in)
    Button btnLogIn;
    private ProgressLoading progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

    }
    private void init(){
        progressLoading = new ProgressLoading(this);
        SharedPreferences prefs = SessionUtil.getUserSessionPreferences(LoginActivity.this);
        /*if (prefs.contains(SessionUtil.USER_ID)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }*/
        if (!SessionUtil.getUserId(getApplicationContext()).equals("")){
            if (SessionUtil.getGroupType(getApplicationContext()).contains("QC")){
                startActivity(new Intent(LoginActivity.this, QCHomeActivity.class));
                finish();
            }else {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }

       /* btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });*/
    }
    @OnClick(R.id.btn_log_in)
    public void logIn() {
        if(DialogUtil.checkInternetConnection(this)) {
            progressLoading.onShow();
            RequestQueue queue = Volley.newRequestQueue(this);
            final String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            JSONObject obj = new JSONObject();
            try {
                obj.put("UserName", userName.getText().toString());
                obj.put("Password", passWord.getText().toString());
                obj.put("Device_id", deviceId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            final String url = AppConstants.APP_BASE_URL+AppConstants.USER_LOGIN+obj;
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            Log.e("Response ", response);
                            progressLoading.dismiss();
                            if (response.contains("Success")) {

                            try{
                            JSONObject data = new JSONObject(response);
                            JSONObject postObj = data.getJSONObject("posts");
                            JSONObject statusObj = postObj.getJSONObject("status");

                            String[] id = statusObj.getString("value").split(",");
                            StringBuilder section = new StringBuilder();
                            for (int i = 3; i < id.length; i++) {
                                if (i==3 ){
                                    section.append(id[i]);
                                }else{
                                    section.append(",").append(id[i]);
                                }

                            }
                            SessionUtil.saveAssignedSection(section.toString(), LoginActivity.this);
                            SessionUtil.saveUserId(id[0], LoginActivity.this);
                            SessionUtil.saveGroupType(id[2], LoginActivity.this);
                            if(SessionUtil.getGroupType(LoginActivity.this).contains("QC"))
                            { Intent intent = new Intent(LoginActivity.this, QCHomeActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            }
                            else if(SessionUtil.getGroupType(LoginActivity.this).contains("Engineer"))

                            {Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this, "Invalid User",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException ignored) {

                        }

                    } else{
                Toast.makeText(LoginActivity.this, "Please enter a valid username or password", Toast.LENGTH_SHORT).show();

            }}

                        }


                    , new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                    progressLoading.dismiss();
                }
            })/* {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("type", "login");
                    params.put("username", userName.getText().toString());
                    params.put("password", passWord.getText().toString());
                    final String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                    return params;
                }

            }*/;


            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }else{
            DialogUtil.showNoConnectionDialog(this);
        }
    }

}