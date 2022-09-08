package swag.rest.education_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swag.rest.education_platform.dao.ProjectMessageRepository;
import swag.rest.education_platform.dto.ProjectPostDto;
import swag.rest.education_platform.entity.ProjectMessage;
import swag.rest.education_platform.entity.ProjectMessagesDto;
import swag.rest.education_platform.entity.ProjectStudent;
import swag.rest.education_platform.entity.Users;
import swag.rest.education_platform.exception.PostNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectMessageService {
    private final ProjectMessageRepository repository;
    private final ProjectStudentService projectStudentService;
    private final UserService userService;
    String baseUrl = "http://159.89.104.8:8022";

    @Transactional
    public List<?> getMessages(Long project_id) {
        List<ProjectMessage> messages = repository.findByProject_Id(project_id);
        Collections.sort(messages);
        return messages;

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

    }

    @Transactional
    public ProjectMessage getMessage(Long message_id) {
        return repository.getById(message_id);
    }

    @Transactional
    public void addMessage(ProjectPostDto projectPostDto, String username) throws IOException {
        ProjectStudent project = projectStudentService.getProjectById(projectPostDto.getProject_id());
        Users user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ProjectMessage message = new ProjectMessage();
        message.setDate(LocalDate.now());
        message.setText(projectPostDto.getText());
        message.setUserfirstname(user.getFirstname());
        message.setUserlastname(user.getLastname());
        message.setUser(user);
        message.setProject(project);
        message.setTitle(projectPostDto.getTitle());
        if(projectPostDto.getFile() != null){
            message.setFile(projectPostDto.getFile().getBytes());
        }
        if(projectPostDto.getImage() != null){
            message.setImage(projectPostDto.getImage().getBytes());
        }
        repository.save(message);

        ProjectMessage projectMessage = repository.findByTitle(message.getTitle());
        if(projectPostDto.getFile() != null){
            projectMessage.setFileLink(baseUrl + "/project/projectPostFile/" + message.getId());
        }
        if(projectPostDto.getImage() != null){
            projectMessage.setImageLink(baseUrl + "/project/projectPostImage/" + message.getId());
        }
        repository.save(projectMessage);
    }
}
