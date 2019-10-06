package JaBae.Customer.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PointDAO {
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
	// 포인트 사용 여부

	public void UpdatePointUse(String tel, int order_no) {
		connectDB();

		String SQL = "update "
				+ "(select s.p_use as p_use from customer c, delivery_select d, sales s where c.cus_id = s.c_id_sales_fk "
				+ "and d.order_no = s.o_no_sales_fk " + "and (d.tel = ? and d.order_no = ?)) a  set a.p_use  = 'o'"
						;

		try { // 안쓰면 에러남
			pstm = conn.prepareStatement(SQL);// 공식
			pstm.setString(1, tel);// 첫번째 물음표
			pstm.setInt(2, order_no);// 두번째 물음표

			pstm.executeUpdate();// insert, delete, update문에 사용=>수행 결과가 없기 때문
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		closeDB();// 연결 끊기

	}

	// 포인트 등록

	public void UpdatePoint(String id) {
		connectDB();

		String SQL = "update customer set point= point + 500 where cus_id = ?";

		try { // 안쓰면 에러남
			pstm = conn.prepareStatement(SQL);// 공식
			pstm.setString(1, id);// 첫번째 물음표

			pstm.executeUpdate();// insert, delete, update문에 사용=>수행 결과가 없기 때문
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		closeDB();// 연결 끊기

	}

	// 등록여부 조회

	public Boolean SelectPointUse(String tel, int order_no) {
		connectDB();

		boolean check_use = false;
		String SQL = "select s.p_use   from sales s, delivery_select d  " + 
				"where s.o_no_sales_fk = d.order_no " + 
				"and (d.tel = ? and d.order_no =?)";

		try { // 안쓰면 에러남
			pstm = conn.prepareStatement(SQL);// 공식
			pstm.setString(1, tel);// 첫번째 물음표
			pstm.setInt(2, order_no);// 두번째 물음표

			rs = pstm.executeQuery();// select문 수행시 사용 => table결과 보여줌

			// 결과출력

			if (rs.next() == true) {
				System.out.println(rs.getString("p_use"));
				if (rs.getString("p_use").equals("x")) {// rs.next() => 커서 한칸 밑으로 옮김

					check_use = true;
					
				} else if (rs.getString("p_use").equals("o")) {
					check_use = false;

					System.out.println(check_use);
				}
			}//rs end
		} catch (SQLException e) {

			e.printStackTrace();
		}
		closeDB();// 연결 끊기
		return check_use;
	}

}