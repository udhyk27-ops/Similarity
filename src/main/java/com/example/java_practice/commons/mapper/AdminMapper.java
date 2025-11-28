package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface AdminMapper {

    ArrayList<Award> selAwardList(AwardSearch awardSearch);

    int cntAwardList(AwardSearch cntWorks);

    ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo);

    ArrayList<User> selUserList(String sort);

    int delWork(String sort, int workNo);

    int updateWork(String sort, ArrayList<String> work);

    int updateUser(ArrayList<String> user);
}
