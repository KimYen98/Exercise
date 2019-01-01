/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import Connect.DataAccessHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author STIREN
 */
public class c extends javax.swing.JFrame {

    /**
     * Creates new form c
     */
    DataAccessHelper d = new DataAccessHelper();
    
    //Lấy họ tên khách hàng theo số xe
    public String LayHotenKHTheoSoXe(String SoXe)
    {
        String SQL = "SELECT HOTENKH FROM KHACHHANG, XE WHERE KHACHHANG.MAKH = XE.MAKH AND SOXE = '" + SoXe + "'";

        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs =st.executeQuery(SQL);
            if(rs!=null &&rs.next() )
                return rs.getString("HOTENKH");
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
//    
//    //Thêm bảo dưỡng
//    public boolean ThemBaoDuong(String MaBD, String NgayGioNhan, int SoKM, String NoiDung, String SoXe)
//    {
//        String SQL = "INSERT INTO BAODUONG (MABD, NGAYGIONHAN, SOKM, NOIDUNG, SOXE) VALUES ('" + MaBD + "', CONVERT(smalldatetime, '" + NgayGioNhan + "' ,103), " + SoKM + ", N'" + NoiDung + "', '" + SoXe + "')";
//        //String SQL = "EXEC ThemBaoDuong '" + MaBD + "', '" + NgayGioNhan + "', " + SoKM + ", N'" + NoiDung + "', '" + SoXe + "'";
//        try {
//            d.getConnect();
//            Statement st = d.getConn().createStatement();
//            int rs = st.executeUpdate(SQL);
//            if(rs>0)
//                return true;
//            d.getClose();
//        } catch (Exception e) {
//        }
//        return false;
//    }
    
    public c() {
        initComponents();
        
        //Nhập số xe hiển thị tên khách hàng
        txfSoXe.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                txfHotenKH.setText(LayHotenKHTheoSoXe(txfSoXe.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                txfHotenKH.setText(LayHotenKHTheoSoXe(txfSoXe.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                txfHotenKH.setText(LayHotenKHTheoSoXe(txfSoXe.getText()));
            }
        });
//        txfSoXe.addActionListener(e -> {
//            String SoXe = txfSoXe.getText();
//            String SQL = "SELECT HOTENKH FROM KHACHHANG, XE WHERE KHACHHANG.MAKH = XE.MAKH AND SOXE = '" + SoXe + "'";
//
//            try {
//                d.getConnect();
//                ResultSet rs = d.getConn().createStatement().executeQuery(SQL);
//                while (rs.next())
//                {
//                    txfHotenKH.setText(rs.getString("HOTENKH"));
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(c.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                
                String MaBD = txfMaBD.getText();
                
                String NgayGioNhan = df.format(new Date());
                
                System.out.println(NgayGioNhan);
                System.out.println(df.format(new Date()));
              
                int SoKM = Integer.parseInt(txfSoKM.getText());
                String NoiDung = txfNoiDungBD.getText();
                String SoXe = txfSoXe.getText();
                
                String SQL = "INSERT INTO BAODUONG (MABD, NGAYGIONHAN, SOKM, NOIDUNG, SOXE) VALUES ('" + MaBD + "', CONVERT(smalldatetime, '" + NgayGioNhan + "' ,103), " + SoKM + ", N'" + NoiDung + "', '" + SoXe + "')";
                
                try {
                    d.getConnect();
                    int st = d.getConn().createStatement().executeUpdate(SQL);
//                if(ThemBaoDuong(MaBD, NgayGioNhan, SoKM, NoiDung, SoXe))
//                    JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
//                else JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
                } catch (SQLException ex) {
                    Logger.getLogger(c.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txfSoXe = new javax.swing.JTextField();
        txfHotenKH = new javax.swing.JTextField();
        txfMaBD = new javax.swing.JTextField();
        txfSoKM = new javax.swing.JTextField();
        txfNoiDungBD = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Số xe");

        jLabel2.setText("Họ tên khách hàng");

        jLabel3.setText("Mã bảo dưỡng");

        jLabel4.setText("Số KM");

        jLabel5.setText("Nội dung bảo dưỡng");

        txfHotenKH.setEditable(false);

        btnThem.setText("THÊM BẢO DƯỠNG");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfHotenKH)
                            .addComponent(txfMaBD)
                            .addComponent(txfSoKM, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txfSoXe)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem)
                            .addComponent(txfNoiDungBD, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))))
                .addContainerGap(329, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txfSoXe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txfHotenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txfMaBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txfSoKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txfNoiDungBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnThem)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(c.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(c.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(c.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(c.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new c().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txfHotenKH;
    private javax.swing.JTextField txfMaBD;
    private javax.swing.JTextField txfNoiDungBD;
    private javax.swing.JTextField txfSoKM;
    private javax.swing.JTextField txfSoXe;
    // End of variables declaration//GEN-END:variables
}
