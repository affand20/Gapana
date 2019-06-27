package id.trydev.gapana.Edukasi.EdukasiPasca;

import java.util.List;

import id.trydev.gapana.Edukasi.EdukasiPasca.Model.EdukasiPasca;

public interface EdukasiPascaView {

    void showLoading();
    void hideLoading();
    void sendToast(String msg);
    void populateData(List<EdukasiPasca> listEdukasiPasca);

}
