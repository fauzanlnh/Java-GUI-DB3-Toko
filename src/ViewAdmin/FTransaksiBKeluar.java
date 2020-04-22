
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewAdmin;

import ViewAdmin.FTransaksiBMasuk;
import ViewAdmin.FormPelanggan;
import ViewAdmin.Login;
import ViewAdmin.FormBarang;
import Class.DatabaseConnection;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperCompileManager;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import ViewAdmin.FTransaksiBMasuk;
import ViewAdmin.FormBarang;
import ViewAdmin.FormPelanggan;
import ViewAdmin.GantiPassword;
import ViewAdmin.GantiPassword;
import ViewAdmin.Login;
import ViewAdmin.TambahPengguna;
import ViewAdmin.TambahPengguna;

/**
 *
 * @author Fauzan13
 */
public class FTransaksiBKeluar extends javax.swing.JFrame {
   DefaultTableModel tabel = new DefaultTableModel();
    /**
     * Creates new form FTransaksiBKeluar
     */
    Connection koneksi;

    public FTransaksiBKeluar() {
        initComponents();
        koneksi = (Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
        AutoNumber();
        setDefaultCloseOperation(FTransaksiBMasuk.HIDE_ON_CLOSE);
                setLocationRelativeTo(this);
    }
     private void itung() {
        String a = txtStok.getText();
        String m = txtKeluar.getText();

        if (txtKeluar.getText() == null) {
            txtStok.setText(a);
        } else {
            int b = Integer.parseInt(a);
            int h = Integer.parseInt(m);
            int jumlah = b - h;
            String s = "" + jumlah;

            txtStok.setText(s);
        }
    }
    private void itung2() {
        String a = txtTotHarga.getText();
        String m = txtKeluar.getText();
        String c = txtHarga.getText();
        
        if (txtKeluar.getText() == null) {
            txtHarga.setText(c);
        } else {
            int b = Integer.parseInt(c);
            int h = Integer.parseInt(m);
            int jumlah = b * h;
            String s = "" + jumlah;

            txtTotHarga.setText(s);
        }
    }
    public void AutoNumber() {
        try {
            Statement stmt = koneksi.createStatement();
            String query_bukaTabel = "select right(kd_transaksi ,1) as no_urut from t_transaksi";
            ResultSet line_result = stmt.executeQuery(query_bukaTabel);
            if (line_result.first() == false) {
                txtKode.setText("BN290001");
            } else {
                line_result.last();
                int no = line_result.getInt(1) + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                for (int i = 0; i < 2 - oto; i++) {
                    nomor = "000" + nomor;
                }
                txtKode.setText("BN29" + nomor);
            }
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }


    private void lihatPelanggan() {

        tabel.addColumn("Kode Pelanggan");
        tabel.addColumn("Nama Pelanggan");

        tblPelanggan.setModel(tabel);

        TableColumn lebar_kolom;

        tblPelanggan.setAutoResizeMode(tblPelanggan.AUTO_RESIZE_OFF);

        lebar_kolom = tblPelanggan.getColumnModel().getColumn(0);
        lebar_kolom.setPreferredWidth(200);
        lebar_kolom = tblPelanggan.getColumnModel().getColumn(1);
        lebar_kolom.setPreferredWidth(200);

        tabel.getDataVector().removeAllElements();
        tabel.fireTableDataChanged();
        try {
            Statement stmt = koneksi.createStatement();
            String query_bukaTabel = "select kd_pelanggan, nama_pelanggan from t_pelanggan";
            ResultSet rs = stmt.executeQuery(query_bukaTabel);
            while (rs.next()) {
                Object[] getO = new Object[2];
                getO[0] = rs.getString("kd_pelanggan");
                getO[1] = rs.getString("nama_pelanggan");
                tabel.addRow(getO);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
    }
     public void proses() {
        DefaultTableModel tableModel = (DefaultTableModel) tblData.getModel();
        String[] data = new String[6];
        data[0] = txtkdBarang.getText();
        data[1] = txtNamaBarang.getText();
        data[2] = txtKeluar.getText();
        data[3] = txtStok.getText();
        data[4] = txtHarga.getText();
        data[5] = txtTotHarga.getText();
        tableModel.addRow(data);
    }
 private String simpan() {

        String tanggalLahir = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tanggalLahir);
        String tanggal = String.valueOf(fm.format(txtTanggal.getDate()));

        String kode = txtKode.getText();
        String pelanggan = txtPelanggan.getText();
        String kdP = txtKdPelanggan.getText();
        String tpemb = txtTotPembelian.getText();
        int jumlah_baris = tblData.getRowCount();
        if (jumlah_baris == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table Masih Kosong!");
        } else {
            try {
                int i = 0;
                while (i < jumlah_baris) {

                    Statement st = koneksi.createStatement();
                    String query = ("insert into t_transaksi (kd_transaksi , tanggal, nama_pelanggan,kd_pelanggan,total_pembelian, kode_barang,nama_barang,keluar,harga_satuan,total_harga) values("
                            + "'" + kode + "','" + tanggal + "','" + pelanggan + "','" + kdP + "','" + tpemb + "',"
                            + "'" + tblData.getValueAt(i, 0) + "',"
                            + "'" + tblData.getValueAt(i, 1) + "',"
                            + "'" + tblData.getValueAt(i, 2) + "',"
                            + "'" + tblData.getValueAt(i, 4) + "',"
                            + "'" + tblData.getValueAt(i, 5) + "')");
                    i++;

                    System.out.println(query);
                    int berhasil = st.executeUpdate(query);
                    if (berhasil == 1) {
                    } else {
                    }
                }
                JOptionPane.showMessageDialog(rootPane, "Berhasil Menyimpan!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan! Error : " + e);
            }
        }
        return null;
    }
 
public void edit1() throws SQLException {

        int jumlah_baris = tblData.getRowCount();
        if (jumlah_baris == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table Masih Kosong!");
        } else {
            try {
                int i = 0;
                while (i < jumlah_baris) {

                    String nama = tblData.getValueAt(i, 0).toString();
                    String tersisa1 = tblData.getValueAt(i, 3).toString();

                    Statement stmt = koneksi.createStatement();
                    
                    String query = "UPDATE t_persediaan SET jumlah_tersedia = '" + tersisa1 + "'"
                            + "WHERE kode_barang = '" + nama + "'";

                    i++;
                    System.out.println(query);
                    int berhasil = stmt.executeUpdate(query);
                    if (berhasil == 1) {
                    } else {
                    }
                }
                JOptionPane.showMessageDialog(rootPane, "Berhasil Menyimpan!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan! Error : " + e);
            }
        }

    }
private void a(){
 int jumlahBaris = tblData.getRowCount();
int totalBiaya = 0;
int jumlahBarang, hargaBarang;
DefaultTableModel tabelModel;
tabelModel = (DefaultTableModel) tblData.getModel();
for (int i=0; i<jumlahBaris; i++){
    jumlahBarang = Integer.parseInt(tabelModel.getValueAt(i, 2).toString());
    hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 4).toString());
    totalBiaya = totalBiaya + (jumlahBarang*hargaBarang);
}
txtTotPembelian.setText(String.valueOf(totalBiaya));
}
private void lihatBarang() {
        
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Jumlah yang Tersedia");
        tabel.addColumn("Harga");

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
            String query_bukaTabel = "select t_persediaan.kode_barang,t_persediaan.nama_barang, t_persediaan.jumlah_tersedia,t_barang.harga_jual from t_persediaan,t_barang WHERE t_barang.kode_barang = t_persediaan.kode_barang GROUP BY t_persediaan.kode_barang";
            ResultSet rs = stmt.executeQuery(query_bukaTabel);
            while (rs.next()) {
                Object[] getO = new Object[4];
                getO[0] = rs.getString("kode_barang");
                getO[1] = rs.getString("nama_barang");
                getO[2] = rs.getString("jumlah_tersedia");
                getO[3] = rs.getString("harga_jual");


                tabel.addRow(getO);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
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

        jDialog1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPelanggan = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jMenu1 = new javax.swing.JMenu();
        jDialog2 = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        txtPelanggan = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        txtKdPelanggan = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtKeluar = new javax.swing.JTextField();
        txtkdBarang = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        btnMasukan = new javax.swing.JButton();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotHarga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtTotPembelian = new javax.swing.JTextField();

        tblPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPelangganMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblPelanggan);

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

        jMenu1.setText("jMenu1");

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
        jScrollPane4.setViewportView(tblBarang);

        jButton8.setText("Kembali");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel5.setBackground(new java.awt.Color(27, 31, 46));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pt anugrah jaya (2).png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Transaksi Barang Keluar");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("No. Jual :");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Tanggal :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Pelanggan :");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Kode Pelanggan :");

        txtKode.setEnabled(false);

        txtTanggal.setEnabled(false);

        txtPelanggan.setEnabled(false);

        btnCari.setBackground(new java.awt.Color(255, 255, 255));
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b/cari.png"))); // NOI18N
        btnCari.setEnabled(false);
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        txtKdPelanggan.setEnabled(false);

        txtHarga.setEnabled(false);

        txtKeluar.setEnabled(false);
        txtKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeluarActionPerformed(evt);
            }
        });
        txtKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKeluarKeyReleased(evt);
            }
        });

        txtkdBarang.setEnabled(false);

        txtStok.setEnabled(false);

        btnMasukan.setText("Masukan");
        btnMasukan.setEnabled(false);
        btnMasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukanActionPerformed(evt);
            }
        });

        txtNamaBarang.setEnabled(false);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Keluar");

        txtTotHarga.setEnabled(false);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("T.Harga");

        btnTambah.setText("Tambah Barang");
        btnTambah.setEnabled(false);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Stok");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Harga");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Nama");

        btnHapus.setText("Hapus Barang");
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Barang", "Keluar", "Stok", "Harga", "Total Harga"
            }
        ));
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblData);

        btnNew.setBackground(new java.awt.Color(0, 255, 0));
        btnNew.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
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

        btnPrint.setBackground(java.awt.Color.darkGray);
        btnPrint.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("Print");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(204, 0, 0));
        btnReset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.setEnabled(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Pembelian");

        txtTotPembelian.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCari))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtkdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(btnTambah))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnMasukan))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(btnHapus))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInsert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtTotPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jLabel15)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnCari)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(txtkdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnHapus))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTotHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMasukan))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInsert)
                        .addComponent(btnNew)
                        .addComponent(btnPrint)
                        .addComponent(btnReset))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tblPelangganMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPelangganMousePressed
        // TODO add your handling code here:
        String getKodeA = tblPelanggan.getValueAt(tblPelanggan.getSelectedRow(), 0).toString();
        String getKodeB = tblPelanggan.getValueAt(tblPelanggan.getSelectedRow(), 1).toString();
           
        txtKdPelanggan.setText(getKodeA);
        txtPelanggan.setText(getKodeB);
        jDialog1.setVisible(false);
    }//GEN-LAST:event_tblPelangganMousePressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblBarangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarangMousePressed
        // TODO add your handling code here:
 String getKodeA = tblBarang.getValueAt(tblBarang.getSelectedRow(), 0).toString();
        String getKodeAa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 1).toString();
        String getKodeAaa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 2).toString();
 String getKodeAaaa = tblBarang.getValueAt(tblBarang.getSelectedRow(), 3).toString();

        txtkdBarang.setText(getKodeA);
        txtNamaBarang.setText(getKodeAa);
        txtStok.setText(getKodeAaa);
        txtHarga.setText(getKodeAaaa);
        jDialog1.setVisible(false);
    }//GEN-LAST:event_tblBarangMousePressed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
jDialog2.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
tblData.removeAll();

        txtKode.setEnabled(false);
        txtTanggal.setEnabled(false);
        txtPelanggan.setEnabled(false);
        txtkdBarang.setEnabled(false);
        txtNamaBarang.setEnabled(false);
        txtKeluar.setEnabled(false);
        txtStok.setEnabled(false);
        btnMasukan.setEnabled(false);
        btnInsert.setEnabled(false);
        btnPrint.setEnabled(false);
        btnReset.setEnabled(false);
        btnNew.setEnabled(true);
        
        txtPelanggan.setText("");
        txtkdBarang.setText("");
        txtNamaBarang.setText("");
        txtKeluar.setText("");
        txtStok.setText("");
        
        txtTotHarga.setText("");
        txtHarga.setText("");
        txtKode.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        String reportSource = null;
        String reportDest = null;

        try {
            HashMap parameter = new HashMap();
            parameter.put("kd_transaksi",txtKode.getText());

            com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) DatabaseConnection.getKoneksi("localhost", "3306", "root", "", "db_tokok");
            reportSource = System.getProperty("user.dir") + "/report/StrukFinal.jrxml";
            reportDest = System.getProperty("user.dir") + "/report/StrukFinal.jasper";

            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, c);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtKdPelanggan.setEnabled(false);
        btnNew.setEnabled(false);
        btnInsert.setEnabled(true);
        btnMasukan.setEnabled(true);
        btnPrint.setEnabled(true);
        btnInsert.setEnabled(true);
        btnReset.setEnabled(true);
        btnTambah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnCari.setEnabled(true);

        txtTotHarga.setEnabled(false);
        txtHarga.setEnabled(false);
        txtKode.setEnabled(false);
        txtTanggal.setEnabled(true);
        txtNamaBarang.setEditable(false);
        txtPelanggan.setEnabled(true);
        txtKeluar.setEnabled(true);
        txtStok.setEnabled(false);

        txtKdPelanggan.setText("");
        txtKode.setText("");
        txtNamaBarang.setText("");
        txtPelanggan.setText("");
        txtKeluar.setText("");
        txtStok.setText("");

        AutoNumber();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
       try {
           edit1();
       } catch (SQLException ex) {
           Logger.getLogger(FTransaksiBKeluar.class.getName()).log(Level.SEVERE, null, ex);
       }
        simpan();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        lihatPelanggan();
        jDialog1.setVisible(true);
        jDialog1.setSize(519, 450);
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtKeluarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKeluarKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeluarKeyReleased

    private void txtKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeluarActionPerformed
        // TODO add your handling code here:
        itung();
        itung2();
    }//GEN-LAST:event_txtKeluarActionPerformed

    private void btnMasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukanActionPerformed
        // TODO add your handling code here:
        proses();
a();
    }//GEN-LAST:event_btnMasukanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
       int row = tblData.getSelectedRow();
   tabel.removeRow(row);
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        jDialog2.setLocationRelativeTo(null);
        lihatBarang();
        jDialog2.setVisible(true);
        txtKeluar.setEditable(true);
        jDialog2.setSize(519, 450);
        txtTotHarga.setText("");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
   int row = tblData.getSelectedRow();
        if(row>=0){
            int ok=JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(ok==0){
                tabel.removeRow(row);
            }

        }
    }//GEN-LAST:event_tblDataMouseClicked

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
            java.util.logging.Logger.getLogger(FTransaksiBKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FTransaksiBKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTransaksiBKeluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnMasukan;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTable tblData;
    private javax.swing.JTable tblPelanggan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKdPelanggan;
    private javax.swing.JTextField txtKeluar;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtPelanggan;
    private javax.swing.JTextField txtStok;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTotHarga;
    private javax.swing.JTextField txtTotPembelian;
    private javax.swing.JTextField txtkdBarang;
    // End of variables declaration//GEN-END:variables
}
