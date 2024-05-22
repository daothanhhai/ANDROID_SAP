package com.example.erp_tuyen.loginsap;

public class BaoCaoThauCNGroup {
    private String makh;
    private String tenkh;
    private String sohd;
    private String ngayhd;
    private String bizdocid;

    public BaoCaoThauCNGroup(String makh, String tenkh, String sohd, String ngayhd,String bizdocid) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.sohd = sohd;
        this.ngayhd = ngayhd;
        this.bizdocid=bizdocid;
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

    public String getSohd() {
        return sohd;
    }

    public void setSohd(String sohd) {
        this.sohd = sohd;
    }

    public String getNgayhd() {
        return ngayhd;
    }

    public void setNgayhd(String ngayhd) {
        this.ngayhd = ngayhd;
    }
    public String getBizdocid() {
        return bizdocid;
    }

    public void setBizdocid(String bizdocid) {
        this.bizdocid = bizdocid;
    }
}
