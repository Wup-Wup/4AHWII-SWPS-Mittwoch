package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.time.LocalDate;

import investStrats.BuyAndHold;
import investStrats.Strat200;
import investStrats.Strat200optimized;

public class TestMain {
	
	static LocalDate startDate;
	
	static String aktie;
	static String end = "%";
	
	static String connectionURL;
	static String sqlUsername;
	static String sqlPassword;
	
	static double strat200d = 0;
	static double strat200opt = 0;
	static double buyAndHold = 0;
	
	static double startMoney = 0;
	
	static int counter=0;
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		
		connectionURL = Config.configRead("ConnectionUrl");
		sqlUsername = Config.configRead("sqlUsername");
		sqlPassword = Config.configRead("sqlPassword");
		startMoney = Double.valueOf(Config.configRead("StartMoney"));
		startDate = LocalDate.parse(Config.configRead("StartDate"));
		
		Database.openConnection(connectionURL,sqlUsername,sqlPassword);

		BufferedReader in = null;
		try {

			in = new BufferedReader(new FileReader("Config.txt"));
			
			while ((aktie = in.readLine()) != null) {
				if (!aktie.endsWith(end)) {
					continue;
				}
				aktie = aktie.replace(end, "");
				
				System.out.println("Aktie: "+aktie+"\nStartgeld: "+startMoney+"€\nStartdatum: "+Config.configRead("StartDate")+"\n");
				
				Database.dropTable(aktie + "200strat");
				Database.createTable(aktie+"200strat");
				Strat200.firstEntry(aktie, BigDecimal.valueOf(startMoney));
				if(Strat200.getInsertData(startDate, aktie)) {
					continue;
				}
				strat200d = strat200d + Database.print(aktie,"200strat",startMoney);

				Database.dropTable(aktie + "200stratOptimized");
				Database.createTable(aktie+"200stratOptimized");
				Strat200optimized.firstEntry(aktie,BigDecimal.valueOf(startMoney));
				Strat200optimized.getInsertData(startDate, aktie);
				strat200opt = strat200opt + Database.print(aktie, "200stratOptimized" ,startMoney);
				
				Database.dropTable(aktie + "BuyAndHold");
				Database.createTable(aktie + "BuyAndHold");
				BuyAndHold.firstAndLastInsert(startDate, aktie,startMoney);
				buyAndHold = buyAndHold+ Database.print(aktie,"BuyAndHold",startMoney);
				
				counter++;
				}
			
			ProfitPrint.profitPrint(counter , Double.valueOf(startMoney), strat200d, strat200opt, buyAndHold);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		Database.closeConnection();
	}
}
