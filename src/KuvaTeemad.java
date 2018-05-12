import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KuvaTeemad {

    //prindime Teemad kaustast teemade nimed ilma .txt-ta
    public List<String> kuvateemad(){
        File kaust = new File("Teemad/");
        List<String> teemad = new ArrayList<>();
        for (File file: kaust.listFiles()){
            if (file.getName().endsWith(".txt")){
                teemad.add(file.getName().replace(".txt",""));
            }
        }
        return teemad;
    }
}
