package sample.resources;


public class Cement {
    double volume;
    double quality;
    double humidity;
    public Cement(double volume, double quality, double humidity){
        this.volume=volume;
        this.quality=quality;
        this.humidity=humidity;
    };

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
};
