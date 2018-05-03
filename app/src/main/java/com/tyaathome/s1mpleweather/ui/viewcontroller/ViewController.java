package com.tyaathome.s1mpleweather.ui.viewcontroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;


/**
 * 页面结构化控制器
 * Created by tyaathome on 2017/9/20.
 */

public abstract class ViewController<T> {
    private T mData;
    protected Context mContext;
    private View mView;
    private int resLayoutId;

    public ViewController(Context context) {
        this.mContext = context;
    }

    public static void inject(ViewController controller) {
        Class<? extends ViewController> target = controller.getClass();
        LayoutID layoutID = target.getAnnotation(LayoutID.class);
        if(layoutID != null) {
            controller.resLayoutId = layoutID.value();
        }
    }

    public void attachRoot(ViewGroup root) {
        initView(root);
        root.addView(mView);
        onCreatedView(mView);
    }

    public void attachRoot(ViewGroup root, int index) {
        initView(root);
        root.addView(mView, index);
        onCreatedView(mView);
    }

    public void attachRoot(ViewGroup root, int width, int height) {
        initView(root);
        root.addView(mView, width, height);
        onCreatedView(mView);
    }

    public void attachRoot(ViewGroup root, ViewGroup.LayoutParams params) {
        initView(root);
        root.addView(mView, params);
        onCreatedView(mView);
    }


    public void attachRoot(ViewGroup root, int index, ViewGroup.LayoutParams params) {
        initView(root);
        root.addView(mView, index, params);
        onCreatedView(mView);
    }

    private void initView(ViewGroup root) {
        ViewController.inject(this);
        if (resLayoutId <= 0) {
            throw new IllegalStateException("Please check your layout id in resLayoutId() method");
        }
        if (mView != null) {
            throw new IllegalStateException("a viewController can't attachRoot twice");
        }
        mView = LayoutInflater.from(mContext).inflate(resLayoutId, root, false);
        //inject((Activity) root.getContext(), root);
    }

    public void fillData(T data) {
        this.mData = data;
        if (mData != null) {
            onBindView(data);
        }
    }

    public void detachedRoot() {
        onDestoryView(mView);
    }

    /**
     * view has been created
     *
     * @param view real view
     */
    protected abstract void onCreatedView(View view);

    /**
     * bind data to view
     *
     * @param data data
     */
    protected abstract void onBindView(T data);

    /**
     * view has been Destory
     *
     * @param view
     */
    protected void onDestoryView(View view) {

    }

    public Context getContext() {
        return mContext;
    }

    public View getView() {
        return mView;
    }

    public T getData() {
        return mData;
    }

    public <R extends View> R findViewById(int id) {
        if(mView == null) {
            return null;
        }
        return mView.findViewById(id);
    }
}
