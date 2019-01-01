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

public class Xe {
    private String SoXe;
    private String HangXe;
    private int NamSX;
    private String MaKH;

    public Xe() {
        this.SoXe = "";
        this.HangXe = "";
        this.NamSX = 0;
        this.MaKH = "";
    }

    public String getSoXe() {
        return SoXe;
    }

    public String getHangXe() {
        return HangXe;
    }

    public int getNamSX() {
        return NamSX;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setSoXe(String SoXe) {
        this.SoXe = SoXe;
    }

    public void setHangXe(String HangXe) {
        this.HangXe = HangXe;
    }

    public void setNamSX(int NamSX) {
        this.NamSX = NamSX;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }
    
}
