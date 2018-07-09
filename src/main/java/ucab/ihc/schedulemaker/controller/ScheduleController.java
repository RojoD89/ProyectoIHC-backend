package ucab.ihc.schedulemaker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ucab.ihc.schedulemaker.command.SectionCommand;
import ucab.ihc.schedulemaker.service.ScheduleService;

import javax.validation.Valid;

@Slf4j

@CrossOrigin
@RestController
@RequestMapping(value = "/schedules", produces = "application/json")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/get", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity sendSchedules(@Valid @RequestBody SectionCommand command) {
        return scheduleService.getSchedules(command);
    }
}
