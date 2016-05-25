package purchaseCase.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import sellCase.model.SellCaseVO;
import tools.DAOInterface;
import tools.HibernateSessionFactory;

public class PurchaseCaseDAO implements DAOInterface<PurchaseCaseVO> {
	private Session session;

	public List<PurchaseCaseVO> getAll() {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		Query query = session.createQuery("from PurchaseCaseVO");
		List<PurchaseCaseVO> list = query.list();
		session.getTransaction().commit();
		
		return list;
	}

	public List<PurchaseCaseVO> getAllOfNotProgress(String progress) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		Query query = session.createQuery("from PurchaseCaseVO where progress is not :progress");
		query.setParameter("progress", progress);
		List<PurchaseCaseVO> list = query.list();
		session.getTransaction().commit();
		
		return list;
	}

	public PurchaseCaseVO getOne(Integer id) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		PurchaseCaseVO vo = (PurchaseCaseVO) session.get(PurchaseCaseVO.class, id);
		session.getTransaction().commit();
		
		return vo;
	}

	public Integer create(PurchaseCaseVO vo) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Integer id = (Integer) session.save(vo);
			session.getTransaction().commit();
			return id;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean update(PurchaseCaseVO vo) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.update(vo);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean delete(Integer id) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			PurchaseCaseVO vo = new PurchaseCaseVO();
			vo.setPurchaseCaseId(id);
			session.delete(vo);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean delete(Integer[] ids) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();

		for (Integer id : ids) {
			try {
				session.beginTransaction();
				PurchaseCaseVO vo = new PurchaseCaseVO();
				vo.setPurchaseCaseId(id);
				session.delete(vo);
			} catch (HibernateException e) {
				e.printStackTrace();
				return false;
			}
		}

		session.getTransaction().commit();
		
		return true;
	}

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		List<PurchaseCaseVO> list = session.createQuery("from PurchaseCaseVO where sellCaseVO is null").list();
		session.getTransaction().commit();
		
		return list;
	}

	public boolean updateSellCaseId(Integer sellCaseId, Integer[] purchaseCaseIds) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			SellCaseVO sellCaseVO = new SellCaseVO();
			sellCaseVO.setSellCaseId(sellCaseId);

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class,
						purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(sellCaseVO);
				session.update(purchaseCaseVO);
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean deleteSellCaseId(Integer[] purchaseCaseIds) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class,
						purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(null);
				session.update(purchaseCaseVO);
			}

			session.getTransaction().commit();
			
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			
			return false;
		}

	}
}
