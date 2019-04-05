package id.trydev.gapana.Berita.Model;

public class Berita {

    private String judul;
    private String url;
    private String image;
    private String tanggal;
    private String sumber;

    public Berita(){}

    public Berita(String judul, String url, String image, String tanggal, String sumber) {
        this.judul = judul;
        this.url = url;
        this.image = image;
        this.tanggal = tanggal;
        this.sumber = sumber;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
