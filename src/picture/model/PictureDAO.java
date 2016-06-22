package picture.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tools.DAOInterface;

public class PictureDAO implements DAOInterface<PictureVO> {
	private DataSource ds;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private static final String GETALL = "SELECT * FROM picture";
	private static final String GETONE = "SELECT * FROM picture WHERE picture_id=?";
	private static final String GET_BY_COMMODITY_ID = "SELECT * FROM picture WHERE commodity_id=? ORDER BY sequence_id";
	private static final String GET_MAX_SEQUENCE_ID = "SELECT MAX(sequence_id) FROM picture where commodity_id=?";
	private static final String CREATE = "INSERT INTO picture (commodity_id, sequence_id, picture, file_name) VALUES (?,?,?,?)";
	private static final String UPDATE = "UPDATE picture SET commodity_id=?, picture=? WHERE picture_id=?";
	private static final String DELETE = "DELETE FROM picture where picture_id=?";
	private static final String GET_PICTURE_COUNTS_BY_COMMODITY_IDS = "select commodity_id, count(*) as count from picture group by commodity_id";
	private static final String GET_PICTURE_COUNTS_BY_COMMODITY_ID = "select count(*) as count from picture where commodity_id=?";

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PictureDAO () {
		try {
			ds = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/jersey");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<PictureVO> getAll() {
		List<PictureVO> list = new ArrayList<PictureVO>();
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection.prepareStatement("SELECT * FROM picture");
			ResultSet rs = this.preparedStatement.executeQuery();
			while (rs.next()) {
				PictureVO vo = new PictureVO();
				vo.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
				vo.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
				vo.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
				vo.setPicture(rs.getBinaryStream("picture"));
				vo.setFileName(rs.getString("file_name"));
				list.add(vo);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Set<Integer> getPictureIds(Integer commodityId) {
		Set<Integer> set = new TreeSet<Integer>();
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement("SELECT picture_id FROM picture where commodity_id=?");
			preparedStatement.setInt(1, commodityId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				set.add(rs.getInt("picture_id"));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	public PictureVO getOne(Integer pictureId) {
		PictureVO vo = new PictureVO();
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection.prepareStatement("SELECT * FROM picture WHERE picture_id=?");
			this.preparedStatement.setInt(1, pictureId.intValue());
			ResultSet rs = this.preparedStatement.executeQuery();
			if (!rs.next()) {
				connection.close();
				return vo;
			}
			vo.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
			vo.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
			vo.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
			vo.setPicture(rs.getBinaryStream("picture"));
			vo.setFileName(rs.getString("file_name"));
			connection.close();
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public List<PictureVO> getPicturesByCommodityId(Integer commodityId) {
		List<PictureVO> list = new ArrayList<PictureVO>();
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection
					.prepareStatement("SELECT * FROM picture WHERE commodity_id=? ORDER BY sequence_id");
			this.preparedStatement.setInt(1, commodityId.intValue());
			ResultSet rs = this.preparedStatement.executeQuery();

			while (rs.next()) {
				PictureVO pictureVO = new PictureVO();
				pictureVO.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
				pictureVO.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
				pictureVO.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
				pictureVO.setPicture(rs.getBinaryStream("picture"));
				pictureVO.setFileName(rs.getString("file_name"));
				list.add(pictureVO);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Map<Integer, Integer> getCommodityIdPictureCountMap() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection
					.prepareStatement("select commodity_id, count(*) as count from picture group by commodity_id");
			ResultSet rs = this.preparedStatement.executeQuery();

			while (rs.next()) {
				map.put(Integer.valueOf(rs.getInt("commodity_id")), Integer.valueOf(rs.getInt("count")));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;

	}

	public Integer getCommodityIdPictureCount(Integer commodityId) {
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection
					.prepareStatement("select count(*) as count from picture where commodity_id=?");
			this.preparedStatement.setInt(1, commodityId.intValue());
			ResultSet rs = this.preparedStatement.executeQuery();
			if (rs.next()) {
				int result = Integer.valueOf(rs.getInt("count"));
				connection.close();
				return result;
			}
			connection.close();
			return Integer.valueOf(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Integer create(PictureVO vo) {
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection
					.prepareStatement("SELECT MAX(sequence_id) FROM picture where commodity_id=?");
			this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
			ResultSet rs = this.preparedStatement.executeQuery();
			Integer sequenceId;
			if (rs.next())
				sequenceId = Integer.valueOf(rs.getInt(1) + 1);
			else {
				sequenceId = Integer.valueOf(1);
			}
			this.preparedStatement = this.connection.prepareStatement(
					"INSERT INTO picture (commodity_id, sequence_id, picture, file_name) VALUES (?,?,?,?)");
			this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
			this.preparedStatement.setInt(2, sequenceId.intValue());
			this.preparedStatement.setBinaryStream(3, vo.getPicture());
			this.preparedStatement.setString(4, vo.getFileName());
			this.preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(1);
	}

	public boolean update(PictureVO vo) {
		boolean check = true;
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection
					.prepareStatement("UPDATE picture SET commodity_id=?, picture=? WHERE picture_id=?");
			this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
			this.preparedStatement.setBinaryStream(2, vo.getPicture());
			this.preparedStatement.setInt(3, vo.getPictureId().intValue());
			this.preparedStatement.setString(4, vo.getFileName());
			this.preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}

	public boolean delete(Integer id) {
		boolean check = true;
		try {
			connection = ds.getConnection();
			this.preparedStatement = this.connection.prepareStatement("DELETE FROM picture where picture_id=?");
			this.preparedStatement.setInt(1, id.intValue());
			this.preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}

	public boolean delete(Integer[] ids) {
		for (Integer id : ids) {
			delete(id);
		}
		return true;
	}
}
