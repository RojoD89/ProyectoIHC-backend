package ucab.ihc.schedulemaker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ucab.ihc.schedulemaker.command.SectionCommand;
import ucab.ihc.schedulemaker.model.ClassHours;
import ucab.ihc.schedulemaker.model.Section;
import ucab.ihc.schedulemaker.model.Subject;
import ucab.ihc.schedulemaker.model.getServerResponse.SectionResponse;
import ucab.ihc.schedulemaker.repository.SectionRepository;
import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service("scheduleService")
public class ScheduleService {
    private List<Subject> subjectList;
    private static final String URL = "http://w2.ucab.edu.ve/tl_files/IngenieriaInformatica/Horarios/201825/201825-timetables.json";
    @Autowired
    private SectionRepository sectionRepository;

    class SortByNumberOfSections implements Comparator<Subject>{
        public int compare(Subject a, Subject b)
        {
            return a.getNumberOfSections() - b.getNumberOfSections();
        }
    }

    public void getServerSubjects() {
        ObjectMapper mapper = new ObjectMapper();
        try{
            TypeReference<List<SectionResponse>> typeReference = new TypeReference<List<SectionResponse>>() {};
             List<SectionResponse> list = mapper.readValue(new URL("http://w2.ucab.edu.ve/tl_files/IngenieriaInformatica/Horarios/201825/201825-timetables.json"), typeReference);
            System.out.println(list.get(1).getProfessor());
            list.forEach(i->{
                sectionRepository.save(i);
            });
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private void checkHours(SectionResponse sectionResponse, Section section){
        if(sectionResponse.getMon()== null)
            section.setMon(null);
        else
            setHours(sectionResponse.getMon(), section.getMon(), section);
        if(sectionResponse.getTue()== null)
            section.setTue(null);
        else
            setHours(sectionResponse.getTue(), section.getTue(), section);
        if(sectionResponse.getWed()== null)
            section.setWed(null);
        else
            setHours(sectionResponse.getWed(), section.getWed(), section);
        if(sectionResponse.getThu()== null)
            section.setThu(null);
        else
            setHours(sectionResponse.getThu(), section.getThu(), section);
        if(sectionResponse.getFri()== null)
            section.setFri(null);
        else
            setHours(sectionResponse.getFri(), section.getFri(), section);
        if(sectionResponse.getSat()== null)
            section.setSat(null);
        else
            setHours(sectionResponse.getSat(), section.getSat(), section);
        if(sectionResponse.getSun()== null)
            section.setSun(null);
        else
            setHours(sectionResponse.getSun(), section.getSun(), section);
    }

    private void setHours(String string, ClassHours hours, Section section){
        String[] splited = string.split("_");
        String unformattedSecondHour;
        String secondHour;
        hours.setFirstHour(splited[0]);
        unformattedSecondHour = splited[1];
        secondHour = unformattedSecondHour.split(":")[0];
        secondHour = secondHour.concat(":00");
        hours.setSecondHour(secondHour);
        hours.setClassroom(unformattedSecondHour.split(" ")[1]);
    }

    private void setSubjectList(SectionCommand command){
        subjectList = new ArrayList<>();
        command.getSubjects().forEach(i->{
           List<SectionResponse> sectionResponseList = sectionRepository.findBySubject(i);
           Subject subject = new Subject();
           subject.setSubject(i);
           subject.setNumberOfSections(sectionResponseList.size());
           List<Section> sectionList = new ArrayList<>();
           sectionResponseList.forEach(j->{
               Section section = new Section();
               section.setSubject(j.getSubject());
               section.setNrc(j.getCrn());
               section.setSection(j.getSection());
               section.setProfessor(j.getProfessor());
               section.initializeHours();
               checkHours(j,section);
               sectionList.add(section);
           });
           subject.setSections(sectionList);
           subjectList.add(subject);
        });
        subjectList.sort(new SortByNumberOfSections());
    }
    private boolean checkConflict(Section[] combination, Section section, int length){
        boolean check = false;
        boolean check1 = false;
        for(int i = 0; i < length; i++){
            if((combination[i].getMon() != null) && (section.getMon() != null)) {
                if (combination[i].getMon().getFirstHour().equals(section.getMon().getFirstHour()) ||
                        combination[i].getMon().getFirstHour().equals(section.getMon().getSecondHour()) ||
                        combination[i].getMon().getSecondHour().equals(section.getMon().getFirstHour()))
                    return true;
            }
            if((combination[i].getThu() != null) && (section.getThu() != null)) {
                if (combination[i].getThu().getFirstHour().equals(section.getThu().getFirstHour()) ||
                        combination[i].getThu().getFirstHour().equals(section.getThu().getSecondHour()) ||
                        combination[i].getThu().getSecondHour().equals(section.getThu().getFirstHour()))
                    return true;
            }
            if((combination[i].getWed() != null) && (section.getWed() != null)) {
                if (combination[i].getWed().getFirstHour().equals(section.getWed().getFirstHour()) ||
                        combination[i].getWed().getFirstHour().equals(section.getWed().getSecondHour()) ||
                        combination[i].getWed().getSecondHour().equals(section.getWed().getFirstHour()))
                    return true;
            }
            if((combination[i].getTue() != null) && (section.getTue() != null)) {
                if (combination[i].getTue().getFirstHour().equals(section.getTue().getFirstHour()) ||
                        combination[i].getTue().getFirstHour().equals(section.getTue().getSecondHour()) ||
                        combination[i].getTue().getSecondHour().equals(section.getTue().getFirstHour()))
                    return true;
            }
            if((combination[i].getFri() != null) && (section.getFri() != null)) {
                if (combination[i].getFri().getFirstHour().equals(section.getFri().getFirstHour()) ||
                        combination[i].getFri().getFirstHour().equals(section.getFri().getSecondHour()) ||
                        combination[i].getFri().getSecondHour().equals(section.getFri().getFirstHour()))
                    return true;
            }
            if((combination[i].getSat() != null) && (section.getSat() != null)) {
                if (combination[i].getSat().getFirstHour().equals(section.getSat().getFirstHour()) ||
                        combination[i].getSat().getFirstHour().equals(section.getSat().getSecondHour()) ||
                        combination[i].getSat().getSecondHour().equals(section.getSat().getFirstHour()))
                    return true;
            }
            if((combination[i].getSun() != null) && (section.getSun() != null)) {
                if (combination[i].getSun().getFirstHour().equals(section.getSun().getFirstHour()) ||
                        combination[i].getSun().getFirstHour().equals(section.getSun().getSecondHour()) ||
                        combination[i].getSun().getSecondHour().equals(section.getSun().getFirstHour()))
                    return true;
            }
            if((section.getMon() == null) && (section.getTue() == null) && (section.getWed() == null) && (section.getThu() == null)
                    &&(section.getFri() == null) && (section.getSat() == null) && section.getSun() == null)
                return true;
            if((section.getSubject().contains("Prácticas"))){
                check = true;
                if((section.getSubject().contains(combination[i].getSubject())) && (section.getProfessor().equals(combination[i].getProfessor())))
                    check1 = true;
            }
            if((combination[i].getSubject().contains(section.getSubject())) && (!section.getProfessor().equals(combination[i].getProfessor())))
                return true;
        }

        if(check && !check1)
            return true;
        else
            return false;
    }

    private void recursiveCombinations(Section[] combination, int ndx2, int ndx3, List<Subject> elems, List<Section> currentList, List<Section[]> combinations){
        if(ndx2 == elems.size()){

        }
        else{
            if(ndx2 == elems.size()-1){
                Section[] aux = combination.clone();
                for(int i = 0; i < currentList.size(); i++){

                    if(!checkConflict(combination, currentList.get(i), ndx2)) {
                        aux[ndx2] = currentList.get(i);
                        Section[] tmp = aux.clone();
                        combinations.add(tmp);
                    }
                }
                recursiveCombinations(combination, ndx2+1, ndx3, elems, elems.get(ndx2).getSections(), combinations);
            }
            else{
                if(ndx3 < currentList.size()){

                    if(!checkConflict(combination, currentList.get(ndx3), ndx2)) {
                        combination[ndx2] = currentList.get(ndx3);


                        if (ndx3 == 0)
                            recursiveCombinations(combination, ndx2 + 1, ndx3, elems, elems.get(ndx2 + 1).getSections(), combinations);
                        else
                            recursiveCombinations(combination, ndx2 + 1, 0, elems, elems.get(ndx2 + 1).getSections(), combinations);
                        recursiveCombinations(combination, ndx2, ndx3 + 1, elems, elems.get(ndx2).getSections(), combinations);

                    }
                    else
                        recursiveCombinations(combination, ndx2, ndx3 + 1, elems, elems.get(ndx2).getSections(), combinations);
                }
            }
        }
    }

    public ResponseEntity<Object> getSchedules(SectionCommand command, int num){
        boolean check;
        boolean check1;
        setSubjectList(command);
        List<Section[]> combinations = new ArrayList<>();
        List<Section[]> results = new ArrayList<>();
        for(int i = 0; i < subjectList.get(0).getNumberOfSections(); i++ ) {
            Section[] combination = new Section[subjectList.size()];
            combination[0] = subjectList.get(0).getSections().get(i);
            recursiveCombinations(combination, 1, 0, subjectList, subjectList.get(1).getSections(), combinations);
        }

        if(num*10 <= combinations.size()) {
            for (int i = num; i < num + 10; i++) {
                results.add(combinations.get(num));
            }
            return ResponseEntity.ok(results);
        }
        else if (num < combinations.size()){
            for (int i = num; i <= combinations.size(); i++) {
                results.add(combinations.get(num));
            }
            return ResponseEntity.ok(results);
        }
        else
            return null;
    }

}
