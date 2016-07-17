package store;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import store.model.StoreDAO;
import store.model.StoreVO;

public class StoreDAOTest {
	
	private StoreDAO storeDAO;
	private List<StoreVO> list;
	
	@Before
	public void Init () {
		storeDAO = new StoreDAO();
	}
	
	@Test
	public void getStoreListByType () {
		list = storeDAO.getStoreListByType("store");
	}
	
	@After
	public void destroy () {
		System.out.println("picture".hashCode());
	} 

}
