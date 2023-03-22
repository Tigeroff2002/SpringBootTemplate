package ru.vlsu.ispi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlsu.ispi.beans.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
