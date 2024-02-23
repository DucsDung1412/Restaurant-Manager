package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.UserInfo;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInfo, Long> {
    @EntityGraph(attributePaths = {"list"}) // Định nghĩa fetch join ở đây
    public UserInfo findUserInfoById(Long id);
}
