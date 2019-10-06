package JaBae.Customer.Main;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import JaBae.Customer.DAO.PointDAO;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cus_Point extends JFrame {

	private JPanel contentPane;
	private JTextField tfSong;
	private JTextField tfTel;
	static String toss;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cus_Point frame = new Cus_Point(toss);
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
	public Cus_Point(String toss) {
		this.toss = toss;
		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_title = new JPanel();
		contentPane.add(panel_title, BorderLayout.NORTH);
		
		JLabel lbl_title = new JLabel("포인트 적립");
		lbl_title.setFont(new Font("돋움체", Font.PLAIN, 30));
		panel_title.add(lbl_title);
		
		JPanel panel_center = new JPanel();
		contentPane.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_grid = new JPanel();
		panel_grid.setBorder(new EmptyBorder(50, 50, 50, 50));
		panel_center.add(panel_grid);
		panel_grid.setLayout(new GridLayout(0, 2, 0, 10));
		
		JLabel lblNewLabel = new JLabel("송장 번호");
		panel_grid.add(lblNewLabel);
		
		tfSong = new JTextField();
		panel_grid.add(tfSong);
		tfSong.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("전화번호");
		panel_grid.add(lblNewLabel_1);
		
		tfTel = new JTextField();
		panel_grid.add(tfTel);
		tfTel.setColumns(10);
		
		JPanel panel_bottom = new JPanel();
		contentPane.add(panel_bottom, BorderLayout.SOUTH);
		
		JPanel panel_btn = new JPanel();
		panel_bottom.add(panel_btn);
		
		//등록 이벤트
		
		JButton btnReg = new JButton("등록");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PointDAO pdao = new PointDAO();
				String tel = tfTel.getText().trim();
				int order_no = Integer.parseInt(tfSong.getText().trim());
				System.out.println(order_no);
				System.out.println(tel);
				
				boolean check = pdao.SelectPointUse(tel, order_no);
				if(check == true) {
					pdao.UpdatePoint(toss);
					pdao.UpdatePointUse(tel, order_no);
					showMessageDialog(null, "등록 완료!");
					dispose();
					
				}
				else  if(check == false) {
					showMessageDialog(null, "이미 등록 한 송장입니다.");
				}
				
				
				
				
				
			}
		});
		panel_btn.add(btnReg);
		
		JButton btnReset = new JButton("취소");
		panel_btn.add(btnReset);
		
		
		
		
		
		
		//가운데 정렬
		setLocationRelativeTo(null);
	}

}
