/*
 * w.이승연
 * */

package JaBae.Gisa.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import JaBae.Gisa.VO.GisaVO;


public class LocalDeliveryDAO {
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
	
	public ArrayList<GisaVO> loadDList(String gisaId) throws SQLException {
		sqlOpen();
		
		ArrayList<GisaVO> dList = new ArrayList<GisaVO>();
		GisaVO gisaVO = null;
		
		String sql = "select order_no, object_name, addr, to_char(start_date) from delivery_select " + 
				"where l_no_ds_fk = (select l_no_gisa_fk from gisa where gisa_id = ?) and gisa_id_ds_fk is null and start_date is not null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, gisaId);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVO = new GisaVO();
			gisaVO.setOrderNo(rs.getString(1));
			gisaVO.setObjectName(rs.getString(2));
			gisaVO.setAddrOrderer(rs.getString(3));
			gisaVO.setStartDate(rs.getString(4));
			
			dList.add(gisaVO);
		}
		
		sqlClose();
		
		return dList;
	}
	
	public void updateGisaId(String gisaId, int order_no) throws SQLException {
		sqlOpen();
		
		ArrayList<GisaVO> dList = new ArrayList<GisaVO>();
		GisaVO gisaVO = null;
		
		String sql = "update delivery_select set gisa_id_ds_fk = ? where order_no = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, gisaId);
		pstmt.setInt(2, order_no);
		
		rs = pstmt.executeQuery();
		sqlClose();
		
	}
}
