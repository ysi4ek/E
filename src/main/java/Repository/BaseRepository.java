package Repository;

import java.util.List;

public interface BaseRepository <T> {
	
	T getById(Long id);
	List <T> getAll ();
	Long add (T t);
	void update (T t);
	void delete (T t);
}