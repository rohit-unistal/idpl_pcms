package unistal.com.idpl_pcms;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {

    private static final String USER_SESSION_PREF = "idpl_pcms_userSessionPref1";
    public static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";
    private static final String GROUP_TYPE = "groupType";
    private static final String USER_ASSIGNED_SECTIONS = "sections";
    private static final String DEVICE_ID = "deviceId";


    public static SharedPreferences getUserSessionPreferences(Context context) {
        return context.getSharedPreferences(USER_SESSION_PREF, Context.MODE_PRIVATE);
    }

    public static void removeUserDetails(Context context) {
        final SharedPreferences prefs = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static void saveUserId(String userId, Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        return preferences.getString(USER_ID, "");
    }

    public static void saveUserName(String userName, Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        return preferences.getString(USER_NAME, "");
    }

    public static void saveGroupType(String groupType, Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GROUP_TYPE, groupType);
        editor.commit();
    }

    public static String getGroupType(Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        return preferences.getString(GROUP_TYPE, "");
    }

    public static void saveAssignedSection(String assignedSection, Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ASSIGNED_SECTIONS, assignedSection);
        editor.commit();
    }

    public static String getAssignedSection(Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        return preferences.getString(USER_ASSIGNED_SECTIONS, "");
    }


    public static void saveDeviceId(String deviceId, Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DEVICE_ID, deviceId);
        editor.commit();
    }

    public static String getDeviceId(Context context) {
        SharedPreferences preferences = getUserSessionPreferences(context);
        return preferences.getString(DEVICE_ID, "");
    }
}
