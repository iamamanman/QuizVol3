import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Mäng {

    public String märgid (int a, String b){
        String märgid = "";
        for (int i = 0; i < a; i++) {
            märgid += b;
        }
        return märgid;
    }
    //siin tavaline loop ühe teema kohta
    public void alustaMängu(Mängija mängija, String failinimi) throws FileNotFoundException {
        List<Küsimus> küsimused1 = loeKüsimused("Teemad/"+failinimi+".txt");
        //tekitame tühja listi, kuhu panna klassi Editor isendid, mille parameetrite järgi teeme uue faili
        for (Küsimus küsimus: küsimused1){//siin defineeritakse, et Listi küsimus.getVastused
            // indeksil 0 on õige vastus
            Scanner scan = new Scanner(System.in);
            String õigevastus = (küsimus.getVastused()).get(0);
            List<String> vastused = küsimus.getVastused();
            //siin shuffletakse ära, et alati poleks esimesel kohal õige vastus
            Collections.shuffle(vastused);
            int küsimusepikkus = küsimus.getKüsimus().length() + failinimi.length()+ 4;
            // Algne tekst + alguses ja lõppus "|" märk + pärast iga vastust(va viimane) koma ja tühik
            int vastusepikkus = ("Vastusevariandid: ").length() +2 +(vastused.size()-1)*2;
            for(String vastus:vastused){
                vastusepikkus += vastus.length();
            }
            int max = 0;

            if (küsimusepikkus > vastusepikkus){
                max += küsimusepikkus;
            }
            else {
                max += vastusepikkus;
            }
            //kriipsude arv vastavalt sellele kas küsmiuste või vastuste rida on pikem.
            System.out.println(märgid(max, "-"));
            System.out.println("|"+failinimi.substring(0, 1).toUpperCase()+failinimi.substring(1)+": "+küsimus.getKüsimus()+märgid(max-küsimusepikkus, " ")+"|");

            System.out.print("|Vastusevariandid: ");
            for (int i = 0; i < vastused.size()-1; i++) {
                System.out.print(vastused.get(i)+", ");
            }
            System.out.println(vastused.get(vastused.size()-1)+märgid(max-vastusepikkus," ")+"|");

            System.out.print(märgid(max, "-"));
            System.out.println();
            System.out.print("Vastus: ");
            String vastus = scan.nextLine();
            //järgnevalt kontrollitakse, kas õigevastus ja vastus(kasutaja oma) on sama väärtusega
            if ((vastus.toLowerCase().replace(" ","").equals(õigevastus.toLowerCase().replace(" ", "")))) {
                mängija.setPunktisumma(mängija.getPunktisumma()+küsimus.getPunktid());
                System.out.println("Vastasid õigesti, +" + küsimus.getPunktid() + "p");
            }
            else{
                System.out.println("Vale vastus! " + "Õige vastus oleks olnud: "+ õigevastus);
            }
        }
    }

    //siin loeme lihtsalt antud teema nimega faili sisse
    public static List<Küsimus> loeKüsimused(String failinimi) throws FileNotFoundException {
        java.io.File fail = new java.io.File(failinimi);
        java.util.Scanner sc = new java.util.Scanner(fail);
        List<Küsimus> küsimused = new ArrayList<>();
        List<Küsimus> küsimused50 = new ArrayList<>();
        List<Küsimus> küsimused100 = new ArrayList<>();
        List<Küsimus> küsimused150 = new ArrayList<>();

        while (sc.hasNextLine()) {
            String rida = sc.nextLine();
            List<String> vastused = new ArrayList<>();
            String[] tükid = rida.split(";");
            String küsimus = tükid[0];
            int punktid = Integer.parseInt(tükid[tükid.length-1]);
            // kolm erinevat listi erinevate punktisummade jaoks, et saaks pärast igast ühe suvalise võtta.
            if (punktid == 50){
                for (int i = 1; i < tükid.length-1; i++) {
                    vastused.add(tükid[i]);
                }
                küsimused50.add(new Küsimus(küsimus, vastused, punktid));
            }
            else if (punktid == 100){
                for (int i = 1; i < tükid.length-1; i++) {
                    vastused.add(tükid[i]);
                }
                küsimused100.add(new Küsimus(küsimus, vastused, punktid));
            }
            else if (punktid == 150){
                for (int i = 1; i < tükid.length-1; i++) {
                    vastused.add(tükid[i]);
                }
                küsimused150.add(new Küsimus(küsimus, vastused, punktid));
            }

        }

        küsimused.add(küsimused50.get((int)Math.round(Math.random()*(küsimused50.size()-1))));
        küsimused.add(küsimused100.get((int)Math.round(Math.random()*(küsimused100.size()-1))));
        küsimused.add(küsimused150.get((int)Math.round(Math.random()*(küsimused150.size()-1))));
        return küsimused;
    }
}