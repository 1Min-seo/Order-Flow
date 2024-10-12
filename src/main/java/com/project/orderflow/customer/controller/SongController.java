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


    @Operation(
            summary = "노래를 신청하는 API(고객)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, tableNumber에는 해당 테이블의 번호를 써주세요. reqiestedAt 시간은 yyyy.MM.dd HH:mm 이런 형식으로 넣어주세용"
    )
    @PostMapping("/request")
    public ResponseEntity<String> requestSong(@RequestBody SongReqDto songReqDto) {
        songService.requestSong(songReqDto);
        return ResponseEntity.ok("노래 신청이 완료되었습니다.");
    }


    @Operation(
            summary = "노래를 신청 List(관리자)",
            description = "ownerId는 JWT토큰에서 추출한 Id값을 넣어주세요."
    )
    @GetMapping("/list/{ownerId}")
    public ResponseEntity<List<Song>> getSongsByOwnerId(@PathVariable Long ownerId) {
        List<Song> songs = songService.getSongsByOwnerId(ownerId);
        return ResponseEntity.ok(songs);
    }

    @Operation(
            summary = "노래 상태변화(관리자)",
            description = "ownerId는 JWT토큰에서 추출한 Id값을 넣어주세요. 그리고 해당 노래의 id값을 넣어줘야함."
    )
    @PutMapping("/status/{ownerId}/{songId}")
    public ResponseEntity<String> changeSongStatus(@PathVariable Long ownerId, @PathVariable Long songId, @RequestParam SongStatus newStatus) {
        songService.changeSongStatus(ownerId, songId, newStatus);
        return ResponseEntity.ok("노래 상태가 변경되었습니다.");
    }
}