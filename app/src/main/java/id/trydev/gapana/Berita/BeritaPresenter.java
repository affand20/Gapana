package id.trydev.gapana.Berita;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;

public class BeritaPresenter {

    private List<Berita> listBeritaCarousel = new ArrayList<>();
    private List<Berita> listBerita = new ArrayList<>();
    private BeritaView view;

    public BeritaPresenter(BeritaView beritaView){
        this.view = beritaView;
    }

    void get5NewestBerita(){
        view.showLoading();
        // fetch data, currently generate dummy data
        for (int i = 0; i < 5; i++) {
            listBeritaCarousel.add(new Berita("Letusan Gunung Anak Krakatau Menyebabkan Tsunami di Lampung dan Banten", "http://google.com", "image.jpg","27 Desember 2018","Detik.com"));
        }
        view.show5NewestBerita(listBeritaCarousel);
        view.hideLoading();
    }

    void getBerita(){
        view.showLoading();
        for (int i = 0; i < 10; i++) {
            listBerita.add(new Berita("Letusan Gunung Anak Krakatau Menyebabkan Tsunami di Lampung dan Banten", "http://google.com", "image.jpg","27 Desember 2018","Detik.com"));
        }
        view.showListBerita(listBerita);
        view.hideLoading();
    }
}
