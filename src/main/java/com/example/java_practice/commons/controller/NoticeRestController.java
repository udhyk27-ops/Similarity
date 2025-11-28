package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.service.FileService;
import com.example.java_practice.commons.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice/*")
@RequiredArgsConstructor
@Slf4j
public class NoticeRestController {

    @Value("${UPLOAD_DIR}")
    private String baseUploadDir;

    private final NoticeService noticeService;
    private final FileService fileService;

    @PostMapping("/viewCnt/{id}")
    public void addViewCnt(@PathVariable("id") int noticeId){
        System.out.println("noticeId : " + noticeId);
        noticeService.updateViewCnt(noticeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("id") int id) {
        try{
            ArrayList<NoticeFile> noticeFiles = noticeService.selectNoticeFilesByNoticeId(id);
            boolean success = noticeService.deleteNoticeById(id);
            if(success){
                List<Path> paths = noticeFiles.stream()
                        .map(f -> Path.of(baseUploadDir + "/notice", f.getF_filename()))
                        .toList();

                fileService.deleteFiles(paths);
                return ResponseEntity.ok().body(
                        Map.of("status", true, "msg", "삭제되었습니다")
                );
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("status", false, "msg", "삭제에 실패했습니다")
                );
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("status", false, "msg", "서버 오류가 발생했습니다")
            );
        }
    }

}
