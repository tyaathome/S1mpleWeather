package com.tyaathome.s1mpleweather.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.mvp.base.BasePresenter;
import com.tyaathome.s1mpleweather.mvp.base.BaseView;
import com.tyaathome.s1mpleweather.utils.manager.InjectManager;

public abstract class BaseFragment extends Fragment implements BaseView {

    protected Context baseContext;
    protected BasePresenter mPresenter;

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
        return InjectManager.inject(this, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = onLoadPresenter();
        if(getPresenter() != null) {
            getPresenter().attachView(this);
            initViews(savedInstanceState);
            initEventAndData();
            getPresenter().start();
        }
    }

    public BasePresenter getPresenter() {
        return mPresenter;
    }

    protected abstract BasePresenter onLoadPresenter();

    public <T extends android.view.View> T findViewById(int id) {
        if (getView() == null)
            throw new NullPointerException();
        return getView().findViewById(id);
    }

}
