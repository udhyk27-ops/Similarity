package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Award;
import com.example.java_practice.commons.dto.AwardSearch;
import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.dto.WorkWithUser;
import com.example.java_practice.commons.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;


    @Override
    public ArrayList<Award> selAwardList(AwardSearch awardSearch) { return adminMapper.selAwardList(awardSearch); }

    @Override
    public int cntAwardList(AwardSearch cntWorks){ return adminMapper.cntAwardList(cntWorks); };

    @Override
    public ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo) { return adminMapper.selWorkWithUser(sort, workNo); }

    @Override
    public ArrayList<User> selUserList(String sort) { return adminMapper.selUserList(sort); }

    @Override
    public int delWork(String sort, int workNo) { return adminMapper.delWork(sort, workNo); }

    @Override
    public int modInfo(ArrayList<String> work, ArrayList<String> user) {
        int workResult = adminMapper.updateWork(work);
        int userResult = adminMapper.updateUser(user);
        int userNoResult = adminMapper.updateWorkUserNo(user);

        if (workResult == 1 && userResult == 1 && userNoResult == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int cntUserList(AwardSearch cntUsers) { return adminMapper.cntUserList(cntUsers); }


}
