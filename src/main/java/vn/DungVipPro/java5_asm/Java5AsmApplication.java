package vn.DungVipPro.java5_asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.DungVipPro.java5_asm.dao.AuthoritiesDAO;
import vn.DungVipPro.java5_asm.dao.UsersDAO;
import vn.DungVipPro.java5_asm.model.Authorities;
import vn.DungVipPro.java5_asm.model.Users;

@SpringBootApplication
public class Java5AsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(Java5AsmApplication.class, args);
	}

}
