package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vlsu.ispi.beans.Notification;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE id = :notificationId ")
    public Notification findNotificationById(@Param("notificationId") Long notificationId);

    @Query("SELECT id FROM Notification n WHERE n.time = :time")
    int calculateMaxNotificationId(@Param("time") LocalTime time);

    @Query("SELECT n FROM Notification n WHERE n.executor.id = :executorId")
    List<Notification> getAllUserNotifications(@Param("executorId") Long executorId);

    @Query("SELECT n FROM Notification n WHERE n.executor.id = :executorId AND n.time > :time")
    List<Notification> getUserNotificationsLaterSomeTime(@Param("executorId") Long executorId, @Param("time") LocalTime time);
}
