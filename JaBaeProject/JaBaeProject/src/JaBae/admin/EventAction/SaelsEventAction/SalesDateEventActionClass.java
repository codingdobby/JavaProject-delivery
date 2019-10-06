/*
 * w.이승연
 * */

package JaBae.admin.EventAction.SaelsEventAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import JaBae.admin.DAO.SalesMgtDAO;
import JaBae.admin.VO.SalesVO;
import JaBae.admin.VO.getDate;

public class SalesDateEventActionClass implements ActionListener {

	private JTextField tfstartDay, tfLastDay;
	private JTable tableSales;
	private DefaultTableModel model;
	boolean detailChk;


	public SalesDateEventActionClass(JTextField tfStartDay, JTextField tfLastDay) {
		this.tfstartDay = tfStartDay;
		this.tfLastDay = tfLastDay;
	}

	public SalesDateEventActionClass(JTable tableSales, DefaultTableModel model, JTextField tfStartDay,
			JTextField tfLastDay, JRadioButton rdbtnCount, JRadioButton rdbtnDay, JRadioButton rdbtnMonth, JRadioButton rdbtnYear) {
		this.tableSales = tableSales;
		this.model = model;
		this.tfstartDay = tfStartDay;
		this.tfLastDay = tfLastDay;
	}

	
	void CheckDateNull(String firstDay, String lastDay) { //공백 검사
		if (firstDay.trim().equals("") || lastDay.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "날짜를 입력해주세요!");
			return;

		} 
	}
	
	void setDate(ActionEvent e) { //날짜 만들어서 넣어주는 메서드
		getDate d = new getDate();
		if (e.getActionCommand().equals("하루")) {
			tfstartDay.setText(d.getYesterday());
			tfLastDay.setText(d.getToday());
		} else if (e.getActionCommand().equals("1개월")) {
			tfstartDay.setText(d.get1MonthAgo());
			tfLastDay.setText(d.getToday());
		} else if (e.getActionCommand().equals("6개월")) {
			tfstartDay.setText(d.get6MonthAgo());
			tfLastDay.setText(d.getToday());
		} else if (e.getActionCommand().equals("1년")) {
			tfstartDay.setText(d.getLastYear());
			tfLastDay.setText(d.getToday());
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		SalesMgtDAO salesDAO = new SalesMgtDAO();
		setDate(e);
	
		if (e.getActionCommand().equals("검색")) {
			String firstDay = tfstartDay.getText().toString().trim();
			String lastDay = tfLastDay.getText().toString().trim();
			
			CheckDateNull(firstDay, lastDay);
			
				ArrayList<SalesVO> arr;
				try {
					arr = salesDAO.getDaySalesList(firstDay, lastDay);
					model.setNumRows(0); // 테이블 초기화

					for (SalesVO one : arr) {
						model.addRow(new Object[] { one.getIdSeller(), one.getNameCustomer(), one.getStartDay(),
								one.getEndDay(), one.getFee(), one.getFreeTicket() });
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		
		}//actionPerformed end

	
}

