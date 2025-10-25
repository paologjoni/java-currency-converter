import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.json.JSONObject;

public class API {
  private String url;

  //Default constructor
  API() {
    this.url="";
  }

  //Constructor that sets url
  API(String url) {
    this.url=url;
  }

  //Setter and Getter for url
  public void setUrl(String url) {
    this.url=url;
  }

  public String getUrl() {
    return this.url;
  }

  //Method for api call
  public double call(String toCurrency) {
    HttpClient client=HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    HttpRequest request=HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .header("Content-Type","application/json") // Set request header
            .build();

    try {
      //Send request save response
      HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());

      //Extract status and response
      int statusCode=response.statusCode();
      String responseBody=response.body();

      //If status = 200 (successful)
      if(statusCode==200) {
        //Parse json
        JSONObject jsonObject=new JSONObject(responseBody);
        JSONObject eur=jsonObject.getJSONObject("eur");

        //Extract exchange rate
        double rate=eur.getDouble(toCurrency);

        //Print rate
        System.out.println("Rate: "+rate);

        //Return rate
        return rate;
      }
    } catch (Exception e) {
      //Print in case of error
      e.printStackTrace();
    }

    //Return 0 if no success
    return 0;
  }
}
