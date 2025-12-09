package com.example.java_practice.commons.controller;

import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/file/*")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId)
    {
        NoticeFile noticeFile = fileService.getFileInfo(fileId);
        if(noticeFile == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Resource resource = fileService.downloadFile(noticeFile.getF_filename());
        String encodedName = URLEncoder.encode(noticeFile.getF_ori_filename(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
