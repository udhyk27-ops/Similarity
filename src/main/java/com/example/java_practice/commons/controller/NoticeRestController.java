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

    @PostMapping("/{id}/view-count")
    public void addViewCnt(@PathVariable("id") int noticeId)
    {
        noticeService.updateViewCnt(noticeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("id") int id)
    {
        List<NoticeFile> noticeFiles = noticeService.selectNoticeFilesByNoticeId(id);
        boolean success = noticeService.deleteNoticeById(id);
        if(success){
            // 실제 업로드 경로의 파일 삭제
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
    }

}
