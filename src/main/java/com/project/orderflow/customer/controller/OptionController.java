package com.project.orderflow.customer.controller;


import com.project.orderflow.customer.domain.ItemOption; // ItemOption으로 변경
import com.project.orderflow.customer.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/options")
@Tag(name = "옵션관리(직원호출,물티슈 등등)", description = "옵션주문(고객)/옵션삭제,옵션추가(관리자)")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @Operation(
            summary = "옵션추가(관리자)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값, 옵션이름"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션이 성공적으로 추가됨"),
    })
    @PostMapping("/add")
    public ResponseEntity<ItemOption> addOption( // 반환 타입을 ItemOption으로 변경
                                                 @RequestParam Long ownerId,
                                                 @RequestParam String optionName
    ) {
        ItemOption itemOption = optionService.addOption(ownerId, optionName); // Option을 ItemOption으로 변경
        return ResponseEntity.ok(itemOption);
    }


    @Operation(
            summary = "옵션리스트(고객)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemOption.class))),
            @ApiResponse(responseCode = "404", description = "해당 ownerId를 가진 옵션을 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 유효한 JWT 토큰 필요", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    // 매장에 따른 옵션 조회
    @GetMapping("/list/{ownerId}")
    public ResponseEntity<List<ItemOption>> getOptionsByOwnerId(@PathVariable Long ownerId) { // 반환 타입을 List<ItemOption>으로 변경
        List<ItemOption> itemOptions = optionService.getOptionsByOwnerId(ownerId); // Option을 ItemOption으로 변경
        return ResponseEntity.ok(itemOptions);
    }

    @Operation(
            summary = "옵션삭제(관리자)",
            description = "해당 옵션 Id값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "옵션이 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "옵션을 찾을 수 없음")
    })
    // 옵션 삭제
    @DeleteMapping("/delete/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }
}
