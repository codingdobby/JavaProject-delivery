

package JaBae.Login.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JaBae.Login.VO.SellerVO;

public class LoginDAO {
//Data Access Object
	// 공식처럼 사용,사용 많이 함

	private Connection conn = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null; // select문 수행 후 결과를 저장하는 객체

	public void connectDB() {
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

	public void closeDB() {
		try {
			if (pstm != null)
				pstm.close();
			if (rs != null)
				rs.close();
			conn.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		}

	}

	/*****************************************************************************************/
	//사용자 id 중복 체크
	public boolean getCustomerid(String id) {
		boolean result = false;

		connectDB();
		String SQL = "select cus_id from customer where cus_id=?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);

			rs = pstm.executeQuery();
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return result;
	}
	
	//판매자 id체크
	public boolean getSellerid(String id) {
		boolean result = false;

		connectDB();
		String SQL = "select sell_id from seller where sell_id=?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);

			rs = pstm.executeQuery();
			if (rs.next()) {
				System.out.println(result);
				result = true;
			} else {
				System.out.println(result);
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return result;
	}

	/*****************************************************************************************/
	//사용자 로그인 기능
	public boolean loginCustomer(String id, String pwd) {
		boolean check = false;
		connectDB();
		String SQL = "Select * from customer where cus_id= ? and pwd =?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);

			rs = pstm.executeQuery();// select문 수행시 사용 => table결과 보여줌

			// 결과출력
			if (rs.next() == true) {// rs.next() => 커서 한칸 밑으로 옮김

				check = true;

			} else {
				check = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return check;
	}

	/*****************************************************************************************/
	// 관리자 로그인 기능
	public boolean loginAdmin(String id, String pwd) {
		boolean checkAdmin = false;

		connectDB();

		String SQL = "select * from admin where ad_id=? and pwd = ?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);

			rs = pstm.executeQuery();// select문 수행시 사용 => table결과 보여줌
		
			// 결과출력
			if (rs.next() == true) {// rs.next() => 커서 한칸 밑으로 옮김

				checkAdmin = true;
				System.out.println(checkAdmin);
			} else {
				checkAdmin = false;
				
				System.out.println(checkAdmin);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return checkAdmin;

	}
	
	/*****************************************************************************************/
	//판매자 로그인 기능
	public boolean loginSeller(String id, String pwd) {
		boolean checkSeller = false;

		connectDB();

		String SQL = "select * from seller where sell_id=? and pwd = ?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);

			rs = pstm.executeQuery();
		
			if (rs.next() == true) {
				checkSeller = true;
			} else {
				checkSeller = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return checkSeller;
	}
	
	/*****************************************************************************************/
	//기사 로그인 기능
	public boolean loginGisa(String id, String pwd) {
		boolean checkSeller = false;

		connectDB();

		String SQL = "select * from gisa where gisa_id=? and pwd = ?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);

			rs = pstm.executeQuery();
		
			if (rs.next() == true) {
				checkSeller = true;
			} else {
				checkSeller = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return checkSeller;
	}
}