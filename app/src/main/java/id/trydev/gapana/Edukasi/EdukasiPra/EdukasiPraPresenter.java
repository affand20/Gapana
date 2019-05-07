package id.trydev.gapana.Edukasi.EdukasiPra;

import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPra.Model.EdukasiPra;

public class EdukasiPraPresenter {

    private List<EdukasiPra> listKontenEdukasiPra = new ArrayList<>();
    private List<String> listGambar = new ArrayList<>();
    private EdukasiPraView view;

    public EdukasiPraPresenter(EdukasiPraView view){
        this.view = view;
    }

    public void getEdukasiPra(){
//        getDataFromServer();
        view.showLoading();
        listGambar.add("R.drawable.dummy_image");
        for (int i = 0; i < 5; i++) {
            EdukasiPra edukasi = new EdukasiPra("Gempa","Anda harus berlindung",listGambar,"Tips berlindung dari gempa");
            listKontenEdukasiPra.add(edukasi);
        }
        view.hideLoading();
        view.showData(listKontenEdukasiPra);
    }


    public void getDataFromServer(){

    }

    public void getDataFromLocal(){

    }
}
