import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

import static javafx.scene.paint.Color.*;


public class QuizTest extends Application{
    // siin defineerin muutujad, mida pmst saab kasutada igas funktsioonis (seni)
    public Image pilt = new Image("file:Resources/taust.jpg");

    // need backgroundasjad tahavad kole palju muutujaid aga neist polegi vaja aru saada
    // see on lihtsalt taust ja eriti ei muutu (v.a tiitellehed kus on tavaline taust
    // ja mänguleht, kuhu peaks panema mingi kuldvillaku ruudustiku

    public BackgroundSize suurus = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
            false, false, true, true);
    public Background taust = new Background(new BackgroundImage(pilt,BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, suurus));
    public Font nupufont = Font.loadFont("file:Resources/peaFont.ttf", 25);
    public Font pealkirjafont = Font.loadFont("file:Resources/Pealkiri.ttf", 80);
    public Font hiscorefont = Font.loadFont("file:Resources/peaFont.ttf", 20);

    // hiscoresleht kuvab hiscored top 10 mängijatega
    public void hiscoresLeht(Stage pealava, Text pealkiri) throws IOException {
        pealkiri.setText("Skoorid");
        HiScores HighScores = new HiScores();
        List<Mängija> mängijad = HighScores.loeskoorid();
        Map<String, Integer> punktid = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            punktid.put(Integer.toString(i+1)+". " +mängijad.get(i).getNimi(),
                    mängijad.get(i).getPunktisumma());
        }
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(15);
        gridPane.setHgap(10);

        int rida = 1;
        // siin paneme gridpane'i mängijad ja nende kohad
        for (String nimi: punktid.keySet()){
            Label nimelabel = new Label(nimi);
            nimelabel.setFont(hiscorefont);
            nimelabel.setTextFill(GOLD);
            gridPane.add(nimelabel, 0, rida);
            String skoor = punktid.get(nimi).toString();
            Label skoorilabel = new Label(skoor);
            skoorilabel.setTextFill(GOLD);
            skoorilabel.setFont(hiscorefont);
            gridPane.add(skoorilabel, 1, rida);
            rida++;
        }

        gridPane.setAlignment(Pos.CENTER);
        BorderPane bp = new BorderPane();
        bp.setBackground(taust);
        bp.setTop(pealkiri);
        bp.setCenter(gridPane);
        bp.setAlignment(gridPane, Pos.CENTER);

        Button tagasi = new Button("Tagasi");
        tagasi.setFont(nupufont);
        tagasi.setOnMouseClicked(event -> tiitelLeht(pealava));

        bp.setBottom(tagasi);

        // stseeni suurus on suhtega 1.53, sest see on tausta mõõtude suhe
        // eriti vajalik pole selline täpsus aga vahet pole

        Scene stseen = new Scene(bp, 1000,652);
        pealava.setScene(stseen);
        pealava.show();
    }

    public void tiitelLeht(Stage pealava){
        Text pealkiri = new Text( "Kuldvillak");
        pealkiri.setFill(GOLD);
        pealkiri.setUnderline(true);
        pealkiri.setFont(pealkirjafont);

        Button start = new Button("Mängima");
        Button skoorid = new Button("Skoorid");
        skoorid.setOnMouseClicked(event -> {
            try {
                hiscoresLeht(pealava, pealkiri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button quit = new Button("Lahku");
        Button edit = new Button("Editor");
        Button[] nupud = {start, skoorid, quit,edit};

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50,50,50,50));
        vbox.setSpacing(25);
        vbox.getChildren().addAll(pealkiri,start,skoorid,edit,quit);
        vbox.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setTop(pealkiri);
        bp.setBackground(taust);
        bp.setAlignment(pealkiri, Pos.CENTER);

        // stseeni suurus on suhtega 1.53, sest see on tausta mõõtude suhe
        // eriti vajalik pole selline täpsus aga nii on vist parem
        Scene stseen = new Scene(bp,1000,652);

        for (Button nupp: nupud){
            nupp.setFont(nupufont);
        }

        pealava.setScene(stseen);
        pealava.show();
    }

    public void start(Stage pealava){
        tiitelLeht(pealava);
    }


    public static void main(String[] args) throws IOException {
        Scanner mängijasisend = new Scanner(System.in);
        System.out.println("Tere tulemast Suurepärasesse Quizi Mängu, kuidas võib sind kutsuda?");
        String mängijanimi = mängijasisend.nextLine();
        System.out.println();
        //mängijanimi võiks olla max 20 tähte, sest meil on kuni 26 kohta nime ja skoori vahel HiScore-s
        //kui sinna tühikuid piisavalt ei lähe, siis programm läheb tuksi HiScores-ega seotud ridadel
        System.out.println("Tere, " + mängijanimi + "!");
        System.out.println("*Mäng ei ole vastuste suhtes tõusutundlik*");
        Mängija player = new Mängija(mängijanimi, 0);
        System.out.println("Kirjuta Editor, kui soovid lisada uue teema+küsimused" + "\n" + "Kirjuta GUI");
        String mängijavalik = mängijasisend.nextLine();
        System.out.println();
        //teeme siin uue HighScores isendi, et hiljem saaks skoore ekraanil kuvada/faili kirjutada/failist lugeda
        HiScores HighScores = new HiScores();
        if (mängijavalik.toLowerCase().equals("")) {
            launch(args);
        }
        if (mängijavalik.toLowerCase().equals("play")) {
            Mäng uus = new Mäng();
            KuvaTeemad teemad = new KuvaTeemad();
            List<String> olemasolevadteemad = teemad.kuvateemad();
            //siin on failid stripitud .txt lõpust
            System.out.println("Teemade valik: " + olemasolevadteemad);
            System.out.println("Kas soovid teemad ise valida? (valin ise, suvalised)");
            String gamemode = mängijasisend.nextLine();
            System.out.println();

            if (gamemode.equals("valin ise")) {
                List<String> valitudteemad = new ArrayList<>();
                System.out.println("Kirjuta 3 soovitud teemat! (eralda tühikuga)");
                String[] teemadenimekiri = mängijasisend.nextLine().split(" ");
                System.out.println();
                for (String teema : teemadenimekiri) {
                    //teen kasutaja valitud teemadest listi
                    valitudteemad.add(teema);
                }
                for (String teema : valitudteemad) {
                    //siin küsitakse valitud teemade kohta küsimused (neid võiks olla valik ka shuffleda)
                    uus.alustaMängu(player, teema);
                }

            } else if (gamemode.equals("suvalised")) {
                //siin shuffleme nii palju teemasid, kui kasutaja soovib
                Collections.shuffle(olemasolevadteemad);
                System.out.println("Teemadeks on: "+ olemasolevadteemad.get(0)+", "+ olemasolevadteemad.get(1)+", "+ olemasolevadteemad.get(2));
                for (int i = 0; i < 3; i++) {
                    uus.alustaMängu(player, olemasolevadteemad.get(i));
                }
            }
            System.out.println("Tubli! Said 900st punktist kokku " + player.getPunktisumma());
            System.out.println();
            //siin on mäng läbi saanud ja teeme HighScoresi uue isendi, kuhu paneme mängija nime ja saavutused
            HighScores.mängijad.add(player);
        }

        else if (mängijavalik.toLowerCase().equals("editor")) {
            Editor uusteema = new Editor();
            uusteema.kirjutafail();
        }

        else if (mängijavalik.toLowerCase().equals("skoorid")){
            //siin lihtsalt laseme kõik HighScores listis olevad isendid reaks
            /*for (String rida: HighScores.loeskoorid().split("\n")){
                System.out.println(rida);
            }*/
            System.out.println();
            main(null);//see paneb psvm uuesti loopima
        }
        /*for (String rida: HighScores.loeskoorid().split("\n")){
            System.out.println(rida);
        }*/
        System.out.println("*PROGRAMMI LÕPP*" + "\n");
        main(null);//see paneb psvm uuesti loopima
    }
}