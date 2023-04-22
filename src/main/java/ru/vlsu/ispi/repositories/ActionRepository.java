package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface ActionRepository extends JpaRepository<Action, Long> {
    @Modifying
    @Query("DELETE FROM Action a WHERE a.id = :actionId")
    void deleteAction(@Param("actionId") Long id);

    @Modifying
    @Query(value = "UPDATE Action a SET a.actiontype = :type WHERE a.id = :actionId")
    void editActionType(@Param("actionId") Long actionId, @Param("type") ActionType type);

    @Modifying
    @Query(value = "UPDATE Action a SET a.isFormalized = :condition WHERE a.id = :actionId")
    void editActionFormalized(@Param("actionId") Long actionId, @Param("condition") boolean isFormalized);

    @Query("SELECT a FROM Action a WHERE a.id = :actionId")
    Action findActionById(@Param("actionId") Long id);

    @Query("SELECT a FROM Action a WHERE a.user.id = :userId")
    List<Action> getAllActionsFromCertainUser(@Param("userId") Long userId);

    @Query("SELECT a FROM Action a WHERE a.task.id = :taskId")
    List<Action> getAllActionsRelatesToCertainTask(@Param("taskId") Long taskId);

    @Query("SELECT a FROM Action a WHERE a.actiontype = :type")
    List<Action> getAllActionsByType(@Param("type") ActionType type);

    @Query("SELECT a FROM Action a WHERE a.actiontype = :type AND a.user.id = :userId")
    List<Action> getAllActionsByUserAndType(@Param("userId") Long userId, @Param("type") ActionType type);

    @Query("SELECT a FROM Action a WHERE a.actiontype = :type AND a.task.id = :taskId")
    List<Action> getAllActionsByTaskAndType(@Param("taskId") Long taskId, @Param("type") ActionType type);

    @Query("SELECT a FROM Action a WHERE a.user.id = :userId AND a.task.id = :taskId AND a.actiontype = :type")
    List<Action> getActionByWholeParams(@Param("userId") Long userId, @Param("taskId") Long taskId, @Param("type") ActionType type);

    @Query("SELECT a FROM Action a WHERE a.isFormalized = :condition")
    List<Action> getAllFormalizedActions(@Param("condition") boolean condition);

    @Query("SELECT a.actiontype FROM Action a WHERE a.user.id = :userId AND a.task.id = :taskId" +
            " AND a.actiontype IN ('Liked', 'Unliked')" +
            " ORDER BY id DESC LIMIT 1")
    ActionType getActionLastLikedStatus(@Param("userId") Long userId, @Param("taskId") Long taskId);

    @Query("SELECT id FROM Action a WHERE a.timemoment = :date")
    int calculateMaxActionId(@Param("date") Date date);
}
