package ru.arduanovaa.parser.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.arduanovaa.parser.dto.ParserResponse;
import ru.arduanovaa.parser.service.SectionService;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MainController {

    SectionService service;

    @PostMapping("/upload")
    public ParserResponse upload(@RequestParam("file") MultipartFile file) {
        ParserResponse response = new ParserResponse();
        response.setSections(service.parser(file));
        return response;
    }
}
