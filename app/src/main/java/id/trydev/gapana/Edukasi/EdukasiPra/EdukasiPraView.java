package id.trydev.gapana.Edukasi.EdukasiPra;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;

public interface EdukasiPraView {

    void showLoading();
    void hideLoading();
    void showEmptyKonten();
    void showData(List<EdukasiPra> listKontenEdukasiPra);
}
