package JaBae.Customer.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JaBae.Customer.VO.MemberVO;

public class MemberDAO {
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
//아이디 조회하기 로그인
	public boolean getid(String id) {
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

			// rs테이블의 모든 레코드를 botari에 담는 과정

		} catch (SQLException e) {
			e.printStackTrace();
		} // 공식

		closeDB();
		return result;
	}

	/*****************************************************************************************/
//selectView 화면 포인트 조회
	public MemberVO getPoint(String id) {
		connectDB();
		String SQL = "select point from customer where cus_id=?";// 회원인지 아닌지 구별
		MemberVO vo = null;// ==>int a;

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);

			rs = pstm.executeQuery(); // select 문 수행시
			// 결과 출력
			if (rs.next() == true) {
				vo = new MemberVO();

				vo.setPoint(rs.getInt("point"));

			} else {

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return vo;

	}

	/*****************************************************************************************/
	// 정보 수정 페이지에 값 바로 뜨게 동작하는 기능
	public MemberVO getInfo(String idchk) { //
		ArrayList<MemberVO> botari = new ArrayList<MemberVO>();

		connectDB();
		String SQL = "select name, birth,substr(tel, 0,3) as tel1 , substr(tel,5,4) as tel2 ,  substr(tel,10,4) as tel3, addr from customer where cus_id=? ";
		MemberVO vo = null;// 결과를 담는 그릇 객체

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, idchk);
			rs = pstm.executeQuery(); // select 문 수행 결과가 rs 테이블에 다 담겨져 있음

			// rs 테이블의 모든 레코드를 botari에 담는다
			if (rs.next() == true) {
				vo = new MemberVO();// 1개의 레코드를 담을 빈그릇(vo)을 준비
				vo.setName(rs.getString("name"));
				vo.setTel(rs.getString("tel1"));
				vo.setTel2(rs.getString("tel2"));
				vo.setTel3(rs.getString("tel3"));
				vo.setAddr(rs.getString("addr"));

				if (rs.getString("birth") == null) {
					vo.setBirth("");

				} else {
					vo.setBirth(rs.getString("birth"));

				}

			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return vo;

	}

	/*****************************************************************************************/
	// 고객 정보 수정
	public void Update(String pwd, String local, String addr, String tel, String birth, String cus_id) {

		connectDB();
		String SQL = "update customer set pwd=? ,l_no_cus_fk = (select l_no from loc where loc = ?), addr=? ,tel=?,birth=?  where cus_id=?";
		try {

			pstm = conn.prepareStatement(SQL);

			pstm.setString(1, pwd);
			pstm.setString(2, local);
			pstm.setString(3, addr);
			pstm.setString(4, tel);
			pstm.setString(5, birth);
			pstm.setString(6, cus_id);

			pstm.executeUpdate();

		} catch (SQLException se) {

			se.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			closeDB();
		}

	}

	
	
	
	/*****************************************************************************************/

	// 탈퇴 기능
	public void Delete(String id) {

		connectDB();
		String SQL = "delete from customer where cus_id=?";
		try {

			pstm = conn.prepareStatement(SQL);

			pstm.setString(1, id);

			pstm.executeUpdate();

		} catch (SQLException se) {

			se.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			closeDB();
		}

	}

	/*****************************************************************************************/
	// 아이디 찾기
	public MemberVO getID(String name, String tel) { //

		connectDB();
		String SQL = "select cus_id  from customer where name=? and tel=?";
		MemberVO vo = null;// 결과를 담는 그릇 객체

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, name);
			pstm.setString(2, tel);

			rs = pstm.executeQuery(); // select 문 수행 결과가 rs 테이블에 다 담겨져 있음

			// rs 테이블의 모든 레코드를 botari에 담는다
			if (rs.next() == true) {
				vo = new MemberVO();// 1개의 레코드를 담을 빈그릇(vo)을 준비
				vo.setCus_id(rs.getString("cus_id"));

			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return vo;

	}

	/*****************************************************************************************/

	// 비밀번호 찾기
	public MemberVO getPwd(String id, String tel) { //

		connectDB();
		String SQL = "select substr(pwd,0,3) as pwd   , substr(pwd, 6,3) as pwd2 from customer where cus_id=? and tel=?";
		MemberVO vo = null;// 결과를 담는 그릇 객체

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, tel);

			rs = pstm.executeQuery(); // select 문 수행 결과가 rs 테이블에 다 담겨져 있음

			// rs 테이블의 모든 레코드를 botari에 담는다
			if (rs.next() == true) {
				vo = new MemberVO();// 1개의 레코드를 담을 빈그릇(vo)을 준비
				vo.setPwd(rs.getString("pwd"));
				vo.setPwd2(rs.getString("pwd2"));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		closeDB();
		return vo;

	}

}