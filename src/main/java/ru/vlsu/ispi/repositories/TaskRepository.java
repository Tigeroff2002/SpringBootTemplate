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

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :taskId")
    void deleteTask(@Param("taskId") Long id);

    @Modifying
    @Query(value = "UPDATE Task t SET t.caption = :caption WHERE t.id = :taskId")
    void editTask(@Param("taskId") long id, @Param("caption") String caption);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Task findTaskById(@Param("taskId") Long id);

    @Query("SELECT t FROM Task t WHERE t.executor.id = :id")
    List<Task> getAllTaskFromCertainExecutor(@Param("id") Long executorId);

    @Query("SELECT MAX(id) FROM Task")
    int calculateMaxTaskId();
}
