package test;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import lebah.db.entity.Persistence;
import lebah.db.entity.User;

public class AppTest {
	
	public static void main(String[] args) throws Exception {
		
		//getUsers();
		testRoles();
		Persistence.db().close();
		
	}
	
	private static void testRoles() {
		Persistence db = Persistence.db();
		User user = db.find(User.class, "ali");
		System.out.println("User: " + user.getFirstName());
		System.out.println("Role: " + user.getRole().getName());
		
		System.out.println(user.hasRole("admin"));
	}

	private static void updateUser() {
		String id = "68f260cd7087a673e00fa41c76a0a57faeddc109";
		User user = Persistence.db().find(User.class, id);
		System.out.println(user.getUserName());
		user.setUserName(user.getUserName() + "123");
		Persistence.db().update(user);
		System.out.println("===");
	}

	private static void getUsers() {
		String hql = "select u from User u";
		List<User> users = Persistence.db().list(hql);
		users.stream().forEach(user -> System.out.println(user.getUserName() + ", " + user.getId()));

	}
	
	public static void createuser(String name) {
		
		User user = new User();
		user.setUserName(name);
		user.setUserPassword("secret");
		
		Persistence.db().save(user);
		
	}

}
