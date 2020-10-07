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
		LookAtDates.finalDays(LocalDate.now(), 10);
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
	        series1.getData().add(new XYChart.Data(LookAtDates.mon, mo));
	        series1.getData().add(new XYChart.Data(LookAtDates.tue, tu));
	        series1.getData().add(new XYChart.Data(LookAtDates.wed, we));
	        series1.getData().add(new XYChart.Data(LookAtDates.tue, th));
	        series1.getData().add(new XYChart.Data(LookAtDates.fri, fr));

	        Scene scene = new Scene(barChart, 640, 480);
	        barChart.getData().addAll(series1);
	        stage.setScene(scene);
	        stage.show();
	    }
}
