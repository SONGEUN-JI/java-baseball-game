package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	int row = 0;
	private String id;
	// 재선언
	String url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
	String user = "cgi_6_0320_1";
	String password = "smhrd1";

	public void getCon() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
			String user = "cgi_6_0320_1";
			String password = "smhrd1";

			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			System.out.println("데이터베이스 오류 !");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (Exception e) {
			System.out.println("close 오류 ");
		}

	}

	public int join(DTO dto) {

		getCon();

		String sql = "insert into baseball_game values(?, ?, ?)";

		try {
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			psmt.setInt(3, 0);

			row = psmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close();
		}
		return row;
	}

	public String login(String id, String pw) {
		String log = null;
		try {
			getCon();
			String sql = "select * from baseball_game where id = ? and pw = ?";

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();

			if (rs.next() == true) {
				log = rs.getString("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return log;

	}

	// 둘이 세트
	public int AddPlayer(String id, String name, int stat) {
		DTO dto = new DTO(id, name, stat);
		return AddPlayer(dto);
	}

	public int AddPlayer(DTO dto) {
		int result = 0;
		try {
			getCon();
			int count = 1;
			// 계정에 할당 된 선수 수 체크
			String sql = "SELECT COUNT(*) FROM players where id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());

			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count < 5) {

				sql = "insert into players (id, name, stat, homerun, strike, hit ) values (?, ?, ?,?,?,? )";

				psmt = conn.prepareStatement(sql);

				psmt.setString(1, dto.getId());
				psmt.setString(2, dto.getName());
				psmt.setInt(3, dto.getStat());
				psmt.setInt(4, 0);
				psmt.setInt(5, 0);
				psmt.setInt(6, 0);

				result = psmt.executeUpdate();
			} else if (count == 5) {
				System.out.println("등록할 수 있는 선수는 최대 5명입니다");
			}
		} catch (SQLException e) {
			System.out.println("이미 같은 이름을 가진 선수가 있습니다.");
		} finally {
			close();
		}
		return result;
	}

	// 전체 선수 목록 조회

	public ArrayList<DTO> selectAll(String id) {
		ArrayList<DTO> list = new ArrayList<DTO>();
		try {

			getCon();

			DTO dto = null;
			String sql = "select * from players where id = ?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String id2 = rs.getString("id");
				String name = rs.getString("name");
				int stat = rs.getInt("stat");
				int homerun = rs.getInt("homerun");
				int strike = rs.getInt("strike");
				int hit = rs.getInt("hit");

				dto = new DTO(id2, name, stat, homerun, strike, hit);
				list.add(dto);

			}
		} catch (SQLException e) {
			System.out.println("D");
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	public DTO getPlayer(String id) {
		DTO player = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);

			String selectSql = "SELECT * FROM PLAYERS WHERE id = ?";
			psmt = conn.prepareStatement(selectSql);
			psmt.setString(1, id);

			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				player = new DTO(rs.getString("ID"), rs.getString("NAME"), rs.getInt("STAT"), rs.getInt("HOMERUN"),
						rs.getInt("STRIKE"), rs.getInt("HIT"));

				// Players DTO를 가저와야 되는거니 새로 짜야 된다.

			}

			rs.close();
			psmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}

	public void addResult(String name, int homerun, int hit, int strike, int score) {
		try { // 이부분 수정
			getCon();

			String Sql = "update PLAYERS set homerun = ? , hit = ? , strike = ?, score = ? where name = ?";
			// 캐릭터 이름으로 바꿔야 할수 있다.
			psmt = conn.prepareStatement(Sql);
			psmt.setInt(1, homerun); // 홈런 수
			psmt.setInt(2, hit); // 안타 수
			psmt.setInt(3, strike); // 스트라이크 수
			psmt.setInt(4, score); // 스트라이크 수
			psmt.setString(5, name); // 사용자 아이디

			int rows = psmt.executeUpdate();
			System.out.println(rows + "개의 행이 추가되었습니다.");

			psmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	    public void addResult(String id, int homerun, int hit, int strike, int score) {
//	    	try { // 이부분 수정
//	    		Class.forName("oracle.jdbc.driver.OracleDriver");
//	    		conn = DriverManager.getConnection(url, user, password);
//	    		
//	    		String insertSql = "INSERT INTO PLAYERS (id, HOMERUN, HIT, STRIKE) VALUES (?, ?, ?, ?)";
//	    		//캐릭터 이름으로 바꿔야 할수 있다.
//	    		psmt = conn.prepareStatement(insertSql);
//	    		psmt.setString(1, id); // 사용자 아이디
//	    		psmt.setInt(2, homerun); // 홈런 수
//	    		psmt.setInt(3, hit); // 안타 수
//	    		psmt.setInt(4, strike); // 스트라이크 수
//	    		
//	    		
//	    		int rows = psmt.executeUpdate();
//	    		System.out.println(rows + "개의 행이 추가되었습니다.");
//	    		
//	    		psmt.close();
//	    		conn.close();
//	    	} catch (ClassNotFoundException e) {
//	    		e.printStackTrace();
//	    	} catch (SQLException e) {
//	    		e.printStackTrace();
//	    	}
//	    }

	public DTO getRandomPlayer() {
		DTO player = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);

			String selectSql = "SELECT * FROM PLAYERS ORDER BY DBMS_RANDOM.RANDOM";
			psmt = conn.prepareStatement(selectSql);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				player = new DTO(rs.getString("ID"), rs.getString("NAME"), rs.getInt("STAT"), rs.getInt("HOMERUN"),
						rs.getInt("STRIKE"), rs.getInt("HIT"));
			}

			rs.close();
			psmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}

	public int getStat(String id) {
		int stat = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);

			String selectSql = "SELECT STAT FROM PLAYERS WHERE id = ?";
			PreparedStatement psmt = conn.prepareStatement(selectSql);
			psmt.setString(1, id);

			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				stat = rs.getInt("STAT");
			}

			rs.close();
			psmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stat;
	}

}