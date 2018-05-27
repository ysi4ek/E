package Service;

import java.util.List;

import Model.Address;
import Model.User;

public interface UserService extends BaseService<User>{
	
	List <User> findInAgeRange(int from, int to);
	void updateAddress(Address address);
	
}