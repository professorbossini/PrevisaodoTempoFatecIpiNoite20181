package br.com.bossini.previsaodotempofatecipinoite20181;

/**
 * Created by rodrigo on 06/04/18.
 */

public class Previsao {
    private long dt;
    private double min, max;
    private int humidity;
    private String description, icon;

    public Previsao (){

    }

    public Previsao (long dt, double min, double max,
                        int humidity, String description,
                                String icon){
        setDt(dt);
        setMin(min);
        setMax(max);
        setHumidity(humidity);
        setDescription(description);
        setIcon(icon);
    }
    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
