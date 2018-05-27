package Model;

public class User {
	private Long userId;
	private String userName;
	private int age;
	private Address address;
	
	public User(String userName, int age, Address address) {
		super();
		this.userName = userName;
		this.age = age;
		this.address = address;
	}

	public User() {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
		this.address = address;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", age=" + age + ", address=" + address + "]";
	}
	
}