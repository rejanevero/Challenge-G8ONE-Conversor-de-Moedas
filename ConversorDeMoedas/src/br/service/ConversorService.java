package br.service;

import br.model.Conversao;
import br.model.ConversaoResposta;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorService {
    private String apiKey = "41faf132b3d2511951710603";

    public Conversao converter(String de, String para, double valor) throws IOException, InterruptedException {
        String url="https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + de;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ConversaoResposta resposta = gson.fromJson(response.body(),ConversaoResposta.class);

        double taxa = resposta.conversion_rates().get(para);
        double valorConverido =  valor * taxa;

        return new Conversao(valorConverido);

    }


}
