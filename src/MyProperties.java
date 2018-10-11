import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("project-name","Store");
        properties.setProperty("LogPath","C:\\Users\\mortl\\IdeaProjects\\JavaCourse\\log");
        FileWriter fileWriter = new FileWriter("conf.properties");
        properties.store(fileWriter,"Author: Avi & Vadim");
    }
}
