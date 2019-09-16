package ru.arduanovaa.parser.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.arduanovaa.parser.dto.Section;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    public List<Section> parser(MultipartFile file) {
        List<Section> sections = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            Section section;
            while ((line = reader.readLine()) != null) {
                if (isHeadline(line)) {
                    section = new Section();
                    section.setLevel(line.chars()
                            .filter(c -> c == '#')
                            .count());
                    section.setTitle(line.substring(line.lastIndexOf("#") + 2));
                    section.setContent(reader.readLine());
                    sections.add(section);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createStructure(sections);
    }

    private List<Section> createStructure(List<Section> sections) {
        List<Section> result = new ArrayList<>();
        Section previous = sections.get(0);
        Section parent = null;
        for (Section current : sections) {
            if (current.getLevel() > previous.getLevel()) {
                parent = previous;
                parent.setChilds(new ArrayList<>());
                parent.getChilds().add(current);
            } else if (current.getLevel() == previous.getLevel() && current.getLevel() > 1) {
                parent.getChilds().add(current);
            } else if (current.getLevel() == 1) {
                result.add(current);
            }
            previous = current;
        }
        return result;
    }

    private boolean isHeadline(String line) {
        return line.indexOf('#') > -1;
    }
}
