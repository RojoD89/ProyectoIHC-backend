package ucab.ihc.schedulemaker.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Section implements Serializable {
    private String section;
    private String subject;
    private int nrc;
    private String professor;
    private ClassHours mon;
    private ClassHours tue;
    private ClassHours wed;
    private ClassHours thu;
    private ClassHours fri;
    private ClassHours sat;
    private ClassHours sun;

    public void initializeHours(){
        mon = new ClassHours();
        tue = new ClassHours();
        wed = new ClassHours();
        thu = new ClassHours();
        fri = new ClassHours();
        sat = new ClassHours();
        sun = new ClassHours();
    }
}
