package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_subject")
public class GroupSubjectKey implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "subject_id")
    private Long subjectId;
}
