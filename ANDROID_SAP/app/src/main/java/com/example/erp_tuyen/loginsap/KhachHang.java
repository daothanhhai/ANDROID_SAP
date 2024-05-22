package com.example.erp_tuyen.loginsap;

public class KhachHang {
    private String makh;
    private String tenkh;
    private String diachi;
    private String dienthoai;
    private String cktt;
    private String cktm;
    private String doitac;

    public KhachHang(String makh, String tenkh, String diachi, String dienthoai,String cktt,String cktm, String doitac) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.dienthoai = dienthoai;
        this.cktt=cktt;
        this.cktm=cktm;
        this.doitac=doitac;
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

    public String getCktt() {
        return cktt;
    }

    public void setCktt(String cktt) {
        this.cktt = cktt;
    }

    public String getCktm() {
        return cktm;
    }

    public void setCktm(String cktm) {
        this.cktm = cktm;
    }

    public String getDoitac() {
        return doitac;
    }

    public void setDoitac(String doitac) {
        this.doitac = doitac;
    }
}
