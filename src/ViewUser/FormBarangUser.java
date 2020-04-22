/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewUser;


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

/**
 *
 * @author Fauzan13
 */
public class FormBarangUser extends javax.swing.JFrame {

    /**
     * Creates new form FormBarang
     */
    Connection koneksi;

    public FormBarangUser() {
        initComponents();
        koneksi = (Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
        showData();
        setDefaultCloseOperation(FormBarangUser.HIDE_ON_CLOSE);
                setLocationRelativeTo(this);

        SelectComboBox();
        AutoNumber();
        
    txtKd.setEnabled(false);
    txtJumlah.setText("0");
    }

    public void simpan() {

        String kode = txtKd.getText();
        String nama = txtNama.getText();
        String kat = cmbKategori.getSelectedItem().toString();
        String har = txtHarga.getText();
        try {
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO t_barang(kode_barang,nama_barang,kode_kategori,harga_jual)"
                    + "VALUES('" + kode + "','" + nama + "','" + kat + "','" + har + "')";
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
    
    public void simpan1() {

        String kode = txtKd.getText();
        String nama = txtNama.getText();
        String kat = cmbKategori.getSelectedItem().toString();
        String jumlah = txtJumlah.getText();

        try { 
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO t_persediaan(kode_barang,nama_barang,kode_kategori,jumlah_tersedia)"
                    + "VALUES('" + kode + "','" + nama + "','" + kat + "','" + jumlah + "')";
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

        String[] kolom = {"NO", "Kode", "Nama", "Kategori", "Harga Jual"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT  t_barang.kode_barang,t_barang.nama_barang,t_barang.kode_kategori,harga_jual FROM t_barang";
            ResultSet rs = stmt.executeQuery(query);    
            int no = 1;
            while (rs.next()) {
                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kode_kategori");
                String har = rs.getString("harga_jual");

                dtm.addRow(new String[]{no + "", kode, nama, kategori,har});
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

 public void cari() {

        String[] kolom = {"NO", "Kode", "Nama", "Kategori"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT * FROM t_barang WHERE nama_barang like  '%" + txtCari.getText() + "%' OR kode_kategori like '%" + txtCari.getText() + "%'";

            ResultSet rs = stmt.executeQuery(query);
            int no = 1;
            while (rs.next()) {

                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kode_kategori");

                dtm.addRow(new String[]{no + "", kode, nama, kategori});
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
            String query_bukaTabel = "select right(kode_barang ,1) as no_urut from t_barang";
            ResultSet line_result = stmt.executeQuery(query_bukaTabel);
            if (line_result.first() == false) {
                txtKd.setText("BRG0001");
            } else {
                line_result.last();
                int no = line_result.getInt(1) + 1;
                String nomor =
                        String.valueOf(no);
                int oto = nomor.length();
                for (int i = 0; i < 2 - oto; i++) {
                    nomor = "000" + nomor;
                }
                txtKd.setText("BRG" + nomor);
            }
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }

 private void klik() {
        String getKode = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 1).toString();
        String getNama = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 2).toString();
        String getBagian = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 3).toString();
String getharga = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 4).toString();

        txtKd.setText(getKode);
        txtNama.setText(getNama);
        cmbKategori.setSelectedItem(getBagian);
        txtHarga.setText(getharga);
    }
 
 public void edit() {

        try {
            String kode = txtKd.getText();
            String nama = txtNama.getText();
            String kat = cmbKategori.getSelectedItem().toString();

            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_barang SET nama_barang = '" + nama + "'," + "kode_kategori = '" + kat + "'"
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

        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox();
        txtNama = new javax.swing.JTextField();
        txtKd = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();
        btnBatal = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Barang");
        setResizable(false);

        jPanel4.setBackground(new java.awt.Color(27, 31, 46));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pt anugrah jaya (2).png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Data Barang");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Kode Barang :");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nama Barang :");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Harga :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Kategori :");

        txtHarga.setEnabled(false);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- Pilih salah satu -", "Mouse", "Keyboard", "Speaker", "Monitor" }));
        cmbKategori.setEnabled(false);

        txtNama.setEnabled(false);

        txtKd.setEnabled(false);

        txtJumlah.setText("0");
        txtJumlah.setEnabled(false);
        txtJumlah.setVisible(false);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pencarian Data Barang");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Masukan Nama Barang atau Kategori:");

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

        btnBatal.setBackground(new java.awt.Color(153, 0, 0));
        btnBatal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Cancel");
        btnBatal.setEnabled(false);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 0, 51));
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setEnabled(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(51, 51, 255));
        btnInsert.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setText("Insert");
        btnInsert.setEnabled(false);
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(0, 255, 0));
        btnNew.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(0, 597, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNama)
                            .addComponent(txtKd)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)
                        .addGap(207, 207, 207)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNew)
                            .addComponent(btnBatal)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnInsert)
                                .addComponent(btnReset)))
                        .addGap(51, 51, 51))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
simpan();
simpan1();
showData();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
AutoNumber();
        txtKd.setEnabled(false);
txtNama.setEnabled(true);
cmbKategori.setEnabled(true);
txtHarga.setEnabled(true);

btnInsert.setEnabled(true);
btnBatal.setEnabled(true);
btnReset.setEnabled(true);
btnNew.setEnabled(false);

txtNama.setText("");
    
    }//GEN-LAST:event_btnNewActionPerformed
int baris;
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
AutoNumber();
txtNama.setText("");
txtKd.setText("");
txtJumlah.setText("");
cmbKategori.setSelectedIndex(0);
    }//GEN-LAST:event_btnResetActionPerformed

    private void tblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenggunaMouseClicked
klik();
btnReset.setEnabled(true);

cmbKategori.setEnabled(true);
txtNama.setEnabled(true);
txtHarga.setEnabled(true);
// TODO add your handling code here:
    }//GEN-LAST:event_tblPenggunaMouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
txtKd.setEnabled(false);
        txtNama.setEnabled(false);
        cmbKategori.setEnabled(false);
btnInsert.setEnabled(false);
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
            java.util.logging.Logger.getLogger(FormBarangUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarangUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarangUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarangUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarangUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox cmbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
