package com.myorg.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myorg.model.User;

@Repository
public class UserDAOImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public User findByUsername(String username) {
		User usr = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			TypedQuery<User> query = session.createQuery("from User where username = :username");
			query.setParameter("username", username);
			usr = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	public User save(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);

			return user;
		} catch(Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public List<User> findAll() {
		TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
		return query.getResultList();
	}

	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();	
		try {
			User usr = session.load(User.class, id);
			//System.out.println("persistaneInstance : " + persistaneInstance);
			if(usr != null) {
				session.delete(usr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}	
	}

	public User findOne(String username) {
		User usr = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from User where username = :username");
			query.setParameter("username", username);
			usr = (User) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	public User findById(Long id) {
		User usr = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from User where id = :id");
			query.setParameter("id", id);
			usr = (User) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}
	
}
