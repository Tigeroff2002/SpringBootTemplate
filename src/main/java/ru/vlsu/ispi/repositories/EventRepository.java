package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Event;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.enums.EventStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("UPDATE Event e SET e.eventstatus = :status WHERE id = :eventId")
    void setCertainStatusForEvent(@Param("status") EventStatus status, @Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e WHERE id = :eventId")
    Event findEventById(@Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e WHERE e.taskaction.task.executor.id = :executorId")
    List<Event> getAllEventsFromCertainExecutor(@Param("executorId") Long executorId);

    @Query("SELECT e FROM Event e WHERE e.taskaction.user.id = :employerId")
    List<Event> getAllEventsFromCertainEmployer(@Param("employerId") Long employerId);

    @Query("SELECT e FROM Event e WHERE e.taskaction.task.executor.id = :executorId AND e.taskaction.user.id = :employerId")
    List<Event> getAllEventsFromCertainExecutorAndEmployer(@Param("executorId") Long executorId, @Param("employerId") Long employerId);

    @Query("SELECT e FROM Event e WHERE e.taskaction.task.id = :taskId")
    List<Event> getAllEventsRelatesToCertainTask(@Param("taskId") Long taskId);

    @Query("SELECT e FROM Event e WHERE e.eventstatus = :status AND e.taskaction.task.executor.id = :executorId")
    List<Event> getAllActionsByExecutorAndStatus(@Param("executorId") Long executorId, @Param("status") EventStatus status);

    @Query("SELECT e FROM Event e WHERE e.eventstatus = :status AND e.taskaction.user.id = :employerId")
    List<Event> getAllActionsByEmployerAndStatus(@Param("employerId") Long employerId, @Param("status") EventStatus status);

    @Query("SELECT e FROM Event e WHERE e.eventstatus = :status AND e.taskaction.task.id = :taskId")
    List<Event> getAllActionsByTaskAndStatus(@Param("taskId") Long taskId, @Param("status") EventStatus status);

    @Query("SELECT e FROM Event e WHERE e.taskaction.task.executor.id = :executorId AND e.taskaction.task.id = :taskId" +
            " ORDER BY id DESC LIMIT 1")
    Optional<Event> getLstEventByExecutorAndTaskAndStatus(@Param("executorId") Long executorId, @Param("taskId") Long taskId);

    @Query("SELECT e FROM Event e WHERE e.eventstatus = :status")
    List<Event> getAllEventsByStatus(@Param("status") EventStatus status);

    @Query("SELECT id FROM Event e WHERE e.formalizedate = :date")
    int calculateMaxEventId(@Param("date") Date date);

}
