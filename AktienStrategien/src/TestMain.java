import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDate;

import database.Database;

public class TestMain {
	public static void main(String[] args) throws MalformedURLException, IOException {
		
		String aktie;
		String end = "%";
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("Config.txt"));
			while ((aktie = in.readLine()) != null) {
				if (!aktie.endsWith(end)) {
					continue;
				}
				aktie = aktie.replace(end, "");
		Database dataB1 = new Database(Config.configRead("sqlUsername"),Config.configRead("sqlPassword"), Config.configRead("DataBaseName"), Config.configRead("ConnectionUrl"));
		dataB1.openConnection();
	//	dataB1.dropTable("aktie");
		dataB1.createTable(aktie);
		dataB1.firstEntry(aktie, BigDecimal.valueOf(10000)); 
		dataB1.getInsertData(LocalDate.parse("2018-06-29"), aktie);
		dataB1.closeConnection();
			}	
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
					in.close();
			}
		}
	}
}
