package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findImageById(Long id);
}

