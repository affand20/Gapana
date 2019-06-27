package id.trydev.gapana.Cuaca.Modal;


import com.google.gson.annotations.SerializedName;

public class Headline {
    @SerializedName("EffectiveDate")
    String EffectiveDate;
    @SerializedName("EffectiveEpochDate")
    int EffectiveEpochDate;
    @SerializedName("Severity")
    int Severity;
    @SerializedName("Text")
    String Text;
    @SerializedName("Category")
    String Category;
    @SerializedName("EndDate")
    String EndDate;
    @SerializedName("EndEpochDate")
    String EndEpochDate;
    @SerializedName("MobileLink")
    String MobileLink;
    @SerializedName("Link")
    String Link;

    public int getEffectiveEpochDate() {
        return EffectiveEpochDate;
    }

    public String getCategory() {
        return Category;
    }

    public int getSeverity() {
        return Severity;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getEndEpochDate() {
        return EndEpochDate;
    }

    public String getLink() {
        return Link;
    }

    public String getMobileLink() {
        return MobileLink;
    }

    public String getText() {
        return Text;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public void setEffectiveEpochDate(int effectiveEpochDate) {
        EffectiveEpochDate = effectiveEpochDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setEndEpochDate(String endEpochDate) {
        EndEpochDate = endEpochDate;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setMobileLink(String mobileLink) {
        MobileLink = mobileLink;
    }

    public void setSeverity(int severity) {
        Severity = severity;
    }

    public void setText(String text) {
        Text = text;
    }
}
