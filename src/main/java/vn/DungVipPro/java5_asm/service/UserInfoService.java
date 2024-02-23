package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.DungVipPro.java5_asm.dao.UserInfoDAO;
import vn.DungVipPro.java5_asm.model.UserInfo;

@Service
public class UserInfoService {
    private UserInfoDAO userInfoDAO;

    @Autowired
    public UserInfoService(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }

    public UserInfo findUserInfoById(Long id){
        return this.userInfoDAO.findUserInfoById(id);
    }

    public void save(UserInfo userInfo){
        this.userInfoDAO.save(userInfo);
    }
}
