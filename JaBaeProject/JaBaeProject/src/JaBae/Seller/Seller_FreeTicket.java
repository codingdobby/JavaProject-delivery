package JaBae.Seller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import JaBae.Login.Main.LoginMain;

import javax.swing.JTextField;

public class Seller_FreeTicket extends JFrame {

	Seller_FreeTicketDAO ftdao = new Seller_FreeTicketDAO();
	private JPanel contentPane;
	
	private JTextField txt_Ft_Id_1;
	private JTable ta_Ft_read;
	private int ft;
	

	/**
	 * Launch the application.
	 */
	
// -----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seller_FreeTicket frame = new Seller_FreeTicket();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
// -----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------
	/**
	 * Create the frame.
	 */
	public Seller_FreeTicket() {
		setTitle("자배 택배");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 200, 900, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String colNames[] = {"판매자 ID","판매자 성명","남은 정액권 갯수"}; // 각 칸의 넣을 이름.
		DefaultTableModel model = new DefaultTableModel(colNames, 0); // 행은 배열에 들어간 값만큼. 열은 0개.
		JTable ta_Ft_read = new JTable(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"\uD310\uB9E4\uC790 ID", "\uD310\uB9E4\uC790 \uC131\uBA85", "\uB0A8\uC740 \uC815\uC561\uAD8C \uAC2F\uC218"
			}
		));
		ta_Ft_read.setFont(new Font("굴림", Font.PLAIN, 12));
		
		int widths[] = {70, 70, 70}; // 각 칸의 크기 설정. 현재 매우 빈 공간이 많음
		for(int i=0; i<3; i++) { // 설정한 행이 3개.
			TableColumn column = ta_Ft_read.getColumnModel().getColumn(i);
			column.setPreferredWidth(widths[i]);
		}
		ta_Ft_read.setRowHeight(20);
		
		JScrollPane scrollPane = new JScrollPane(); // JT들어가 있는 스크롤 패널
		scrollPane.setBounds(100, 290, 608, 43);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(ta_Ft_read);
		
		JLabel lb_Ft_Name = new JLabel("정액권 관리"); // 이름 제목
		lb_Ft_Name.setFont(new Font("굴림", Font.PLAIN, 26));
		lb_Ft_Name.setBounds(15, 10, 154, 50);
		getContentPane().add(lb_Ft_Name);
		
		
		JButton btn_Ft_Sell = new JButton("정액권 구매"); // 정액권 구매 버튼
		btn_Ft_Sell.setBounds(100, 500, 110, 50);
		getContentPane().add(btn_Ft_Sell);
		
		JButton btn_Ft_seoo = new JButton("담기 초기화"); // 담아놓은 정액권 초기화 버튼
		btn_Ft_seoo.setBounds(222, 500, 110, 50);
		contentPane.add(btn_Ft_seoo);
		
		JButton btn_Con_go = new JButton("송장 등록"); // 송장등록Seller_Conveyor로 이동하는 버튼
		btn_Con_go.setBounds(571, 500, 110, 50);
		getContentPane().add(btn_Con_go);
		
		JButton btn_Main_Go = new JButton("돌아가기"); // Seller_Main으로 돌아가는 버튼
		btn_Main_Go.setBounds(693, 500, 110, 50);
		getContentPane().add(btn_Main_Go);
		
		JButton btn_Logout = new JButton("로그아웃"); // Login_Main으로 돌아가는 버튼
		btn_Logout.setBounds(768, 10, 114, 42);
		getContentPane().add(btn_Logout);
		
		JButton btn_Ft_10 = new JButton("정액권 10개 담기"); // 정액권 10개 담는 버튼
		btn_Ft_10.setBounds(100, 360, 150, 60);
		getContentPane().add(btn_Ft_10);
		
		JButton btn_Ft_20 = new JButton("정액권 20개 담기"); // 정액권 20개 담은 버튼
		btn_Ft_20.setBounds(365, 360, 150, 60);
		getContentPane().add(btn_Ft_20);
		
		JButton btn_Ft_30 = new JButton("정액권 30개 담기"); // 정액권 30개 담은 버튼
		btn_Ft_30.setBounds(653, 360, 150, 60);
		getContentPane().add(btn_Ft_30);
		
		JButton btn_Ft_num = new JButton("남은 정액권 확인"); // where id = ? id를 조건절로 남은 정액권 확인
		btn_Ft_num.setBounds(100, 230, 154, 50);
		getContentPane().add(btn_Ft_num);
		
		JLabel la_Ft_num = new JLabel("잔여 정액권 확인을 위해서 빈 공간에 ID 입력 후 정액권 확인을 눌러주세요."); // 그냥 설명 라벨
		la_Ft_num.setFont(new Font("굴림", Font.PLAIN, 18));
		la_Ft_num.setBounds(100, 96, 640, 30);
		getContentPane().add(la_Ft_num);
		
		JLabel lb_Ft_sell = new JLabel("정액권 구매는 입력칸에 ID 입력 후 구매 버튼을 눌러주세요."); // 설명 라벨 2
		lb_Ft_sell.setFont(new Font("굴림", Font.PLAIN, 18));
		lb_Ft_sell.setBounds(100, 136, 614, 30);
		contentPane.add(lb_Ft_sell);
		
		JLabel lb_ftcount1 = new JLabel("현재 담긴 정액권 갯수 : "); // 남은 정액권 갯수 라벨
		lb_ftcount1.setBounds(100, 445, 136, 30);
		contentPane.add(lb_ftcount1);
		
		JLabel lb_ftcount2 = new JLabel("0"); // 담긴 정액권 갯수만큼 숫자가 변동하는 라벨
		lb_ftcount2.setBounds(248, 445, 80, 30);
		contentPane.add(lb_ftcount2);
		
		JTextField txt_Ft_Id_1 = new JTextField(); // id 입력하는 텍필
		txt_Ft_Id_1.setBounds(263, 245, 116, 21);
		contentPane.add(txt_Ft_Id_1);
		txt_Ft_Id_1.setColumns(20);

		
//		---------------------------------------------------------------------------------------
//		--------------------------------- 버튼 기능동작 --------------------------------------------
//		---------------------------------------------------------------------------------------	
		

		
//		로그아웃화면 이동버튼. 정액권 → 로그인	 (정상작동)
		btn_Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginMain newWindow = new LoginMain();
				
				int ans = JOptionPane.showConfirmDialog(null, "정말 로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(ans == JOptionPane.CLOSED_OPTION) {
					
				} else if(ans == JOptionPane.YES_OPTION) {
					newWindow.setFocusable(true);
					newWindow.setVisible(true);
					dispose();
				} else {
					
				}
			}
		});
	
//		판매자 메인화면 이동버튼. 정액권 → 판매자 메인 (정상작동)
		btn_Main_Go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Seller_Main();
			}
		});
		
//		송장등록 화면 이동버튼. 정액권 → 송장등록 (정상작동)
		btn_Con_go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Seller_Conveyor();
			}
		});
		
//		감아놓은 정액권 초기화 (정상작동)
		btn_Ft_seoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft = 0;
				String fa = Integer.toString(ft);
				lb_ftcount2.setText(fa);
			}
		});
		
		
//		정액권 100개 담기 버튼 (정상작동)
		btn_Ft_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft += 10;
				String fa = Integer.toString(ft);
				lb_ftcount2.setText(fa);
			}
		});
		
//		정액권 200개 담기 버튼 (정상작동)
		btn_Ft_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft += 20;
				String fa = Integer.toString(ft);
				lb_ftcount2.setText(fa);
			}
		});
		
//		정액권 300개 담기 버튼 (정상작동)
		btn_Ft_30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ft += 30;
				String fa = Integer.toString(ft);
				lb_ftcount2.setText(fa);
			}
		});
		
//		정액권 구매 버튼. 정액권 갯수 버튼을 쌓아놓고 구매하여 DB에 넣는 버튼.
		btn_Ft_Sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ftdao.insert(ft, txt_Ft_Id_1);
				} catch (SQLException e1) {
					System.out.println("정액권 구매 단계 에러 발생");
					e1.printStackTrace();
				}
				
//				구매 후 담아놓은 정액권 갯수 초기화
				ft = 0;
				String fa = Integer.toString(ft);
				lb_ftcount2.setText(fa);
				
//	텍스트 필드의 id 값을 db로 가져가 같은 대상을 찾아 그 대상의 잔여 정액권에 저장된 ft값 만큼 더해서 넣는다.
//	정상 작동중.
			}
		});

//		남은 정액권 확인 버튼.
		btn_Ft_num.addActionListener(new Seller_FreeTicketDAO(ta_Ft_read,txt_Ft_Id_1));
		
// 텍스트 필드의 id를 db로 가져가 같은 대상를 찾아 아이디,이름,잔여 정액권을 시각적으로 송출하는 버튼.
// 현재 정상 작동중.
		
		//화면을 모니터 가운데 정렬
				setLocationRelativeTo(null);
	}
}
