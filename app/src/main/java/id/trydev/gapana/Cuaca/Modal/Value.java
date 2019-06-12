package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("Value")
    int Value;
    @SerializedName("Unit")
    String Unit;
    @SerializedName("UnitType")
    int UnitType;

    public Value(){
        Value = 0;
        Unit = "";
        UnitType = 0;
    }

    public int getUnitType() {
        return UnitType;
    }

    public String getUnit() {
        return Unit;
    }

    public int getValue() {
        return Value;
    }

    public void setUnitType(int unitType) {
        UnitType = unitType;
    }

    public void setValue(int value) {
        Value = value;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
