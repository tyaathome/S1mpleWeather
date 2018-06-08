package com.tyaathome.s1mpleweather.ui.adapter.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyaathome on 2018/06/07.
 */
public class CityResultAdapter extends RecyclerView.Adapter<CityResultAdapter.ViewHolder> {

    private Context context;
    private String key;
    private List<CityBean> cityList;

    public CityResultAdapter(String key, List<CityBean> cityList) {
        this.key = key;
        this.cityList = cityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityBean cityBean = cityList.get(position);
        String str = cityBean.getCounty() + ", " + cityBean.getCity() + ", " + cityBean.getProvince();
        SpannableString spannableString = new SpannableString(str);
        List<Integer> indexs = getKeyIndexs(str, key);
        holder.tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    /**
     * 获取关键字位置
     * @return
     */
    private List<Integer> getKeyIndexs(String str, String key) {
        List<Integer> indexs = new ArrayList<>();
        for(int i = -1; (i = str.indexOf(key, i+1)) != -1; i++) {
            indexs.add(i);
        }
        return indexs;
    }

    public void updateData(String key, List<CityBean> cityList) {
        this.key = key;
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }

}
