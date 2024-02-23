package vn.DungVipPro.java5_asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.DungVipPro.java5_asm.dao.AuthoritiesDAO;
import vn.DungVipPro.java5_asm.model.Authorities;

@Service
public class AuthoritiesService {
    private AuthoritiesDAO authoritiesDAO;

    @Autowired
    public AuthoritiesService(AuthoritiesDAO authoritiesDAO) {
        this.authoritiesDAO = authoritiesDAO;
    }

    public void save(Authorities authorities){
        this.authoritiesDAO.save(authorities);
    }
}
