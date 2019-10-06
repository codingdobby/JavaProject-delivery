package JaBae.Login.Main.LoginCustomer;

import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.omg.CORBA.INITIALIZE;

import JaBae.Customer.DAO.MemberDAO;
import JaBae.Customer.Main.Cus_selectView;
import JaBae.Customer.VO.MemberVO;
import JaBae.Login.DAO.LoginDAO;
import JaBae.Login.Main.LoginMain;
import JaBae.admin.Main.AdminMain;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Cus_Login extends JFrame {
	
	private JPanel contentPane;
	private JTextField tfId;// 입력한 아이디 텍스트필드
	private JPasswordField tfPwd;// 비밀번호값
	private String toss;// 입력한 아이디

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cus_Login frame = new Cus_Login();
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
	public Cus_Login() {
		try {
			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 437, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelBack = new JPanel();
		panelBack.setBorder(new EmptyBorder(0, 0, 100, 20));
		panelTop.add(panelBack);
		
		JButton btnBack = new JButton("←");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginMain newwin = new LoginMain();
				newwin.setVisible(true);
				dispose();
			}
		});
		panelBack.add(btnBack);
		
		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(new EmptyBorder(60, 0, 0, 70));
		panelTop.add(panelLogo);
		
				JLabel lblNewLabel = new JLabel("자바 택배");
				panelLogo.add(lblNewLabel);
				lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 56));

		JPanel panelBtn = new JPanel();
		panelBtn.setBorder(new EmptyBorder(0, 0, 60, 0));
		contentPane.add(panelBtn, BorderLayout.SOUTH);
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

		JButton btnFindInfo = new JButton("아이디/비밀번호 찾기");
		btnFindInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Cus_FindInfo FIV = new Cus_FindInfo();
				FIV.setVisible(true);

			}
		});
		panelBtn.add(btnFindInfo);

		JButton btnSignUp = new JButton("회원가입");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cus_SignUp cus_join = new Cus_SignUp();
				cus_join.setVisible(true);

			}
		});
		panelBtn.add(btnSignUp);

		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new EmptyBorder(50, 90, 20, 90));
		contentPane.add(panelLogin, BorderLayout.CENTER);
		panelLogin.setLayout(new BorderLayout(0, 0));

		JPanel panelLoginInput = new JPanel();
		panelLogin.add(panelLoginInput);
		panelLoginInput.setLayout(new GridLayout(0, 2, -60, 20));

		JLabel lblId = new JLabel("아이디");
		lblId.setFont(new Font("굴림", Font.PLAIN, 17));
		panelLoginInput.add(lblId);

		tfId = new JTextField();
		panelLoginInput.add(tfId);
		tfId.setColumns(20);

		JLabel lblPwd = new JLabel("비밀번호");
		lblPwd.setFont(new Font("굴림", Font.PLAIN, 17));
		panelLoginInput.add(lblPwd);

		tfPwd = new JPasswordField();
		panelLoginInput.add(tfPwd);

		JPanel panelLoginBtn = new JPanel();
		panelLoginBtn.setBorder(new EmptyBorder(20, 0, 0, 0));
		panelLogin.add(panelLoginBtn, BorderLayout.SOUTH);
		panelLoginBtn.setLayout(new GridLayout(0, 1, 0, 0));

		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = tfId.getText().trim();
				String pwd = tfPwd.getText().trim();
				
				LoginDAO sdao = new LoginDAO();
				// 관리자 로그인
				if (id.equals("admin")) {
					boolean checkAdmin = sdao.loginAdmin(id, pwd);
					if (checkAdmin == true) {
						AdminMain adminMain = new AdminMain();
						adminMain.setVisible(true);
						dispose();

					} else {
						showMessageDialog(null, "관리자 로그인 실패!");
						return;
					}
				}

				
				if(!id.equals("admin")) {
				// 사용자 로그인
					boolean check = sdao.loginCustomer(id, pwd);
					if (check == true) {
						toss = tfId.getText();
						Cus_selectView newwin = new Cus_selectView(toss);
						newwin.setVisible(true);
						dispose();

					} else {
						showMessageDialog(null, "사용자 로그인 실패!");
						return;
					}

				}

			}
		});

		panelLoginBtn.add(btnLogin);

		setLocationRelativeTo(null);
		this.addWindowListener(new JFrameWindowClosingEventHandler());

	}

	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame) e.getWindow();
			frame.dispose();
			System.out.println("windowClosing()");
		}
	}

	// 아이디 값 넘길때 사용하는 메서드
	public String check() {
		System.out.println(toss);
		return toss;
	}
}
