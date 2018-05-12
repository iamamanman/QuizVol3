public class Mängija implements Comparable<Mängija>{
    private String nimi;
    private int punktisumma;

    public Mängija(String nimi, int punktisumma) {
        this.nimi = nimi;
        this.punktisumma = punktisumma;
    }

    public int getPunktisumma() {
        return punktisumma;
    }

    public void setPunktisumma(int punktisumma) {
        this.punktisumma = punktisumma;
    }

    public String getNimi() {
        return nimi;
    }

    @Override
    //siin otsustame, mis pidi Collections.sort töötab HiScore-de kirjutamisel/kuvamisel
    public int compareTo(Mängija o) {
        if (this.getPunktisumma() > o.getPunktisumma()){
            return -1;
        }
        else if (this.getPunktisumma()==o.getPunktisumma()){
            return 0;
        }
        else {
            return 1;
        }
    }
}
