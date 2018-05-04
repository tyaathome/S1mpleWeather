package com.tyaathome.s1mpleweather.ui.viewcontroller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.annonations.inject.LayoutID;
import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqBean;
import com.tyaathome.s1mpleweather.model.bean.main.sstq.SstqInfoBean;
import com.tyaathome.s1mpleweather.ui.viewcontroller.entity.MainEntity;

@LayoutID(R.layout.layout_controller_main)
public class MainViewController extends ViewController<MainEntity> {

    private Context context;
    private TextView tvCurrentTemp;


    public MainViewController(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreatedView(View view) {
        tvCurrentTemp = findViewById(R.id.tv_current_temp);
    }

    @Override
    protected void onBindView(MainEntity data) {
        if(data == null) return;
        SstqBean sstqBean = data.getSstqBean();
        if(sstqBean != null) {
            SstqInfoBean sstqInfoBean = sstqBean.getSstq();
            if(sstqInfoBean != null) {
                tvCurrentTemp.setText(sstqInfoBean.getCt() + "  " + sstqInfoBean.getCityName());
                Log.e("time____", String.valueOf(System.currentTimeMillis()));
            }
        }
    }

}
