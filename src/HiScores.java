import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HiScores {
    List<Mängija> mängijad = new ArrayList<>();
    String skoorid;

    public List<Mängija> loeskoorid() throws IOException {
        java.io.File fail = new java.io.File("Resources/HiScores.txt");
        java.util.Scanner sc = new java.util.Scanner(fail);
        if (sc.hasNextLine()) {
            sc.nextLine();//skipime 1. rea, sest seal pole mängija skoorid
        }
        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            String tuvastus = rida.split(" ")[0]+" " +rida.split(" ")[1];//splitime selleks, et teada saada mängija nime pikkust
            //seda teeme, et panna iga nime vahele nii palju tühikuid, et punktid oleksid samal joonel vertikaalselt
            String tühikutearv = "";
            for (int i = 0; i < 26-tuvastus.length(); i++) {
                tühikutearv += " "; //siin teeme nii palju tühikuid kui vaja on, et skoorid ilusad oleksid
            }
            String[] andmed = rida.split(tühikutearv);
            String nimi = rida.split(" ")[1];
            int punktisumma = Integer.parseInt(andmed[1]);
            /*teeme igast leitud nimest ja skoorist mängija isendi, et saaks CompareTo meetodit (Mängija klassis) rakendada,
            selleks et saaksime skoorid hiljem reastada suurimast väiksemani. Kuna uue mängija skoor tuleb ka faili
            HiScores kirjutada, siis peamegi seda listi peale iga mängu sorteerima
             */
            mängijad.add(new Mängija(nimi, punktisumma));
        }
        Collections.sort(mängijad);
        FileWriter fw = new FileWriter("Hiscores.txt");
        String failiread = "Mängija"+"-------------------"+"Skoor"+"\n";
        for (int j = 0; j < mängijad.size(); j++) {
            String tühikutehulk = "";
            if(j<9){
                for (int i = 0; i < 26-mängijad.get(j).getNimi().length()-3; i++) {
                    tühikutehulk += " ";
                }//siin loome tühikute hulga nime ja skoori vahele ja siis lükkame selle stringi otsa
                //võib päris pikk string tulla, aga pole vahet, võiks ka String[]-ina storeda või
                //isegi teha uus klass Skoor, kus hoiame HiScores faili ridade isendeid (nimi, tühikud, punktid)
            }
            else if(j<99){
                for (int i = 0; i < 26-mängijad.get(j).getNimi().length()-4; i++) {
                    tühikutehulk += " ";
                }
            }
            else if (j<999){
                for (int i = 0; i < 26-mängijad.get(j).getNimi().length()-5; i++) {
                    tühikutehulk += " ";
                }
            }
            failiread += (j+1)+". "+mängijad.get(j).getNimi()+tühikutehulk+ mängijad.get(j).getPunktisumma()+"\n";

        }

        fw.write(failiread);
        fw.close();
        return mängijad;
    }
}