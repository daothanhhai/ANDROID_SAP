package com.example.erp_tuyen.loginsap;

public class DoanhThu {
    private String soct,ngayct,madt,tendt;
    private String doanhthu;

    public DoanhThu(String tendt,String doanhthu) {
        this.soct = soct;
        this.ngayct = ngayct;
        this.madt = madt;
        this.tendt = tendt;
        this.doanhthu = doanhthu;
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

    public String getMadt() {
        return madt;
    }

    public void setMadt(String madt) {
        this.madt = madt;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
    }

    public String getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(String doanhthu) {
        this.doanhthu = doanhthu;
    }
}
