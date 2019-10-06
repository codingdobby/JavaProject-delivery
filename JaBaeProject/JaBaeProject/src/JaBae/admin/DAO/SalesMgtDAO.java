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

import JaBae.admin.VO.SalesVO;

public class SalesMgtDAO {
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
	
	//날짜로 매출 검색
	public ArrayList<SalesVO> getDaySalesList(String fristday, String lastDay) throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesDateList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		System.out.println(fristday);
		System.out.println(lastDay);
		
		String sql = "select se.sell_id, nvl(sa.c_id_sales_fk, d.name), to_char(d.start_date), nvl(to_char(d.end_date), '배송중'), sa.fee, nvl2(to_char(sa.fee), 'x', 'O') " + 
				"from sales sa, delivery_select d, seller se " + 
				"where sa.o_no_sales_fk = d.order_no and se.sell_id = d.seller_id_ds_fk "
				+ " and (d.start_date between ? and sysdate)"
				+ " order by d.start_date desc";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, fristday);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setIdSeller(rs.getString(1));
			salesVo.setNameCustomer(rs.getString(2));
			salesVo.setStartDay(rs.getString(3));
			salesVo.setEndDay(rs.getString(4));
			salesVo.setFee(rs.getString(5));
			salesVo.setFreeTicket(rs.getString(6));
			
			salesDateList.add(salesVo);
		}
		
		sqlClose();
		
		return salesDateList;
	}
	
	//고객별 매출 검색
	public ArrayList<SalesVO> getCustomerSales() throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesCustomerList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		
		String sql = "select se.name, c.cus_id, to_char(d.start_date), nvl(to_char(d.end_date), '배송중')," + 
				"        sa.fee, nvl2(to_char(sa.fee), 'x', 'O')" + 
				"from sales sa, delivery_select d, seller se, customer c " + 
				"where sa.o_no_sales_fk = d.order_no and se.sell_id = d.seller_id_ds_fk " + 
				"and sa.c_id_sales_fk = c.cus_id"
				+ " order by c.name";
		
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setIdSeller(rs.getString(1));
			salesVo.setNameCustomer(rs.getString(2));
			salesVo.setStartDay(rs.getString(3));
			salesVo.setEndDay(rs.getString(4));
			salesVo.setFee(rs.getString(5));
			salesVo.setFreeTicket(rs.getString(6));
			
			salesCustomerList.add(salesVo);
		}
		
		sqlClose();
		
		return salesCustomerList;
	}
	
	//이름 조회
	public ArrayList<SalesVO> getCustomerSalesName(String tfName) throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesCustomerNameList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		
		String sql = "select se.name, c.cus_id, to_char(d.start_date), nvl(to_char(d.end_date), '배송중')," + 
				"        sa.fee, nvl2(to_char(sa.fee), 'x', 'O')" + 
				"from sales sa, delivery_select d, seller se, customer c " + 
				"where sa.o_no_sales_fk = d.order_no and se.sell_id = d.seller_id_ds_fk " + 
				"and sa.c_id_sales_fk = c.cus_id and c.name = ?"
				+ " order by c.name";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tfName);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setIdSeller(rs.getString(1));
			salesVo.setNameCustomer(rs.getString(2));
			salesVo.setStartDay(rs.getString(3));
			salesVo.setEndDay(rs.getString(4));
			salesVo.setFee(rs.getString(5));
			salesVo.setFreeTicket(rs.getString(6));
			
			salesCustomerNameList.add(salesVo);
		}
		
		sqlClose();
		
		return salesCustomerNameList;
	}
	
	//아이디 조회
	public ArrayList<SalesVO> getCustomerSalesId(String tfId) throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesCustomerIdList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		
		String sql = "select se.name, c.cus_id, to_char(d.start_date), nvl(to_char(d.end_date), '배송중')," + 
				"        sa.fee, nvl2(to_char(sa.fee), 'x', 'O')" + 
				"from sales sa, delivery_select d, seller se, customer c " + 
				"where sa.o_no_sales_fk = d.order_no and se.sell_id = d.seller_id_ds_fk " + 
				"and sa.c_id_sales_fk = c.cus_id and c.cus_id = ?"
				+ " order by c.name";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tfId);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setIdSeller(rs.getString(1));
			salesVo.setNameCustomer(rs.getString(2));
			salesVo.setStartDay(rs.getString(3));
			salesVo.setEndDay(rs.getString(4));
			salesVo.setFee(rs.getString(5));
			salesVo.setFreeTicket(rs.getString(6));
			
			salesCustomerIdList.add(salesVo);
		}
		
		sqlClose();
		
		return salesCustomerIdList;
	}
	
	//아이디 이름 조회
	public ArrayList<SalesVO> getCustomerSalesIdName(String tfId,String tfName) throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesCustomerNameIdList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		
		String sql = "select se.name, c.cus_id, to_char(d.start_date), nvl(to_char(d.end_date), '배송중')," + 
				"        sa.fee, nvl2(to_char(sa.fee), 'x', 'O')" + 
				"from sales sa, delivery_select d, seller se, customer c " + 
				"where sa.o_no_sales_fk = d.order_no and se.sell_id = d.seller_id_ds_fk " + 
				"and sa.c_id_sales_fk = c.cus_id and (c.cus_id = ? and d.name =?)"
				+ " order by c.name";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tfId);
		pstmt.setString(2, tfName);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setIdSeller(rs.getString(1));
			salesVo.setNameCustomer(rs.getString(2));
			salesVo.setStartDay(rs.getString(3));
			salesVo.setEndDay(rs.getString(4));
			salesVo.setFee(rs.getString(5));
			salesVo.setFreeTicket(rs.getString(6));
			
			salesCustomerNameIdList.add(salesVo);
		}
		
		sqlClose();
		
		return salesCustomerNameIdList;
	}
	
	public ArrayList<SalesVO> getDayGragh() throws SQLException {
		sqlOpen();
		ArrayList<SalesVO> salesDayList = new ArrayList<SalesVO>();
		
		SalesVO salesVo = null;
		
		
		String sql = "select start_date, sum(s.fee) " + 
				"from delivery_select d, sales s " + 
				"where s.o_no_sales_fk = d.order_no " + 
				"group by start_date";
		
		pstmt = conn.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			salesVo = new SalesVO();
			salesVo.setStartDay(rs.getString(1));
			salesVo.setFee(rs.getString(2));
			
			salesDayList.add(salesVo);
		}
		
		sqlClose();
		return salesDayList;
	}
}
