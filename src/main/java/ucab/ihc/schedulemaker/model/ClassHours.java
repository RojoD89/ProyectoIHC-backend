package ucab.ihc.schedulemaker.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ClassHours implements Serializable {
    private String firstHour;
    private String secondHour;
    private String classroom;
}
