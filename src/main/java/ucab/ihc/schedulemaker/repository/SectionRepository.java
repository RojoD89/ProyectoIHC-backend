package ucab.ihc.schedulemaker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucab.ihc.schedulemaker.model.getServerResponse.SectionResponse;

import java.util.List;

@Repository("sectionRepository")
public interface SectionRepository extends CrudRepository<SectionResponse, Integer> {
    List<SectionResponse>  findBySubject(String subject);
}
