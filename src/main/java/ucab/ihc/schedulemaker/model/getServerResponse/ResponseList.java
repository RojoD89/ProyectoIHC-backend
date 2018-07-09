package ucab.ihc.schedulemaker.model.getServerResponse;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class ResponseList implements Serializable {
    List<SectionResponse> sectionResponseList;
}
