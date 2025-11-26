package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final NoticeMapper noticeMapper;

    @Override
    public Notice insertNotice(Notice notice) {
        noticeMapper.insertNotice(notice);
        return notice;
    }

    @Override
    public void insertNoticeFile(int noticeId, List<MultipartFile> noticeFiles) {

        noticeFiles.forEach(file -> {
            try {
                String originalFileName = file.getOriginalFilename();
                String storedFileName = UUID.randomUUID() + "_" + originalFileName;
                Path dirPath = Paths.get(uploadDir + "/notice");
                Path filePath = dirPath.resolve(storedFileName);

                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                file.transferTo(filePath);

                NoticeFile noticeFile = new NoticeFile();
                noticeFile.setF_notice_id(noticeId);
                noticeFile.setF_filename(storedFileName);
                noticeFile.setF_ori_filename(originalFileName);
                noticeFile.setF_filepath("/uploads/notice/" + storedFileName);
                noticeFile.setF_filesize(String.valueOf(file.getSize()));

                noticeMapper.insertNoticeFile(noticeFile);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 중 오류 발생: " + file.getOriginalFilename(), e);
            }

        });
    }

    @Override
    public ArrayList<Notice> selNoticeList() {
        ArrayList<Notice> noticeList = noticeMapper.selectNoticeList();
        return noticeList;
    }


}
