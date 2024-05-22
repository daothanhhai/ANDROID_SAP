package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class DsBkNopTienGHAdapter extends BaseAdapter {
    private activity_dsbknoptiengh context;
    private int layout;
    private List<DsBKNopTienGH> dsbknoptienList;

    public DsBkNopTienGHAdapter(activity_dsbknoptiengh context, int layout, List<DsBKNopTienGH> dsbknoptienList) {
        this.context = context;
        this.layout = layout;
        this.dsbknoptienList = dsbknoptienList;
    }

    @Override
    public int getCount() {
        return dsbknoptienList.size();
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
        TextView txtngaybk;
        TextView txtnguoilap;
        TextView txttongtien;
        Button   btupdatebkntgh;
        Button   btxoabkntgh;


    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);


            holder.txtstt=(TextView) view.findViewById(R.id.tvsttbkntgh);

            holder.txtngaybk=(TextView) view.findViewById(R.id.tvngaybkntgh);

            holder.txtnguoilap=(TextView) view.findViewById(R.id.tvnlbkntgh);

            holder.txttongtien=(TextView) view.findViewById(R.id.tvtienbkntgh);

            //holder.btupdatebkntgh=(Button) view.findViewById(R.id.btcapnhatbkntgh);
            //holder.btxoabkntgh=(Button) view.findViewById(R.id.btxoabkntgh);



            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DsBKNopTienGH dsbknoptiengh=dsbknoptienList.get(i);

        holder.txtstt.setText(dsbknoptiengh.getStt());
        holder.txtstt.setWidth(10);
        holder.txtstt.setHeight(70);
        holder.txtngaybk.setText(dsbknoptiengh.getNgaybk());

        //holder.txtdoituong.setWidth(170);
        //holder.txtdoituong.setHeight(70);

        holder.txtnguoilap.setText(dsbknoptiengh.getNguoilap());
        holder.txtnguoilap.setWidth(100);
        holder.txtnguoilap.setHeight(70);


        holder.txttongtien.setText(String.format("%,.0f", Float.valueOf(dsbknoptiengh.getTongtien()) ));


        holder.txttongtien.setWidth(80);
        holder.txttongtien.setHeight(70);





        return view;




    }


}
