package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.*;


@RestController
@RequestMapping("/callservice")
public class CallServiceController {

    private final RestTemplate restTemplate;

    public CallServiceController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private record ServiceRequest(String externalBaseUrl, String parameters) {}

    @PostMapping
    public ResponseEntity<?> callExtService(@RequestBody ServiceRequest request) {
        try {
            // Trim parameters and construct path
            String path = request.parameters().trim();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            URI uri = UriComponentsBuilder
                    .fromUriString(request.externalBaseUrl())
                    .path(path)
                    .build()
                    .encode()
                    .toUri();

            RequestEntity<Void> req = RequestEntity.get(uri)
                    .accept(MediaType.ALL)
                    .build();

            ResponseEntity<byte[]> response = restTemplate.exchange(req, byte[].class);

            MediaType contentType = response.getHeaders().getContentType();
            if (contentType == null) {
                contentType = MediaType.TEXT_PLAIN;
            }

            return ResponseEntity.status(response.getStatusCode())
                    .contentType(contentType)
                    .body(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(null);
        }
    }



}