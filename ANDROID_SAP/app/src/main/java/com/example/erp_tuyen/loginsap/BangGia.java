package com.example.erp_tuyen.loginsap;

public class BangGia {
    private String tenvt;
    private String gia;

    public BangGia(String tenvt, String gia) {
        this.tenvt = tenvt;
        this.gia = gia;
    }

    public String getTenvt() {
        return tenvt;
    }

    public void setTenvt(String tenvt) {
        this.tenvt = tenvt;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
