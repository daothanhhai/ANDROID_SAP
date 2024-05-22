package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KhachHangAdapter extends BaseAdapter {
    private activity_khachhang context;
    private int layout;
    private List<KhachHang> khachhangList;
    private List<KhachHang> khachhangListALL;

    public KhachHangAdapter(activity_khachhang context, int layout, ArrayList<KhachHang> khachhangList) {
        this.context = context;
        this.layout = layout;
        this.khachhangList = khachhangList;
        this.khachhangListALL = khachhangList;
    }


    @Override
    public int getCount() {
        return khachhangList.size();
    }


    public Filter getFilter()
    {
        return filter;
    }

    Filter filter=new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filteredList=new FilterResults();
            if(charSequence==null || charSequence.length()==0){
                filteredList.values=khachhangListALL;
                filteredList.count=khachhangListALL.size();
            }else {

                ArrayList<KhachHang> khvt =new ArrayList<>();
                for(KhachHang vt:khachhangList){

                    charSequence = VNCharacterUtils.removeAccent(charSequence.toString());
                    String q=VNCharacterUtils.removeAccent(vt.getTenkh().toLowerCase());

                    if(q.toLowerCase(Locale.getDefault()).contains(charSequence)){
                        khvt.add(vt);
                    }
                }
                filteredList.values=khvt;
                filteredList.count=khachhangList.size();
            }

            return filteredList;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.count==0){
                notifyDataSetInvalidated();
            }else {
                khachhangList=(ArrayList<KhachHang>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    };


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class Viewholder{
        TextView txttenkh;
        TextView txttendt;
        TextView txtdiachi;
        TextView txtdienthoai;
        TextView txtcktt;
        TextView txtcktm;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttenkh=(TextView) view.findViewById(R.id.txttenkh);
            holder.txttendt=(TextView) view.findViewById(R.id.txttendt);
            holder.txtdiachi=(TextView) view.findViewById(R.id.txtdiachi);
            holder.txtdienthoai=(TextView) view.findViewById(R.id.txtsodtkh);
            holder.txtcktt=(TextView) view.findViewById(R.id.txtckttkh);
            holder.txtcktm=(TextView) view.findViewById(R.id.txtcktmkh);
            view.setTag(holder);



        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final KhachHang khachHang=khachhangList.get(i);

        holder.txttenkh.setText(khachHang.getTenkh());
        holder.txttenkh.setWidth(120);
        holder.txttenkh.setHeight(60);
        holder.txttendt.setText(khachHang.getDoitac());
        holder.txtdiachi.setText(khachHang.getDiachi());
        holder.txtdienthoai.setText(khachHang.getDienthoai());
        holder.txtcktt.setText(khachHang.getCktt());
        holder.txtcktm.setText(khachHang.getCktm());


        return view;
    }
}
