package store.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tools.DAOInterface;

public class StoreDAO implements DAOInterface<StoreVO> {
	private Connection connection;
	private DataSource ds;

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StoreDAO () {
		try {
			ds = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/jersey");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PreparedStatement getPreparedStatement(String sql) {
		try {
			connection = ds.getConnection();
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<StoreVO> getAll() {
		PreparedStatement preparedStatement = getPreparedStatement("select * from store");

		List<StoreVO> list = new ArrayList<StoreVO>();
		try {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				StoreVO vo = new StoreVO();
				vo.setStoreId(Integer.valueOf(resultSet.getInt(1)));
				vo.setType(resultSet.getString(2));
				vo.setName(resultSet.getString(3));
				list.add(vo);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public StoreVO getOne(Integer id) {
		PreparedStatement preparedStatement = getPreparedStatement("select * from store where store_id = ?");

		StoreVO vo = new StoreVO();
		try {
			preparedStatement.setInt(1, id.intValue());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next())
				return vo;
			vo.setStoreId(Integer.valueOf(resultSet.getInt(1)));
			vo.setType(resultSet.getString(2));
			vo.setName(resultSet.getString(3));
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public Integer create(StoreVO vo) {
		String sql = "INSERT INTO store(type, name) values(?,?)";
		PreparedStatement preparedStatement = getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, vo.getType());
			preparedStatement.setString(2, vo.getName());
			int result = Integer.valueOf(preparedStatement.executeUpdate());
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public boolean update(StoreVO vo) {
		String sql = "UPDATE store SET type=?, name=? where store_id=?";
		PreparedStatement preparedStatement = getPreparedStatement(sql);
		try {
			preparedStatement.setString(1, vo.getType());
			preparedStatement.setString(2, vo.getName());

			preparedStatement.setInt(3, vo.getStoreId().intValue());

			int count = preparedStatement.executeUpdate();
			connection.close();
			if (count == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Integer id) {
		String sql = "DELETE FROM store where store_id = ?";
		PreparedStatement preparedStatement = getPreparedStatement(sql);
		try {
			preparedStatement.setInt(1, id.intValue());

			int count = preparedStatement.executeUpdate();
			connection.close();
			if (count == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<StoreVO> getStoreListByType(String type) {
		String sql = "SELECT * FROM store WHERE type=?";
		PreparedStatement preparedStatement = getPreparedStatement(sql);
		List<StoreVO> list = new ArrayList<StoreVO>();
		try {
			preparedStatement.setString(1, type);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				StoreVO storeVO = new StoreVO();
				storeVO.setStoreId(Integer.valueOf(rs.getInt(1)));
				storeVO.setType(rs.getString(2));
				storeVO.setName(rs.getString(3));
				list.add(storeVO);
			}
			connection.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean delete(Integer[] ids) {
		String sql = "DELETE FROM store WHERE store_id IN (";
		int length = ids.length;
		for (int i = 0; i < ids.length; i++) {
			sql = sql.concat("?,");
		}
		sql = sql.substring(0, sql.length() - 1);
		sql = sql.concat(")");
		PreparedStatement preparedStatement = getPreparedStatement(sql);
		try {
			for (int i = 0; i < ids.length; i++) {
				preparedStatement.setInt(i + 1, ids[i].intValue());
			}
			int count = preparedStatement.executeUpdate();
			connection.close();
			if (count == length)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
