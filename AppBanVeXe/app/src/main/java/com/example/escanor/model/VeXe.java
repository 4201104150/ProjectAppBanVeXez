package com.example.escanor.model;

import java.io.Serializable;

public class VeXe implements Serializable
{
    public String soGhe;
    public String soTien;
    public String SDT;
    public String trangThai;

    public VeXe() {
    }

    public VeXe(String soGhe, String soTien, String SDT, String trangThai) {
        this.soGhe = soGhe;
        this.soTien = soTien;
        this.SDT = SDT;
        this.trangThai = trangThai;
    }

    public String getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(String soGhe) {
        this.soGhe = soGhe;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
