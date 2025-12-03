package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Invit;
import com.example.java_practice.commons.dto.WorkSearch;
import com.example.java_practice.commons.mapper.AwardWorkMapper;
import com.example.java_practice.commons.mapper.InvitWorkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    public void insertSingleAwardWork(Award params, MultipartFile file) {

        String code = String.format("%06d", (int)(Math.random() * 1000000));
        params.setF_code("EMC" + code);
        boolean hasFile = (file != null && !file.isEmpty());

        if(hasFile) {
            String originalFileName = file.getOriginalFilename();
            params.setF_filename(originalFileName);

            long currentTime = System.currentTimeMillis();
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID() + "_" + currentTime + ext;
            Path dirPath = Paths.get(uploadDir + "/images");
//            Path filePath = dirPath.resolve(storedFileName);
            Path filePath = dirPath.resolve(originalFileName);

            try{
//                if(!Files.exists(dirPath)){
//                    Files.createDirectories(dirPath);
//                }
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
    public void insertSingleInvitWork(Invit params, MultipartFile file) {

        String code = String.format("%06d", (int)(Math.random() * 1000000));
        params.setF_code("EMC" + code);
        boolean hasFile = (file != null && !file.isEmpty());

        if(hasFile) {
            String originalFileName = file.getOriginalFilename();
            params.setF_filename(originalFileName);

            long currentTime = System.currentTimeMillis();
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            String storedFileName = UUID.randomUUID() + "_" + currentTime + ext;
            Path dirPath = Paths.get(uploadDir + "/images");
//            Path filePath = dirPath.resolve(storedFileName);
            Path filePath = dirPath.resolve(originalFileName);

            try{
//                if(!Files.exists(dirPath)){
//                    Files.createDirectories(dirPath);
//                }
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
}
