package swag.rest.education_platform.service.internal;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swag.rest.education_platform.service.MeetingService;

import java.util.List;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final WebClient webClient;
    @Value("${baseUrl}")
    private String baseUrl;


    @Override
    public String generateMeeting() {
         return webClient.get()
                .uri(baseUrl).accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(String.class)
                 .map(response -> {
                     return response;
                 }).block();
    }
}
