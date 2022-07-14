package swag.rest.education_platform.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeetingService {

    List<String> generateMeeting(Integer count);


}
