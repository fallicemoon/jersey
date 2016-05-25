package tools;

import java.util.List;

public abstract interface DAOInterface<E> {
	public abstract List<E> getAll();

	public abstract E getOne(Integer paramInteger);

	public abstract Integer create(E paramE);

	public abstract boolean update(E paramE);

	public abstract boolean delete(Integer paramInteger);

	public abstract boolean delete(Integer[] paramArrayOfInteger);
}
