package com.brainworks.rest;

import com.brainworks.rest.configuration.AppConstants;
import com.brainworks.rest.entities.Role;
import com.brainworks.rest.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BloggerApi2Application implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BloggerApi2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			Role role=new Role ();
			role.setRoleId (AppConstants.ROLE_ADMIN);
			role.setName ("ROLE_ADMIN");

			Role role1=new Role ();
			role1.setRoleId (AppConstants.ROLE_USER);
			role1.setName ("ROLE_USER");

			List<Role> roleList = List.of (role, role1);
			List<Role> savedRoles = this.roleRepository.saveAll (roleList);
			savedRoles.forEach (r->{
				System.out.println (r.getName ());
			});
		}catch (Exception e){
			e.printStackTrace ();
		}
	}
}