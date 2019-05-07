package id.trydev.gapana.Edukasi.EdukasiPra.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdukasiPra implements Serializable {

    private int id;
    private String kategori;
    private String konten;
    private List<String> listGambar;
    private String judul;

    public EdukasiPra(String kategori, String konten, List<String> listGambar, String judul) {
        this.kategori = kategori;
        this.konten = konten;
        this.listGambar = listGambar;
        this.judul = judul;
    }

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

    public List<String> getListGambar() {
        return listGambar;
    }

    public void setListGambar(List<String> listGambar) {
        this.listGambar = listGambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
