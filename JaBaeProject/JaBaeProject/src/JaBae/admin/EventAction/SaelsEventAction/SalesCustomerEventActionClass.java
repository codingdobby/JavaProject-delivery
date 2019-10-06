/*
 * w.이승연
 * */
package JaBae.admin.EventAction.SaelsEventAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import JaBae.admin.DAO.SalesMgtDAO;
import JaBae.admin.VO.SalesVO;

public class SalesCustomerEventActionClass implements ActionListener {
	private  DefaultTableModel model;
	private JTextField tfName, tfId;
	
	
	public SalesCustomerEventActionClass(JTextField tfName, JTextField tfId, DefaultTableModel model) {
		this.tfName = tfName;
		this.tfId = tfId;
		this.model = model;
	}
	
	//매출 조회 테이블
	void readSalesList(ArrayList<SalesVO> salesList) {
		model.setNumRows(0); //테이블 초기화
		
		for(SalesVO getSearchName : salesList) {  //리스트 불러옴
			model.addRow(new Object[] { getSearchName.getIdSeller(), getSearchName.getNameCustomer(), 
					getSearchName.getStartDay(), getSearchName.getEndDay(), getSearchName.getFee(), getSearchName.getFreeTicket() });
		}
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		SalesMgtDAO salesDAO = new SalesMgtDAO();
		
		String customerName = tfName.getText().toString();
		String customerId = tfId.getText().toString();
		
		if(e.getActionCommand().equals("검색")) {
			
			/******************************************이름 입력되어 있을 때***/
			if(!customerName.trim().equals("") && customerId.trim().equals("")) {
				ArrayList<SalesVO> salesList;
				try {
					salesList = salesDAO.getCustomerSalesName(customerName);
					readSalesList(salesList);
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				/******************************************아이디 입력되어 있을 때***/
			}else if(!customerId.trim().equals("") && customerName.trim().equals("")) {
				ArrayList<SalesVO> salesList;
				try {
					salesList = salesDAO.getCustomerSalesId(customerId);
					readSalesList(salesList);
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				/******************************************아이디 이름 입력되어 있을 때***/
			}else if(!customerId.trim().equals("") && !customerName.trim().equals("")) {
				ArrayList<SalesVO> salesList;
				try {
					salesList = salesDAO.getCustomerSalesIdName(customerId, customerName);
					readSalesList(salesList);	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				/**************************************************전체조회********/
			}else {
				ArrayList<SalesVO> salesList;
				try {
					salesList = salesDAO.getCustomerSales();
					readSalesList(salesList);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}//else end

			
		}
	}
}
