/*
 * w.이승연
 * */

package JaBae.Login.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JaBae.Customer.VO.MemberVO;
import JaBae.Login.VO.SellerVO;

public class FindInfoDAO {
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
	
	
	//판매자 아이디 비밀번호 찾기
	public SellerVO getfindSellerid(String name, String tel) throws SQLException {
		
		sqlOpen();
		String SQL = "select sell_id  from seller where name=? and tel=?";
		SellerVO vo = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, name);
			pstmt.setString(2, tel);

			rs = pstmt.executeQuery();

			if (rs.next() == true) {
				vo = new SellerVO();
				vo.setSellerId(rs.getString("sell_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sqlClose();
		return vo;
	}
	
	// 비밀번호 찾기
	public SellerVO getPwd(String id, String tel) throws SQLException {

		sqlOpen();
		String SQL = "select pwd from seller where sell_id=? and tel=?";
		SellerVO vo = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			pstmt.setString(2, tel);

			rs = pstmt.executeQuery(); 

			if (rs.next() == true) {
				vo = new SellerVO();
				vo.setSellerPwd(rs.getString("pwd"));

			}
		} catch (SQLException e) {
				e.printStackTrace();
		}

		sqlClose();
		return vo;

	}

}
