package com.elfn.producer.controller;

import com.elfn.producer.model.Event;
import com.elfn.producer.service.EventProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Elimane
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final ObjectMapper objectMapper;
    private final EventProducerService eventProducerService;

    public EventController(ObjectMapper objectMapper, EventProducerService eventProducerService) {
        this.objectMapper = objectMapper;
        this.eventProducerService = eventProducerService;
    }

    @PostMapping
    public String sendEvent(@RequestBody Event event) throws Exception {
        String message = objectMapper.writeValueAsString(event);
        eventProducerService.sendEvent(event);
        return "Événement envoyé : " + message;
    }

}
