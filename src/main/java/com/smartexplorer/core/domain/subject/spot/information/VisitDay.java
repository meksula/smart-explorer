package com.smartexplorer.core.domain.subject.spot.information;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VisitDay {
    private boolean isOpen;
    private int[] hours;

    public VisitDay() {}

    public VisitDay(int [] hours) {
        this.hours = hours;
        this.isOpen = true;
    }

    @Override
    public String toString() {
        if (isOpen)
            return "Open at " + hours[0] + " - " + hours[hours.length - 1];
        else
            return "Closed today.";
    }
}
