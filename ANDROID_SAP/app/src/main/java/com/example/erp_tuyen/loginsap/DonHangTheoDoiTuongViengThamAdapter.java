package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DonHangTheoDoiTuongViengThamAdapter extends BaseAdapter {
    private activity_donhangtheodoituongviengtham context;
    private int layout;
    private List<DonHangTheoDoiTuongViengTham> donhangtheodoituongviengthamList;

    public DonHangTheoDoiTuongViengThamAdapter(activity_donhangtheodoituongviengtham context, int layout, List<DonHangTheoDoiTuongViengTham> donhangtheodoituongviengthamList) {
        this.context = context;
        this.layout = layout;
        this.donhangtheodoituongviengthamList = donhangtheodoituongviengthamList;
    }

    @Override
    public int getCount() {
        return donhangtheodoituongviengthamList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class Viewholder{
        TextView txtsoct;
        TextView txtngayct;
        TextView txttinhtrang;

        TextView txttongtien;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txtsoct=(TextView) view.findViewById(R.id.tvsoctdtvt);
            holder.txtngayct=(TextView) view.findViewById(R.id.tvngayctdtvt);
            holder.txttongtien=(TextView) view.findViewById(R.id.tvtongtiendtvt);
            holder.txttinhtrang=(TextView) view.findViewById(R.id.tvtinhtrangdtvt);



            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DonHangTheoDoiTuongViengTham donhangtheodoidoituongviengtham=donhangtheodoituongviengthamList.get(i);



        holder.txtsoct.setText(donhangtheodoidoituongviengtham.getSoct());
        //holder.txtsoct.setWidth(90);
        holder.txtsoct.setHeight(80);



        holder.txtngayct.setText(donhangtheodoidoituongviengtham.getNgayct());
        //holder.txtngayct.setWidth(90);
        holder.txtngayct.setHeight(80);



        holder.txttongtien.setText(String.format("%,.0f", Float.valueOf(donhangtheodoidoituongviengtham.getTongtien()) ));

        //holder.txttongtien.setWidth(90);
        holder.txttongtien.setHeight(80);

        holder.txttinhtrang.setText(donhangtheodoidoituongviengtham.getTinhtrang());
        //holder.txttinhtrang.setWidth(90);
        holder.txttinhtrang.setHeight(80);







        return view;
    }
}
