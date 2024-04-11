package unistal.com.idpl_pcms;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProgressLoading {
    private final Context context;
    private SweetAlertDialog loadingDialog;

    public ProgressLoading(Context context) {
        this.context = context;
        this.loadingDialog = new SweetAlertDialog(context);
    }

    public void onShow() {
        loadingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingDialog.setTitleText("Loading");
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    public void dismiss() {
        loadingDialog.dismiss();
    }

    public boolean isShowing() {
        return loadingDialog.isShowing();
    }
}