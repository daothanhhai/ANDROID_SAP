package com.example.erp_tuyen.loginsap;

import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ThuNoKhachHangAdapter extends BaseAdapter {
    private activity_thunokhachhang context;

    private int layout;
    private List<ThuNoKhachHang> thunokhachhangList;
    boolean[] checked;
    int dem=0;
    ResultSet resultSet;
    String stthdhttcode;
    String giatricode;
    String tientrucode;
    public ThuNoKhachHangAdapter(activity_thunokhachhang context, int layout, List<ThuNoKhachHang> thunokhachhangList) {
        this.context = context;
        this.layout = layout;
        this.thunokhachhangList = thunokhachhangList;
    }

    @Override
    public int getCount() {
        return thunokhachhangList.size();
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
        String stthd;
        TextView txtsohd;
        TextView txtngayhd;
        TextView txtmadt;
        TextView txttendt;
        TextView txttiengoc;
        TextView txttienno;
        CheckBox checboxtieno;
        CheckBox checboxtrucodekm;
        EditText edthuno;
        EditText edghichu;

    }
    public void updatelistview(ArrayList<ThuNoKhachHang> thuno)
    {
        this.thunokhachhangList=thuno;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txtsohd=(TextView) view.findViewById(R.id.txtsohdcnkh);
            holder.txtngayhd=(TextView) view.findViewById(R.id.txtngayhdcnkh);
            holder.txtmadt=(TextView) view.findViewById(R.id.txtmadtcnkh);
            holder.txttendt=(TextView) view.findViewById(R.id.txttendtcnkh);
            holder.txttiengoc=(TextView) view.findViewById(R.id.txttiengochdcnkh);
            holder.txttienno=(TextView) view.findViewById(R.id.txttiennohdcnkh);
            holder.checboxtieno=(CheckBox) view.findViewById(R.id.checkthunokh);
            holder.checboxtrucodekm=(CheckBox) view.findViewById(R.id.checknoptrucodekm);
            holder.edthuno=(EditText) view.findViewById(R.id.edittextthuno);
            holder.edghichu=(EditText) view.findViewById(R.id.editghichuttgh);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final ThuNoKhachHang thunokhachhang=thunokhachhangList.get(i);
        holder.stthd=thunokhachhang.getStt();

        holder.txtsohd.setText(thunokhachhang.getSohd());
        holder.txtsohd.setWidth(90);
        holder.txtsohd.setHeight(160);
        holder.txtsohd.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txtsohd.setTextSize(9);

        holder.txtngayhd.setText(thunokhachhang.getNgayhd());
        holder.txtngayhd.setWidth(100);
        holder.txtngayhd.setHeight(160);
        holder.txtngayhd.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txtngayhd.setTextSize(9);

        holder.txtmadt.setText(thunokhachhang.getMadt());
        holder.txtmadt.setWidth(150);
        holder.txtmadt.setHeight(160);
        holder.txtmadt.setTextSize(9);
        holder.txtmadt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txttendt.setText(thunokhachhang.getTendt());
        holder.txttendt.setWidth(220);
        holder.txttendt.setHeight(160);
        holder.txttendt.setTextSize(9);
        holder.txttendt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txttiengoc.setText(String.format("%,.0f", Float.valueOf(thunokhachhang.getTiengoc()) ));
        holder.txttiengoc.setWidth(0);
        holder.txttiengoc.setHeight(160);
        holder.txttiengoc.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.txttienno.setText(String.format("%,.0f", Float.valueOf(thunokhachhang.getTienno()) ));
        holder.txttienno.setWidth(220);
        holder.txttienno.setHeight(160);
        holder.txttienno.setBackground(ContextCompat.getDrawable(context,R.drawable.border));

        holder.edthuno.setText(String.format("%,.0f", Float.valueOf(thunokhachhang.getTienno()) ));
        holder.edthuno.setWidth(160);
        holder.edthuno.setHeight(160);

        holder.edghichu.setText(thunokhachhang.getGhichu());
        holder.edghichu.setWidth(200);
        holder.edghichu.setHeight(160);
        holder.edghichu.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
        holder.edghichu.setTextSize(9);

        holder.checboxtieno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dem=dem+1;
                    String trano = holder.edthuno.getText().toString();
                    trano=trano.replace(".","");
                    Float a= Float.valueOf(trano);
                    String stthdhtt = holder.stthd;
                    String ghichu=holder.edghichu.getText().toString();

                    if(activity_thunokhachhang.arrayTraNoKhachHang !=null) {
                        activity_thunokhachhang.arrayTraNoKhachHang.add(new ThuNoKhachHang(stthdhtt,"","","","","","",true,trano,ghichu));
                    }
                    else {
                        activity_thunokhachhang.arrayTraNoKhachHang=new ArrayList<>();
                        activity_thunokhachhang.arrayTraNoKhachHang.add(new ThuNoKhachHang(stthdhtt,"","","","","","",true,trano,ghichu));
                    }
                    Toast.makeText(context, "bạn chọn stt"+ trano, Toast.LENGTH_SHORT).show();
                }
                else {
                    String trano = holder.edthuno.getText().toString();
                    trano=trano.replace(".","");
                    Float a= Float.valueOf(trano);
                    String stthdhtt = holder.stthd;
                    Toast.makeText(context, "bạn bỏ chọn stt"+ trano , Toast.LENGTH_SHORT).show();

                    if(activity_thunokhachhang.arrayTraNoKhachHang !=null) {
                        activity_thunokhachhang.arrayTraNoKhachHang.remove(dem-1);
                        dem=dem-1;
                    }
                }
            }
        });


        holder.checboxtrucodekm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String tienno=holder.txttienno.getText().toString();

                if(isChecked) {
                    stthdhttcode = holder.stthd;
                    Toast.makeText(context, "bạn chọn stt" + stthdhttcode, Toast.LENGTH_SHORT).show();
                    try {
                        selectgtc();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    holder.edthuno.setText(String.format("%,.0f", Float.valueOf(tientrucode)));

                }

                else {
                    stthdhttcode = holder.stthd;
                    holder.edthuno.setText(tienno);
                }
            }
        });


        return view;
    }

    public void selectgtc() throws SQLException
    {
        Connection con = SERVER.Connect(); //khởi tạo kết nối tới server, SERVER chính là class riêng, tìm trong table java
        if (con == null) {
            Toast.makeText(context, "Không thể kết nối", Toast.LENGTH_SHORT).show();
        } else {

            String query = "SELECT ISNULL(Gia_Tri_Code,0) AS Gia_Tri_Code,(Tong_Tien-ISNULL(Gia_Tri_Code,0)) AS Tien_Tru_Code " +
                    "FROM B30Ct WHERE Stt='"+ stthdhttcode +"' ";
            //trên đây là câu truy vấn
            Statement stmt = con.createStatement(); //blah blah blah
            resultSet = stmt.executeQuery(query);
            for(int i = 0; i<=resultSet.getRow();i++ ) {
                if (resultSet.next())//nếu trong resultset không null thì sẽ trả về True
                {
                    giatricode = resultSet.getString("Gia_Tri_Code");
                    tientrucode = resultSet.getString("Tien_Tru_Code");
                }
            }

            con.close();
        }
    }


}
