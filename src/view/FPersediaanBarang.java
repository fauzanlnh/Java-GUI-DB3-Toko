/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.FHelp;
import view.FAbout;
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
import studikasusbarang.View.MenuUtama;
import view.FTransaksiBMasuk;
import view.FormBarang;
import view.FormPelanggan;
import view.GantiPassword;
import view.Login;
import view.TambahPengguna;

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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtKd = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mnFB = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnFP = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        TPemB = new javax.swing.JMenuItem();
        TPenB = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        LprnBarangKeluar = new javax.swing.JMenuItem();
        LprnBarangMasuk = new javax.swing.JMenuItem();
        LprnPersediaan = new javax.swing.JMenuItem();
        LprnBrngYangDijual = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        Help = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        Logout = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("Pencarian Data Barang");

        jLabel9.setText("Masukan Kode Barang:");

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
                        .addGap(18, 18, 18)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kode Barang :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Barang :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Kategori :");

        txtNama.setEnabled(false);

        txtKd.setEnabled(false);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- Pilih salah satu -", "Mouse", "Keyboard", "Speaker", "Monitor" }));
        cmbKategori.setEnabled(false);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Jumlah :");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNama)
                            .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(236, Short.MAX_VALUE))
        );

        btnReset.setText("Reset");
        btnReset.setEnabled(false);

        btnUpdate.setText("Update");
        btnUpdate.setEnabled(false);

        btnBatal.setText("Batal");
        btnBatal.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate)
                    .addComponent(btnBatal))
                .addGap(34, 34, 34))
        );

        jMenuBar3.setBackground(new java.awt.Color(255, 255, 255));

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Home.png"))); // NOI18N
        jMenu11.setText("Home");
        jMenu11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu11ActionPerformed(evt);
            }
        });

        jMenuItem4.setText("Home");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem4);

        jMenuBar3.add(jMenu11);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Master.png"))); // NOI18N
        jMenu3.setText("Master");

        mnFB.setText("Form Barang");
        mnFB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFBActionPerformed(evt);
            }
        });
        jMenu3.add(mnFB);

        jMenuItem18.setText("Persediaan");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem18);
        jMenu3.add(jSeparator1);

        mnFP.setText("Form Pelanggan");
        mnFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFPActionPerformed(evt);
            }
        });
        jMenu3.add(mnFP);

        jMenuBar3.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/transaksi.png"))); // NOI18N
        jMenu4.setText("Transaksi");

        TPemB.setText("Pembelian Barang");
        TPemB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPemBActionPerformed(evt);
            }
        });
        jMenu4.add(TPemB);

        TPenB.setText("Penjualan Barang");
        TPenB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPenBActionPerformed(evt);
            }
        });
        jMenu4.add(TPenB);

        jMenuBar3.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/laporan.png"))); // NOI18N
        jMenu5.setText("Laporan");

        LprnBarangKeluar.setText("Penjualan");
        LprnBarangKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LprnBarangKeluarActionPerformed(evt);
            }
        });
        jMenu5.add(LprnBarangKeluar);

        LprnBarangMasuk.setText("Pembelian");
        LprnBarangMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LprnBarangMasukActionPerformed(evt);
            }
        });
        jMenu5.add(LprnBarangMasuk);

        LprnPersediaan.setText("Persediaan Barang");
        LprnPersediaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LprnPersediaanActionPerformed(evt);
            }
        });
        jMenu5.add(LprnPersediaan);

        LprnBrngYangDijual.setText("Data Barang");
        LprnBrngYangDijual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LprnBrngYangDijualActionPerformed(evt);
            }
        });
        jMenu5.add(LprnBrngYangDijual);
        jMenu5.add(jSeparator4);

        jMenuItem17.setText("Pelanggan");
        jMenu5.add(jMenuItem17);

        jMenuBar3.add(jMenu5);

        Help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hepl.png"))); // NOI18N
        Help.setText("Help");
        Help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpActionPerformed(evt);
            }
        });

        jMenuItem6.setText("Help");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        Help.add(jMenuItem6);

        jMenuItem12.setText("About");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        Help.add(jMenuItem12);

        jMenuBar3.add(Help);

        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        Logout.setText("System");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jMenuItem13.setText("Logout");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        Logout.add(jMenuItem13);
        Logout.add(jSeparator3);

        jMenuItem14.setText("Ganti Password");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        Logout.add(jMenuItem14);

        jMenuItem3.setText("Tambah Pengguna");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        Logout.add(jMenuItem3);

        jMenuBar3.add(Logout);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(698, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(429, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(129, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        MenuUtama m = new MenuUtama();
        m.show();
        dispose();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu11ActionPerformed

    }//GEN-LAST:event_jMenu11ActionPerformed

    private void mnFBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFBActionPerformed
        FormBarang fb = new FormBarang();
        fb.show();
        dispose();
    }//GEN-LAST:event_mnFBActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        FPersediaanBarang fpb = new FPersediaanBarang();
        fpb.show();
        dispose();
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void mnFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFPActionPerformed
        FormPelanggan fp = new FormPelanggan();
        fp.show();
        dispose();
    }//GEN-LAST:event_mnFPActionPerformed

    private void TPemBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPemBActionPerformed
        FTransaksiBMasuk FTB = new FTransaksiBMasuk();
        FTB.show();
        dispose();
    }//GEN-LAST:event_TPemBActionPerformed

    private void TPenBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPenBActionPerformed
        FTransaksiBKeluar FTB = new FTransaksiBKeluar();
        FTB.show();
        dispose();
    }//GEN-LAST:event_TPenBActionPerformed

    private void LprnBarangKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LprnBarangKeluarActionPerformed
        // TODO add your handling code here:
        String reportSource = null;
        String reportDest = null;

        try {

            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
            reportSource = System.getProperty("user.dir") + "/report/ReportPenjualanBarang.jrxml";
            reportDest = System.getProperty("user.dir") + "/report/ReportPenjualanBarang.jasper";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, c);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_LprnBarangKeluarActionPerformed

    private void LprnBarangMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LprnBarangMasukActionPerformed
        // TODO add your handling code here:
        String reportSource = null;
        String reportDest = null;

        try {

            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
            reportSource = System.getProperty("user.dir") + "/report/ReportBarangMasuk.jrxml";
            reportDest = System.getProperty("user.dir") + "/report/ReportBarangMasuk.jasper";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, c);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_LprnBarangMasukActionPerformed

    private void LprnPersediaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LprnPersediaanActionPerformed
        // TODO add your handling code here:
        String reportSource = null;
        String reportDest = null;

        try {

            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
            reportSource = System.getProperty("user.dir")+ "/report/ReportPersediaan.jrxml";
            reportDest = System.getProperty("user.dir")+ "/report/ReportPersediaan.jasper";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, c);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint, false);

        }catch(Exception e){
            System.out.println(e);

        }
    }//GEN-LAST:event_LprnPersediaanActionPerformed

    private void LprnBrngYangDijualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LprnBrngYangDijualActionPerformed
        // TODO add your handling code here:
        String reportSource = null;
        String reportDest = null;

        try {

            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
            reportSource = System.getProperty("user.dir") + "/report/ReportDataBarang.jrxml";
            reportDest = System.getProperty("user.dir") + "/report/ReportDataBarang.jasper";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, c);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_LprnBrngYangDijualActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        FHelp h = new FHelp();
        h.show();
        dispose();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        FAbout f = new FAbout();
        f.show();
        dispose();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void HelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpActionPerformed

    }//GEN-LAST:event_HelpActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        Login l = new Login();
        l.show();
        dispose();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        GantiPassword  trsk1 = new GantiPassword();
        trsk1.show();
        dispose();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        TambahPengguna  trsk1 = new TambahPengguna();
        trsk1.show();
        dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        Login l = new Login();
        l.show();
        dispose();
    }//GEN-LAST:event_LogoutActionPerformed

    private void tblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenggunaMouseClicked
        klik();
        btnUpdate.setEnabled(true);
        btnReset.setEnabled(true);

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
    private javax.swing.JMenu Help;
    private javax.swing.JMenu Logout;
    private javax.swing.JMenuItem LprnBarangKeluar;
    private javax.swing.JMenuItem LprnBarangMasuk;
    private javax.swing.JMenuItem LprnBrngYangDijual;
    private javax.swing.JMenuItem LprnPersediaan;
    private javax.swing.JMenuItem TPemB;
    private javax.swing.JMenuItem TPenB;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem mnFB;
    private javax.swing.JMenuItem mnFP;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
