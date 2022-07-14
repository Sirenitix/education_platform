package swag.rest.education_platform.service.internal;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import swag.rest.education_platform.entity.MeetResponse;
import swag.rest.education_platform.exception.MeetingApiFailure;
import swag.rest.education_platform.service.MeetingService;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    @Value("${meet.baseUrl}")
    private String base_url;

    @Value("${rapid.api.key}")
    private String rapid_api_key;

    @Value("${rapid.api.host}")
    private String rapid_api_host;

    private final WebClient webClient;

    @Override
    public List<String> generateMeeting(Integer count) {
        Map<String, Integer> countRequest = new HashMap<>();
        countRequest.put("count", count);
         List<String> links = webClient.post()
                .uri(base_url)
                .header("X-RapidAPI-Key" , rapid_api_key)
                .header("Content-Type" , "application/json")
                .header("X-RapidAPI-Host" , rapid_api_host)
                .body(Mono.just(countRequest), Map.class)
                .retrieve()
                .bodyToMono(MeetResponse.class)
                .blockOptional()
                .map(response -> {
                    log.info(response + " - google meet response");
                    return response.getMeetings();
                })
                .orElseThrow(MeetingApiFailure::new);
        List<String> list = links.stream().map(q -> q.substring(0 , q.length() - 0)).collect(Collectors.toList());
        return list;
    }
}
