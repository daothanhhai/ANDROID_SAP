package com.example.erp_tuyen.loginsap;

public class ThuNoKhachHang {
    private String stt,sohd,ngayhd,madt,tendt,tiengoc,tienno,thuno,ghichu;
    private Boolean ischecked;

    public ThuNoKhachHang(String stt, String sohd, String ngayhd, String madt, String tendt, String tiengoc, String tienno, Boolean ischecked,String thuno,String ghichu) {
        this.stt = stt;
        this.sohd = sohd;
        this.ngayhd = ngayhd;
        this.madt = madt;
        this.tendt = tendt;
        this.tiengoc = tiengoc;
        this.tienno = tienno;
        this.ischecked = ischecked;
        this.thuno=thuno;
        this.ghichu=ghichu;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
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

    public String getTiengoc() {
        return tiengoc;
    }

    public void setTiengoc(String tiengoc) {
        this.tiengoc = tiengoc;
    }

    public String getTienno() {
        return tienno;
    }

    public void setTienno(String tienno) {
        this.tienno = tienno;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getThuno() {
        return thuno;
    }

    public void setThuno(String thuno) {
        this.thuno = thuno;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
