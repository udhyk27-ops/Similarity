package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/workList/*")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;

    @DeleteMapping("/{type}/{workId}")
    public ResponseEntity<?> delWork(@PathVariable("type") String type, @PathVariable int workId)
    {
        try{
            boolean success = userService.deleteWork(type, workId);
            if(success) return ResponseEntity.ok().body(Map.of("status", true, "msg", "삭제되었습니다"));
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", false, "msg", "삭제에 실패했습니다"));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", false, "msg", "서버 오류가 발생했습니다"));
        }
    }
}
