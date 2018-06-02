package sample.resources;

public class Raw {
    double volume;
    double quality;

    public Raw(double vol, double qual){
        setQuality(qual);
        setVolume(vol);
    }

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
}