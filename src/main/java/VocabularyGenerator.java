import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VocabularyGenerator {

    public static void main(String[] args) {

        String dirLocation = "C:\\Users\\Damien\\Documents\\M2\\PRM\\TP2\\Buffer-in";
        List<String> vocabulaire = new ArrayList<>();

        try {
            List<File> files = Files.list(Paths.get(dirLocation))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            for (File file : files){
                try {
                    PDDocument document = PDDocument.load(file);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String text = pdfStripper.getText(document);
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode(text);
                    text = StandardCharsets.UTF_8.decode(buffer).toString();
                    text = text.replace(",", "").replace(".", "").replace("\n", "");
                    List<String> array = Arrays.asList(text.split(" "));

                    array = array.stream().filter(s -> s.length() >4 && s.length() < 15 && !s.contains("\t") ).collect(Collectors.toList());

                    for ( String mot : array){
                        if (!vocabulaire.contains(mot) && !mot.matches(".*(\t|\n|\r|!|$|#|:|-|_|').*"))
                            vocabulaire.add(mot);
                    }
                    document.close();

                }catch (Exception ignored){

                }
            }


        } catch (IOException e) {
            // Error while reading the directory
        }
        System.out.println(vocabulaire.toString());

        try {
            // To overwrite
            Files.write(Paths.get("vocabulary.txt"), vocabulaire.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
