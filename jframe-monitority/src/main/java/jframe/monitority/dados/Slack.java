/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jframe.monitority.dados;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 *
 * @author guill
 */
public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static final String URL = "https://hooks.slack.com/services/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";
    
    public static void sendMessage(JSONObject content) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("text", " ");
        Slack.sendMessage(json);
    }
}
