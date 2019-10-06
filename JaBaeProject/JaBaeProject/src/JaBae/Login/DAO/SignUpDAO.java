/*
 * w.이승연
 * */

package JaBae.Login.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import JaBae.Login.VO.SellerVO;

public class SignUpDAO {

	private static Connection conn; // id, pwd, ulr
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
	}

	void sqlClose() throws SQLException {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}
	
	
	//지역 목록을 가져오는 메서드
	public ArrayList<SellerVO> getLocalList() throws SQLException {
		sqlOpen();
				
		ArrayList<SellerVO> localList = new ArrayList<SellerVO>();
		SellerVO signupVO = new SellerVO();
				
		String sql = "select loc from loc";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			signupVO = new SellerVO();
			signupVO.setLocal(rs.getString("loc"));

			localList.add(signupVO);
		}
				
		sqlClose();
				
		return localList;
	}
	
	
	
	//고객 회원가입 기능
	public void signUpCustomer(String id, String pwd, String name, String addr, String tel, String birth, String local) throws SQLException {
		sqlOpen();

		String SQL = "insert into customer values(?, ?, ?, ?, ?, ?, sysdate ,"
				+ "0, (select l_no from loc where loc = ?))";

		try { 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, addr);
			pstmt.setString(5, tel);
			pstmt.setString(6, birth);
			pstmt.setString(7, local);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sqlClose();

	}
	
	//고객 회원가입 기능
	public void signUpSeller(String id, String pwd, String name, String addr, String tel) throws SQLException {
		sqlOpen();

		String SQL = "insert into seller values(?, ?, ?, ?, ?, sysdate , 0)";

		try { 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, addr);
			pstmt.setString(5, tel);
			
			System.out.println("Aa");

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sqlClose();

	}
		
}
