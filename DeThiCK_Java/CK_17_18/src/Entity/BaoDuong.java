/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author STIREN
 */

import java.util.Date;

public class BaoDuong {
    private String MaBD;
    private Date NgayGioNhan;
    private Date NgayGioTra;
    private int SoKM;
    private String NoiDung;
    private String SoXe;

    public BaoDuong() {
        this.MaBD = "";
        this.NgayGioNhan = null;
        this.NgayGioTra = null;
        this.SoKM = 0;
        this.NoiDung = "";
        this.SoXe = "";
    }

    public String getMaBD() {
        return MaBD;
    }

    public Date getNgayGioNhan() {
        return NgayGioNhan;
    }

    public Date getNgayGioTra() {
        return NgayGioTra;
    }

    public int getSoKM() {
        return SoKM;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public String getSoXe() {
        return SoXe;
    }

    public void setMaBD(String MaBD) {
        this.MaBD = MaBD;
    }

    public void setNgayGioNhan(Date NgayGioNhan) {
        this.NgayGioNhan = NgayGioNhan;
    }

    public void setNgayGioTra(Date NgayGioTra) {
        this.NgayGioTra = NgayGioTra;
    }

    public void setSoKM(int SoKM) {
        this.SoKM = SoKM;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    public void setSoXe(String SoXe) {
        this.SoXe = SoXe;
    }
    
}
