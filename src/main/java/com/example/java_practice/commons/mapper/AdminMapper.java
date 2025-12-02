package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminMapper {

    ArrayList<Award> selAwardList(AwardSearch awardSearch);

    int cntAwardList(AwardSearch cntWorks);

    ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo);

    ArrayList<User> selUserList(Map<String,Object> param);

    int delWork(String sort, int workNo);

    int updateWork(ArrayList<String> work);
    int updateUser(ArrayList<String> user);
    int updateWorkUserNo(ArrayList<String> user);

    int cntUserList(AwardSearch cntUsers);

    ArrayList<User> selManageList(AwardSearch userSearch);

    int delUser(String sort, int userNo);
}
