package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public interface ImageService {
    Image getImage(Long id);

    Image addImage(MultipartFile image) throws IOException;
}
