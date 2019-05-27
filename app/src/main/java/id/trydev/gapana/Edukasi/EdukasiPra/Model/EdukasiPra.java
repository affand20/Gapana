package id.trydev.gapana.Edukasi.EdukasiPra.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class EdukasiPra implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "judul")
    private String judul;
    @ColumnInfo(name = "kategori")
    private String kategori;
    @ColumnInfo(name = "konten")
    private String konten;
    @ColumnInfo(name = "listGambar")
    private String listGambar;


    public EdukasiPra(String kategori, String konten, String listGambar, String judul) {
        this.kategori = kategori;
        this.konten = konten;
        this.listGambar = listGambar;
        this.judul = judul;
    }

    @Ignore
    public EdukasiPra(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getListGambar() {
        return listGambar;
    }

    public void setListGambar(String listGambar) {
        this.listGambar = listGambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
