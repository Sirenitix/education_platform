package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swag.rest.education_platform.dao.ProjectMessageRepository;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectMessagesDto;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectMessageService {
    private final ProjectMessageRepository repository;
    private final ProjectStudentService projectStudentService;
    private final UserService userService;

    public List<?> getMessages(Long project_id) {
        ProjectStudent project = projectStudentService.getProjectById(project_id);
        List<ProjectMessage> messages = project.getMessages();
//        List<ProjectMessagesDto> dto = new ArrayList<>();
//
//        for (ProjectMessage message : messages) {
//            String username = userService.findById(message.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found")).getUsername();
//            dto.add(ProjectMessagesDto.builder()
//                    .content(message.getText())
//                    .date(message.getDate())
//                    .user_id(message.getId())
//                    .username(username)
//                    .build());
//        }
        return messages;
    }

    public void addMessage(Long project_id, String text, String username, String title) {
        ProjectStudent project = projectStudentService.getProjectById(project_id);
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ProjectMessage message = new ProjectMessage();
        message.setDate(LocalDate.now());
        message.setText(text);
        message.setUserfirstname(user.getFirstname());
        message.setUserlastname(user.getLastname());
        message.setUser(user);
        message.setProject(project);
        message.setTitle(title);
        repository.save(message);
    }
}
