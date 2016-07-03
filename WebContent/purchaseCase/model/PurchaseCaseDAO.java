package purchaseCase.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sellCase.model.SellCaseVO;
import tools.AbstractDAO;
import tools.HibernateSessionFactory;

public class PurchaseCaseDAO extends AbstractDAO<PurchaseCaseVO> {

	public PurchaseCaseDAO() {
		super(PurchaseCaseVO.class, "purchaseCaseId");
	}

	// public List<PurchaseCaseVO> getAll() {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// Query query = session.createQuery("from PurchaseCaseVO");
	// List<PurchaseCaseVO> list = query.list();
	// session.getTransaction().commit();
	//
	// return list;
	// }

	public List<PurchaseCaseVO> getAllOfNotProgress(String progress) {
		return getHelper(Restrictions.ne("progress", progress));
		// session = HibernateSessionFactory.getSession();
		// session.getTransaction().begin();
		// Query query = session.createQuery("from PurchaseCaseVO where progress
		// is not :progress");
		// query.setParameter("progress", progress);
		// List<PurchaseCaseVO> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
	}

	// public PurchaseCaseVO getOne(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// PurchaseCaseVO vo = (PurchaseCaseVO) session.get(PurchaseCaseVO.class,
	// id);
	// session.getTransaction().commit();
	//
	// return vo;
	// }

	// public Integer create(PurchaseCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// Integer id = (Integer) session.save(vo);
	// session.getTransaction().commit();
	// return id;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	// public boolean update(PurchaseCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// session.update(vo);
	// session.getTransaction().commit();
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }

	// public boolean delete(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// PurchaseCaseVO vo = new PurchaseCaseVO();
	// vo.setPurchaseCaseId(id);
	// session.delete(vo);
	// session.getTransaction().commit();
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }

	// public boolean delete(Integer[] ids) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	//
	// for (Integer id : ids) {
	// try {
	// session.beginTransaction();
	// PurchaseCaseVO vo = new PurchaseCaseVO();
	// vo.setPurchaseCaseId(id);
	// session.delete(vo);
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	//
	// session.getTransaction().commit();
	//
	// return true;
	// }

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		return getHelper(Restrictions.isNull("sellCaseVO"));
	}

	public boolean updateSellCaseId(Integer sellCaseId, Integer[] purchaseCaseIds) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			SellCaseVO sellCaseVO = new SellCaseVO();
			sellCaseVO.setSellCaseId(sellCaseId);

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(sellCaseVO);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}		
	}

	public boolean deleteSellCaseId(Integer[] purchaseCaseIds) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(null);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}

	}
}
