package id.trydev.gapana.Edukasi.EdukasiPasca.Model;


public class EdukasiPasca {
    private String judul;
    private String thumbnail;
    private String video;

    public EdukasiPasca (){

    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public EdukasiPasca(String judul, String thumbnail, String video) {
        this.judul = judul;
        this.thumbnail = thumbnail;
        this.video = video;
    }
}
