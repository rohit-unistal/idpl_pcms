package unistal.com.idpl_pcms;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
/*import android.support.annotation.NonNull;
import android.support.annotation.Nullable;*/
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;


@SuppressLint("InflateParams")
public class RightSideMenuHome extends DialogFragment implements OnClickListener {
    private View view;

    @Override
    public void onStart() {
        super.onStart();
        // set the animations to use on showing and hiding the dialog
        getDialog().getWindow().setWindowAnimations(R.style.RightAnimation);
        // alternative way of doing it
        // getDialog().getWindow().getAttributes().
        // windowAnimations = R.style.dialog_animation_fade;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setGravity(Gravity.RIGHT | Gravity.TOP);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        view = inflater.inflate(R.layout.rightmenuslide, null);
        findViewById();
        return view;
    }

    private void findViewById() {
        LinearLayout userHome = view.findViewById(R.id.userHome);
        LinearLayout linLogOut = view.findViewById(R.id.linLogOut);

        linLogOut.setOnClickListener(this);
        userHome.setOnClickListener(this);
        userHome.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.userHome:
                getActivity().finish();
                break;

            case R.id.linLogOut:
                logOutDialog();
                break;

            default:
                break;
        }
    }

    private void logOutDialog() {
        new SweetAlertDialog(getActivity())
                .setTitleText("Are you sure?")
                .setContentText("Want to Logout")
                .setConfirmText("ok")
                .setCancelText("Cancel")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        SessionUtil.removeUserDetails(getActivity());



                        Intent logout = new Intent(getActivity(), LoginActivity.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(logout);
                        getActivity().finish();
                    }
                })

                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                    }
                }).show();
    }
}
