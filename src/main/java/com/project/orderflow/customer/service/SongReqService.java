package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.Song;
import com.project.orderflow.customer.domain.enums.SongStatus;
import com.project.orderflow.customer.dto.SongReqDto;
import com.project.orderflow.customer.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongReqService {

    private final SongRepository songRepository;

    /**
     * 신청 노래 저장
     */
    public void saveSongRequest(SongReqDto songReqDto) {
        Song songReq = Song.builder()
                .tableNumber(songReqDto.getTableNumber())
                .title(songReqDto.getTitle())
                .artist(songReqDto.getArtist())
                .status(SongStatus.IN_PROGRESS)
                .requestedAt(LocalDateTime.now())
                .build();

        songRepository.save(songReq);
    }

    /**
     * 신청 노래 목록 조회
     */
    public List<SongReqDto> getAllSongRequests() {
        return songRepository.findAllByOrderByRequestedAtAsc().stream()
                .map(song -> new SongReqDto(
                        song.getTableNumber(),
                        song.getArtist(),
                        song.getTitle(),
                        song.getStatus(),
                        song.getRequestedAt()
                ))
                .toList();
    }

    /**
     * 노래 신청 상태 변경
     */
    public void updateSongRequestStatus(Long id, SongStatus status) {
        Song songRequest = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("노래 신청을 찾을 수 없습니다."));

        songRequest.changeStatus(status);
        songRepository.save(songRequest);
    }
}