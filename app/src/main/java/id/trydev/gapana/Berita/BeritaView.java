package id.trydev.gapana.Berita;

import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;

public interface BeritaView {

    void showLoading();
    void hideLoading();
    void show5NewestBerita(List<Berita> listCarousel);
    void showListBerita(List<Berita> listBeritaGrid);
    void sendToast(String msg);
}
