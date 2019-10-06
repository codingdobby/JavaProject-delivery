package JaBae.Customer.DAO;

//조회기능 저장 기능ㄴ 전체 보여주기
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import JaBae.Customer.VO.SearchVO;

public class SearchDAO {
	private Connection conn = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null; // select문 수행 후 결과를 저장하는 객체

	public void connectDB() {
		String url = "jdbc:oracle:thin:@localhost:1521/xe"; // 2.url설정
		String id = "sample";
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

	public Vector getList() {

		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		connectDB();
		String SQL = "select   * from delivery_select";

		try {
			pstm = conn.prepareStatement(SQL);

			rs = pstm.executeQuery();

			while (rs.next()) {

				int order_no = rs.getInt("order_no");
				String cus_name = rs.getString("name");
				String cus_tel = rs.getString("tel");
				String gisa_id = rs.getString("gisa_id_fk");
				String start_Date = rs.getString("start_date");
				String end_date = rs.getString("end_date");
				int l_no_fk = rs.getInt("l_no_fk");

				Vector row = new Vector();

				row.add(order_no);
				row.add(cus_name);
				row.add(cus_tel);
				row.add(gisa_id);
				row.add(start_Date);
				row.add(end_date);
				row.add(l_no_fk);

				data.add(row);

			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return data;
	}

	// 송장 번호
	// 정보 수정 페이지에 값 바로 뜨게 동작하는 기능
	public SearchVO getSong(int songjang) { //
		connectDB();

		String SQL = " select d.order_no, d.name, d.tel,d. object_name, g.name as gName, g.tel as gtel ,d.start_date, d.end_date, l.loc  , s.name sname "
				+ "from  gisa g  right join  delivery_select d  on g.gisa_id = d.gisa_id_ds_fk  , loc l ,seller s "
				+ "where l.l_no = d.l_no_ds_fk  " + "and d.order_no =? " + "and s.sell_id = d.seller_id_ds_fk";

		SearchVO vo = null;// 결과를 담는 그릇 객체

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, songjang);
			rs = pstm.executeQuery(); // select 문 수행 결과가 rs 테이블에 다 담겨져 있음

			// rs 테이블의 모든 레코드를 botari에 담는다
			if (rs.next() == true) {
				vo = new SearchVO();// 1개의 레코드를 담을 빈그릇(vo)을 준비
				vo.setObject_name(rs.getString("object_name"));
				vo.setName(rs.getString("name"));
				vo.setTel(rs.getString("tel"));
				vo.setGtel(rs.getString("gtel"));
				vo.setGName(rs.getString("gname"));
				vo.setStart_date(rs.getString("start_date"));
				vo.setEnd_date(rs.getString("end_date"));
				vo.setSname(rs.getString("sname"));
				vo.setLoc(rs.getString("loc"));

			}
			System.out.println(rs.getString("object_name"));
			System.out.println("기사이름" + rs.getString("gname"));
			System.out.println("판매자 이름" + rs.getString("sname"));
			System.out.println("지역" + rs.getString("loc"));
			System.out.println("도착일" + rs.getString("end_date"));
			System.out.println("출발일" + rs.getString("start_date"));

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return vo;

	}
	//

	// 주문 내역 조회
	public Vector getOrder(String id, String First) {

		Vector data = new Vector();
		connectDB();
		String SQL = "select d.order_no ,d.name, d.addr,d.tel, g.name as Gname, d.start_date, d.end_date "
				+ "from delivery_select d, gisa g, customer c , sales s " + "where d.gisa_id_ds_fk = g.gisa_id "
				+ "and d.order_no= s.o_no_sales_fk " + "and c.cus_id = s.c_id_sales_fk " + "and  c.cus_id=? "
				+ "and TO_CHAR(d.end_date,'yyyy-mm-dd') between ? and sysdate";

		try {
			pstm = conn.prepareStatement(SQL);

			pstm.setString(1, id);
			pstm.setString(2, First);

			rs = pstm.executeQuery();

			while (rs.next()) {

				int order_no = rs.getInt("order_no");
				String cus_name = rs.getString("name");
				String cus_tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String gisa_name = rs.getString("Gname");
				String start_date = rs.getString("start_date");
				String endDate = rs.getString("end_date");

				// 데이터 추가
				Vector row = new Vector();

				row.add(order_no);
				row.add(cus_name);
				row.add(cus_tel);
				row.add(addr);
				row.add(gisa_name);
				row.add(start_date);
				row.add(endDate);

				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return data;
	}
}