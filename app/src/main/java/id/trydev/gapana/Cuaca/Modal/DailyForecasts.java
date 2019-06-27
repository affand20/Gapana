package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

public class DailyForecasts {
    @SerializedName("Temperature")
    Temperature Temperature;
    @SerializedName("Date")
    String Date;
    @SerializedName("Day")
    Day Day;

    public DailyForecasts(){
        this.Date = "";
        Temperature = new Temperature();
        this.Day = new Day();
    }

    public Day getDay() {
        return Day;
    }

    public String getDate() {
        return Date;
    }

    public Temperature getTemperature() {
        return Temperature;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTemperature(Temperature temperature) {
        Temperature = temperature;
    }

    public void setDay(Day day) {
        Day = day;
    }
}