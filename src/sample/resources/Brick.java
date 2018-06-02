package sample.resources;



public class Brick {
    double volume;
    double quality;
    public Brick(int volume, double quality){
        this.volume=volume;
        this.quality=quality;
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
};
