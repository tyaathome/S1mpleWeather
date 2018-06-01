package com.tyaathome.s1mpleweather.ui.adapter.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tyaathome.s1mpleweather.R;
import com.tyaathome.s1mpleweather.model.bean.city.CityBean;
import com.tyaathome.s1mpleweather.utils.tools.CityTools;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by tyaathome on 2018/06/01.
 */
public class PopularCityAdapter extends RecyclerView.Adapter<PopularCityAdapter.ViewHolder> {

    private Context mContext;
    // 已选择的城市列表
    private List<CityBean> selectedList;
    // 热门城市列表
    private List<CityBean> cityList;
    private PublishSubject<CityBean> onClickSubject = PublishSubject.create();

    public PopularCityAdapter(Context context) {
        mContext = context;
        cityList = getPopularCity(mContext);
        selectedList = CityTools.getInstance(mContext).getSelectedCityList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_city, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityBean cityBean = cityList.get(position);
        holder.tv.setText(cityBean.getCity());
        holder.tv.setOnClickListener(v -> onClickSubject.onNext(cityBean));
        boolean flag = false;
        for (CityBean selectedCity : selectedList) {
            if (selectedCity.getId().equals(cityBean.getId())) {
                flag = true;
            }
        }
        if(flag) {
            holder.tv.setBackgroundResource(R.drawable.btn_city_grid_item_sel);
        } else {
            holder.tv.setBackgroundResource(R.drawable.btn_city_grid_item_nor);
        }
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    /**
     * 获取热门城市
     * @return
     */
    private List<CityBean> getPopularCity(Context context) {
        String[] cityArray = context.getResources().getStringArray(R.array.popular_city);
        List<CityBean> cityBeanList = new ArrayList<>();
        for(String cityid : cityArray) {
            CityBean bean = CityTools.getInstance(context).getCityById(cityid);
            if(bean != null) {
                cityBeanList.add(bean);
            }
        }
        return cityBeanList;
    }

    public Observable<CityBean> getPositionClicks() {
        return onClickSubject.hide();
    }

    public void updateSelectedCityList(List<CityBean> cityList) {
        if(cityList != null) {
            selectedList = cityList;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
