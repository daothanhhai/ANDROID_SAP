package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DoanhThuTheoKhachHangAdapter extends BaseAdapter {
    private activity_doanhthutheokhachhang context;
    private int layout;
    private List<DoanhThuTheoDoiTuong> doanhThutheokhachhangList;

    public DoanhThuTheoKhachHangAdapter(activity_doanhthutheokhachhang context, int layout, List<DoanhThuTheoDoiTuong> doanhThutheokhachhangList) {
        this.context = context;
        this.layout = layout;
        this.doanhThutheokhachhangList = doanhThutheokhachhangList;
    }

    @Override
    public int getCount() {
        return doanhThutheokhachhangList.size();
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
        TextView txtmadt;
        TextView txttendt;
        TextView txtdoanhthu;
        TextView txttyle;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);



            holder.txttendt=(TextView) view.findViewById(R.id.textViewdoanhthutendt);
            holder.txtdoanhthu=(TextView) view.findViewById(R.id.textViewdoanhthutheodt);
            holder.txttyle=(TextView) view.findViewById(R.id.textViewtyle);
            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DoanhThuTheoDoiTuong doanhThuthedoituong=doanhThutheokhachhangList.get(i);

        holder.txttendt.setText(doanhThuthedoituong.getTendt());

        holder.txtdoanhthu.setText(String.format("%,.0f", Float.valueOf(doanhThuthedoituong.getDoanhthu()) ));



        holder.txttyle.setText(String.format("%.2f", Float.valueOf(doanhThuthedoituong.getTyle()) ) +""+"%");




        return view;
    }
}
