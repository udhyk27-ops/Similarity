package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.*;
import com.example.java_practice.commons.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public List<Award> selAwardList(Search search) { return adminMapper.selAwardList(search); }

    @Override
    public int cntAwardList(Search cntWorks){ return adminMapper.cntAwardList(cntWorks); };

    @Override
    public List<WorkWithUser> selWorkWithUser(String sort, int workNo) { return adminMapper.selWorkWithUser(sort, workNo); }

    @Override
    public List<User> selUserList(String sort, int userNo) { return adminMapper.selUserList(sort, userNo); }

    @Override
    public int delWork(String sort, int workNo) { return adminMapper.delWork(sort, workNo); }

    @Override
    public int modInfo(List<String> work, List<String> user) {
        int workResult = 1;
        int userResult = 1;
        int userNoResult = 1;

        if (work != null && !work.isEmpty()) {
            workResult = adminMapper.updateWork(work);
            userNoResult = adminMapper.updateWorkUserNo(user);
        }

        if (user != null && !user.isEmpty()) {
            userResult = adminMapper.updateUser(user);
        }

        if (workResult == 1 && userResult == 1 && userNoResult == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int cntUserList(Search cntUsers) { return adminMapper.cntUserList(cntUsers); }

    @Override
    public List<User> selManageList(Search userSearch) { return adminMapper.selManageList(userSearch); }

    @Override
    public int delUser(String sort, int userNo) { return adminMapper.delUser(sort, userNo); }

    @Override
    public int saveInfo(User user, Auth auth) {
        int userResult = 1;
        int adminResult = 1;

        if (user.getF_user_no() == null) { // 신규
            userResult = adminMapper.insertUser(user);
            if ("관리자".equals(user.getF_sort())) {
                auth.setF_user_no(user.getF_user_no());
                adminResult = adminMapper.insertAuth(auth);
            }
        } else { // 기존
            userResult = adminMapper.updateUserAll(user);
            if ("관리자".equals(user.getF_sort())) {
                adminResult = adminMapper.updateAuth(auth);
            }
        }

        if (userResult == 1 && adminResult == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int checkWorkCode(String workCode) { return adminMapper.checkWorkCode(workCode); }

    @Override
    public int regWork(String sort, int workNo, String workCode) { return adminMapper.regWork(sort, workNo, workCode); }

    @Override
    public Similar compareImage(String filename) { return adminMapper.compareImage(filename); }

    @Override
    public List<ExcelUser> selExcelList(Search userSearch) { return adminMapper.selExcelList(userSearch); }
}
