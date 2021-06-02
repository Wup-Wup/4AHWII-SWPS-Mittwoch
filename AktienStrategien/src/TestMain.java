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
				
				System.out.println("Aktie: "+aktie+"\nStartgeld: "+Config.configRead("StartMoney")+"€\nStartdatum: "+Config.configRead("StartDate")+"\n");	

				
				Strat200 strat200 = new Strat200(Config.configRead("sqlUsername"), Config.configRead("sqlPassword"),
						Config.configRead("DataBaseName"), Config.configRead("ConnectionUrl"));
				strat200.openConnection();
				strat200.dropTable(aktie + "200strat");
				strat200.createTable(aktie);
				strat200.firstEntry(aktie, BigDecimal.valueOf(Integer.parseInt(Config.configRead("StartMoney"))));
				strat200.getInsertData(LocalDate.parse(Config.configRead("StartDate")), aktie);
				strat200.print(aktie);
				strat200.closeConnection();

				Strat200optimized strat200optimized = new Strat200optimized(Config.configRead("sqlUsername"),
						Config.configRead("sqlPassword"), Config.configRead("DataBaseName"),
						Config.configRead("ConnectionUrl"));
				strat200optimized.openConnection();
				strat200optimized.dropTable(aktie + "200stratOptimized");
				strat200optimized.createTable(aktie);
				strat200optimized.firstEntry(aktie,
						BigDecimal.valueOf(Integer.parseInt(Config.configRead("StartMoney"))));
				strat200optimized.getInsertData(LocalDate.parse(Config.configRead("StartDate")), aktie);
				strat200optimized.print(aktie);
				strat200optimized.closeConnection();

				BuyAndHold buyandhold = new BuyAndHold(Config.configRead("sqlUsername"),
						Config.configRead("sqlPassword"), Config.configRead("DataBaseName"),
						Config.configRead("ConnectionUrl"));
				buyandhold.openConnection();
				buyandhold.dropTable(aktie + "200BuyAndHold");
				buyandhold.createTable(aktie);
				buyandhold.firstAndLastInsert(LocalDate.parse(Config.configRead("StartDate")), aktie,
						BigDecimal.valueOf(Integer.parseInt(Config.configRead("StartMoney"))));
				buyandhold.print(aktie);
				buyandhold.closeConnection();
				}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}
