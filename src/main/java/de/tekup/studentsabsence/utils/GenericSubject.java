package de.tekup.studentsabsence.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class GenericSubject implements Comparable<GenericSubject>{
    private String name;
    private float hours;

    @Override
    public int compareTo(GenericSubject o) {
        return (int) (this.hours - o.getHours());
    }
}
