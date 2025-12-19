package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {

    ArrayList<Award> selAwardList(Search search);

    int cntAwardList(Search cntWorks);

    ArrayList<WorkWithUser> selWorkWithUser(String sort, int workNo);

    ArrayList<User> selUserList(String sort, int userNo);

    int delWork(String sort, int workNo);

    int updateWork(ArrayList<String> work);
    int updateUser(ArrayList<String> user);
    int updateWorkUserNo(ArrayList<String> user);

    int cntUserList(Search cntUsers);

    ArrayList<User> selManageList(Search userSearch);

    int delUser(String sort, int userNo);


    int insertUser(User user);
    int insertAuth(Auth auth);
    int updateUserAll(User user);
    int updateAuth(Auth auth);

    int regWork(String sort, int workNo, String workCode);

    Similar compareImage(@Param("filename") String filename);

    List<ExcelUser> selExcelList(Search userSearch);
}
