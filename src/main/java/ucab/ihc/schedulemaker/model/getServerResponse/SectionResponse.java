package ucab.ihc.schedulemaker.model.getServerResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@ToString
public class SectionResponse implements Serializable {
    private int term;
    private String school;
    @Id
    private int crn;
    private String semester;
    private String section;
    private String professor;
    private String subject;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
}
