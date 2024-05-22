package com.example.erp_tuyen.loginsap;

public class BangKeNopTienTDV {
    String stt;
    String soct;
    String ngayct;
    String tiennop;
    String tiengoc;

    public BangKeNopTienTDV(String stt, String soct, String ngayct, String tiennop, String tiengoc) {
        this.stt = stt;
        this.soct = soct;
        this.ngayct = ngayct;
        this.tiennop = tiennop;
        this.tiengoc = tiengoc;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getSoct() {
        return soct;
    }

    public void setSoct(String soct) {
        this.soct = soct;
    }

    public String getNgayct() {
        return ngayct;
    }

    public void setNgayct(String ngayct) {
        this.ngayct = ngayct;
    }

    public String getTiennop() {
        return tiennop;
    }

    public void setTiennop(String tiennop) {
        this.tiennop = tiennop;
    }

    public String getTiengoc() {
        return tiengoc;
    }

    public void setTiengoc(String tiengoc) {
        this.tiengoc = tiengoc;
    }
}

