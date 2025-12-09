package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.dto.NoticeSearch;
import com.example.java_practice.commons.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final NoticeMapper noticeMapper;
    private final FileService fileService;

    @Override
    @Transactional
    public Notice insertNotice(Notice notice) {
        noticeMapper.insertNotice(notice);
        return notice;
    }

    @Override
    @Transactional
    public void insertNoticeFile(int noticeId, List<MultipartFile> noticeFiles) {

        List<Path> uploadedFiles = new ArrayList<>();
        Path dirPath = fileService.createDirPath("notice");

        noticeFiles.forEach(file -> {
            try {

                String originalFileName = file.getOriginalFilename();
                String storedFileName = fileService.createStoredFileName(originalFileName);
                Path filePath = dirPath.resolve(storedFileName);

                file.transferTo(filePath);
                uploadedFiles.add(filePath); // 업로드된 파일 기록

                NoticeFile noticeFile = new NoticeFile();
                noticeFile.setF_notice_id(noticeId);
                noticeFile.setF_filename(storedFileName);
                noticeFile.setF_ori_filename(originalFileName);
                noticeFile.setF_filepath("/uploads/notice/" + storedFileName);
                noticeFile.setF_filesize(String.valueOf(file.getSize()));

                noticeMapper.insertNoticeFile(noticeFile);

            } catch (IOException e) {
                // insert 실패 시 이미 업로드된 파일도 삭제
                fileService.deleteFiles(uploadedFiles);
                throw new RuntimeException("파일 업로드 중 오류 발생: " + file.getOriginalFilename(), e);
            }

        });
    }

    @Override
    public ArrayList<Notice> selNoticeList(NoticeSearch noticeSearch, int page, int size) {
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;

        noticeSearch.setStartRow(startRow);
        noticeSearch.setEndRow(endRow);
        ArrayList<Notice> noticeList = noticeMapper.selectNoticeListBySearch(noticeSearch);
        return noticeList;
    }

    @Override
    public int selNoticeCnt(NoticeSearch noticeSearch) {return noticeMapper.selectNoticeCntBySearch(noticeSearch);}

    @Override
    public Notice selectNoticeDetailById(int noticeId) {
        Notice notice = noticeMapper.selectNoticeDetailById(noticeId);
        return notice;
    }

    @Override
    @Transactional
    public void updateViewCnt(int noticeId) {
        noticeMapper.updateViewCnt(noticeId);
    }

    @Override
    public ArrayList<NoticeFile> selectNoticeFilesByNoticeId(int noticeId) {
        ArrayList<NoticeFile> noticeFiles = noticeMapper.selectNoticeFilesByNoticeId(noticeId);
        return noticeFiles;
    }

    @Override
    @Transactional
    public boolean deleteNoticeById(int noticeId) {
        int rows = noticeMapper.deleteNoticeById(noticeId);
        return rows > 0;
    }

    @Override
    @Transactional
    public Notice updateNoticeById(Notice notice) {
        // 조회수 초기화
        noticeMapper.updateViewCntToZero(notice.getF_id());
        noticeMapper.updateNoticeById(notice);
        return notice;
    }
    @Override
    @Transactional
    public void deleteNoticeFilesById(int fileId) {
        NoticeFile noticeFile = noticeMapper.selectNoticeFileById(fileId);
        Path filePath = Paths.get(uploadDir + "/notice", noticeFile.getF_filename());
        fileService.deleteFile(filePath);
        noticeMapper.deleteNoticeFileById(fileId);
    }

}
