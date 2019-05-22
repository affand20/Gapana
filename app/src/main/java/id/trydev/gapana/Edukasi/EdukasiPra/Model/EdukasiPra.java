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
    @ColumnInfo(name = "warnaBg")
    private String warnaBg;

    public EdukasiPra(){};

    public EdukasiPra(int id, String judul, String kategori, String konten, String listGambar, String warnaBg) {
        this.id = id;
        this.judul = judul;
        this.kategori = kategori;
        this.konten = konten;
        this.listGambar = listGambar;
        this.warnaBg = warnaBg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
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

    public String getWarnaBg() {
        return warnaBg;
    }

    public void setWarnaBg(String warnaBg) {
        this.warnaBg = warnaBg;
    }
}
