package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DonDatHangAdapter extends BaseAdapter {
    private activity_dondathang context;

    private int layout;
    private List<DonDatHang> dondathangList;

    public DonDatHangAdapter(activity_dondathang context, int layout, List<DonDatHang> dondathangList) {
        this.context = context;
        this.layout = layout;
        this.dondathangList = dondathangList;
    }

    @Override
    public int getCount() {
        return dondathangList.size();
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

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txtmadt=(TextView) view.findViewById(R.id.textViewmadt);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DonDatHang donDatHang=dondathangList.get(i);

        holder.txtmadt.setText(donDatHang.getMadt());

        return view;

    }
}
