package Main;

public class ProfitPrint {
	
	
	static double profit200 = 0;
	static double profit200opt = 0;
	static double profitbuy = 0;
	static double sumStartMoney = 0;
	
	public static void profitPrint(int counter, double startMoney, double strat200d, double strat200opt, double buyAndHold) {
		
		sumStartMoney = Double.valueOf(startMoney)*counter;
		profit200 = strat200d-sumStartMoney;
		profit200opt = strat200opt-sumStartMoney;
		profitbuy = buyAndHold-sumStartMoney;
		
		System.out.println("\n \n");
		System.out.println("200Strat:");
		System.out.println("Endgeld: "+strat200d+"€");
		System.out.println("Absoluter Gewinn: "+ profit200+"€");
		System.out.println("Prozentueller Gehalt: "+strat200d*100/sumStartMoney+"% \n");
		System.out.println("200StratOptimized:");
		System.out.println("Endgeld: "+strat200opt+"€");
		System.out.println("Absoluter Gewinn: "+ profit200opt+"€");
		System.out.println("Prozentueller Gehalt: "+strat200opt*100/sumStartMoney+"% \n");
		System.out.println("BuyAndHold:");
		System.out.println("Endgeld: "+buyAndHold+"€");
		System.out.println("Absoluter Gewinn: "+ profitbuy+"€");
		System.out.println("Prozentueller Gehalt: "+buyAndHold*100/sumStartMoney+"% \n");
	}

}
