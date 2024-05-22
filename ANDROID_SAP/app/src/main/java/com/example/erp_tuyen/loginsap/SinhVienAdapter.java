package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class SinhVienAdapter extends BaseAdapter implements Filterable {
//public class SinhVienAdapter extends Recy implements Filterable {
    //private MainActivity context;
    private SinhVienActivity context;

    private int layout;
    private ArrayList<SinhVien> sinhVienList;
    private ArrayList<SinhVien> sinhVienListAll;


    public SinhVienAdapter(SinhVienActivity context, int layout, ArrayList<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
        this.sinhVienListAll = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
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
                filteredList.values=sinhVienListAll;
                filteredList.count=sinhVienListAll.size();
            }else {

                ArrayList<SinhVien> sinhvienmoi=new ArrayList<SinhVien>();
                for(SinhVien sv:sinhVienList){
                    //if(sv.getTensv().toUpperCase().startsWith(charSequence.toString().toUpperCase())){
                    if(sv.getTensv().toUpperCase().startsWith(charSequence.toString().toUpperCase())){
                        sinhvienmoi.add((sv));
                    }
                }
                filteredList.values=sinhvienmoi;
                filteredList.count=sinhVienList.size();
                }

                return filteredList;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.count==0){
                notifyDataSetInvalidated();
            }else {
                sinhVienList=(ArrayList<SinhVien>) filterResults.values;
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
        TextView txtmsv;
        TextView txttsv;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);
            holder.txtmsv=(TextView) view.findViewById(R.id.textviewmasv);
            holder.txttsv=(TextView) view.findViewById(R.id.textviewtensv);
            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();
        }
        final SinhVien sinhVien=sinhVienList.get(i);
        holder.txtmsv.setText(sinhVien.getMasv());
        holder.txttsv.setText(sinhVien.getTensv());
        return view;
    }
}
