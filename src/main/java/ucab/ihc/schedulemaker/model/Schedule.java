package ucab.ihc.schedulemaker.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class Schedule implements Serializable {
    List<Section> sections;
}
