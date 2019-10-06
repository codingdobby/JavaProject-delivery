/*
 * w.이승연
 * */

package JaBae.admin.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import JaBae.admin.VO.GisaVO;

public class GisaJoinUpdateDAO {
	private static Connection conn; // id, pwd, ulr
	private static Statement stmt; // sql execute
	private static ResultSet rs; // 결과물
	private static PreparedStatement pstmt;

	void sqlOpen() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521/xe"; // 2.url설정
		String id = "Sample";
		String pwd = "sample";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 1.드라이버 로딩
			System.out.println("jdbc 연결 성공");

			conn = DriverManager.getConnection(url, id, pwd); // 3.db연결 접속 확인
			System.out.println("oracle연결 성공");

		} catch (ClassNotFoundException e) {
			System.out.println("jdbc 연결 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("orcale연결 실패");
		}

		stmt = conn.createStatement();

	}

	void sqlClose() throws SQLException {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	//지역 목록을 가져오는 메서드
	public ArrayList<GisaVO> getLocalList() throws SQLException {
		sqlOpen();
		
		ArrayList<GisaVO> localList = new ArrayList<GisaVO>();
		GisaVO gisaVO = new GisaVO();
		
		String sql = "select loc from loc";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVO = new GisaVO();
			gisaVO.setLocal(rs.getString("loc"));

			localList.add(gisaVO);
		}
		
		sqlClose();
		
		return localList;
	}
	
	//아이디가 있는지 체크하는 메서드
	public boolean getid(String id) throws SQLException {
		sqlOpen();

		boolean result = false;
		ArrayList<GisaVO> botari = new ArrayList<GisaVO>();

		String SQL = "select gisa_id from gisa  where gisa_id = ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}

			// rs테이블의 모든 레코드를 botari에 담는 과정

		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		sqlClose();
		return result;
	}

	//기사등록
	public void insertGisa(String id, String pwd, String name, String tel, String local) throws SQLException {
		sqlOpen();
		
		String SQL = "insert into gisa values(?,?,?,?,sysdate, null, "
				+ "(select l_no from loc where loc = ?))";

		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, id);
		pstmt.setString(2, pwd);
		pstmt.setString(3, name);
		pstmt.setString(4, tel);
		pstmt.setString(5, local);

		pstmt.executeUpdate();		
		
		sqlClose();
	}
	
	//기사 불러옴
	public GisaVO loadGisa(String id) throws SQLException {
		sqlOpen();

		GisaVO gisaVo = null;

		String sql = "select gisa_id, name, tel, loc "
				+ "from gisa, loc where gisa.l_no_gisa_fk = loc.l_no and gisa_id = ?";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setLocal(rs.getString(4));
		}

		sqlClose();
		return gisaVo;
	}
	
	//업데이트 기사
	public void updateGisa(String id, String pwd, String name, String tel, String local) throws SQLException {
		sqlOpen();
	
		String SQL = "update gisa set name = ?, pwd= ?, tel= ?, "
				+ "l_no_gisa_fk = (select l_no from loc where loc = ?) " + 
				"where gisa_id = ?";

		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, name);
		pstmt.setString(2, pwd);
		pstmt.setString(3, tel);
		pstmt.setString(4, local);
		pstmt.setString(5, id);

		pstmt.executeUpdate();		
		
		sqlClose();
	}

	//기사 퇴직
	public void ResignGisa(String id) throws SQLException {
		sqlOpen();
		
		String SQL = "update gisa set regdate = sysdate " + 
				"where gisa_id = ?";

		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, id);

		pstmt.executeUpdate();		
		
		sqlClose();
	}
	
}
