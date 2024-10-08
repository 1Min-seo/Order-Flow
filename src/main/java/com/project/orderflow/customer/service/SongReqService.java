package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.Song;
import com.project.orderflow.customer.domain.enums.SongStatus;
import com.project.orderflow.customer.dto.SongReqDto;
import com.project.orderflow.customer.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongReqService {

    @Autowired
    private SongRepository songRepository;

    public void requestSong(SongReqDto songReqDto) {
        Song song = Song.builder()
                .ownerId(songReqDto.getOwnerId())
                .tableNumber(songReqDto.getTableNumber())
                .title(songReqDto.getTitle())
                .artist(songReqDto.getArtist())
                .status(songReqDto.getStatus())
                .requestedAt(songReqDto.getRequestedAt())
                .build();
        songRepository.save(song);
    }

    public List<Song> getSongsByOwnerId(Long ownerId) {
        return songRepository.findByOwnerId(ownerId);
    }

    public void changeSongStatus(Long ownerId, Long songId, SongStatus newStatus) {
        Optional<Song> songOptional = songRepository.findByIdAndOwnerId(songId, ownerId);
        if (songOptional.isEmpty()) {
            throw new IllegalStateException("해당 노래를 찾을 수 없습니다.");
        }
        Song song = songOptional.get();
        song.changeStatus(newStatus);
        songRepository.save(song);
    }
}