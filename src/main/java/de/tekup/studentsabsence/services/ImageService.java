package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImage(String id);

    Image addImage(MultipartFile image) throws IOException;
}
