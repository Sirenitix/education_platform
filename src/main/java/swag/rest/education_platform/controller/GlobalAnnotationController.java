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
@RequestMapping("/annotation/global")
public class GlobalAnnotationController {

    private final AnnotationService service;

    @PostMapping
    public void addAnnotation(@RequestBody String annotation, @RequestParam Long pdfId) {
        service.addGlobalAnnotation(pdfId,annotation);
    }
    @GetMapping("{pdfId}")
    public List<String> getAnnotation(@PathVariable Long pdfId) {
        List<String> result = new ArrayList<>();
        List<Annotation> globalAnnotation = service.getGlobalAnnotation(pdfId);
        for(Annotation annotation : globalAnnotation) {
            result.add(annotation.getContent());
        }
        return result;
    }


}
