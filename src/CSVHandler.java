// this file has been taken directly from the bookshop example with little change carried out

import java.io.*;
import java.util.ArrayList;

public class CSVHandler {

    public static ArrayList<String> readLines(String path) {
        ArrayList<String> lines = new ArrayList<String>();
        File file = new File(path);
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLines(String path, ArrayList<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, false))) {
            for (int i = 0; i < lines.size(); i++) {
                pw.println(lines.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendLine(String path, String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, true))) { // append = true
            pw.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createFileIfNotExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + filename);
            e.printStackTrace();
        }
    }
}
