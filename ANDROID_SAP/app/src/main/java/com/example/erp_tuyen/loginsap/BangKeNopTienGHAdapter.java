package com.example.erp_tuyen.loginsap;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BangKeNopTienGHAdapter extends BaseAdapter {
    private activity_bknoptiengh context;

    private int layout;
    private List<BangKeNopTienGH> bangkenoptienghList;

    public BangKeNopTienGHAdapter(activity_bknoptiengh context, int layout, List<BangKeNopTienGH> bangkenoptienghList) {
        this.context = context;
        this.layout = layout;
        this.bangkenoptienghList = bangkenoptienghList;
    }

    @Override
    public int getCount() {
        return bangkenoptienghList.size();

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


            holder.txtstt=(TextView) view.findViewById(R.id.tvsttbkgh);
            holder.txtsoct=(TextView) view.findViewById(R.id.tvsoctbkgh);
            holder.txtngayct=(TextView) view.findViewById(R.id.tvngayctbkgh);
            holder.txttiennop=(TextView) view.findViewById(R.id.tvtnbkgh);
            holder.txttiengoc=(TextView) view.findViewById(R.id.tvtgbkgh);



            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final BangKeNopTienGH bangkenoptienGH=bangkenoptienghList.get(i);

        holder.txtstt.setText(bangkenoptienGH.getStt());
        holder.txtstt.setWidth(220);
        holder.txtstt.setHeight(80);



        holder.txtsoct.setText(bangkenoptienGH.getSoct());
        holder.txtsoct.setWidth(10);
        holder.txtsoct.setHeight(80);
        holder.txtsoct.setGravity(Gravity.CENTER);


        holder.txtngayct.setText(bangkenoptienGH.getNgayct());
        holder.txtngayct.setWidth(20);
        holder.txtngayct.setHeight(80);
        holder.txtngayct.setGravity(Gravity.CENTER);

        holder.txttiennop.setText(bangkenoptienGH.getTiennop());
        holder.txttiennop.setWidth(50);
        holder.txttiennop.setHeight(60);

        holder.txttiengoc.setText(bangkenoptienGH.getTiengoc());
        holder.txttiengoc.setWidth(50);
        holder.txttiengoc.setHeight(60);



        return view;
    }
}
