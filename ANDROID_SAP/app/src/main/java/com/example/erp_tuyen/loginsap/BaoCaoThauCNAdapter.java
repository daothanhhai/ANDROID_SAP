package com.example.erp_tuyen.loginsap;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class BaoCaoThauCNAdapter extends BaseExpandableListAdapter {
    
    private List<BaoCaoThauCNGroup> mListGroup;
    private Map<BaoCaoThauCNGroup,List<BaoCaoThauCN>> mListItem;


    public BaoCaoThauCNAdapter(List<BaoCaoThauCNGroup> mListGroup, Map<BaoCaoThauCNGroup, List<BaoCaoThauCN>> mListItem) {
        this.mListGroup = mListGroup;
        this.mListItem = mListItem;
    }

    @Override
    public int getGroupCount() {
        if(mListGroup !=null){
            return mListGroup.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mListGroup !=null && mListItem !=null){
            return mListItem.get(mListGroup.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListItem.get(mListGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        BaoCaoThauCNGroup groupObject=mListGroup.get(groupPosition);
        return 1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        BaoCaoThauCN itemObject=mListItem.get(mListGroup.get(groupPosition)).get(childPosition);
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_groupbaocaothaucn, parent,false);

        }



        TextView tvsohdgroup=convertView.findViewById(R.id.tvsohdbcthau);
        TextView tvngayhdgroup=convertView.findViewById(R.id.tvngayhdbcthau);
        TextView tvmadtgroup=convertView.findViewById(R.id.tvmadtbcthau);
        TextView tvtendtgroup=convertView.findViewById(R.id.tvtendtbcthau);

        BaoCaoThauCNGroup groupObject= mListGroup.get(groupPosition);
        tvsohdgroup.setText(groupObject.getSohd());
        tvngayhdgroup.setText(groupObject.getNgayhd());
        tvmadtgroup.setText(groupObject.getMakh());
        tvtendtgroup.setText(groupObject.getTenkh());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_baocaothaucn, parent,false);

        }
        TextView tvmavtitem=convertView.findViewById(R.id.tvmavtbcthaucn);





        TextView tvtenvtitem=convertView.findViewById(R.id.tvtenvtbcthaucn);
        TextView tvdvtitem=convertView.findViewById(R.id.tvdvtbcthaucn);
        TextView tvslttitem=convertView.findViewById(R.id.tvslttbcthaucn);
        TextView tvdgtitem=convertView.findViewById(R.id.tvdgttbcthaucn);
        TextView tvtttitem=convertView.findViewById(R.id.tvttttbcthaucn);
        TextView tvsldgitem=convertView.findViewById(R.id.tvsldgbcthaucn);
        TextView tvslqditem=convertView.findViewById(R.id.tvslqdbcthaucn);

        BaoCaoThauCN itemObject= mListItem.get(mListGroup.get(groupPosition)).get(childPosition);

        tvmavtitem.setText(itemObject.getMavt());

        tvmavtitem.setWidth(80);
        tvmavtitem.setHeight(100);
        


        tvtenvtitem.setText(itemObject.getTenvt());

        tvtenvtitem.setWidth(170);
        tvtenvtitem.setHeight(100);

        tvdvtitem.setText(itemObject.getDvt());
        tvtenvtitem.setWidth(70);
        tvtenvtitem.setHeight(100);




        tvslttitem.setText(String.format("%,.0f", Float.valueOf(itemObject.getSltt()) ));

        tvslttitem.setWidth(70);
        tvslttitem.setHeight(100);
        tvslttitem.setGravity(Gravity.CENTER);


        tvdgtitem.setText(String.format("%,.0f", Float.valueOf(itemObject.getDgtt()) ));
        tvdgtitem.setWidth(100);
        tvdgtitem.setHeight(100);

        tvtttitem.setText(String.format("%,.0f", Float.valueOf(itemObject.getTttt()) ));
        tvtttitem.setWidth(100);
        tvtttitem.setHeight(100);

        tvsldgitem.setText(itemObject.getSldg());
        tvsldgitem.setWidth(100);
        tvsldgitem.setHeight(100);
        tvsldgitem.setGravity(Gravity.CENTER);


        tvslqditem.setText(String.format("%,.0f", Float.valueOf(itemObject.getSlqd()) ));
        tvslqditem.setWidth(100);
        tvslqditem.setHeight(100);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
