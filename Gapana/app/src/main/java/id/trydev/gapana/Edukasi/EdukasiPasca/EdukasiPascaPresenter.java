package id.trydev.gapana.Edukasi.EdukasiPasca;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPasca.Model.EdukasiPasca;

public class EdukasiPascaPresenter {

    private EdukasiPascaView view;
    private List<EdukasiPasca> listVideo = new ArrayList<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public EdukasiPascaPresenter(EdukasiPascaView view){
        this.view = view;
    }

    void getListVideo(){
        Log.d("TAG", "getListVideo: TRIGGERED");
        view.showLoading();
        firestore.collection("edukasi-pasca")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            listVideo.clear();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                EdukasiPasca edukasiPasca = document.toObject(EdukasiPasca.class);
                                listVideo.add(edukasiPasca);
                            }
                            Log.d("TAG", "onComplete: "+listVideo.size());
                            view.hideLoading();
                            view.populateData(listVideo);
                        } else{
                            Log.d("TAG", "onComplete: "+task.getException().getLocalizedMessage());
                            view.hideLoading();
                            view.sendToast(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }
}
