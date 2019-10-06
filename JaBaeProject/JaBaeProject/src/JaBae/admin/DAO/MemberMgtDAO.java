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
import JaBae.admin.VO.MemberMgtCustomerVO;
import JaBae.admin.VO.MemberMgtSellerVO;

public class MemberMgtDAO {
	private static Connection conn; //id, pwd, ulr
	private static Statement stmt; //sql execute
	private static ResultSet rs; //결과물
	private static PreparedStatement pstmt;
	
	void sqlOpen() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521/xe"; //2.url설정
		String id = "Sample";
		String pwd = "sample";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//1.드라이버 로딩
			System.out.println("jdbc 연결 성공");
			
			conn = DriverManager.getConnection(url, id, pwd); //3.db연결 접속 확인
			System.out.println("oracle연결 성공");
			
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc 연결 실패");
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("orcale연결 실패");
		}
		
		stmt = conn.createStatement();
		
	}
	
	void sqlClose() throws SQLException {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(stmt != null) stmt.close();
		if(conn!= null) conn.close();
	}
	
	/*
	 * 고객 전체 조회
	 * */
	//주문 시켰던 고객
	public ArrayList<MemberMgtCustomerVO> getCusomerOrderList() throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select distinct c.cus_id, c.name, to_char(c.birth, 'yyyy-mm-dd'), to_char(c.joineddate, 'yyyy-mm-dd'), nvl(to_char(d.end_date, 'yyyy-mm-dd'), '배송 이력 없음'), c.point " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk " + 
				"and (cus_id,nvl(to_char(d.end_date, 'yyyy/mm/dd'), '배송 이력 없음')) in (select cus_id, nvl(to_char(max(d.end_date), 'yyyy/mm/dd'), '배송 이력 없음')" + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer(rs.getString(5));
			customerVO.setPointCustomer(rs.getString(6));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	//주문 안시킨 고객
	public ArrayList<MemberMgtCustomerVO> getCusomerNonOrderList() throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select cus_id, name, to_char(birth, 'yyyy-mm-dd'), to_char(joineddate, 'yyyy-mm-dd'), point"
				+ " from customer " + 
				"where cus_id != all(select distinct c.cus_id " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer("배송 이력 없음");
			customerVO.setPointCustomer(rs.getString(5));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	/*
	 * 고객 아이디
	 * */
	public ArrayList<MemberMgtCustomerVO> getCusomerOrderIdList(String id) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select distinct c.cus_id, c.name, to_char(c.birth, 'yyyy-mm-dd'), to_char(c.joineddate, 'yyyy-mm-dd'), nvl(to_char(d.end_date, 'yyyy-mm-dd'), '배송 이력 없음'), c.point " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk " + 
				"and (cus_id,nvl(to_char(d.end_date, 'yyyy/mm/dd'), '배송 이력 없음')) in (select cus_id, nvl(to_char(max(d.end_date), 'yyyy/mm/dd'), '배송 이력 없음')" + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)"
				+ " and cus_id = ?";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer(rs.getString(5));
			customerVO.setPointCustomer(rs.getString(6));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	public ArrayList<MemberMgtCustomerVO> getCusomerNonOrderIdList(String id) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select cus_id, name, to_char(birth, 'yyyy-mm-dd'), to_char(joineddate, 'yyyy-mm-dd'), point"
				+ " from customer " + 
				"where cus_id != all(select distinct c.cus_id " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)"
				+ " and cus_id = ?";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer("배송 이력 없음");
			customerVO.setPointCustomer(rs.getString(5));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	/*
	 * 고객 이름
	 * */
	public ArrayList<MemberMgtCustomerVO> getCusomerOrderNameList(String name) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select distinct c.cus_id, c.name, to_char(c.birth, 'yyyy-mm-dd'), to_char(c.joineddate, 'yyyy-mm-dd'), nvl(to_char(d.end_date, 'yyyy-mm-dd'), '배송 이력 없음'), c.point " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk " + 
				"and (cus_id,nvl(to_char(d.end_date, 'yyyy/mm/dd'), '배송 이력 없음')) in (select cus_id, nvl(to_char(max(d.end_date), 'yyyy/mm/dd'), '배송 이력 없음')" + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)"
				+ " and c.name = ?";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer(rs.getString(5));
			customerVO.setPointCustomer(rs.getString(6));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	public ArrayList<MemberMgtCustomerVO> getCusomerNonOrderNameList(String name) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtCustomerVO> customerList = new ArrayList<MemberMgtCustomerVO>();
		MemberMgtCustomerVO customerVO = null;

		String sql = "select cus_id, name, to_char(birth, 'yyyy-mm-dd'), to_char(joineddate, 'yyyy-mm-dd'), point"
				+ " from customer " + 
				"where cus_id != all(select distinct c.cus_id " + 
				"from customer c, sales s, delivery_select d " + 
				"where c.cus_id = s.c_id_sales_fk and d.order_no = s.o_no_sales_fk group by cus_id)"
				+ " and name = ?";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			customerVO = new MemberMgtCustomerVO();
			customerVO.setIdCustomer(rs.getString(1));
			customerVO.setNameCustomer(rs.getString(2));
			customerVO.setBirthCustomer(rs.getString(3));
			customerVO.setJoinedDateCustomer(rs.getString(4));
			customerVO.setLastOrderCustomer("배송 이력 없음");
			customerVO.setPointCustomer(rs.getString(5));

			customerList.add(customerVO);
		}

		sqlClose();

		return customerList;
	}
	
	//판매자 전체 조회
	public ArrayList<MemberMgtSellerVO> getSellerList() throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtSellerVO> sellerList = new ArrayList<MemberMgtSellerVO>();
		MemberMgtSellerVO sellerVO = null;

		String sql = "select sell_id, name, joineddate, freeticketcnt " + 
				"from seller";

		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			sellerVO = new MemberMgtSellerVO();
			sellerVO.setIdSeller(rs.getString(1));
			sellerVO.setNameSeller(rs.getString(2));
			sellerVO.setJoinedDateSeller(rs.getString(3));
			
			if(rs.getString("freeticketcnt").equals("0")) sellerVO.setFreeTicket("정기권 없음");
			else sellerVO.setFreeTicket(rs.getString(4));

			sellerList.add(sellerVO);
		}

		sqlClose();

		return sellerList;
	}
	
	//판매자 아이디 조회
	public ArrayList<MemberMgtSellerVO> getSellerIdList(String id) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtSellerVO> sellerList = new ArrayList<MemberMgtSellerVO>();
		MemberMgtSellerVO sellerVO = null;
		String sql = "select sell_id, name, joineddate, freeticketcnt " + 
				"from seller"
				+ " where sell_id = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			sellerVO = new MemberMgtSellerVO();
			sellerVO.setIdSeller(rs.getString(1));
			sellerVO.setNameSeller(rs.getString(2));
			sellerVO.setJoinedDateSeller(rs.getString(3));
			
			if(rs.getString("freeticketcnt").equals("0")) sellerVO.setFreeTicket("정기권 없음");
			else sellerVO.setFreeTicket(rs.getString(4));

			sellerList.add(sellerVO);
		}

		sqlClose();
		return sellerList;
	}
	
	//판매자 이름 조회
	public ArrayList<MemberMgtSellerVO> getSellerNameList(String name) throws SQLException{
		sqlOpen();

		ArrayList<MemberMgtSellerVO> sellerList = new ArrayList<MemberMgtSellerVO>();
		MemberMgtSellerVO sellerVO = null;
		String sql = "select sell_id, name, joineddate, freeticketcnt " + 
				"from seller"
				+ " where name = ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			sellerVO = new MemberMgtSellerVO();
			sellerVO.setIdSeller(rs.getString(1));
			sellerVO.setNameSeller(rs.getString(2));
			sellerVO.setJoinedDateSeller(rs.getString(3));
			
			if(rs.getString("freeticketcnt").equals("0")) sellerVO.setFreeTicket("정기권 없음");
			else sellerVO.setFreeTicket(rs.getString(4));

			sellerList.add(sellerVO);
		}	

		sqlClose();
		return sellerList;
	}
}
