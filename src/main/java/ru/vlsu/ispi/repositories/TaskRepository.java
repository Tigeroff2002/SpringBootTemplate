package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("SELECT t FROM Task t ORDER BY id")
    List<Task> findAllTasks();

    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :taskId")
    void deleteTask(@Param("taskId") Long id);

    @Modifying
    @Query(value = "UPDATE Task t SET t.caption = :caption WHERE t.id = :taskId")
    void editTaskCaption(@Param("taskId") Long taskId, @Param("caption") String caption);

    @Modifying
    @Query(value = "UPDATE Task t SET t.status = :status WHERE t.id = :taskId")
    void editTaskStatus(@Param("taskId") Long taskId, @Param("status") TaskStatus status);


    @Modifying
    @Query(value = "UPDATE Task t SET t.caption = :caption, t.type = :type, t.status = :status," +
            " t.description = :description, t.price = :price WHERE t.id = :taskId")
    void updateTaskInfo(@Param("taskId") Long taskId, @Param("caption") String caption, @Param("type") TaskType type,
                        @Param("status") TaskStatus status, @Param("description") String description, @Param("price") float price);


    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Task findTaskById(@Param("taskId") Long id);

    @Query("SELECT t FROM Task t WHERE t.executor.id = :id")
    List<Task> getAllTaskFromCertainExecutor(@Param("id") Long executorId);

    @Query("SELECT id FROM Task t WHERE t.createdate = :date")
    int calculateMaxTaskId(@Param("date") Date date);
}
