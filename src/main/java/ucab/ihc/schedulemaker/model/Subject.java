package ucab.ihc.schedulemaker.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class Subject implements Serializable {
    private String subject;
    private List<Section> sections;
    private int numberOfSections;
}
