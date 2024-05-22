package com.example.erp_tuyen.loginsap;

public class VienThamKhachHang {
    private String makh;
    private String tenkh;
    private String diachi;
    private String dienthoai;


    public VienThamKhachHang(String makh, String tenkh, String diachi, String dienthoai) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.dienthoai = dienthoai;

    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }


}
