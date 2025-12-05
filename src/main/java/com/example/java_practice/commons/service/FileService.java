package com.example.java_practice.commons.service;


import com.example.java_practice.commons.dto.NoticeFile;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.List;

public interface FileService {
    // 파일 정보 가져오기
    NoticeFile getFileInfo(int fileId);
    // 파일 다운로드
    Resource downloadFile(String fileName);
    // 파일 삭제(여러개)
    void deleteFiles(List<Path> files);
    // 파일 삭제(1개)
    void deleteFile(Path file);
    // 파일명 + 디렉토리 생성
    String createStoredFileName(String originalName);
    Path createDirPath(String dirName);
}
