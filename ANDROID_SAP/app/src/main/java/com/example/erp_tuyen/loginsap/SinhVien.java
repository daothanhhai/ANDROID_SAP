package com.example.erp_tuyen.loginsap;



public class SinhVien {

    private String masv;
    private String tensv;

    public SinhVien(String masv, String tensv) {
        this.masv = masv;
        this.tensv = tensv;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }
}
