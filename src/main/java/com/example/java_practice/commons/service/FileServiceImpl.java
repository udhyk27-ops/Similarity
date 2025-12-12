package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final NoticeMapper noticeMapper;

    @Override
    public NoticeFile getFileInfo(int fileId) {
        return noticeMapper.selectNoticeFileById(fileId);
    }

    @Override
    public Resource downloadFile(String fileName) {
            String filePath = uploadDir + "/notice/" + fileName;
        try{

            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            Resource resource = new UrlResource(path.toUri());

            if(!resource.exists() || !resource.isReadable()){
                throw new FileNotFoundException("파일을 찾을 수 없습니다" + filePath);
            }
            return resource;
        }catch (Exception e){
            throw new RuntimeException("파일 다운로드 실패" + filePath, e);
        }
    }

    @Override
    public void deleteFiles(List<Path> files) {
        if (files == null || files.isEmpty()) return;

        files.forEach(f -> {
            try {
                Files.deleteIfExists(f);
            } catch (IOException e) {
                throw new RuntimeException("파일 삭제 중 오류 발생", e);
            }
        });
    }

    @Override
    public void deleteFile(Path file) {
        if (file == null) return;

        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 중 오류 발생", e);
        }
    }

    @Override
    public String createStoredFileName(String originalName) {
        long currentTime = System.currentTimeMillis();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        return UUID.randomUUID() + "_" + currentTime + ext;
    }

    @Override
    public Path createDirPath(String dirName) {
        Path dirPath = Paths.get(uploadDir + "/" + dirName);
        try{
            if (!Files.exists(dirPath)) Files.createDirectories(dirPath);

        }catch (IOException e){
            throw new RuntimeException("디렉토리 생성 중 오류 발생", e);
        }

        return dirPath;
    }

}
