package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.DungVipPro.java5_asm.dao.UsersDAO;
import vn.DungVipPro.java5_asm.model.Authorities;
import vn.DungVipPro.java5_asm.model.UserInfo;
import vn.DungVipPro.java5_asm.model.Users;

import java.sql.Date;
import java.util.Optional;

@Service
public class UsersService {
    private UsersDAO usersDAO;
    private UserInfoService userInfoService;
    @Autowired
    public UsersService(UsersDAO usersDAO, UserInfoService userInfoService) {
        this.usersDAO = usersDAO;
        this.userInfoService = userInfoService;
    }

    @Transactional
    public void save(Users u){
        if (this.usersDAO.existsById(u.getUserName())) {
            Users existingUser = this.usersDAO.findById(u.getUserName()).orElse(null);
            if (existingUser != null) {
                UserInfo userInfo = this.userInfoService.findUserInfoById(existingUser.getUserInfo().getId());
                userInfo.setAddress(u.getUserInfo().getAddress());
                userInfo.setPhone(u.getUserInfo().getPhone());
                userInfo.setName(u.getUserInfo().getName());
                userInfo.setBirthDay(u.getUserInfo().getBirthDay());
                this.userInfoService.save(userInfo);
                if (u.getPassword() != null) {
                    existingUser.setPassword("{noop}" + u.getPassword());
                    this.usersDAO.save(existingUser);
                }
            }
        } else {
            Authorities authorities = new Authorities(u, "ROLE_USER");
            UserInfo userInfo = new UserInfo(u.getUserInfo().getName(), u.getUserInfo().getAddress(), u.getUserInfo().getPhone(), null, u.getUserInfo().getBirthDay(), u);
            u.setAuthorities(authorities);
            u.setUserInfo(userInfo);
            u.setEnabled(true);
            u.setPassword("{noop}" + u.getPassword());
            usersDAO.save(u);
        }
    }

    @Transactional
    public void delete(Users users){
        this.usersDAO.findById(users.getUserName()).ifPresent(u -> {
            this.usersDAO.delete(this.usersDAO.findById(users.getUserName()).get());
        });
    }

    public Users findById(String username){
        return this.usersDAO.findById(username).get();
    }

    public Page<Users> findAll(Pageable pageable) {
        return this.usersDAO.findAll(pageable);
    }

    public Page<Users> findByUserNameContains(String username, Pageable pageable) {
        return this.usersDAO.findByUserNameContains(username, pageable);
    }
}
