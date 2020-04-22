/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import studikasusbarang.View.FHelp;
import view.FormPelanggan;
import view.Login;
import view.FormBarang;
import Class.DatabaseConnection;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
public class FTransaksiBMasuk extends javax.swing.JFrame {

    DefaultTableModel tabel = new DefaultTableModel();
    /**
     * Creates new form FTransaksiBMasuk
     */
    Connection koneksi;

    public FTransaksiBMasuk() {
        initComponents();
        koneksi = (Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
        showData();
           setDefaultCloseOperation(FTransaksiBMasuk.HIDE_ON_CLOSE);
                setLocationRelativeTo(this);
    }

    private void itung() {
        String a = txtStok.getText();
        String m = txtMasuk.getText();

        if (txtMasuk.getText() == null) {
            txtStok.setText(a);
        } else {
                int b = Integer.parseInt(a);
            int h = Integer.parseInt(m);
            int jumlah = b + h;
            String s = "" + jumlah;

            txtStok.setText(s);
        }
    }

    private void lihatBarang() {

        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Kategori");
        tabel.addColumn("Jumlah yang Tersedia");

        tblBarang.setModel(tabel);

        TableColumn lebar_kolom;

        tblBarang.setAutoResizeMode(tblBarang.AUTO_RESIZE_OFF);

        lebar_kolom = tblBarang.getColumnModel().getColumn(0);
        lebar_kolom.setPreferredWidth(100);
        lebar_kolom = tblBarang.getColumnModel().getColumn(1);
        lebar_kolom.setPreferredWidth(200);
        lebar_kolom = tblBarang.getColumnModel().getColumn(2);
        lebar_kolom.setPreferredWidth(100);
        lebar_kolom = tblBarang.getColumnModel().getColumn(3);
        lebar_kolom.setPreferredWidth(100);

        tabel.getDataVector().removeAllElements();
        tabel.fireTableDataChanged();
        try {
            Statement stmt = koneksi.createStatement();
            String query_bukaTabel = "select kode_barang,nama_barang,kode_kategori, jumlah_tersedia from t_persediaan";
            ResultSet rs = stmt.executeQuery(query_bukaTabel);
            while (rs.next()) {
                Object[] getO = new Object[4];
                getO[0] = rs.getString("kode_barang");
                getO[1] = rs.getString("nama_barang");
                getO[2] = rs.getString("kode_kategori");
                getO[3] = rs.getString("jumlah_tersedia");

                tabel.addRow(getO);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
    }

    private void klik() {
        
        
        String getKode = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 1).toString();
        String getNama = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 2).toString();
        String getKategori = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 3).toString();
        String getJumlah = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 4).toString();
        String getHarga = tblPengguna.getValueAt(tblPengguna.getSelectedRow(), 5).toString();
        

        txtKd.setText(getKode);
        txtNama.setText(getNama);
        cmbKategori.setSelectedItem(getKategori);
        txtHarga.setText(getHarga);
        txtMasuk.setText(getJumlah);
        
    }
    
     public void editbmasuk() {
        String tot = txtStok.getText();
        String kode = txtKd.getText();

        try {

            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_persediaan SET jumlah_tersedia = '" + tot + "'"
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
     
      public void edit() {

        try {
          String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal.getDate()));
          
        String kode = txtKd.getText();
        String nama = txtNama.getText();
        String kat = cmbKategori.getSelectedItem().toString();
        String mas = txtMasuk.getText();
        String har = txtHarga.getText();

            Statement stmt = koneksi.createStatement();
            String query = "UPDATE t_bmasuk SET harga = '" + har + "'," + "jumlah = '" + mas + "'," + "tgl_masuk = '" + tanggal + "'"
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
 
 
    
      public void simpan() {
  
        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal.getDate()));
          
        String kode = txtKd.getText();
        String nama = txtNama.getText();
        String kat = cmbKategori.getSelectedItem().toString();
        String mas = txtMasuk.getText();
        String har = txtHarga.getText();

        try {
            Statement stmt = koneksi.createStatement();
            String query = "INSERT INTO t_bmasuk(kode_barang,nama_barang,kode_kategori,jumlah,harga,tgl_masuk)"
                    + "VALUES('" + kode + "','" + nama + "','" + kat + "','" + mas + "','" + har + "','" + tanggal + "')";
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

        String[] kolom = {"NO", "Kode", "Nama", "Kategori", "Jumlah", "Harga", "Tanggal Masuk"};

        dtm = new DefaultTableModel(null, kolom);

        try {
            Statement stmt = koneksi.createStatement();
            String query = "SELECT  t_bmasuk.kode_barang,t_bmasuk.nama_barang,t_bmasuk.kode_kategori,t_bmasuk.jumlah,t_bmasuk.harga,t_bmasuk.tgl_masuk FROM t_bmasuk";
            ResultSet rs = stmt.executeQuery(query);    
            int no = 1;
            while (rs.next()) {
                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kode_kategori");
                String jumlah = rs.getString("jumlah");
                String harga = rs.getString("harga");
                String tgl = rs.getString("tgl_masuk");
                

                dtm.addRow(new String[]{no + "", kode, nama, kategori,jumlah,harga,tgl});
                no++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblPengguna.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtKd = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox();
        btnLihat = new javax.swing.JButton();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        txtMasuk = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPengguna = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblBarangMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblBarang);

        jButton7.setText("Kembali");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Kode Barang :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Barang :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Kategori :");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Harga Beli :");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Jumlah :");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Tanggal Masuk :");

        txtNama.setEnabled(false);

        txtKd.setEnabled(false);

        txtHarga.setEnabled(false);

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- Pilih salah satu -", "Mouse", "Keyboard" }));
        cmbKategori.setEnabled(false);

        btnLihat.setEnabled(false);
        btnLihat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/cari.png"))); // NOI18N
        btnLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatActionPerformed(evt);
            }
        });

        txtTanggal.setEnabled(false);

        jLabel6.setText("Total");

        txtStok.setEnabled(false);
        txtStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStokKeyTyped(evt);
            }
        });

        txtMasuk.setEnabled(false);
        txtMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMasukActionPerformed(evt);
            }
        });
        txtMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMasukKeyTyped(evt);
            }
        });

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKd, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txtNama))
                        .addGap(18, 18, 18)
                        .addComponent(btnLihat))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLihat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnNew)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        tblPengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kategori", "Harga", "Jumlah", "Tanggal Masuk"
            }
        ));
        tblPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPengguna);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("Pencarian Data Barang Masuk");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
int baris;
    private void tblBarangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarangMousePressed
        // TODO add your handling code here:
        String getKodeA = tblBarang.getValueAt(tblBarang.getSelectedRow(), 0).toString();
        String getKodeAa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 1).toString();
        String getKodeAaa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 2).toString();
        String getKodeAaaa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 3).toString();

        txtKd.setText(getKodeA);
        txtNama.setText(getKodeAa);
        cmbKategori.setSelectedItem(getKodeAaa);
        txtStok.setText(getKodeAaaa);
        jDialog1.setVisible(false);
    }//GEN-LAST:event_tblBarangMousePressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyPressed

    private void tblPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenggunaMouseClicked
        klik();

        txtStok.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnReset.setEnabled(true);

        txtNama.setEnabled(true);
        cmbKategori.setEnabled(true);
        txtHarga.setEnabled(true);
        txtMasuk.setEnabled(true);
        txtTanggal.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPenggunaMouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        txtKd.setEnabled(false);
        txtNama.setEnabled(false);
        cmbKategori.setEnabled(false);
        txtHarga.setEnabled(false);
        txtMasuk.setEnabled(false);
        txtStok.setEnabled(false);
        txtTanggal.setEnabled(false);

        btnInsert.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnBatal.setEnabled(false);
        btnReset.setEnabled(false);
        btnNew.setEnabled(true);
        btnLihat.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtKd.setText("");
        txtNama.setText("");
        txtHarga.setText("");
        txtMasuk.setText("");
        txtStok.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String idWhoWantToBeDelete = tblPengguna.getValueAt(baris, 0).toString();
        try {
            Statement stmt = koneksi.createStatement();
            String query = "DELETE FROM t_bmasuk WHERE kode_barang = '" + idWhoWantToBeDelete + "';";
            int berhasil = stmt.executeUpdate(query);
            if (berhasil == 1) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                tabel.getDataVector().removeAllElements();
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

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        simpan();
        //editbmasuk();
        showData();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtKd.setEnabled(true);
        txtNama.setEnabled(true);
        cmbKategori.setEnabled(true);
        txtHarga.setEnabled(true);
        txtMasuk.setEnabled(true);
        txtStok.setEnabled(true);
        txtTanggal.setEnabled(true);

        btnInsert.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnBatal.setEnabled(true);
        btnReset.setEnabled(true);
        btnNew.setEnabled(false);
        btnLihat.setEnabled(true);

    }//GEN-LAST:event_btnNewActionPerformed

    private void txtMasukKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMasukKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMasukKeyTyped

    private void txtMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMasukActionPerformed
        // TODO add your handling code here:
        itung();
    }//GEN-LAST:event_txtMasukActionPerformed

    private void txtStokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStokKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokKeyTyped

    private void btnLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatActionPerformed
        jDialog1.setLocationRelativeTo(null);
        lihatBarang();
        jDialog1.setVisible(true);
        jDialog1.setSize(519, 450);
    }//GEN-LAST:event_btnLihatActionPerformed

    
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
            java.util.logging.Logger.getLogger(FTransaksiBMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTransaksiBMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLihat;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbKategori;
    private javax.swing.JButton jButton7;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTable tblPengguna;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKd;
    private javax.swing.JTextField txtMasuk;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
