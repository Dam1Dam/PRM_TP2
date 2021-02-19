import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignGenerator {

    public static void main(String[] args) throws IOException {

        String dirLocation = "C:\\Users\\Damien\\Documents\\M2\\PRM\\TP2\\Buffer-in";
        String vocLocation = "C:\\Users\\Damien\\Documents\\M2\\PRM\\MavenProject\\simpleVoc.txt";

        List<String> vocabulary = Files.readAllLines(new File(vocLocation).toPath(), StandardCharsets.UTF_8);

        HashMap<String, int[]>  mapVectors = new HashMap<>();
        try {
            List<File> files = Files.list(Paths.get(dirLocation))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());


            for (File file : files) {
                int[] vector = new int[vocabulary.size()];
                PDDocument document = PDDocument.load(file);
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);
                ByteBuffer buffer = StandardCharsets.UTF_8.encode(text);
                text = StandardCharsets.UTF_8.decode(buffer).toString();

                int i =0;
                for(String word : vocabulary){

                    if(text.contains(word)){
                        vector[i] = StringUtils.countMatches(text, word);
                    }
                    i++;
                }

                mapVectors.put(file.getName(),vector.clone());
                document.close();
            }
        } catch (IOException e) {
            // Error while reading the directory
        }

        for (Map.Entry<String, int[]> entry : mapVectors.entrySet()){

            List<String> fichierCommun = mapVectors.entrySet()
                    .stream()
                    .filter(e -> !e.getKey().equals(entry.getKey()) && Arrays.equals(e.getValue(), entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (fichierCommun.size() >0)
                System.out.println(entry.getKey() + " a " + fichierCommun.size()
                        + " fichier semblable d'après notre vocabulaire :"
                        + fichierCommun.toString());
        }





    }


}
