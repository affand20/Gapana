package id.trydev.gapana.Edukasi.EdukasiPra.Model;

import java.io.Serializable;
import java.util.List;

public class EdukasiPra implements Serializable {

    private int id;
    private String judul;
    private List<String> kategori;
    private String konten;
    private List<String> listGambar;
}
