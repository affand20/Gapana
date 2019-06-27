package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Cuaca {
    @SerializedName("Headline")
    Headline Headline;
    @SerializedName("DailyForecasts")
    List<DailyForecasts> DailyForecasts;

    public Cuaca(){
        Headline = new Headline();
        DailyForecasts = new ArrayList<>();
    }

    public List<DailyForecasts> getDailyForecasts() {
        return DailyForecasts;
    }

    public Headline getHeadline() {
        return Headline;
    }

    public void setDailyForecasts(List<DailyForecasts> dailyForecasts) {
        DailyForecasts = dailyForecasts;
    }

    public void setHeadline(Headline headline) {
        Headline = headline;
    }
}

//class Cuaca {
//    @SerializedName("Value")
//    int Value;
//    @SerializedName("Unit")
//    String Unit;
//    @SerializedName("UnitType")
//    int UnitType;
//    @SerializedName("result")
//    Cuaca cuaca;
//
//    public Cuaca(){
//        this.Value = 0;
//        this.Unit = "";
//        this.UnitType = 0;
//    }
//
//    public int getValue() {
//        return Value;
//    }
//
//    public String getUnit() {
//        return Unit;
//    }
//
//    public int getUnitType() {
//        return UnitType;
//    }
//
//    public void setValue(int value) {
//        Value = value;
//    }
//
//    public void setUnit(String unit) {
//        Unit = unit;
//    }
//
//    public void setUnitType(int unitType) {
//        UnitType = unitType;
//    }
//
//    public Cuaca getCuaca(){
//        return cuaca;
//    }
//}
