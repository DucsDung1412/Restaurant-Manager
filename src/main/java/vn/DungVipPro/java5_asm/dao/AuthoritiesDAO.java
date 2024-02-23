package vn.DungVipPro.java5_asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.DungVipPro.java5_asm.model.Authorities;
import vn.DungVipPro.java5_asm.model.Users;

@Repository
public interface AuthoritiesDAO extends JpaRepository<Authorities, Long> {

}
