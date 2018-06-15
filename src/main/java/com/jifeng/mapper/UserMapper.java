package com.jifeng.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.jifeng.entity.Page;
import com.jifeng.entity.system.User;
import com.jifeng.util.PageData;

/**
 * @author sushengli
 * 2015年5月22日下午1:14:01
 */
@Repository
public interface UserMapper {

	List<User> queryUserListPage(Page page);
	
	List<User> queryUserList(PageData pd);
	
	User selectByUserId(Integer userId);
	
	User selectByUserName(String userName);
	
	User validateUserName(PageData pd);
	
	int insert(User user);

    int update(User user);
	
	int deleteByUserId(Integer userId);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
