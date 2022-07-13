package swag.rest.education_platform.service.internal;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.service.MeetingService;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    @Value("${videosdk.api_key_generated}")
    private String apiKey;

    @Value("${videosdk.secret_key_generated}")
    private String secret;



    @Override
    public String generateMeeting() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("apikey", apiKey);
        String token = Jwts.builder().setClaims(payload).setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 60000)))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
        return token;
    }
}
