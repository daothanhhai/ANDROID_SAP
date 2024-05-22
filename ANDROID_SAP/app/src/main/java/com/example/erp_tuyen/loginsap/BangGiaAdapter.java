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

public class BangGiaAdapter extends BaseAdapter {
    private activity_banggia context;
    private int layout;
    private List<BangGia> banggiaList;
    private List<BangGia> banggiaListALL;

    public BangGiaAdapter(activity_banggia context, int layout, List<BangGia> banggiaList) {
        this.context = context;
        this.layout = layout;
        this.banggiaList = banggiaList;
        this.banggiaListALL = banggiaList;
    }

    @Override
    public int getCount() {
        return banggiaList.size();
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
                filteredList.values=banggiaListALL;
                filteredList.count=banggiaListALL.size();
            }else {

                ArrayList<BangGia> khvt =new ArrayList<>();
                for(BangGia bg:banggiaList){

                    charSequence = VNCharacterUtils.removeAccent(charSequence.toString());
                    String q=VNCharacterUtils.removeAccent(bg.getTenvt().toLowerCase());

                    if(q.toLowerCase(Locale.getDefault()).contains(charSequence)){
                        khvt.add(bg);
                    }
                }
                filteredList.values=khvt;
                filteredList.count=banggiaList.size();
            }

            return filteredList;

        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.count==0){
                notifyDataSetInvalidated();
            }else {
                banggiaList=(ArrayList<BangGia>) filterResults.values;
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
        TextView txttenvt;
        TextView txtgia;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttenvt=(TextView) view.findViewById(R.id.textViewtenvt);
            holder.txtgia=(TextView) view.findViewById(R.id.textViewgia);
            view.setTag(holder);



        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final BangGia bangiga=banggiaList.get(i);

        holder.txttenvt.setText(bangiga.getTenvt());

        holder.txtgia.setText(String.format("%,.0f", Float.valueOf(bangiga.getGia()) ));



        return view;

    }
}
