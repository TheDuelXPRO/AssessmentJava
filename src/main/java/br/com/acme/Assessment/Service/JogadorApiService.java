package br.com.acme.Assessment.Service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class JogadorApiService {
    HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://api-football-beta.p.rapidapi.com/teams/statistics?team=33&season=2019&league=39"))
            .header("X-RapidAPI-Key", "9adfb87511msh98c46970e31e518p1d8c81jsncf519a93fe60")
            .header("X-RapidAPI-Host", "api-football-beta.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .header("Accept", "application/java")
            .build();
    HttpResponse<String> response;

    {
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public HttpResponse<String> response() {
        return response;
    }
}

