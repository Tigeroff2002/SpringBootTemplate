package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.ispi.beans.ChatMessage;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT c FROM ChatMessage c WHERE c.id = :messageId")
    public ChatMessage findMessageById(@Param("messageId") Long messageId);

    @Modifying
    @Query("UPDATE ChatMessage c SET text = :newText, time = :newDate, isEdited = true WHERE id = :messageId")
    public ChatMessage editMessageText(@Param("messageId") Long messageId, @Param("newText") String newText, @Param("newDate") LocalDateTime newDate);

    @Modifying
    @Query("UPDATE ChatMessage c SET c.isViewed = true WHERE id = :messageId")
    public ChatMessage editMessageReplyStatus(@Param("messageId") Long messageId);

    @Modifying
    @Query("UPDATE ChatMessage c SET c.isReplied = true AND c.isViewed = true WHERE id = :messageId")
    public ChatMessage editMessageViewStatus(@Param("messageId") Long messageId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId")
    public List<ChatMessage> findAllMessagesByEventId(@Param("eventId") Long eventId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId AND c.user.id = :userId")
    public List<ChatMessage> findAllMessagesByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

    @Query("SELECT c FROM ChatMessage c WHERE c.user.id = :userId")
    public List<ChatMessage> findAllMessagesByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM ChatMessage c WHERE c.user.id = :userId AND c.isViewed = false")
    public List<ChatMessage> findAllUnviewedMessagesDirectedToUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId AND c.event.taskaction.task.executor.id = :executorId")
    public List<ChatMessage> findAllMessagesOfExecutorByEventId(@Param("eventId") Long eventId, @Param("executorId") Long executorId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId AND c.event.taskaction.user.id = :employerId")
    public List<ChatMessage> findAllMessagesOfEmployerByEventId(@Param("eventId") Long eventId, @Param("employerId") Long employerId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId AND c.isViewed = false")
    public List<ChatMessage> findAllUnviewedMessageOfEventId(@Param("eventId") Long eventId);

    @Query("SELECT c FROM ChatMessage c WHERE c.event.id = :eventId AND c.isReplied = true")
    public List<ChatMessage> findAllRepliedMessageOfEventId(@Param("eventId") Long eventId);

    @Query("SELECT id FROM ChatMessage c WHERE c.time = :date")
    int calculateMaxChatMessageId(@Param("date") LocalDateTime date);
}

