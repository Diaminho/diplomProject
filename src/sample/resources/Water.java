package sample.resources;

public class Water {
    double volume;
    double quality;

    public Water(double vol, double qual){
        setQuality(qual);
        setVolume(vol);
    }

    public Water(){
        setQuality(0);
        setVolume(0);
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
