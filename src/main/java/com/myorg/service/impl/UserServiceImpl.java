package com.myorg.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myorg.dao.UserDao;
import com.myorg.model.User;
import com.myorg.service.UserService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	/*AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();*/
	private UserDao userDao;

	@SuppressWarnings("unused")
	@Transactional
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.findByUsername(userId);
		/*User user = new User();
		user.setUsername("Alex123");
		user.setPassword("$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu");*/
		
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Transactional
	public List<User> findAll() {
		/*List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		User user = new User();
		user.setUsername("Alex123");
		user.setPassword("$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu");
		
		list.add(user);
		return list;*/
		
		return userDao.findAll();
	}

	@Override
	@Transactional
	public void delete(long id) {
		userDao.delete(id);
	}

	@Override
	@Transactional
	public User findOne(String username) {
		/*User user = new User();
		user.setUsername("Alex123");
		user.setPassword("$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu");
		
		return user;*/
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id);
	}

	@Override
    public User save(User user) {
        return userDao.save(user);
    }
}
