/*
 * w.이승연
 * */

package JaBae.admin.Main.Mgt;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import JaBae.admin.EventAction.MemberEventAction.MemberEventClass;
import JaBae.admin.Main.AdminMain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import java.awt.Component;
import java.awt.ScrollPane;

public class MemberManagement extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfId;
	private JTable tableCustomer;
	private JTable tableSeller;
	Vector titleCustomer = null, titleSeller = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberManagement frame = new MemberManagement();
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
	public MemberManagement() {
		setTitle("자배 택배");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panelSwitch = new JPanel();
		panelNorth.add(panelSwitch);
		ButtonGroup bg = new ButtonGroup();
		
		JPanel panelBack = new JPanel();
		panelBack.setBorder(new EmptyBorder(0, 0, 0, 300));
		panelSwitch.add(panelBack);
		
		JButton btnBack = new JButton("←");
		panelBack.add(btnBack);
		
		JPanel panelRdbtn = new JPanel();
		panelRdbtn.setBorder(new EmptyBorder(0, 0, 0, 350));
		panelSwitch.add(panelRdbtn);
		
		JRadioButton rdbtnCustomer = new JRadioButton("회원");
		panelRdbtn.add(rdbtnCustomer);
		
		
		rdbtnCustomer.setSelected(true);
		bg.add(rdbtnCustomer);
		JRadioButton rdbtnSeller = new JRadioButton("판매자");
		panelRdbtn.add(rdbtnSeller);
		bg.add(rdbtnSeller);
		
		/****************************************************************/
		JPanel panelSearch = new JPanel();
		panelNorth.add(panelSearch);
		
		JLabel lblId = new JLabel("아이디");
		panelSearch.add(lblId);
		
		tfId = new JTextField();
		panelSearch.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblName = new JLabel("이름");
		panelSearch.add(lblName);
		
		tfName = new JTextField();
		panelSearch.add(tfName);
		tfName.setColumns(10);
		
		JButton btnSearch = new JButton("검색");
		panelSearch.add(btnSearch);
		
		
		/*********************************************************************************/
		
		JPanel panelShowTable = new JPanel();
		contentPane.add(panelShowTable, BorderLayout.CENTER);
		panelShowTable.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane spTable = new JScrollPane();
		spTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panelShowTable.add(spTable);
		
		/******************회원 테이블******************************************************/
		titleCustomer = new Vector<>();
		titleCustomer.add("아이디"); titleCustomer.add("이름"); 
		titleCustomer.add("생년월일"); titleCustomer.add("가입일");  titleCustomer.add("마지막 배송 완료 날짜");
		titleCustomer.add("포인트");
		DefaultTableModel modelCustomer = new DefaultTableModel(titleCustomer, 0);
		tableCustomer = new JTable(modelCustomer);

		tableCustomer.getTableHeader().setReorderingAllowed(false); 
		
		
		spTable.setViewportView(tableCustomer);
		

		/******************판매자 테이블******************************************************/
		titleSeller = new Vector<>();
		titleSeller.add("아이디"); titleSeller.add("이름"); 
		titleSeller.add("가입일"); titleSeller.add("정기권"); 
		DefaultTableModel modelSeller = new DefaultTableModel(titleSeller, 0);
		tableSeller = new JTable(modelSeller);
		
		tableSeller.getTableHeader().setReorderingAllowed(false); 
		
		
		/*******************ActionListener******************************************************/
		
		
		
		btnSearch.addActionListener(new MemberEventClass(tfId, tfName, rdbtnCustomer, rdbtnSeller, modelCustomer, modelSeller));
		
		
		rdbtnSeller.addActionListener(new ActionListener() { //판매자 창을 선택했을 때
			public void actionPerformed(ActionEvent e) {
				panelShowTable.removeAll();
				panelShowTable.revalidate();
				panelShowTable.repaint();
				panelShowTable.add(spTable);
				spTable.setViewportView(tableSeller);
			}
		});
		
		rdbtnCustomer.addActionListener(new ActionListener() {//사용자 창을 선택했을 때
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCustomer.isSelected()) {
					panelShowTable.removeAll();
					panelShowTable.revalidate();
					panelShowTable.repaint();
					panelShowTable.add(spTable);
					spTable.setViewportView(tableCustomer);
				}

			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMain newwin = new AdminMain();
				newwin.setVisible(true);
				dispose();
			}
		});
		
		setLocationRelativeTo(null);
	}//end

}
