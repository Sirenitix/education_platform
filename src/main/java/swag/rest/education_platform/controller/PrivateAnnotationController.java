package swag.rest.education_platform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swag.rest.education_platform.entity.annotation.Annotation;
import swag.rest.education_platform.service.AnnotationService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/annotation/private")
public class PrivateAnnotationController {
    private final AnnotationService service;

    @PostMapping
    public void addAnnotation(@RequestParam String annotation, @RequestParam Long pdfId, Principal principal) {
        service.addPrivateAnnotation(pdfId,annotation, principal.getName());

    }
    @GetMapping
    public List<String> getAnnotation(@RequestParam Long pdfId, Principal principal) {
        List<String> result = new ArrayList<>();
        List<Annotation> globalAnnotation = service.getPrivateAnnotation(pdfId, principal.getName());
        for(Annotation annotation : globalAnnotation) {
            result.add(annotation.getContent());
        }
        return result;
    }
}
