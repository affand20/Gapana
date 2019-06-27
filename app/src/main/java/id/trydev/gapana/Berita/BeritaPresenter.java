package id.trydev.gapana.Berita;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Berita.Model.Berita;

public class BeritaPresenter {

    private List<Berita> listBeritaCarousel = new ArrayList<>();
    private List<Berita> listBerita = new ArrayList<>();
    private BeritaView view;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public BeritaPresenter(BeritaView beritaView){
        this.view = beritaView;
    }

    void get5NewestBerita(){
        listBeritaCarousel.clear();
        view.showLoading();
        // fetch data, currently generate dummy data
        for (int i = 0; i < 5; i++) {
            listBeritaCarousel.add(new Berita("Letusan Gunung Anak Krakatau Menyebabkan Tsunami di Lampung dan Banten", "http://news.detik.com", "image.jpg","27 Desember 2018","Detik.com"));
        }
        view.show5NewestBerita(listBeritaCarousel);
        view.hideLoading();
    }

    void getBerita(){
        view.showLoading();
        firestore.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            listBerita.clear();
                            listBeritaCarousel.clear();
                            for (QueryDocumentSnapshot document: task.getResult()){
                                if (listBeritaCarousel.size()<=5){
                                    listBeritaCarousel.add(document.toObject(Berita.class));
                                } else{
                                    listBerita.add(document.toObject(Berita.class));
                                }
                            }
                            Log.d("ON COMPLETE", "onComplete: "+listBerita.size());
                            Log.d("ON COMPLETE", "onComplete: "+listBeritaCarousel.size());
                            view.hideLoading();
                            view.showListBerita(listBerita);
                            view.show5NewestBerita(listBeritaCarousel);
                        } else{
                            view.hideLoading();
                            view.sendToast(task.getException().getLocalizedMessage());
                        }
                    }
                });
//        view.showListBerita(listBerita);
//        view.hideLoading();
    }
}
