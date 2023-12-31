import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {

    Path filePath;

    public Reader(String filePath){
        this.filePath = Path.of(filePath);
    }

    public Queue<Process> read(){

        Queue<Process> processes = new LinkedList<>();

        try {
            // CSV dosyasını satırlara ayır ve listeye ekle
            List<String> lines = Files.lines(filePath)
                    .collect(Collectors.toList());

            // Her bir satırı işle
            for (String line : lines) {
                // Satırı virgül veya istediğin ayırıcıya göre parçala
                String[] columns = line.split(",");

                processes.add(new Process(
                    Integer.parseInt(columns[0].trim()),
                    Integer.parseInt(columns[1].trim()),
                    Integer.parseInt(columns[2].trim()),
                    Integer.parseInt(columns[3].trim()),
                    Integer.parseInt(columns[4].trim()),
                    Integer.parseInt(columns[5].trim()),
                    Integer.parseInt(columns[6].trim()),
                    Integer.parseInt(columns[7].trim())
                ));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return processes;

    }




}