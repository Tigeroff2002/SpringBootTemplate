package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.repositories.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
}
