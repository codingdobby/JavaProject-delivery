/*
 * w.이승연
 * */

package JaBae.admin.Main.GisaMgt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import JaBae.admin.DAO.GisaJoinUpdateDAO;
import JaBae.admin.EventAction.GisaEventAction.GisaJoinedEventClass;
import JaBae.admin.VO.GisaVO;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GisaJoined extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfTel;
	private JPasswordField tfPwd;
	private static String id;
	private JPasswordField tfPwdCheck;
	private JButton btnResign;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GisaJoined frame = new GisaJoined(id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GisaJoined(String id) {
		GisaJoinUpdateDAO gisaJUDAO = new GisaJoinUpdateDAO();

		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGisaName = new JLabel("이름");
		lblGisaName.setBounds(54, 276, 57, 15);
		contentPane.add(lblGisaName);
		
		JLabel lblGisaId = new JLabel("아이디");
		lblGisaId.setBounds(54, 100, 57, 15);
		contentPane.add(lblGisaId);
		
		JLabel lblTel = new JLabel("전화번호");
		lblTel.setBounds(54, 332, 62, 18);
		contentPane.add(lblTel);
		
		JLabel lblLocal = new JLabel("지역");
		lblLocal.setBounds(54, 394, 62, 18);
		contentPane.add(lblLocal);
		
		JButton btnBack = new JButton("←");
		btnBack.setBounds(14, 12, 47, 27);
		contentPane.add(btnBack);
		
		tfId = new JTextField();
		tfId.setBounds(163, 95, 116, 24);
		contentPane.add(tfId);
		tfId.setColumns(10);
		
		tfName = new JTextField();
		tfName.setBounds(163, 267, 116, 24);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		tfTel = new JTextField();
		tfTel.setBounds(163, 326, 116, 24);
		contentPane.add(tfTel);
		tfTel.setColumns(10);
		
		JComboBox cbboxLocal = new JComboBox();
		cbboxLocal.setBounds(163, 391, 116, 24);
		contentPane.add(cbboxLocal);
		
		cbboxLocal.addItem("지역을 선택해주세요");
		
		//체크 박스안의 내용 등록
		ArrayList<GisaVO> localList = new ArrayList<GisaVO>();
		try {
			cbboxLocal.removeAllItems();
			localList = gisaJUDAO.getLocalList();
			int localIndex = 0;
			for(GisaVO local : localList) {
				cbboxLocal.addItem(local.getLocal());
				localIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		

		JButton btnOverlapCheck = new JButton("중복체크");
		btnOverlapCheck.setBounds(294, 94, 97, 27);
		contentPane.add(btnOverlapCheck);
		
		JLabel lblPwd = new JLabel("비밀번호");
		lblPwd.setBounds(54, 154, 62, 18);
		contentPane.add(lblPwd);
		
		tfPwdCheck = new JPasswordField();
		tfPwdCheck.setColumns(10);
		tfPwdCheck.setBounds(163, 211, 116, 24);
		contentPane.add(tfPwdCheck);
		
		JLabel lblPwdCheck = new JLabel("비밀번호확인");
		lblPwdCheck.setBounds(54, 214, 95, 18);
		contentPane.add(lblPwdCheck);
		
		tfPwd = new JPasswordField();
		tfPwd.setBounds(163, 151, 116, 24);
		contentPane.add(tfPwd);
		tfPwd.setColumns(10);
		

		JPanel panelBtn = new JPanel();
		panelBtn.setBounds(30, 456, 375, 37);
		contentPane.add(panelBtn);
		
		JButton btnRegister = new JButton("등록");
		panelBtn.add(btnRegister);
		
		
		
		
		/********************************************택배 기사 정보 업데이트*****************/
		if(!id.equals("")) {
			btnRegister.setText("수정");
			tfId.setEnabled(false);
			btnOverlapCheck.setEnabled(false);
			btnResign = new JButton("퇴사");
			panelBtn.add(btnResign);
			//값 불러옴 
			try {
				GisaVO loadGisa = gisaJUDAO.loadGisa(id);
				tfId.setText(id);
				tfName.setText(loadGisa.getGisaName());
				tfTel.setText(loadGisa.getGisaTel());
				cbboxLocal.setSelectedItem(loadGisa.getLocal());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			/********************************************ActionListenerEvent*********/
			btnResign.addActionListener(new GisaJoinedEventClass(tfId));
		}
		
		/*****************************************ActionListnerEvent***************/
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GisaManagement newWindow = new GisaManagement();
				newWindow.setVisible(true);
				dispose();
			}
		});
		
		btnOverlapCheck.addActionListener(new GisaJoinedEventClass(btnOverlapCheck, tfId));
		
		btnRegister.addActionListener(new GisaJoinedEventClass(btnOverlapCheck, tfId, tfPwd, tfPwdCheck,
				tfName, tfTel, cbboxLocal));
		
		//화면을 모니터 가운데 정렬
		setLocationRelativeTo(null);
		
	}
}
