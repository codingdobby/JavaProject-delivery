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

public class GisaMgtDAO {
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

	// 기사 전체 조회
	public ArrayList<GisaVO> getGisaList() throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaList.add(gisaVo);
		}

		sqlClose();

		return gisaList;
	}

	// 아이디만 검색한 경우
	public ArrayList<GisaVO> getGisaIdList(String id) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaIdList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.gisa_id = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, id);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaIdList.add(gisaVo);
		}

		sqlClose();

		return gisaIdList;
	}

	// 아이디만 검색, 퇴사한 사람 제외한 경우
	public ArrayList<GisaVO> getGisaIdExcudeRegList(String id) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaIdList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.gisa_id = ? and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, id);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaIdList.add(gisaVo);
		}

		sqlClose();

		return gisaIdList;
	}

	// 이름만 검색한 경우
	public ArrayList<GisaVO> getGisaNameList(String name) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.name = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 이름만 검색, 퇴사한 사람 제외한 경우
	public ArrayList<GisaVO> getGisaNameExcudeRegList(String name) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.name = ? and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 입사일로만 검색한 경우
	public ArrayList<GisaVO> getGisaHireDateList(String hiredate) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaHireDateList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.hiredate = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, hiredate);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaHireDateList.add(gisaVo);
		}

		sqlClose();

		return gisaHireDateList;
	}

	// 입사일로만 검색, 퇴사한 사람 제외한 경우
	public ArrayList<GisaVO> getGisaHireDateExcudeRegList(String hiredate) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaHireDateList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.hiredate = ? and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, hiredate);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaHireDateList.add(gisaVo);
		}

		sqlClose();

		return gisaHireDateList;
	}

	// 지역만 검색한 경우
	public ArrayList<GisaVO> getGisaLocalList(String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaLocalList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and l.loc = ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaLocalList.add(gisaVo);
		}

		sqlClose();

		return gisaLocalList;
	}

	// 지역만 검색, 퇴사한 사람 제외한 경우
	public ArrayList<GisaVO> getGisaLocalExcudeRegList(String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaLocalList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and l.loc = ? and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaLocalList.add(gisaVo);
		}

		sqlClose();

		return gisaLocalList;
	}

	// 퇴직한 사람 제외하고 조회
	public ArrayList<GisaVO> getGisaExcudeRegList() throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaExcudeRegList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaExcudeRegList.add(gisaVo);
		}

		sqlClose();

		return gisaExcudeRegList;
	}

	// 이름과 입사일로 검색
	public ArrayList<GisaVO> getGisaNameHireDateList(String name, String hiredate) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name = ? and g.hiredate = ?)";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, hiredate);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 이름과 입사일로 검색
	public ArrayList<GisaVO> getGisaNameHireDateExcudeRegList(String name, String hiredate) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name = ? and g.hiredate = ?) "
				+ "and g.regdate is null";
		;

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, hiredate);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 이름과 지역으로 검색

	public ArrayList<GisaVO> getGisaNameLocalRegList(String name, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name = ? and l.loc = ?) ";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 이름과 지역으로 검색 + 퇴사자 제외
	public ArrayList<GisaVO> getGisaNameLocalExcudeRegList(String name, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name = ? and l.loc = ?) "
				+ "and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 입사일과 지역으로 검색
	public ArrayList<GisaVO> getGisaHireDateLocalRegList(String hiredate, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.hiredate = ? and l.loc = ?) ";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, hiredate);
		pstmt.setString(2, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 입사일과 지역 +퇴사자 제외 검색

	public ArrayList<GisaVO> getGisaHireDateLocalExcudeRegList(String hiredate, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.hiredate = ? and l.loc = ?) "
				+ "and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, hiredate);
		pstmt.setString(2, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

	// 이름과 입사일 지역으로 검색
	
	public ArrayList<GisaVO> getGisaNameHireDateLocalRegList(String name ,String hiredate, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name =? and g.hiredate = ? and l.loc = ? ) ";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, hiredate);
		pstmt.setString(3, local);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}
	
	
	
	
	
	// 이름과 입사일 지역 + 퇴사자 제외 검색

	public ArrayList<GisaVO> getGisaNameHireDateLocalExcudeRegList(String name ,String hiredate, String local) throws SQLException {
		sqlOpen();

		ArrayList<GisaVO> gisaNameList = new ArrayList<GisaVO>();
		GisaVO gisaVo = null;

		String sql = "select g.gisa_id, g.name, g.tel, to_char(g.hiredate, 'yyyy-mm-dd'), nvl(to_char(g.regdate, 'yyyy-mm-dd'),'재직중') , l.loc "
				+ "from gisa g, loc l " + "where g.l_no_gisa_fk = l.l_no" + " and (g.name =? and g.hiredate = ? and l.loc = ? ) "
				+ "and g.regdate is null";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, name);
		pstmt.setString(2, hiredate);
		pstmt.setString(3, local);
		
		rs = pstmt.executeQuery();

		while (rs.next()) {
			gisaVo = new GisaVO();
			gisaVo.setGisaId(rs.getString(1));
			gisaVo.setGisaName(rs.getString(2));
			gisaVo.setGisaTel(rs.getString(3));
			gisaVo.setHiredate(rs.getString(4));
			gisaVo.setRegdate(rs.getString(5));
			gisaVo.setLocal(rs.getString(6));

			gisaNameList.add(gisaVo);
		}

		sqlClose();

		return gisaNameList;
	}

}