package JaBae.Login.Main.LoginCustomer;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import JaBae.Customer.DAO.MemberDAO;
import JaBae.Customer.VO.MemberVO;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Cus_FindInfo extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfTel1;
	private JTextField tf_Name;
	private JTextField tfTel;
	private JTextField tf_Id;
	private JTextField tf_Tel_Pwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cus_FindInfo frame = new Cus_FindInfo();
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
	public Cus_FindInfo() {
		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelTop = new JPanel();
		FlowLayout fl_panelTop = (FlowLayout) panelTop.getLayout();
		fl_panelTop.setVgap(20);
		contentPane.add(panelTop, BorderLayout.NORTH);

		JLabel lb_top = new JLabel("아이디 찾기");
		lb_top.setFont(new Font("돋움", Font.PLAIN, 30));
		panelTop.add(lb_top);

		JPanel paneBottom = new JPanel();
		contentPane.add(paneBottom, BorderLayout.CENTER);
		paneBottom.setLayout(new BorderLayout(0, 0));

		JPanel panelRdbtn = new JPanel();
		paneBottom.add(panelRdbtn, BorderLayout.NORTH);

		JRadioButton rbId = new JRadioButton("아이디 찾기");

		panelRdbtn.add(rbId);

		JRadioButton rbPwd = new JRadioButton("비밀번호 찾기");

		JPanel card = new JPanel();
		paneBottom.add(card, BorderLayout.CENTER);
		card.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		card.add(panel, "a1");

		JPanel panel_findId = new JPanel();
		panel.add(panel_findId);
		panel_findId.setLayout(new BorderLayout(0, 0));

		JPanel panel_findId_btn = new JPanel();
		panel_findId_btn.setBorder(new EmptyBorder(50, 0, 50, 20));
		panel_findId.add(panel_findId_btn, BorderLayout.EAST);
		panel_findId_btn.setLayout(new BorderLayout(0, 0));

		JButton btnFindID = new JButton("아이디 찾기");
		panel_findId_btn.add(btnFindID);

		JPanel panel_Tf_Id = new JPanel();
		panel_Tf_Id.setBorder(new EmptyBorder(60, 60, 60, 60));
		panel_findId.add(panel_Tf_Id, BorderLayout.CENTER);
		panel_Tf_Id.setLayout(new GridLayout(2, 2, 0, 15));

		JLabel lblNewLabel = new JLabel("이름");
		panel_Tf_Id.add(lblNewLabel);

		tf_Name = new JTextField();
		panel_Tf_Id.add(tf_Name);
		tf_Name.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("전화번호");
		panel_Tf_Id.add(lblNewLabel_1);

		tfTel = new JTextField();
		panel_Tf_Id.add(tfTel);
		tfTel.setColumns(10);

		JPanel panel_1 = new JPanel();
		card.add(panel_1, "aa");

		JPanel panel_findPwd = new JPanel();
		panel_1.add(panel_findPwd);
		panel_findPwd.setLayout(new BorderLayout(0, 0));

		JPanel panel_tf_pwd = new JPanel();
		panel_tf_pwd.setBorder(new EmptyBorder(60, 60, 60, 60));
		panel_findPwd.add(panel_tf_pwd, BorderLayout.CENTER);
		panel_tf_pwd.setLayout(new GridLayout(2, 2, 0, 15));

		JLabel lblNewLabel_2 = new JLabel("아이디");
		panel_tf_pwd.add(lblNewLabel_2);

		tf_Id = new JTextField();
		panel_tf_pwd.add(tf_Id);
		tf_Id.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("전화번호");
		panel_tf_pwd.add(lblNewLabel_3);

		tf_Tel_Pwd = new JTextField();
		panel_tf_pwd.add(tf_Tel_Pwd);
		tf_Tel_Pwd.setColumns(10);

		JPanel panel_findpwd_btn = new JPanel();
		panel_findpwd_btn.setBorder(new EmptyBorder(50, 10, 50, 20));
		panel_findPwd.add(panel_findpwd_btn, BorderLayout.EAST);
		panel_findpwd_btn.setLayout(new BorderLayout(0, 0));

		JButton btnFindPwd = new JButton("비밀번호 찾기");
		panel_findpwd_btn.add(btnFindPwd);

		CardLayout cardLayout = (CardLayout) card.getLayout();
		cardLayout.show(card, "a1");

		ButtonGroup bg = new ButtonGroup();

		panelRdbtn.add(rbPwd);
		bg.add(rbId);
		bg.add(rbPwd);

		rbId.setSelected(true);

		rbId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbId.isSelected() == true) {
					cardLayout.show(card, "a1");
					lb_top.setText("아이디 찾기");

				}

			}
		});

		rbPwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPwd.isSelected() == true) {
					cardLayout.show(card, "aa");
					lb_top.setText("비밀번호 찾기");
				}
			}
		});
		
		/*********************************************************************************/
		btnFindID.addActionListener(new ActionListener() { //아이디 찾기
			public void actionPerformed(ActionEvent e) {

				String name = tf_Name.getText();
				String tel = tfTel.getText();

				MemberDAO mdao = new MemberDAO();
				MemberVO vo = mdao.getID(name, tel);

				if (tf_Name.getText().equals("")) {

					showMessageDialog(null, "이름을 입력해주세요!");
					return;

				} else if (tfTel.getText().equals("")) {
					showMessageDialog(null, "전화번호를 입력해주세요!");
					return;

				}

				else if (vo == null) {
					showMessageDialog(null, "일치하는 값이 없습니다.");
					return;
				} else {

					showMessageDialog(null, vo.getCus_id());
					return;
				}

			}
		});
		
		btnFindPwd.addActionListener(new ActionListener() {		// 비밀번호 찾기
			public void actionPerformed(ActionEvent e) {

				String id = tf_Id.getText().trim();
				String tel_Pwd = tf_Tel_Pwd.getText().trim();

				MemberDAO mdao = new MemberDAO();

				MemberVO vo = mdao.getPwd(id, tel_Pwd);
				if (tf_Id.getText().equals("")) {

					showMessageDialog(null, "아이디를 입력해주세요!");
					return;

				} else if (tf_Tel_Pwd.getText().equals("")) {
					showMessageDialog(null, "전화번호를 입력해주세요!");
					return;

				} else if (vo == null) {
					showMessageDialog(null, "일치하는 값이 없습니다.");
					return;
				}

				else {

					showMessageDialog(null, "비밀번호 : " + vo.getPwd()+"**"+vo.getPwd2());

					return;
				}

			}
		});

		setLocationRelativeTo(null);
	}

}
