package Repository;

import java.util.List;

import Model.User;

public interface UserRepository extends BaseRepository<User>{

	List<User> getInAgeRange(int from, int to);	
	
}