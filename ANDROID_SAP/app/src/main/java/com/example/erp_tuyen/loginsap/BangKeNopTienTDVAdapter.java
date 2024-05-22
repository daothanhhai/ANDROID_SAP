package com.example.erp_tuyen.loginsap;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BangKeNopTienTDVAdapter extends BaseAdapter {
    private activity_bknoptientdv context;

    private int layout;
    private List<BangKeNopTienTDV> bangkenoptientdvList;

    public BangKeNopTienTDVAdapter(activity_bknoptientdv context, int layout, List<BangKeNopTienTDV> bangkenoptientdvList) {
        this.context = context;
        this.layout = layout;
        this.bangkenoptientdvList = bangkenoptientdvList;
    }

    @Override
    public int getCount() {
        return bangkenoptientdvList.size();
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
        TextView txtstt;
        TextView txtsoct;
        TextView txtngayct;
        TextView txttiennop;
        TextView txttiengoc;


    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);


            holder.txtstt=(TextView) view.findViewById(R.id.tvsttbktdv);
            holder.txtsoct=(TextView) view.findViewById(R.id.tvsoctbktdv);
            holder.txtngayct=(TextView) view.findViewById(R.id.tvngayctbktdv);
            holder.txttiennop=(TextView) view.findViewById(R.id.tvtnbktdv);
            holder.txttiengoc=(TextView) view.findViewById(R.id.tvtgbktdv);



            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final BangKeNopTienTDV bangkenoptienTDV=bangkenoptientdvList.get(i);

        holder.txtstt.setText(bangkenoptienTDV.getStt());
        holder.txtstt.setWidth(220);
        holder.txtstt.setHeight(80);



        holder.txtsoct.setText(bangkenoptienTDV.getSoct());
        holder.txtsoct.setWidth(10);
        holder.txtsoct.setHeight(80);
        holder.txtsoct.setGravity(Gravity.CENTER);


        holder.txtngayct.setText(bangkenoptienTDV.getNgayct());
        holder.txtngayct.setWidth(20);
        holder.txtngayct.setHeight(80);
        holder.txtngayct.setGravity(Gravity.CENTER);

        holder.txttiennop.setText(bangkenoptienTDV.getTiennop());
        holder.txttiennop.setWidth(50);
        holder.txttiennop.setHeight(60);

        holder.txttiengoc.setText(bangkenoptienTDV.getTiengoc());
        holder.txttiengoc.setWidth(50);
        holder.txttiengoc.setHeight(60);



        return view;
    }
}
