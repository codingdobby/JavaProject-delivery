/*
 * w.이승연
 * */

package JaBae.admin.EventAction.GisaEventAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import JaBae.admin.DAO.GisaMgtDAO;
import JaBae.admin.VO.GisaVO;

public class GisaEventClass implements ActionListener, KeyListener {

	private DefaultTableModel model;
	private JCheckBox chckbxExcudeReg;
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfHiredate;
	private JTextField tfLocal;

	public GisaEventClass(DefaultTableModel model, JCheckBox chckbxExcudeReg, JTextField tfName, JTextField tfId,
			JTextField tfHiredate, JTextField tfLocal) {
		this.model = model;
		this.chckbxExcudeReg = chckbxExcudeReg;
		this.tfName = tfName;
		this.tfId = tfId;
		this.tfHiredate = tfHiredate;
		this.tfLocal = tfLocal;
	}

	void readGisaList(ArrayList<GisaVO> gisaList) {
		model.setNumRows(0); // 테이블 초기화

		for (GisaVO getSearchName : gisaList) { // 리스트 불러옴
			model.addRow(
					new Object[] { getSearchName.getGisaId(), getSearchName.getGisaName(), getSearchName.getGisaTel(),
							getSearchName.getHiredate(), getSearchName.getRegdate(), getSearchName.getLocal() });
		}
	}
	
	void Search() {
		GisaMgtDAO gisaDAO = new GisaMgtDAO();
		ArrayList<GisaVO> gisaList;

		String id = tfId.getText().toString();
		String name = tfName.getText().toString();
		String hiredate = tfHiredate.getText().toString();
		String local = tfLocal.getText().toString();
		Boolean checkExcudeReg = chckbxExcudeReg.isSelected();

		/** 아이디만 검색한 경우 **/
		if (!id.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaIdList(id);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 이름만 검색한 경우 **/
		} else if (!name.trim().equals("") && id.trim().equals("") && hiredate.trim().equals("")
				&& local.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaNameList(name);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 입사일으로만 검색한 경우 **/
		} else if (!hiredate.trim().equals("") && id.trim().equals("") && name.trim().equals("")
				&& local.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaHireDateList(hiredate);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 지역만 검색한 경우 **/
		} else if (!local.trim().equals("") && id.trim().equals("") && name.trim().equals("")
				&& hiredate.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaLocalList(local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 이름과 입사일로 검색한 경우 **/
		} else if (!name.trim().equals("") && !hiredate.trim().equals("") && id.trim().equals("")
				&& local.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaNameHireDateList(name, hiredate);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 이름과 입사일로 검색, 퇴사한 사람 제회한 경우 **/
		} else if (!name.trim().equals("") && !hiredate.trim().equals("") && chckbxExcudeReg.isSelected()
				&& id.trim().equals("") && local.trim().equals("")) {
			try {
				gisaList = gisaDAO.getGisaNameHireDateExcudeRegList(name, hiredate);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/*
			 * 퇴사한 사람 제외
			 * 
			 */
			/** 아이디만 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!id.trim().equals("") && chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaIdExcudeRegList(id);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 이름만 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!name.trim().equals("") && chckbxExcudeReg.isSelected() && id.trim().equals("")
				&& hiredate.trim().equals("") && local.trim().equals("")) {
			try {
				gisaList = gisaDAO.getGisaNameExcudeRegList(name);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 입사일으로만 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!hiredate.trim().equals("") && chckbxExcudeReg.isSelected() && id.trim().equals("")
				&& name.trim().equals("") && local.trim().equals("")) {
			try {
				gisaList = gisaDAO.getGisaHireDateExcudeRegList(hiredate);
				readGisaList(gisaList);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 지역만 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!local.trim().equals("") && chckbxExcudeReg.isSelected() && id.trim().equals("")
				&& name.trim().equals("") && hiredate.trim().equals("")) {
			try {
				gisaList = gisaDAO.getGisaLocalExcudeRegList(local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 이름과 지역으로 검색한 경우 **/
		} else if (!name.trim().equals("") && !local.trim().equals("") && id.trim().equals("")
				&& hiredate.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaNameLocalRegList(name, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			/** 이름과 지역으로 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!name.trim().equals("") && chckbxExcudeReg.isSelected() && !local.trim().equals("")
				&& id.trim().equals("") && hiredate.trim().equals("")) {
			try {
				gisaList = gisaDAO.getGisaNameLocalExcudeRegList(name, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 입사일과 지역으로 검색, 퇴사한 사람 제외한 경우 **/
		}

		else if (!hiredate.trim().equals("") && !local.trim().equals("") && id.trim().equals("")
				&& name.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaHireDateLocalRegList(hiredate, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 입사일과 지역으로 검색, 퇴사한 사람 제외한 경우 **/
		}

		else if (!hiredate.trim().equals("") && !local.trim().equals("") && id.trim().equals("")
				&& name.trim().equals("") && chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaHireDateLocalExcudeRegList(hiredate, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 이름, 입사일, 지역으로 검색한 경우 **/
		} else if (!hiredate.trim().equals("") && !local.trim().equals("") && !name.trim().equals("")
				&& id.trim().equals("") && !chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaNameHireDateLocalRegList(name, hiredate, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			/** 이름, 입사일, 지역으로 검색, 퇴사한 사람 제외한 경우 **/
		} else if (!hiredate.trim().equals("") && !local.trim().equals("") && !name.trim().equals("")
				&& id.trim().equals("") && chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaNameHireDateLocalExcudeRegList(name, hiredate, local);
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

		/** 퇴사한 사람 제외 체크박스가 선택된 경우 **/
		else if (chckbxExcudeReg.isSelected()) {
			try {
				gisaList = gisaDAO.getGisaExcudeRegList();
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		/*
		 * 전체조회
		 */
		else {
			try {
				gisaList = gisaDAO.getGisaList();
				readGisaList(gisaList);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
	}

	/*********************************************************************/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("검색")) {// 검색 버튼을 눌렀을 때
			Search();
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		System.out.println(code);

		if(code == KeyEvent.VK_ENTER) //엔터키가 눌리면
			Search();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
