package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.ispi.beans.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
