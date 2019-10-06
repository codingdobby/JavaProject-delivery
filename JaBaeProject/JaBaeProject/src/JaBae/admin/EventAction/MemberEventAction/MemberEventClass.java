/*
 * w.이승연
 * */

package JaBae.admin.EventAction.MemberEventAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import JaBae.admin.DAO.MemberMgtDAO;
import JaBae.admin.VO.MemberMgtCustomerVO;
import JaBae.admin.VO.MemberMgtSellerVO;
import JaBae.admin.VO.SalesVO;

public class MemberEventClass implements ActionListener {
	
	private JTextField tfId;
	private JTextField tfName;
	private JRadioButton rdbtnCustomer;
	private JRadioButton rdbtnSeller;
	private DefaultTableModel modelCustomer;
	private DefaultTableModel modelSeller;

	public MemberEventClass(JTextField tfId, JTextField tfName, JRadioButton rdbtnCustomer, JRadioButton rdbtnSeller, DefaultTableModel modelCustomer, DefaultTableModel modelSeller) {
		this.tfId = tfId;
		this.tfName = tfName;
		this.rdbtnCustomer = rdbtnCustomer;
		this.rdbtnSeller = rdbtnSeller;
		this.modelCustomer = modelCustomer;
		this.modelSeller = modelSeller;
	}
	
	void isCustomer(String id, String name) throws SQLException { //사용자일 경우
		MemberMgtDAO memberDAO = new MemberMgtDAO();
		ArrayList<MemberMgtCustomerVO> customerOrderList = null;
		ArrayList<MemberMgtCustomerVO> customerNonList = null;
		if(!id.equals("")) { //아이디가 입력되었을 때
			customerOrderList = memberDAO.getCusomerOrderIdList(id);
			customerNonList = memberDAO.getCusomerNonOrderIdList(id);
			readCustomerList(customerOrderList, customerNonList);
		}else if(!name.equals("") && id.equals("")){ //이름이 입력되었을 때
			customerOrderList = memberDAO.getCusomerOrderNameList(name);
			customerNonList = memberDAO.getCusomerNonOrderNameList(name);
			readCustomerList(customerOrderList, customerNonList);
		}else {
			customerOrderList = memberDAO.getCusomerOrderList();
			customerNonList = memberDAO.getCusomerNonOrderList();
			readCustomerList(customerOrderList, customerNonList);
		}
	}
	
	void isSeller(String id, String name) throws SQLException { //판매자일 경우
		MemberMgtDAO memberDAO = new MemberMgtDAO();
		ArrayList<MemberMgtSellerVO> sellerList = null;
		
		if(!id.equals("")) { //아이디가 입력되었을 때
			sellerList = memberDAO.getSellerIdList(id);
			readSellerList(sellerList);
		}else if(!name.equals("") && id.equals("")){ //이름이 입력되었을 때
			sellerList = memberDAO.getSellerNameList(name);
			readSellerList(sellerList);
		}else {
			sellerList = memberDAO.getSellerList();
			readSellerList(sellerList);
		}
	}

	//회원 테이블 설정
	void readCustomerList(ArrayList<MemberMgtCustomerVO> customerOrderList,ArrayList<MemberMgtCustomerVO> customerNonList) {
		modelCustomer.setNumRows(0); //테이블 초기화
		
		for(MemberMgtCustomerVO getCustomer : customerOrderList) {  //리스트 불러옴
			modelCustomer.addRow(new Object[] { getCustomer.getIdCustomer(), getCustomer.getNameCustomer(), getCustomer.getBirthCustomer(), 
					getCustomer.getJoinedDateCustomer(), getCustomer.getLastOrderCustomer(), getCustomer.getPointCustomer() });
		}
		for(MemberMgtCustomerVO getCustomer : customerNonList) {  //리스트 불러옴
			modelCustomer.addRow(new Object[] { getCustomer.getIdCustomer(), getCustomer.getNameCustomer(), getCustomer.getBirthCustomer(), 
					getCustomer.getJoinedDateCustomer(), getCustomer.getLastOrderCustomer(), getCustomer.getPointCustomer() });
		}
	}
	
	//판매자 테이블 설정
	void readSellerList(ArrayList<MemberMgtSellerVO> sellerList) {
		modelSeller.setNumRows(0); //테이블 초기화
		
		for(MemberMgtSellerVO getSeller : sellerList) {  //리스트 불러옴
			modelSeller.addRow(new Object[] { getSeller.getIdSeller(), getSeller.getNameSeller(),  
					getSeller.getJoinedDateSeller(), getSeller.getFreeTicket() });
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = tfId.getText().toString();
		String name = tfName.getText().toString();
		ArrayList<MemberMgtCustomerVO> customerList = null;
		
		if(e.getActionCommand().equals("검색")) {
			
			if(rdbtnCustomer.isSelected()) {//사용자 라디오 버튼이 선택되어 있을 떄
				try {
					isCustomer(id, name);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(rdbtnSeller.isSelected()){//판매자 라디오 버튼이 선택되어 있을 떄
				try {
					isSeller(id, name);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
	}

}
