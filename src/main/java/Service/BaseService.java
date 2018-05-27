package Service;

import java.util.List;

public interface BaseService <T> {
	
	void save (T t);
	T findById(long id);
	List <T> findAll();
	void update (T t);
	void remove (T t);
}