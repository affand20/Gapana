package id.trydev.gapana.Cuaca;

import java.util.List;

import id.trydev.gapana.Cuaca.Modal.Cuaca;
import id.trydev.gapana.Cuaca.Modal.Location;

public interface CuacaView {
    public void showLoading();
    public void dismissLoading();
    public void showData(Cuaca cuaca, Location location);
    public void emptyData(String msg);
    public void showLocation(List<Location> list);
}
