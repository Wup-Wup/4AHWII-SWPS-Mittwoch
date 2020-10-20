import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class TestMain extends Application{
	
	public static void main(String[] args) throws Exception {
		LookAtDates.finalDays(LocalDate.of(2020, 1, 1), 1);
		LookAtDates.ausgabe();
		
		launch(args);
	}
	
	
	 @Override
	    public void start(Stage stage) throws Exception {
	        String mo="Monday", tu="Tuesday", we="Wednesday", th="Thursday", fr="Friday";

	        final NumberAxis xAxis = new NumberAxis();
	        final CategoryAxis yAxis = new CategoryAxis();

	        final BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);
	        barChart.setTitle("Free Weekdays");
	        xAxis.setLabel("Weekdays");
	        yAxis.setLabel("Days");

	        XYChart.Series series1 = new XYChart.Series();
	        series1.getData().add(new XYChart.Data(LookAtDates.frih2, fr));
	        series1.getData().add(new XYChart.Data(LookAtDates.thuh2, th));
	        series1.getData().add(new XYChart.Data(LookAtDates.wedh2, we));
	        series1.getData().add(new XYChart.Data(LookAtDates.tueh2, tu));
	        series1.getData().add(new XYChart.Data(LookAtDates.monh2, mo));

	        Scene scene = new Scene(barChart, 640, 480);
	        barChart.getData().addAll(series1);
	        stage.setScene(scene);
	        stage.show();
	    }
}
