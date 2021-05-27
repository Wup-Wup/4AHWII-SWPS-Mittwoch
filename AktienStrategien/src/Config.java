import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Config {

	public static String configRead(String key) {
		File Config = new File("C:\\Users\\berto\\Documents\\AktienNeu\\Config.txt");
		Properties properties = new Properties();

			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(Config));
			} catch (FileNotFoundException e) {
				System.out.println("Die Config File konnte nicht gefunden werden");
				System.exit(0);
			}
			try {
				properties.load(bis);
				bis.close();
				return properties.getProperty(key);
			}catch(Exception e){
				System.out.println("Fehler beim Auslesen der Config Datei");
				e.printStackTrace();
			}
			return null;
	}
}