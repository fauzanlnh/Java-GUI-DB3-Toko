/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewAdmin;

import ViewAdmin.FTransaksiBKeluar;
import ViewAdmin.FHelp;
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
import ViewAdmin.MenuUtama;
import ViewAdmin.FTransaksiBMasuk;
import ViewAdmin.FTransaksiBMasuk;
import ViewAdmin.FormBarang;
import ViewAdmin.FormBarang;
import ViewAdmin.FormPelanggan;
import ViewAdmin.FormPelanggan;
import ViewAdmin.GantiPassword;
import ViewAdmin.GantiPassword;
import ViewAdmin.Login;
import ViewAdmin.Login;
import ViewAdmin.TambahPengguna;
import ViewAdmin.TambahPengguna;

/**
 *
 * @author Fauzan13
 */
public class FPersediaanBarang extends javax.swing.JFrame {

    /**
     * Creates new form FPersediaanBarang
     */
    Connection koneksi;
    public FPersediaanBarang() {
        initComponents();
    koneksi = (Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
        showData();
        SelectComboBox();
                setDefaultCloseOperation(FPersediaanBarang.HIDE_ON_CLOSE);
                setLocationRelativeTo(this);

    }
    
    private void klik() {
        String getKode = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 1).toString();
        String getNama = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 2).toString();
        String getBagian = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 3).toString();
        String getJumlah = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 4).toString();

        txtKd.setText(getKode);
        txtNama.setText(getNama);
        txtJumlah.setText(getJumlah);
        cmbKategori.setSelectedItem(getBagian);
     
    }
 public void cari() {

        String[] kolom = {"NO", "Kode", "Nama", "Kategori","Jumlah"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_persediaan WHERE nama_barang like  '%" + txtCari.getText() + "%' OR kode_kategori like '%" + txtCari.getText() + "%'";

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {

                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kode_kategori");
                String jmlh = rs.getString("jumlah_tersedia");

                dtm.addRow(new String[]{no + "", kode, nama, kategori,jmlh});
                no++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblPengguna.setModel(dtm);
    }
    DefaultTableModel dtm;
 public void showData() {

        String[] kolom = {"NO", "Kode", "Nama", "Kategori","jumlah"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT  t_persediaan.kode_barang,t_persediaan.nama_barang,t_persediaan.kode_kategori,t_persediaan.jumlah_tersedia FROM t_persediaan";
            ResultSet rs = stmt.executeQuery(query);    
            int no = 1;
            while (rs.next()) {
                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kode_kategori");
                String jmlh = rs.getString("jumlah_tersedia");


                dtm.addRow(new String[]{no + "", kode, nama, kategori,jmlh});
                no++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblPengguna.setModel(dtm);
    }
 
  public void SelectComboBox(){
        
         
        try {

            String query = "SELECT * FROM t_kategori";
            java.sql.Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(query);
             
            while (rs.next()) {                
                cmbKategori.addItem(rs.getString("kode_kategori"));
             
            }
             
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
             
        } catch (SQLException e) {
        }
 }

    public void edit() {

        try {
            String kode = txtKd.getText();
            String nama = txtNama.getText();
            String jmlh = txtJumlah.getText();
            String kat = cmbKategori.getSelectedItem().toString();

            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_persediaan SET nama_barang = '" + nama + "'," + "kode_kategori = '" + kat + "'," + "jumlah_tersedian = '" + jmlh + "'"
                    + "WHERE kode_barang = '" + kode + "'";
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

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtKd = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox();
        txtJumlah = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(27, 31, 46));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pt anugrah jaya (2).png"))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nama Barang :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Kategori :");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Kode Barang :");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Jumlah :");

        txtKd.setEnabled(false);

        txtNama.setEnabled(false);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- Pilih salah satu -", "Mouse", "Keyboard", "Speaker", "Monitor" }));
        cmbKategori.setEnabled(false);

        txtJumlah.setEnabled(false);
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 204, 0));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Edit");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(204, 0, 0));
        btnBatal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Reset");
        btnBatal.setEnabled(false);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Form Persediaan Barang");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pencarian Data Barang");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Masukan Nama Barang atau kategori :");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        tblPengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kategori"
            }
        ));
        tblPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPengguna);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal)
                        .addGap(64, 64, 64))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNama)
                                .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate)
                            .addComponent(btnBatal))
                        .addGap(41, 41, 41))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenggunaMouseClicked
        klik();
        btnUpdate.setEnabled(true);
        btnBatal.setEnabled(true);
        cmbKategori.setEnabled(true);
        txtNama.setEnabled(true);
        txtJumlah.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPenggunaMouseClicked

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_txtCariKeyReleased

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahKeyTyped

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
txtKd.setText("");
txtNama.setText("");
txtJumlah.setText("");
cmbKategori.setSelectedIndex(0);

txtKd.setEnabled(false);
txtNama.setEnabled(false);
txtJumlah.setEnabled(false);
cmbKategori.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

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
            java.util.logging.Logger.getLogger(FPersediaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FPersediaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FPersediaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FPersediaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FPersediaanBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
