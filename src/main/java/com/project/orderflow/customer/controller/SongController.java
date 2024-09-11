package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.enums.SongStatus;
import com.project.orderflow.customer.dto.SongReqDto;
import com.project.orderflow.customer.service.SongReqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/song")
@RequiredArgsConstructor
@Tag(name = "Song", description = "노래 신청 API")
public class SongController {

    private final SongReqService songReqService;

    @PostMapping("/request")
    @Operation(summary = "노래 신청", description = "고객이 노래 제목과 가수 정보와 함께 신청하였습니다.")
    public ResponseEntity<Void> requestSong(@RequestBody SongReqDto songReqDto){
        songReqService.saveSongRequest(songReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    @Operation(summary = "신청받은 노래 목록 조히", description = "신청받은 노래 목록를 최신순으로 조회합니다.")
    public ResponseEntity<List<SongReqDto>> listSong(){
        return ResponseEntity.ok(songReqService.getAllSongRequests());
    }

    // 관리자가 노래 신청 상태 변경
    @PatchMapping("/{id}/status")
    @Operation(summary = "노래 신청 상태 변경", description = "노래 신청 상태를 변경합니다.")
    public ResponseEntity<Void> updateSongRequestStatus(@PathVariable Long id, @RequestParam SongStatus status) {
        songReqService.updateSongRequestStatus(id, status);
        return ResponseEntity.ok().build();
    }
}