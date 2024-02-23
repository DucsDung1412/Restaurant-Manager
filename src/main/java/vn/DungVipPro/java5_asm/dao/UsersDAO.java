package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.Users;

@Repository
public interface UsersDAO extends JpaRepository<Users, String> {
    public Page<Users> findAll(Pageable pageable);

    public Page<Users> findByUserNameContains(String username, Pageable pageable);
}
