package Main;

import java.text.NumberFormat;

public class ProfitPrint {
	
	
	static String profit200;
	static String profit200opt;
	static String profitbuy;
	static double sumStartMoney = 0;
	static  NumberFormat n = NumberFormat.getInstance();

	
	public static void profitPrint(int counter, double startMoney, double strat200d, double strat200opt, double buyAndHold) {
		
		n.setMaximumFractionDigits(2);

		
		sumStartMoney = Double.valueOf(startMoney)*counter;
		profit200 = n.format(strat200d-sumStartMoney);
		profit200opt = n.format(strat200opt-sumStartMoney);
		profitbuy = n.format(buyAndHold-sumStartMoney);
		
		System.out.println("\n \n");
		System.out.println("200Strat:"); 	
		System.out.println("Endgeld: "+n.format(strat200d)+"€");
		System.out.println("Absoluter Gewinn: "+ profit200+"€");
		System.out.println("Prozentueller Gehalt: "+strat200d*100/sumStartMoney+"% \n");
		System.out.println("200StratOptimized:");
		System.out.println("Endgeld: "+n.format(strat200opt)+"€");
		System.out.println("Absoluter Gewinn: "+ profit200opt+"€");
		System.out.println("Prozentueller Gehalt: "+strat200opt*100/sumStartMoney+"% \n");
		System.out.println("BuyAndHold:");
		System.out.println("Endgeld: "+n.format(buyAndHold)+"€");
		System.out.println("Absoluter Gewinn: "+ profitbuy+"€");
		System.out.println("Prozentueller Gehalt: "+buyAndHold*100/sumStartMoney+"% \n");
	}

}
