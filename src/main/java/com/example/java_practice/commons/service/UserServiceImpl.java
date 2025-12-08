package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;
import com.example.java_practice.commons.mapper.AwardWorkMapper;
import com.example.java_practice.commons.mapper.InvitWorkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AwardWorkMapper awardWorkMapper;
    private final InvitWorkMapper invitWorkMapper;
    private final FileService fileService;

    @Override
    public ArrayList<?> selectList(String type, WorkSearch workSearch, int page, int size) {
        int startRow = (page - 1) * size + 1;
        int endRow = page * size;

        workSearch.setStartRow(startRow);
        workSearch.setEndRow(endRow);
        if(type.equals("award")){
            ArrayList<Award> awardList = awardWorkMapper.selectAwardListBySearch(workSearch);
            return awardList;
        }else{
            ArrayList<Invit> invitList = invitWorkMapper.selectInvitListBySearch(workSearch);
            return invitList;
        }
    }

     @Override
    public int selectListTotalCnt(String type) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardListCnt();
        }else{
            return invitWorkMapper.selectInvitListCnt();
        }
    }

    @Override
    public int selectListSearchCnt(String type, WorkSearch workSearch) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardListCntBySearch(workSearch);
        }else{
            return invitWorkMapper.selectInvitListCntBySearch(workSearch);
        }
    }

    @Override
    public List<?> selectListForExcel(String type, WorkSearch workSearch) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardListForExcel(workSearch);
        }else{
            return invitWorkMapper.selectInvitListForExcel(workSearch);
        }
    }

    @Override
    public List<String> selectYearList(String type) {
        if(type.equals("award")){
            return awardWorkMapper.selectAwardYearList();
        }else{
            return invitWorkMapper.selectInvitYearList();
        }
    }

    @Override
    @Transactional
    public boolean deleteWork(String type, int workNo) {
        int rows;
        if(type.equals("award")){
            rows = awardWorkMapper.updateAwardStatusByWorkNo(workNo);
        }else{
            rows = invitWorkMapper.updateInvitStatusByWorkNo(workNo);
        }
        return rows > 0;
    }

    @Override
    public boolean chkDupAwardWork(String contest, String award, String year) {
        return awardWorkMapper.chkDupAwardWork(contest, award, year);
    }

    @Override
    @Transactional
    public void insertSingleAwardWork(Award params, MultipartFile file) {

        boolean hasFile = (file != null && !file.isEmpty());

        if(hasFile) {

            String originalFileName = file.getOriginalFilename();
            params.setF_filename(originalFileName);

//            String storedFileName = fileService.createStoredFileName(originalFileName);

            Path dirPath = fileService.createDirPath("images");
            Path filePath = dirPath.resolve(originalFileName);
//            Path filePath = dirPath.resolve(storedFileName);

            try{
                file.transferTo(filePath);
//                params.setF_filepath("/uploads/image/" + storedFileName);
                params.setF_filepath("/images/" + originalFileName);
            }catch (IOException e){
                fileService.deleteFile(filePath);
                throw new RuntimeException("파일 업로드 중 오류 발생: " + file.getOriginalFilename(), e);
            }

        } else {
            params.setF_filename("");
            params.setF_filepath("");
        }
        awardWorkMapper.insertAwardWork(params);
    }

    @Override
    public boolean chkDupInvitWork(String title, String author, String year) {
        return invitWorkMapper.chkDupInvitWork(title, author, year);
    }

    @Override
    @Transactional
    public void insertSingleInvitWork(Invit params, MultipartFile file) {

        boolean hasFile = (file != null && !file.isEmpty());

        if(hasFile) {
            String originalFileName = file.getOriginalFilename();
            params.setF_filename(originalFileName);

//            String storedFileName = fileService.createStoredFileName(originalFileName);

            Path dirPath = fileService.createDirPath("images");
            Path filePath = dirPath.resolve(originalFileName);
//            Path filePath = dirPath.resolve(storedFileName);

            try{
                file.transferTo(filePath);
//                params.setF_filepath("/uploads/image/" + storedFileName);
                params.setF_filepath("/images/" + originalFileName);
            }catch (IOException e){
                fileService.deleteFile(filePath);
                throw new RuntimeException("파일 업로드 중 오류 발생: " + file.getOriginalFilename(), e);
            }

        } else {
            params.setF_filename("");
            params.setF_filepath("");
        }
        invitWorkMapper.insertInvitWork(params);
    }

    @Override
    @Transactional
    public boolean insertBatchAwardWork(int userNo, List<Award> awardList, List<MultipartFile> files) {

        // pk 값 가져오기
        int maxNum = awardWorkMapper.selectNextWorkNo();
        boolean hasFile = (files != null && !files.isEmpty());

        List<Award> validAwardList = new ArrayList<>();

        List<Path> uploadedFiles = new ArrayList<>();

        Map<String, MultipartFile> fileMap = Map.of();
        if(hasFile) {
            fileMap = files.stream()
                    .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));
        }

        Path dirPath = fileService.createDirPath("images");

        for(int i = 0; i < awardList.size(); i++) {
            Award award = awardList.get(i);

            boolean isDup = chkDupAwardWork(award.getF_contest(), award.getF_award(), award.getF_year());
            if(isDup) continue;

            validAwardList.add(award);

            String code = String.format("%06d", (int)(Math.random() * 1000000));
            award.setF_code("EMC" + code);
            award.setF_memo("");

            award.setF_work_no(maxNum + i);
            award.setF_user_no(userNo);

            if(hasFile) {
                String originalFileName = award.getF_filename();

                if(originalFileName!= null && fileMap.containsKey(originalFileName)) {
                    MultipartFile matchedFile = fileMap.get(originalFileName);
//                    String storedFileName = fileService.createStoredFileName(originalFileName);
                    Path filePath = dirPath.resolve(originalFileName);
//            Path filePath = dirPath.resolve(storedFileName);
                    try {
                        matchedFile.transferTo(filePath);
                        uploadedFiles.add(filePath);
//                award.setF_filepath("/uploads/image/" + storedFileName);
                        award.setF_filepath("/images/" + originalFileName);
                    } catch (IOException e) {
                        fileService.deleteFile(filePath);
                        throw new RuntimeException("파일 업로드 중 오류 발생: " + matchedFile.getOriginalFilename(), e);
                    }
                }else {
                    award.setF_filename("");
                    award.setF_filepath("");
                }

            } else {
                award.setF_filename("");
                award.setF_filepath("");
            }
        }
        if(validAwardList.isEmpty()) return false;

        int rows = awardWorkMapper.insertBatchAwardWork(validAwardList);
        if(rows == 0) fileService.deleteFiles(uploadedFiles);
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean insertBatchInvitWork(int userNo, List<Invit> invitList, List<MultipartFile> files) {

        int maxNum = invitWorkMapper.selectNextWorkNo();
        boolean hasFile = (files != null && !files.isEmpty());

        List<Invit> validInvitList = new ArrayList<>();

        List<Path> uploadedFiles = new ArrayList<>();
        Map<String, MultipartFile> fileMap = Map.of();
        if(hasFile) {
            fileMap = files.stream()
                    .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));
        }
        Path dirPath = fileService.createDirPath("images");
        for(int i = 0; i < invitList.size(); i++) {
            Invit invit = invitList.get(i);

            boolean isDup = chkDupInvitWork(invit.getF_title(), invit.getF_author(), invit.getF_year());
            if(isDup) continue;

            validInvitList.add(invit);

            String code = String.format("%06d", (int)(Math.random() * 1000000));
            invit.setF_code("EMC" + code);
            invit.setF_memo("");
            invit.setF_work_no(maxNum + i);
            invit.setF_user_no(userNo);
            if(hasFile) {
                String originalFileName = invit.getF_filename();
                if(originalFileName!= null && fileMap.containsKey(originalFileName)) {
                    MultipartFile matchedFile = fileMap.get(originalFileName);
//                    String storedFileName = fileService.createStoredFileName(originalFileName);
                    Path filePath = dirPath.resolve(originalFileName);
//            Path filePath = dirPath.resolve(storedFileName);
                    try{
                        matchedFile.transferTo(filePath);
                        uploadedFiles.add(filePath);

//                invit.setF_filepath("/uploads/image/" + storedFileName);
                        invit.setF_filepath("/images/" + originalFileName);
                    }catch(IOException e){
                        fileService.deleteFile(filePath);
                        throw new RuntimeException("파일 업로드 중 오류 발생: " + matchedFile.getOriginalFilename(), e);
                    }
                }else {
                    invit.setF_filename("");
                    invit.setF_filepath("");
                }
            }else {
                invit.setF_filename("");
                invit.setF_filepath("");
            }
        }

        if(validInvitList.isEmpty()) return false;

        int rows = invitWorkMapper.insertBatchInvitWork(validInvitList);
        if(rows == 0) fileService.deleteFiles(uploadedFiles);
        return rows > 0;
    }

    @Override
    public TotalWorkStats selectWorkStats() {

        WorkStats awardStat = awardWorkMapper.selectAwardWorkStats();
        WorkStats invitStat = invitWorkMapper.selectInvitWorkStats();

        return TotalWorkStats.builder()
                .awardStats(awardStat)
                .invitStats(invitStat)
                .build();
    }

}
