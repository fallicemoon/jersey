package tools;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface DAOInterface<E> {
	
	public List<E> getAll();

	public E getOne(Integer id);

	public Integer create(E vo);

	public boolean update(E vo);

	public boolean delete(E vo);

	public boolean delete(Integer[] ids);
	
	public List<E> getHelper(String[] columnNames, Order order, Criterion... criterions);
	
	public List<E> getHelper(Criterion... criterions);
	
	public List<E> getHelper(String[] columnNames);
	
	public List<E> getHelper(String[] columnNames, Criterion... criterions);
	
}
