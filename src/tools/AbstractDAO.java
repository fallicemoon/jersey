package tools;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public abstract class AbstractDAO<E> implements DAOInterface<E> {

	private Class<E> voType;
	private String pk;

	public AbstractDAO(Class<E> type, String pk) {
		// 實際上vo的型別
		this.voType = type;
		this.pk = pk;
	}

	@Override
	public List<E> getAll() {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		List<E> list = session.createQuery("from " + voType.getName()).list();
		session.getTransaction().commit();
		return list;
	}

	@Override
	public E getOne(Integer id) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		E vo = (E) session.get(voType, id);
		session.getTransaction().commit();
		return vo;
	}

	@Override
	public Integer create(E vo) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Integer id = (Integer) session.save(vo);
			session.getTransaction().commit();
			return id;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(E vo) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(vo);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(E vo) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(vo);
			session.getTransaction().commit();

			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Integer[] ids) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from " + voType.getName() + " vo where vo." + pk + " in :ids")
					.setParameterList("ids", ids).executeUpdate();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<E> getHelper(Criterion... criterions) {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(voType);
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		List<E> list = criteria.list();
		session.getTransaction().commit();
		return list;
	}

}
