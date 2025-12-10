package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.dto.NoticeSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void insertNotice(Notice notice);
    void insertNoticeFile(NoticeFile noticeFile);
    List<Notice> selectNoticeListBySearch(NoticeSearch noticeSearch);
    int selectNoticeCntBySearch(NoticeSearch noticeSearch);
    void updateViewCnt(@Param("noticeId") int id);
    void updateViewCntToZero(@Param("noticeId") int id);
    Notice selectNoticeDetailById(@Param("noticeId") int id);
    List<NoticeFile> selectNoticeFilesByNoticeId(@Param("noticeId") int id);
    NoticeFile selectNoticeFileById(@Param("fileId") int id);
    int deleteNoticeById(@Param("noticeId") int id);
    void updateNoticeById(Notice notice);
    void deleteNoticeFileById(@Param("fileId") int id);
}
