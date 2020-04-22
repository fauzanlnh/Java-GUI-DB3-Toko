/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import studikasusbarang.View.FHelp;
import view.Login;
import view.FormBarang;
import Class.DatabaseConnection;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import studikasusbarang.View.FPersediaanBarang;
import studikasusbarang.View.MenuUtama;
import view.GantiPassword;
import view.TambahPengguna;

/**
 *
 * @author Fauzan13
 */
public class FormPelanggan extends javax.swing.JFrame {

    /**
     * Creates new form FormPelanggan
     */
    Connection koneksi;

    public FormPelanggan() {
        initComponents();
        koneksi = (Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
    showData();
    AutoNumber();
           setDefaultCloseOperation(FormPelanggan.HIDE_ON_CLOSE);
                setLocationRelativeTo(this);
    }
    
        public void simpan() {

        String kode = txtKd.getText();
        String nama = txtNama.getText();
        String ala = txtAlamat.getText();
        String telp = txtTelp.getText();

        try {
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO t_pelanggan(kd_pelanggan,nama_pelanggan,alamat,no_telp)"
                    + "VALUES('" + kode + "','" + nama + "','" + ala + "','" + telp + "')";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dimasukan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal DImasukan");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database");
        }
    }
        
         DefaultTableModel dtm;
 public void showData() {

        String[] kolom = {"NO", "Kode", "Nama", "Alamat", "No Telp"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT  t_pelanggan.kd_pelanggan,t_pelanggan.nama_pelanggan,t_pelanggan.alamat,t_pelanggan.no_telp FROM t_pelanggan";
            ResultSet rs = stmt.executeQuery(query);    
            int no = 1;
            while (rs.next()) {
                String kode = rs.getString("kd_pelanggan");
                String nama = rs.getString("nama_pelanggan");
                String alamat = rs.getString("alamat");
                String telp = rs.getString("no_telp");

                
                dtm.addRow(new String[]{no + "", kode, nama, alamat,telp});
                no++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblPengguna.setModel(dtm);
    }
 
 
 public void cari() {

        String[] kolom = {"NO", "Kode", "Nama", "Alamat", "No Telp"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_pelanggan WHERE nama_pelanggan like  '%" + txtCari.getText() + "%' OR alamat like '%" + txtCari.getText() + "%'";

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {

                String kode = rs.getString("kd_pelanggan");
                String nama = rs.getString("nama_pelanggan");
                String alamat = rs.getString("alamat");
                String telp = rs.getString("no_telp");

                
                dtm.addRow(new String[]{no + "", kode, nama, alamat,telp});
                no++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblPengguna.setModel(dtm);
    }
  
   public void AutoNumber() {
        try {
            Statement stmt = koneksi.createStatement();
            String query_bukaTabel = "select right(kd_pelanggan ,1) as no_urut from t_pelanggan";
            ResultSet line_result = stmt.executeQuery(query_bukaTabel);
            if (line_result.first() == false) {
                txtKd.setText("PLG0001");
            } else {
                line_result.last();
                int no = line_result.getInt(1) + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                for (int i = 0; i < 2 - oto; i++) {
                    nomor = "000" + nomor;
                }
                txtKd.setText("PLG" + nomor);
            }
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
   
   private void klik() {
        String getKode = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 1).toString();
        String getNama = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 2).toString();
        String getAlamat = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 3).toString();
        String getTelp = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 4).toString();

        txtKd.setText(getKode);
        txtNama.setText(getNama);
        txtAlamat.setText(getAlamat);
        txtTelp.setText(getTelp);

   }
   
   public void edit() {

        try {
            String kode = txtKd.getText();
            String nama = txtNama.getText();
            String alamat = txtAlamat.getText();
            String telp = txtTelp.getText();
            

            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_pelanggan SET nama_pelanggan = '" + nama + "'," + "alamat = '" + alamat + "'," + "no_telp = '" + telp + "'"
                    + "WHERE kd_pelanggan = '" + kode + "'";
            System.out.println(query);
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Diubah");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Pada Database");
        }
    }
 


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtKd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtTelp = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kode Pelanggan :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Pelanggan :");

        txtNama.setEnabled(false);

        txtKd.setEnabled(false);

        jLabel3.setText("No Telp :");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Alamat :");

        txtAlamat.setEnabled(false);

        txtTelp.setEnabled(false);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/tambah.jpg"))); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/insert.png"))); // NOI18N
        btnInsert.setEnabled(false);
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/update.png"))); // NOI18N
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/delete.png"))); // NOI18N
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/reset.png"))); // NOI18N
        btnReset.setEnabled(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/batal1.png"))); // NOI18N
        btnBatal.setEnabled(false);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNama)
                            .addComponent(txtKd, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelp)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnNew)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("Pencarian Data Pelanggan");

        jLabel9.setText("Masukan Kode atau Nama Pelanggan:");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tblPengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama ", "Alamat", "No Telp"
            }
        ));
        tblPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPengguna);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
simpan();
showData();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
txtKd.setEnabled(true);
        txtNama.setEnabled(true);
        txtAlamat.setEnabled(true);
txtTelp.setEnabled(true);
btnInsert.setEnabled(true);
btnUpdate.setEnabled(true);
btnDelete.setEnabled(true);
btnBatal.setEnabled(true);
btnReset.setEnabled(true);
btnNew.setEnabled(false);
AutoNumber();
        txtNama.setText("");
        txtAlamat.setText("");
txtTelp.setText("");
    
        
    }//GEN-LAST:event_btnNewActionPerformed
int baris;
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String idWhoWantToBeDelete = tblPengguna.getValueAt(baris, 1).toString();
        try {
            Statement stmt = koneksi.createStatement();
            String query = "DELETE FROM t_pelanggan WHERE kd_pelanggan = '" + idWhoWantToBeDelete + "';";
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                dtm.getDataVector().removeAllElements();
                showData();
            } else {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        showData();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
edit();
showData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtNama.setText("");
        txtAlamat.setText("");
txtTelp.setText("");

        AutoNumber();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenggunaMouseClicked
klik();
btnDelete.setEnabled(true);
btnUpdate.setEnabled(true);
btnReset.setEnabled(true);
txtNama.setEnabled(true);
txtAlamat.setEnabled(true);
txtTelp.setEnabled(true);
    }//GEN-LAST:event_tblPenggunaMouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
txtKd.setEnabled(false);
        txtNama.setEnabled(false);
        txtAlamat.setEnabled(false);
txtTelp.setEnabled(false);
btnInsert.setEnabled(false);
btnUpdate.setEnabled(false);
btnDelete.setEnabled(false);
btnBatal.setEnabled(false);
btnReset.setEnabled(false);
btnNew.setEnabled(true);
AutoNumber();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
cari();
    }//GEN-LAST:event_txtCariKeyReleased

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
            java.util.logging.Logger.getLogger(FormPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTelp;
    // End of variables declaration//GEN-END:variables
}
