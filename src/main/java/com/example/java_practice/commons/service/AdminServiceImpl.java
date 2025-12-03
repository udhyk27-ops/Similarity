package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.Search;
import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.dto.WorkWithUser;
import com.example.java_practice.commons.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;


    @Override
    public ArrayList<Award> selAwardList(Search search) { return adminMapper.selAwardList(search); }

    @Override
    public int cntAwardList(Search cntWorks){ return adminMapper.cntAwardList(cntWorks); };

    @Override

    public ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo) { return adminMapper.selWorkWithUser(sort, workNo); }

    @Override
    public List<User> selUserList(String sort, int userNo) { return adminMapper.selUserList(sort, userNo); }

    @Override
    public int delWork(String sort, int workNo) { return adminMapper.delWork(sort, workNo); }

    @Override
    public int modInfo(ArrayList<String> work, ArrayList<String> user) {
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
    public ArrayList<User> selManageList(Search userSearch) { return adminMapper.selManageList(userSearch); }

    @Override
    public int delUser(String sort, int userNo) { return adminMapper.delUser(sort, userNo); }
}
