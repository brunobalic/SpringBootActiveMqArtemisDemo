package com.brunobalic.activemqdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/test")
public class Controller {

    private final JmsSender jmsSender;

    @Autowired
    public Controller(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/send")
    public ResponseEntity<String> sendDefaultMessage() {
        jmsSender.sendMessage("Default message");
        return ResponseEntity.ok("Request received.");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/send/{times}")
    public ResponseEntity<String> sendDefaultMessageMultipleTimes(@PathVariable(name = "times") Integer times) {
        IntStream
            .range(0, times)
            .forEach(i -> jmsSender.sendMessage("Message no. %d".formatted(i)));
        return ResponseEntity.ok("Request received.");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/send/{times}")
    public ResponseEntity<String> sendDefaultMessageMultipleTimesAtNextMinute(
        @PathVariable(name = "times") Integer times,
        @RequestBody String sendAt) {

        IntStream
            .range(0, times)
            .forEach(i -> jmsSender.sendMessage("Message no. %d".formatted(i),Instant.from(ZonedDateTime.parse(sendAt))));
        return ResponseEntity.ok("Request received.");
    }

}
