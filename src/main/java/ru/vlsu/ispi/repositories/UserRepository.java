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

@Repository
//@Transactional(isolation = Isolation.READ_COMMITTED)
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.nickname = :name")
    User findUserByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") Long id);

    @Query("SELECT id FROM User u WHERE u.email = :email")
    int calculateMaxUserId(@Param("email") String email);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Query("INSERT INTO User (email, nickname, password)" +
            " VALUES (:email, :nickname, :password)")
    void insertOneUser(@Param("email") String email, @Param("nickname") String nickname, @Param("password") String password);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :userId")
    void deleteOneUser(@Param("userId") Long userId);
}
