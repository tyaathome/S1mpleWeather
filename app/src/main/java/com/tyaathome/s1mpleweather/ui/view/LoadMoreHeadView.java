package com.tyaathome.s1mpleweather.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by tyaathome on 2017/10/13.
 */

public class LoadMoreHeadView extends FrameLayout implements PtrUIHandler {

    private Context context;
    private TextView tvTop, tvBottom;

    public static final String DROP_DOWN_TO_REFRESH = "下拉刷新";
    public static final String RELEASE_TO_REFRESH = "松开刷新";
    public static final String REFRESHING = "正在刷新";
    public static final String REFRESH_COMPELETE = "刷新完成";

    public LoadMoreHeadView(@NonNull Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public LoadMoreHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public LoadMoreHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_head_view, this);
        tvTop = findViewById(R.id.top);
        tvBottom = findViewById(R.id.bottom);
        tvTop.setText(DROP_DOWN_TO_REFRESH);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        tvTop.setText(DROP_DOWN_TO_REFRESH);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tvTop.setText(DROP_DOWN_TO_REFRESH);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        tvTop.setText(REFRESHING);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        tvTop.setText(REFRESH_COMPELETE);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvTop.setText(DROP_DOWN_TO_REFRESH);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvTop.setText(RELEASE_TO_REFRESH);
            }
        }
    }
}
