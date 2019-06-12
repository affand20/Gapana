package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("Icon")
    int Icon;
    @SerializedName("IconPhrase")
    String IconPhrase;
    @SerializedName("HasPrecipitation")
    String HasPrecipitation;

    public Day(){
        Icon = 0;
        IconPhrase = "";
        HasPrecipitation = "";
    }

    public int getIcon() {
        return Icon;
    }

    public String getHasPrecipitation() {
        return HasPrecipitation;
    }

    public String getIconPhrase() {
        return IconPhrase;
    }

    public void setHasPrecipitation(String hasPrecipitation) {
        HasPrecipitation = hasPrecipitation;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public void setIconPhrase(String iconPhrase) {
        IconPhrase = iconPhrase;
    }
}
