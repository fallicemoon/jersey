package commodity.model;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import purchaseCase.model.PurchaseCaseVO;
import tools.DAOInterface;
import tools.HibernateSessionFactory;

public class CommodityDAO implements DAOInterface<CommodityVO> {
	private Session session;

	public List<CommodityVO> getAll() {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		List<CommodityVO> list = session.createQuery("from CommodityVO").list();
		session.getTransaction().commit();

		return list;
	}

	public CommodityVO getOne(Integer id) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		CommodityVO vo = (CommodityVO) session.get(CommodityVO.class, id);
		session.getTransaction().commit();
		
		return vo;
	}

	public List<CommodityVO> getByRule(Map<String, Object> rule) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(CommodityVO.class);

		for (String key : rule.keySet()) {
			Object value = rule.get(key);
			criteria.add(Restrictions.eq(key, value));
		}

		List<CommodityVO> list = criteria.list();
		session.getTransaction().commit();
		
		return list;
	}

	public Integer create(CommodityVO vo) {
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

	public boolean update(CommodityVO vo) {
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
			CommodityVO vo = new CommodityVO();
			vo.setCommodityId(id);
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
		session.beginTransaction();

		for (Integer id : ids) {
			try {
				CommodityVO vo = new CommodityVO();
				vo.setCommodityId(id);
				session.delete(vo);
			} catch (HibernateException e) {
				e.printStackTrace();
				return false;
			}
		}

		session.getTransaction().commit();
		
		return true;
	}

	public List<CommodityVO> getByPurchaseCaseIdIsNull() {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		List<CommodityVO> list = session.createQuery("from CommodityVO where purchaseCaseVO is null").list();
		session.getTransaction().commit();
		
		return list;
	}

	public void updatePurchaseCaseId(PurchaseCaseVO purchaseCaseVO, Integer[] commodityIds) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		for (Integer commodityId : commodityIds) {
			CommodityVO commodityVO = (CommodityVO) session.load(CommodityVO.class, commodityId);
			commodityVO.setPurchaseCaseVO(purchaseCaseVO);
			session.update(commodityVO);
		}
		session.getTransaction().commit();
		
	}

	public void deletePurchaseCaseId(Integer[] commodityIds) {
		session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		for (Integer commodityId : commodityIds) {
			CommodityVO commodityVO = (CommodityVO) session.load(CommodityVO.class, commodityId);
			commodityVO.setPurchaseCaseVO(null);
			session.update(commodityVO);
		}
		session.getTransaction().commit();
		
	}
}
