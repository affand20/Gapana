package id.trydev.gapana.Berita.Model;

public class Berita {

    private String title;
    private String url;
    private String photo_url;
    private String tanggal;
    private String sumber;

    public Berita(){}

    public Berita(String judul, String url, String photo_url, String tanggal, String sumber) {
        this.title = judul;
        this.url = url;
        this.photo_url = photo_url;
        this.tanggal = tanggal;
        this.sumber = sumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSumber() {
        return sumber;
    }

    public void setSumber(String sumber) {
        this.sumber = sumber;
    }
}
