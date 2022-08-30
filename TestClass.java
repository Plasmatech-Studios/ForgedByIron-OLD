import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

public class TestClass implements Config {

    public static void main(String[] args) {
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("http://fbi.sytes.net/api/")) //getting the correct Route
          //.uri(URI.create("http://localhost:5000/FBI_AUTH")) //for local testing
          .header("x-fbi-username", "Pete")
          .header("x-fbi-secretkey", "123456789")
          //.POST(HttpRequest.BodyPublishers.
          .header("data", "{'name': 'test', 'age': 20}")
          .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
        System.out.println("Error: " + e);
        }

        request = HttpRequest.newBuilder()
          .uri(URI.create("http://fbi.sytes.net/api/")) //getting the correct Route
          //.uri(URI.create("http://localhost:5000/hcs")) //for local testing
          .POST(HttpRequest.BodyPublishers.ofString("{'name': 'test', 'age': 20}"))
          .header("x-fbi-command", "Pete")
          .header("x-fbi-uniqueid", "123456789")          
          .header("x-fbi-username", "Pete")
          .header("x-fbi-secretkey", "123456789")
          //.POST(HttpRequest.BodyPublishers.
          .header("data", "{'name': 'test', 'age': 10}")
          .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    
    
        //System.out.println(response.body()); //wont give me anything because its a sqlite db
        //   need to find a way to secure the sqlite db



        // String body = "Hi Server";   
        // // create a post request
        // HttpRequest request = HttpRequest.newBuilder()
        //         .uri(URI.create("http://localhost:5000/"))
        //         .headers("Content-Type", "text/plain;charset=UTF-8")
        //         .POST(HttpRequest.BodyPublishers.ofString(body))
        //         .build();
        
        // // send the request
        // try {
        // HttpClient client = HttpClient.newHttpClient();
        // HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.body());
        // } catch (Exception e) {
        //    System.out.println("Error");
        // }
    }


        
    
}