import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

public class CreateSet {
    public void makeFile()  {
        try{
            File mySet = new File ("filename.txt");
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
            } else {
                System.out.println("File already exists.");
        }
            } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String name, String string) {
        Path path = Path.of("./Sets/"+ name + ".txt");
        try {
            Files.writeString(path, string, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }

    }
}

