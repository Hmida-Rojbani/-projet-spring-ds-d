package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Image;
import de.tekup.studentsabsence.repositories.ImageRepository;
import de.tekup.studentsabsence.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class ImageServiceImp implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImp(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image getImage(Long id) {
        return this.imageRepository
                .findImageById(id)
                .orElseThrow(() -> new NoSuchElementException("No Image With Id: " + id));
    }

    @Override
    public Image addImage(MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String fileType = image.getContentType();
        byte[] data = image.getBytes();
        Image img = new Image(null, fileName, fileType, data);

        return this.imageRepository.save(img);
    }

}
