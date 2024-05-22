package com.example.erp_tuyen.loginsap;

import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DoiTacAdapter extends BaseAdapter {
    private activity_doitac context;
    private int layout;
    private ArrayList<DoiTac> doitacList;

    public DoiTacAdapter(activity_doitac context, int layout, ArrayList<DoiTac> doitacList) {
        this.context = context;
        this.layout = layout;
        this.doitacList = doitacList;
    }

    @Override
    public int getCount() {
        return doitacList.size();
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
        TextView txttenkhdt;
        TextView txttendtkh;
        TextView txtdiachidt;
        TextView txtsinhnhatdt;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttenkhdt=(TextView) view.findViewById(R.id.txttenkhdt);
            holder.txttendtkh=(TextView) view.findViewById(R.id.txttendtkh);
            holder.txtdiachidt=(TextView) view.findViewById(R.id.txtdiachidt);
            holder.txtsinhnhatdt=(TextView) view.findViewById(R.id.txtsinhnhatdt);
            view.setTag(holder);



        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DoiTac doitac=doitacList.get(i);

        holder.txttenkhdt.setText(doitac.getTenkh());

        holder.txttenkhdt.setWidth(150);
        holder.txttenkhdt.setHeight(80);
        holder.txttenkhdt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));

        holder.txttendtkh.setText(doitac.getTendt());
        holder.txttendtkh.setWidth(150);
        holder.txttendtkh.setHeight(80);
        holder.txttendtkh.setBackground(ContextCompat.getDrawable(context,R.drawable.border));

        holder.txtdiachidt.setText(doitac.getDiachi());
        holder.txtdiachidt.setWidth(150);
        holder.txtdiachidt.setHeight(80);
        holder.txtdiachidt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txtsinhnhatdt.setText(doitac.getNgaysinh());
        holder.txtsinhnhatdt.setWidth(100);
        holder.txtsinhnhatdt.setHeight(80);
        holder.txtsinhnhatdt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));


        return view;
    }
}
