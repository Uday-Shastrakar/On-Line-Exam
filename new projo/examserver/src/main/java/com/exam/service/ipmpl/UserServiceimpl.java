package com.exam.service.ipmpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;


@Service
public class UserServiceimpl implements UserService {
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
	User local =this.userRepository.findByUsername(user.getUsername());
		if (local!=null) {
			//error reporting
			System.out.println("User is already there !!");
			throw new UserFoundException();
		}else {
			//user create 
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
				
			}
			
			user.getUserRoles().addAll(userRoles);
		local = this.userRepository.save(user);
			
			
		}
		return local;
	}


//getting user by username
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return this.userRepository.findByUsername(username);
	}
	
	// deleting by user id 
	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}
// Updating user data (manual written)
   @Override
	public void updateUser(User user) {
		 // check if the user with the passed id exists or not
	    User userDB = userRepository.findById(user.getId()).orElseThrow();
	    // If user exists then updated
	    userRepository.save(user);
	}
	
		

}
