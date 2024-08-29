package com.bigfoot.tenantmonitor.client.jwt;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class JwtInterceptor implements ClientHttpRequestInterceptor {
    private final JwtTokenService jwtTokenService;
    List<String> tokenWhiteList = List.of("/api/v1/register", "/api/v1/login"); //JWT not needed for these endpoints

    public JwtInterceptor(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if(tokenWhiteList.contains(request.getURI().getPath())){
            return execution.execute(request,body);
        }

        request.getHeaders().setBearerAuth(JwtStore.getAccessToken());

        ClientHttpResponse response = execution.execute(request, body);
        HttpStatusCode statusCode = response.getStatusCode();
        if(statusCode.isSameCodeAs(HttpStatusCode.valueOf(401)) || statusCode.isSameCodeAs(HttpStatusCode.valueOf(403))){
            System.out.println("Getting refresh token");
            //fetch new token && send request again
            String refreshToken = jwtTokenService.refreshToken(JwtStore.getRefreshToken());
            request.getHeaders().setBearerAuth(refreshToken);
            response = execution.execute(request, body);
        }

        return response;

    }
}
