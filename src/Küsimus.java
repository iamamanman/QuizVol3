import java.util.List;

public class Küsimus {
    private String küsimus;
    private List<String> vastused;
    private int punktid;

    public Küsimus(String küsimus, List<String> vastused, int punktid) {
        this.küsimus = küsimus;
        this.vastused = vastused;
        this.punktid = punktid;
    }

    @Override
    public String toString() {
        return "Küsimus{" +
                "küsimus='" + küsimus + '\'' +
                ", vastused=" + vastused +
                "; punktid=" + punktid +
                '}';
    }

    public String getKüsimus() {
        return küsimus;
    }

    public void setKüsimus(String küsimus) {
        this.küsimus = küsimus;
    }

    public List<String> getVastused() {
        return vastused;
    }

    public void setVastused(List<String> vastused) {
        this.vastused = vastused;
    }

    public int getPunktid() {
        return punktid;
    }

    public void setPunktid(int punktid) {
        this.punktid = punktid;
    }
}
