import com.twilio.rest.api.v2010.account.Message;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;


public class Twilio {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACdaec9e6564de3c95258cad179a02f3c2";
    // System.getenv("TWILIO_ACCOUNT_SID")
    public static final String AUTH_TOKEN = "0b80bde81e4e6370b0af6a754e5b4489";
    // System.getenv("TWILIO_AUTH_TOKEN")

    public static void main(String[] args) {
        try {
            // Set up the API URL
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=40.7128&" +
                    "lon=-74.0060&appid=32b9a9ac241b92496b0c9e9c74710aa6&units=imperial";

            // open connection to API
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // set request method
            con.setRequestMethod("GET");

            // send request in and get a response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            // read through all the response, and add to stringbuilder
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Convert StringBuilder to string
            String jsonString = content.toString();

            // Create a JSONObject from the JSON string
            JSONObject json = new JSONObject(jsonString);
            double temp = json.getJSONObject("main").getDouble("temp");

            // Print the current temperature
            System.out.println("Current temperature in NYC: " + temp + " degrees Fahrenheit");

            // Print the current time
            System.out.println("Current time: " + new Date());


            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 2);

            com.twilio.Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("+19176341367"),
                            new com.twilio.type.PhoneNumber("+19313295556"),
                            "Current time: " + new Date() + "\n" +
                                    "Current temperature in NYC: " + temp + " degrees Fahrenheit")
                    .create();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

