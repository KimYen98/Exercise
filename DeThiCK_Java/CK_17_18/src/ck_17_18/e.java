/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import Connect.DataAccessHelper;
import Entity.BaoDuong;
import Entity.CongViec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STIREN
 */
public class e extends javax.swing.JFrame {

    /**
     * Creates new form e
     */
    DataAccessHelper d = new DataAccessHelper();
    //Lấy số xe theo ngày nhận
    public ArrayList<BaoDuong> LayDSSoXeTheoNgayNhan(String NgayNhan)
    {
        String SQL = "SELECT DISTINCT SOXE FROM BAODUONG WHERE DATEDIFF(DAY, (CONVERT(smalldatetime, '" + NgayNhan + "' ,103)), NGAYGIONHAN) = 0";
        ArrayList<BaoDuong> temp = new ArrayList<BaoDuong>();
        try{
            d.getConnect();
            PreparedStatement ps = d.getConn().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if(rs!=null)
                while(rs.next()){
                    BaoDuong BD = new BaoDuong();
                    BD.setSoXe(rs.getString("SOXE"));
                    temp.add(BD);
                }
            d.getClose();
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }
    //Lấy danh sách tên công việc và giá
    public ArrayList<CongViec> LoadCongViec(String SoXe, String NgayNhan)
    {
        ArrayList<CongViec> temp =new ArrayList<>();
        String SQL = "SELECT TENCV, DONGIA\n" +
                     "FROM BAODUONG, CONGVIEC, CT_BD\n" +
                     "WHERE BAODUONG.MABD = CT_BD.MABD AND CONGVIEC.MACV = CT_BD.MACV AND DATEDIFF(DAY, (CONVERT(smalldatetime, '" + NgayNhan + "' ,103)), NGAYGIONHAN) = 0 AND SOXE = '" + SoXe + "'";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs!=null)
                while(rs.next())
                {
                    CongViec CV =new CongViec();
                    CV.setTenCV(rs.getString("TENCV"));
                    CV.setDonGia(rs.getInt("DONGIA"));
                    temp.add(CV);
                }
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
    //Hiển thị thông tin lên bảng
    public void bindingCongViec(JTable name, ArrayList<CongViec> arrCV)
    {
        Vector header = new Vector();
        header.add("Tên công việc");
        header.add("Đơn giá");
        
        Vector data = new Vector();
        for(CongViec CV : arrCV)
        {
            Vector row = new Vector();
            row.add(CV.getTenCV());
            row.add(CV.getDonGia());
            
            data.add(row);
        }
        //DefaultTableModel
        DefaultTableModel dtm = new DefaultTableModel(data,header){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        name.setModel(dtm);      
    }
    
    //Tính thành tiền
    public int TinhThanhTien(String SoXe, String NgayNhan)
    {
        String SQL = "SELECT SUM(DONGIA) AS S\n" +
                     "FROM CONGVIEC, BAODUONG, CT_BD\n" +
                     "WHERE CONGVIEC.MACV = CT_BD.MACV AND BAODUONG.MABD = CT_BD.MABD AND DATEDIFF(DAY, (CONVERT(smalldatetime, '" + NgayNhan + "' ,103)), NGAYGIONHAN) = 0 AND SOXE = '" + SoXe + "' \n" +
                     "GROUP BY SOXE";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs =st.executeQuery(SQL);
            if(rs!=null && rs.next() )
                return rs.getInt("S");
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    //Cập nhật ngày giờ trả trong bảng bảo dưỡng
    public boolean CapNhatNgayGioTra(String SoXe, String NgayNhan)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String SQL = "UPDATE BAODUONG\n" +
                     "SET NGAYGIOTRA = CONVERT(smalldatetime, '" + df.format(new Date()) + "' ,103)\n" +
                     "WHERE DATEDIFF(DAY, (CONVERT(smalldatetime, '" + NgayNhan + "' ,103)), NGAYGIONHAN) = 0 AND SOXE = '" + SoXe + "'";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            int rs = st.executeUpdate(SQL);
            if(rs>0)
                return true;
            d.getClose();
        } catch (Exception e) {
        }
        return false;
    }
    
    public e() {
        initComponents();
        cbSoXe.removeAllItems();
        
        txfNgayNhan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(txfNgayNhan.getText().length() >= 10)
                    for(BaoDuong BD : LayDSSoXeTheoNgayNhan(txfNgayNhan.getText()))
                        cbSoXe.addItem(BD.getSoXe());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(txfNgayNhan.getText().length() >= 10)
                    for(BaoDuong BD : LayDSSoXeTheoNgayNhan(txfNgayNhan.getText()))
                        cbSoXe.addItem(BD.getSoXe());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(txfNgayNhan.getText().length() >= 10)
                    for(BaoDuong BD : LayDSSoXeTheoNgayNhan(txfNgayNhan.getText()))
                        cbSoXe.addItem(BD.getSoXe());
            }
        });
        
        cbSoXe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String SoXe = cbSoXe.getSelectedItem().toString();
                String NgayNhan = txfNgayNhan.getText();
                bindingCongViec(jTable1, LoadCongViec(SoXe, NgayNhan));
                txfThanhTien.setText(String.valueOf(TinhThanhTien(SoXe, NgayNhan)));
            }
        });
        
        btnThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String SoXe = cbSoXe.getSelectedItem().toString();
                String NgayNhan = txfNgayNhan.getText();
                
                if(CapNhatNgayGioTra(SoXe, NgayNhan))
                    JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công");
                else JOptionPane.showMessageDialog(rootPane, "cập nhật thất bại");
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
        cbSoXe = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txfNgayNhan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txfThanhTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Số xe");

        jLabel2.setText("Ngày nhận");

        jLabel3.setText("Thành tiền");

        txfThanhTien.setEditable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnThanhToan.setText("THANH TOÁN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cbSoXe, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(185, 185, 185)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(txfThanhTien))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(txfNgayNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane1)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbSoXe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txfNgayNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txfThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnThanhToan)
                .addContainerGap(57, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(e.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(e.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(e.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(e.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new e().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JComboBox<String> cbSoXe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txfNgayNhan;
    private javax.swing.JTextField txfThanhTien;
    // End of variables declaration//GEN-END:variables
}
