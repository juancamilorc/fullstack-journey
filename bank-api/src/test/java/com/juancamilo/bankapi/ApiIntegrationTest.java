package com.juancamilo.bankapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    ObjectMapper objectMapper; // viene por Jackson (starter-web)

    private final HttpClient http = HttpClient.newHttpClient();

    @Test
    void withdraw_createsMovement() throws Exception {
        String base = "http://localhost:" + port;

        // 1) create client
        var res1 = post(base + "/api/v1/clients",
                """
                { "id": "1", "name": "Juan", "document": "123" }
                """);
        assertThat(res1.statusCode()).isEqualTo(201);

        // 2) create account
        var res2 = post(base + "/api/v1/clients/1/accounts",
                """
                { "number": "ACC-100", "type": "AHORROS" }
                """);
        assertThat(res2.statusCode()).isEqualTo(201);

        // 3) deposit
        var res3 = post(base + "/api/v1/accounts/ACC-100/deposit",
                """
                { "amount": 5000 }
                """);
        assertThat(res3.statusCode()).isEqualTo(200);

        // 4) withdraw
        var res4 = post(base + "/api/v1/accounts/ACC-100/withdraw",
                """
                { "amount": 2000 }
                """);
        assertThat(res4.statusCode()).isEqualTo(200);

        // 5) list movements and assert order (RETIRO first)
        var res5 = get(base + "/api/v1/accounts/ACC-100/movements");
        assertThat(res5.statusCode()).isEqualTo(200);

        JsonNode arr = objectMapper.readTree(res5.body());
        assertThat(arr.isArray()).isTrue();
        assertThat(arr.size()).isGreaterThanOrEqualTo(2);

        assertThat(arr.get(0).get("type").asText()).isEqualTo("RETIRO");
        assertThat(arr.get(0).get("amount").asLong()).isEqualTo(2000);
        assertThat(arr.get(0).get("resultingBalance").asLong()).isEqualTo(3000);

        assertThat(arr.get(1).get("type").asText()).isEqualTo("DEPOSITO");
    }

    @Test
    void withdraw_insufficientFunds_returns409_withCode() throws Exception {
        String base = "http://localhost:" + port;

        // create client
        var res1 = post(base + "/api/v1/clients",
                """
                { "id": "2", "name": "Camilo", "document": "456" }
                """);
        assertThat(res1.statusCode()).isEqualTo(201);

        // create account
        var res2 = post(base + "/api/v1/clients/2/accounts",
                """
                { "number": "ACC-200", "type": "AHORROS" }
                """);
        assertThat(res2.statusCode()).isEqualTo(201);

        // withdraw without deposit => 409
        var res3 = post(base + "/api/v1/accounts/ACC-200/withdraw",
                """
                { "amount": 1000 }
                """);
        assertThat(res3.statusCode()).isEqualTo(409);

        JsonNode err = objectMapper.readTree(res3.body());
        assertThat(err.get("code").asText()).isEqualTo("INSUFFICIENT_FUNDS");
    }

    // ---------------- helpers ----------------

    private HttpResponse<String> post(String url, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return http.send(req, HttpResponse.BodyHandlers.ofString());
    }
}