import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import org.json.JSONException;

public class TestMain {
	
	public static void main(String[] args) throws MalformedURLException, JSONException, IOException {
		LookAtDates.finalDays(LocalDate.now(), 10);
		LookAtDates.ausgabe();
	}
}
