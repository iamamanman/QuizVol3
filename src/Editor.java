import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Editor {

    public void kirjutafail() throws IOException {
        while (true) {
            Scanner kasutaja = new Scanner(System.in);
            String kasutajaküsimused = ""; //siin tühi list, kuhu otsa lükkame kõik read, mis faili kirjutatakse (pärast eraldame märgiga "@")
            KuvaTeemad teemad = new KuvaTeemad();
            List<String> olemasolevadteemad = teemad.kuvateemad();
            System.out.println("Mis on teema nimeks, mida soovid lisada? Olemasolevad teemad: "+ olemasolevadteemad);
            String failinimi = kasutaja.nextLine();
            System.out.println();
            FileWriter fw = new FileWriter("Teemad/" + failinimi + ".txt");
            System.out.println("Vajuta lihtsalt 'Enter' klahvi, kui küsimused lisatud!");
            while (true) {
                System.out.println("Sisesta küsimuse andmed kujul: 'küsimus;õige vastus;vale vastus1;vale vastus2;vale vastus3;punktid'");
                String küss = kasutaja.nextLine();
                System.out.println();
                if (küss.equals("")) {
                    break;
                }
                kasutajaküsimused += küss + "@"; //lükkame "@" lõppu, et Editor klass oskaks eristada, mida peab eri ridadesse panema.
            }
            String[] failiread = kasutajaküsimused.split("@");
            for (String rida : failiread) {
                fw.write(rida + "\n");
            }
            fw.close();
            System.out.println("Kas soovid veel teemasi lisada? Kirjuta 'jah' või vajuta 'Enter klahvi");
            String otsus = kasutaja.nextLine();
            System.out.println();
            if (otsus.equals("")) {
                break;
            }
        }
    }
}