package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    int cntAwardList(Search cntWorks);
    int delWork(String sort, int workNo);
    int updateWork(List<String> work);
    int updateUser(List<String> user);
    int updateWorkUserNo(List<String> user);
    int cntUserList(Search cntUsers);
    int delUser(String sort, int userNo);
    int insertUser(User user);
    int insertAuth(Auth auth);
    int updateUserAll(User user);
    int updateAuth(Auth auth);
    int checkWorkCode(String workCode);
    int regWork(String sort, int workNo, String workCode);
    List<User> selUserList(String sort, int userNo);
    List<User> selManageList(Search userSearch);
    List<Award> selAwardList(Search search);
    List<ExcelUser> selExcelList(Search userSearch);
    List<WorkWithUser> selWorkWithUser(String sort, int workNo);
    Similar compareImage(@Param("filename") String filename);
}
