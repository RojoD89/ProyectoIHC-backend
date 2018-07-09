package ucab.ihc.schedulemaker.command;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SectionCommand {
    List<String> subjects;
}
