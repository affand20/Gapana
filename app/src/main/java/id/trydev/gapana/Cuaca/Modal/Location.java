package id.trydev.gapana.Cuaca.Modal;

public class Location {
    String key;
    String nama;
    String latitude;
    String longitude;

    public Location(){
        key = "";
        nama = "";
        latitude = "";
        longitude = "";
    }

    public Location(String key, String nama, String latitude, String longitude){
        this.key = key;
        this.nama = nama;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNama() {
        return nama;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
