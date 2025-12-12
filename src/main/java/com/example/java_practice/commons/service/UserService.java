package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    /** 등록현황 리스트(수상작/초대작) */
    List<?> selectList(String type, WorkSearch workSearch, int page, int size);
    /** 등록현황 총 개수(수상작/초대작) */
    int selectListTotalCnt(String type);
    int selectListSearchCnt(String type, WorkSearch workSearch);
    /** 엑셀 저장 */
    List<?> selectListForExcel(String type, WorkSearch workSearch);
    /** 발표년도 select */
    List<String> selectYearList(String type);
    /** 삭제 */
    boolean deleteWork(String type, int workNo);
    /** 중복 체크 */
    boolean chkDupAwardWork(String author, String contest, String award, String year);
    boolean chkDupInvitWork(String title, String author, String year);
    /** 개별 등록 */
    void insertSingleAwardWork(Award award, MultipartFile file);
    void insertSingleInvitWork(Invit invit, MultipartFile file);
    /** 단체등록 */
    boolean insertBatchAwardWork(int userNo, List<Award> awardList, List<MultipartFile> file);
    boolean insertBatchInvitWork(int userNo, List<Invit> invitList, List<MultipartFile> files);
    /** dashboard - 등록 현황 */
    TotalWorkStats selectWorkStats();
    /** dashboard - 발매년도별 등록 현황 */
    List<WorkYearStats> selectYearStats();
}
