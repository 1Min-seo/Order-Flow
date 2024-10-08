package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByOwnerId(Long ownerId);

    Optional<Song> findByIdAndOwnerId(Long id, Long ownerId);
}