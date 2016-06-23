package tools;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface DAOInterface<E> {
	
	public List<E> getAll();

	public E getOne(Integer id);

	public Integer create(E vo);

	public boolean update(E vo);

	public boolean delete(E vo);

	public boolean delete(Integer[] ids);
	
	public List<E> getHelper(Criterion... criterions);
	
}
