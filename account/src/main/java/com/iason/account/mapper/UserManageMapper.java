package com.iason.account.mapper;

import com.iason.account.data.entity.UserManage;

public interface UserManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserManage record);

    int insertSelective(UserManage record);

    UserManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserManage record);

    int updateByPrimaryKey(UserManage record);
}