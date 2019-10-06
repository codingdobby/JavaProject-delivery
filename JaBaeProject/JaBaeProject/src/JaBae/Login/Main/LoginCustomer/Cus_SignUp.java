package JaBae.Login.Main.LoginCustomer;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import JaBae.TextHint;
import JaBae.Login.DAO.LoginDAO;
import JaBae.Login.DAO.SignUpDAO;
import JaBae.Login.VO.SellerVO;
import JaBae.admin.VO.GisaVO;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JSplitPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;

public class Cus_SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfBirth;
	private JTextField tfAddr;
	private JPasswordField tfPwd;
	private JPasswordField tfPwdChk;
	private JTextField tfJoinTel;
	private JTextField tfJoinTel2;
	private JButton btn_check;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cus_SignUp frame = new Cus_SignUp();
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
	public Cus_SignUp() {
		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 598);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel la_panel = new JPanel();
		la_panel.setBackground(SystemColor.control);
		FlowLayout flowLayout = (FlowLayout) la_panel.getLayout();
		flowLayout.setVgap(20);
		contentPane.add(la_panel, BorderLayout.NORTH);

		JLabel la_title = new JLabel("회원가입");
		la_title.setFont(new Font("돋움", Font.PLAIN, 30));
		la_panel.add(la_title);

		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(SystemColor.control);
		FlowLayout flowLayout_1 = (FlowLayout) btnPanel.getLayout();
		flowLayout_1.setVgap(20);
		contentPane.add(btnPanel, BorderLayout.SOUTH);

		// 회원 가입 이벤트
		JButton btnReg = new JButton("등록");
		btnPanel.add(btnReg);

		// 취소 이벤트
		JButton btnReset = new JButton("취소");
		btnPanel.add(btnReset);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 20));

		JPanel Form = new JPanel();
		Form.setBackground(SystemColor.control);
		panel.add(Form, BorderLayout.CENTER);
		Form.setBorder(new EmptyBorder(50, 30, 20, 30));
		Form.setLayout(null);

		JLabel lb_Id = new JLabel("아이디");
		lb_Id.setFont(new Font("돋움", Font.BOLD, 15));
		lb_Id.setBounds(42, 36, 57, 15);
		Form.add(lb_Id);

		JLabel lb_pwd = new JLabel("비밀번호");
		lb_pwd.setFont(new Font("돋움", Font.BOLD, 15));
		lb_pwd.setBounds(42, 89, 72, 15);
		Form.add(lb_pwd);

		JLabel lb_name = new JLabel("이름");
		lb_name.setFont(new Font("돋움", Font.BOLD, 15));
		lb_name.setBounds(42, 170, 48, 15);
		Form.add(lb_name);

		JLabel lb_addr = new JLabel("주소");
		lb_addr.setFont(new Font("돋움", Font.BOLD, 15));
		lb_addr.setBounds(42, 220, 57, 15);
		Form.add(lb_addr);
		
		JLabel lb_tel = new JLabel("전화번호");
		lb_tel.setFont(new Font("돋움", Font.BOLD, 15));
		lb_tel.setBounds(42, 315, 72, 15);
		Form.add(lb_tel);

		JLabel lb_birth = new JLabel("생년월일");
		lb_birth.setFont(new Font("돋움", Font.BOLD, 15));
		lb_birth.setBounds(42, 370, 72, 15);
		Form.add(lb_birth);

		/*********************************************************************************/
		
		tfId = new JTextField();
		TextHint hint = new TextHint(tfId, "영어 소문자와 숫자 포함 4-20자리");
		tfId.setBounds(147, 33, 116, 21);
		Form.add(tfId);
		tfId.setColumns(10);

		JComboBox ComboTel = new JComboBox();
		ComboTel.setModel(new DefaultComboBoxModel(new String[] { "010", "011" }));
		ComboTel.setFont(new Font("굴림", Font.PLAIN, 15));
		ComboTel.setBounds(147, 312, 69, 21);
		Form.add(ComboTel);

		// 중복 체크
		btn_check = new JButton("중복체크");
		btn_check.setBounds(291, 30, 97, 23);
		Form.add(btn_check);

		tfName = new JTextField();
		tfName.setBounds(147, 167, 116, 21);
		Form.add(tfName);
		tfName.setColumns(10);
		
		JComboBox cbboxLocal = new JComboBox();
		cbboxLocal.setBounds(147, 217, 116, 21);
		Form.add(cbboxLocal);
		cbboxLocal.addItem("지역을 선택해주세요");
		
		//체크 박스안의 내용 등록*********************************
		ArrayList<SellerVO> localList = new ArrayList<SellerVO>();
		SignUpDAO getLocal = new SignUpDAO();
		try {
			cbboxLocal.removeAllItems();
			localList = getLocal.getLocalList();
			int localIndex = 0;
			for(SellerVO local : localList) {
				cbboxLocal.addItem(local.getLocal());
				localIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tfAddr = new JTextField();
		tfAddr.setColumns(20);
		tfAddr.setBounds(147, 264, 265, 21);

		Form.add(tfAddr);

		tfBirth = new JTextField();
		tfBirth.setBounds(147, 364, 116, 21);
		Form.add(tfBirth);
		tfBirth.setColumns(10);

		tfPwd = new JPasswordField();

		tfPwd.setBounds(147, 83, 116, 21);
		Form.add(tfPwd);

		tfPwdChk = new JPasswordField();
		TextHint hint2 = new TextHint(tfAddr, "상세 주소를 입력하세요");
		tfPwdChk.setBounds(147, 128, 116, 21);
		Form.add(tfPwdChk);

		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setFont(new Font("돋움", Font.PLAIN, 20));
		lblNewLabel.setBounds(222, 299, 11, 52);
		Form.add(lblNewLabel);

		tfJoinTel = new JTextField();
		tfJoinTel.setBounds(240, 312, 72, 21);
		Form.add(tfJoinTel);
		tfJoinTel.setColumns(10);

		JLabel label = new JLabel("-");
		label.setFont(new Font("돋움", Font.PLAIN, 20));
		label.setBounds(318, 307, 18, 37);
		Form.add(label);

		tfJoinTel2 = new JTextField();
		tfJoinTel2.setColumns(10);
		tfJoinTel2.setBounds(335, 312, 72, 21);
		Form.add(tfJoinTel2);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호 확인");
		lblNewLabel_1.setFont(new Font("돋움", Font.BOLD, 15));
		lblNewLabel_1.setBounds(42, 131, 107, 15);
		Form.add(lblNewLabel_1);
		
		/**********************************************************ActionListener***************/
		btn_check.addActionListener(new ActionListener() { //즁복체크
			public void actionPerformed(ActionEvent e) {

				String id = tfId.getText();
				String regExp = "^[a-z0-9_-]{4,20}$";

				boolean result = false;
				if (id == null || id.equals("")) { // 텍스트필드가 빈칸일때
					showMessageDialog(null, "아이디를 입력하세요!");
				} else {
					if (!tfId.getText().matches(regExp)) {

						showMessageDialog(null, "아이디 형식이 잘못 되었습니다.");
						return;
					}

					LoginDAO dao = new LoginDAO();
					result = dao.getCustomerid(id);

					if (result == true) { // 결과값이 이미 있을 때
						showMessageDialog(null, "중복된 아이디입니다.");

					} else { // 결과값이 없을 때
						showMessageDialog(null, "사용가능한 아이디입니다.");
						btn_check.setEnabled(false);
					}
				}

			}
		});
		
		btnReg.addActionListener(new ActionListener() { //등록 버튼 
			public void actionPerformed(ActionEvent e) {
				SignUpDAO singupDAO = new SignUpDAO();
				nullCheck();

				String id = tfId.getText();
				String pwd = tfPwd.getText();
				String name = tfName.getText();
				String addr = tfAddr.getText();
				String birth = tfBirth.getText();
				String local = cbboxLocal.getSelectedItem().toString();
				String tel = ComboTel.getSelectedItem().toString()+"-" + tfJoinTel.getText().trim()+"-" + tfJoinTel2.getText().trim();

				try {
					singupDAO.signUpCustomer(id, pwd, name, addr, tel, birth, local);
					showMessageDialog(null, "가입 완료");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnReset.addActionListener(new ActionListener() { //취소 버튼 
			public void actionPerformed(ActionEvent e) {

				tfName.setText("");
				tfPwd.setText("");
				tfPwdChk.setText("");
				tfId.setText("");
				tfAddr.setText("");
				tfBirth.setText("");
				tfJoinTel.setText("");
				tfJoinTel2.setText("");

			}
		});
		
		tfId.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				btn_check.setEnabled(true);
			}
		});

		setLocationRelativeTo(null);

	}
	
	
	void nullCheck() {
		// 공백 체크
		if (tfId.getText().equals("")) {
			showMessageDialog(null, "아이디가 공백 입니다.");
			tfId.requestFocus();
			return;
		}

		if (tfPwd.getText().equals("") || tfPwdChk.getText().equals("")) {

			showMessageDialog(null, "비밀번호가 공백입니다..");
			tfPwd.requestFocus();
			return;

		}
		String regExp = "^[a-z0-9_-]{8,20}$";
		if (!tfPwd.getText().matches(regExp)) {
			showMessageDialog(null, "비밀번호 형식이 올바르지 않습니다!");
			tfPwd.requestFocus();
			return;

		}

		if (!tfPwd.getText().equals(tfPwdChk.getText())) {
			// 비밀번호와 비밀번호 확인 값이 일치하지 않을때 메세지뜸
			showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
			tfPwd.requestFocus();
			return;
		}
		if(!tfPwd.getText().contains("_")||!tfPwd.getText().contains("-")) {

			showMessageDialog(null, "특수문자(-,_)가 포함되지 않았습니다");

			

		}

		if (tfName.getText().equals("")) {

			showMessageDialog(null, "이름이 공백입니다.");
			tfName.requestFocus();
			return;

		}
		if (tfJoinTel.getText().equals("") || tfJoinTel2.getText().equals("")) {

			showMessageDialog(null, "전화번호가 공백입니다..");
			tfJoinTel.requestFocus();
			return;
		}
		
//		if (tfBirth.getText().trim() != null) {
//			showMessageDialog(null, "생일을 입력하지 않았습니다 생일을 입력해주세요 ");
//			tfJoinTel.requestFocus();
//			return;
//		}

		if (btn_check.isEnabled() == true) {
			showMessageDialog(null, "아이디 중복 체크를 하세요.");
			return;

		}
	}
}
