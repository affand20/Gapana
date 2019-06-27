package id.trydev.gapana.Cuaca.Modal;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("Minimum")
    Value Minimum;
    @SerializedName("Maximum")
    Value Maximum;

    public Temperature(){
        Minimum = new Value();
        Maximum = new Value();
    }

    public Value getMaximum() {
        return Maximum;
    }

    public Value getMinimum() {
        return Minimum;
    }

    public void setMaximum(Value maximum) {
        Maximum = maximum;
    }

    public void setMinimum(Value minimum) {
        Minimum = minimum;
    }
}
