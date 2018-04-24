package com.tyaathome.s1mpleweather.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tyaathome on 2017/9/19.
 */

public abstract class AAAFragment extends Fragment {

    protected Context baseContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseContext = context;
    }

    @Override
    public Context getContext() {
        return super.getContext() != null ? super.getContext() : baseContext;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public <T extends android.view.View> T findViewById(int id) {
        return getView().findViewById(id);
    }


    public void setupUI(final View view) {
        if(view == null) return;
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(view);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getContext().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    // 等待对话框
    private ProgressDialog mProgress;

    /**
     * 显示等待对话框
     */
    public void showProgressDialog(Activity activity, String keyWord) {
        if (mProgress == null) {
            mProgress = new ProgressDialog(activity);
            mProgress.setCancelable(true);
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.setOnCancelListener(mProgressOnCancel);
        }
        if (mProgress.isShowing()) {
            mProgress.setMessage(keyWord);
        } else {
            mProgress.show();
            mProgress.setMessage(keyWord);
        }
    }

    public void showProgressDialog(Activity activity) {
    }

    /**
     * 取消等待对话框
     */
    public void dismissProgressDialog() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    /**
     * 进度框OnCancel
     */
    private DialogInterface.OnCancelListener mProgressOnCancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            mProgress.dismiss();
            //activity.this.finish();
        }
    };

    /* 判断是否是有网络*/
    public boolean isOpenNet(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null;
    }

    private Toast toast;

    public void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(getContext().getApplicationContext(), str,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    /* 判断是否是处于wifi状态下*/
    protected boolean isWiFiNewWord(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

}
