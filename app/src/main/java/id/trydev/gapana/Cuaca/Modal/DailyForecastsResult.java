package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastsResult {
    @SerializedName("DailyForecasts")
    private List<DailyForecasts> dailyForecasts;

    public List<DailyForecasts> getDailyForecasts() {
        return dailyForecasts;
    }
}
