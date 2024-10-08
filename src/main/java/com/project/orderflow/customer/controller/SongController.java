package com.project.orderflow.customer.controller;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.customer.domain.Song;
import com.project.orderflow.customer.domain.enums.SongStatus;
import com.project.orderflow.customer.dto.SongReqDto;
import com.project.orderflow.customer.service.SongReqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SongReqService songService;

    @PostMapping("/request")
    public ResponseEntity<String> requestSong(@RequestBody SongReqDto songReqDto) {
        songService.requestSong(songReqDto);
        return ResponseEntity.ok("노래 신청이 완료되었습니다.");
    }

    @GetMapping("/list/{ownerId}")
    public ResponseEntity<List<Song>> getSongsByOwnerId(@PathVariable Long ownerId) {
        List<Song> songs = songService.getSongsByOwnerId(ownerId);
        return ResponseEntity.ok(songs);
    }

    @PutMapping("/status/{ownerId}/{songId}")
    public ResponseEntity<String> changeSongStatus(@PathVariable Long ownerId, @PathVariable Long songId, @RequestParam SongStatus newStatus) {
        songService.changeSongStatus(ownerId, songId, newStatus);
        return ResponseEntity.ok("노래 상태가 변경되었습니다.");
    }
}