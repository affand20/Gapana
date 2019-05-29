package id.trydev.gapana.Cuaca;

import java.util.ArrayList;

import id.trydev.gapana.Cuaca.Modal.Cuaca;

public interface CuacaView {
    public void showLoading();
    public void dismissLoading();
    public void showData(ArrayList<Cuaca> list);
    public void emptyData(String msg);
}
