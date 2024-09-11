package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByOrderByRequestedAtAsc();
}