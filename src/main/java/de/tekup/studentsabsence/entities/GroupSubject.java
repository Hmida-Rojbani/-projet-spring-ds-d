package de.tekup.studentsabsence.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "groups_subjects")
public class GroupSubject implements Serializable {

    @EmbeddedId
    private GroupSubjectKey id;

    @MapsId("group_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @MapsId("subject_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "hours", nullable = false)
    private float hours;
}
