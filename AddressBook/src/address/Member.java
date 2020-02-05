package address;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import java.awt.SystemColor;

public class Member extends JFrame implements ActionListener {	
 
	JLabel l_name, l_age, l_addr, l_phone, l_phone1, l_phone2, l_email, l_email1, l_gender;
	JTextField txt_name, txt_age, txt_addr, txt_phone1, txt_phone2, txt_phone3, txt_email_id, txt_email_addr, txt_keyword;
	CheckboxGroup gender;
	Checkbox female, male;
	Choice ch_email, ch_category;
	JTable table;
	JScrollPane scroll;
	JButton bt_regist, bt_edit, bt_delete, bt_select, bt_getListAll, bt_exit;
	JPanel south, north, center, p2, p3, p4, p5, p6, p7;

		
	Login log;
	String driver, url, userid, passwd;

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;


	String g ;
	String sql;	
	String category;


	MemberModel model = new MemberModel();
	int selRow; 
	int idx; 
	private JPanel panel;

	
// ���� IO
	 public void exportDataToExcel() {

	        FileOutputStream excelFos = null;
	        XSSFWorkbook excelJTableExport = null;
	        BufferedOutputStream excelBos = null;
	        try {
	 
	            JFileChooser excelFileChooser = new JFileChooser("D:/");
	            excelFileChooser.setDialogTitle("Save As ..");
	            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");

	            excelFileChooser.setFileFilter(fnef);
	            int chooser = excelFileChooser.showSaveDialog(null);

	            if (chooser == JFileChooser.APPROVE_OPTION) {
	                
	                excelJTableExport = new XSSFWorkbook();
	                XSSFSheet excelSheet = excelJTableExport.createSheet("�ּҷ�");
	                
	                for (int i = 0; i < model.getRowCount(); i++) {
	                    XSSFRow excelRow = excelSheet.createRow(i);
	                    for (int j = 0; j < model.getColumnCount(); j++) {
	                        XSSFCell excelCell = excelRow.createCell(j);
	 
	                    
	                       
	 
	                        excelCell.setCellValue(model.getValueAt(i, j).toString());
	                        if (excelCell.getColumnIndex() == model.getColumnCount() - 1) {
	                            
	                        }
	                    }
	                }
	                excelFos = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
	                excelBos = new BufferedOutputStream(excelFos);
	                excelJTableExport.write(excelBos);
	                JOptionPane.showMessageDialog(null, "���� �Ϸ�");
	            }
	 
	        } catch (FileNotFoundException ex) {
	            JOptionPane.showMessageDialog(null, ex);
	        } catch (IOException ex) {
	            JOptionPane.showMessageDialog(null, ex);
	        } finally {
	            try {
//	                if (excelFos != null) {
//	                    excelFos.close();
//	                }
	                if (excelBos != null) {
	                    excelBos.close();
	                }
	                if (excelJTableExport != null) {
	                    excelJTableExport.close();
	                }
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(null, ex);
	                
	            }
	        }
	} 
	
	
	
	public Member(Login log) {
		super("�ڹ� �ּҷ� ���� ���α׷�");		
		this.log=log;		
		this.con=log.con;
		Font f1 = new Font("serif", Font.BOLD, 20);

		l_name = new JLabel("��    ��");
		l_name.setFont(new Font("���� ���", Font.BOLD, 12));
		l_name.setBounds(73, 9, 40, 15);
		l_age = new JLabel(" ��   ��");
		l_age.setFont(new Font("���� ���", Font.BOLD, 12));
		l_age.setBounds(237, 8, 40, 15);
		l_addr = new JLabel("��    ��");
		l_addr.setFont(new Font("���� ���", Font.BOLD, 12));
		l_addr.setBounds(73, 8, 40, 15);
		l_phone = new JLabel("�� �� ó");
		l_phone.setFont(new Font("���� ���", Font.BOLD, 12));
		l_phone.setBounds(73, 5, 49, 18);
		l_phone1 = new JLabel("-");
		l_phone1.setBounds(247, 9, 6, 15);
		l_phone2 = new JLabel("-");
		l_phone2.setBounds(374, 8, 6, 15);
		l_gender = new JLabel(" ��   ��");
		l_gender.setFont(new Font("���� ���", Font.BOLD, 12));
		l_gender.setBounds(326, 8, 40, 15);

		txt_name = new JTextField(8);
		txt_name.setBounds(136, 6, 94, 21);
		txt_age = new JTextField(3);
		txt_age.setBounds(282, 5, 39, 21);
		txt_addr = new JTextField(30);
		txt_addr.setBounds(136, 5, 336, 21);
		txt_phone1 = new JTextField(9);
		txt_phone1.setBounds(136, 5, 105, 21);
		txt_phone2 = new JTextField(9);
		txt_phone2.setBounds(261, 5, 105, 21);
		txt_phone3 = new JTextField(9);
		txt_phone3.setBounds(387, 5, 105, 21);

		gender = new CheckboxGroup();
		male = new Checkbox("Male", gender, false);
		male.setBounds(371, 4, 45, 23);
		female = new Checkbox("Female", gender, false);
		female.setBounds(421, 4, 61, 23);

		bt_regist = new JButton("���");
		bt_edit = new JButton("����");
		bt_delete = new JButton("����");
		bt_exit = new JButton("�ؽ�Ʈ���");

		table = new JTable(model);		
		scroll = new JScrollPane(table);

		south = new JPanel();
		north = new JPanel();
		center = new JPanel();
		p2 = new JPanel();
		p2.setBackground(SystemColor.inactiveCaption);
		p3 = new JPanel();
		p3.setBackground(SystemColor.inactiveCaption);
		p4 = new JPanel();
		p4.setBackground(SystemColor.inactiveCaption);
		

		north.setPreferredSize(new Dimension(600, 200));
		center.setPreferredSize(new Dimension(600, 400));
		south.setPreferredSize(new Dimension(600, 50));

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);		
		
		north.setLayout(new GridLayout(6, 1));
		txt_keyword = new JTextField(10);
		txt_keyword.setBounds(293, 7, 124, 21);
		
		ch_category=new Choice();
		ch_category.setBounds(74, 6, 190, 21);
		ch_category.add("�˻��� ī�װ��� �����ϼ���");
		ch_category.add("Name");
		ch_category.add("Age");
		ch_category.add("gender");
		ch_category.add("address");
		ch_category.add("Phone");
		ch_category.add("Email");
		bt_select = new JButton("��ȸ");
		bt_select.setBounds(434, 4, 70, 27);
		bt_getListAll = new JButton("��ü��ȸ");
		bt_getListAll.setBounds(507, 4, 88, 27);
		p6= new JPanel();
		north.add(p6);
				p6.setBackground(SystemColor.activeCaption);
				p6.setLayout(null);
				p6.add(ch_category);
				p6.add(txt_keyword);
				p6.add(bt_select);
				p6.add(bt_getListAll);
				
				// �˻� ī�װ� ���̽� �ڽ� ������
						ch_category.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent ie) {
								Object obj = ie.getSource();
								category=(String)ie.getItem();
							}
						});
						bt_select.addActionListener(this);
						bt_getListAll.addActionListener(this);
		north.add(p2);
		north.add(p3);
		north.add(p4);
		l_email = new JLabel("�� �� ��");
		l_email.setFont(new Font("���� ���", Font.BOLD, 12));
		l_email.setBounds(74, 6, 51, 18);
		l_email1 = new JLabel("@");
		l_email1.setBounds(248, 9, 12, 15);
		txt_email_id = new JTextField(9);
		txt_email_id.setBounds(136, 5, 105, 21);
		txt_email_addr = new JTextField(10);
		txt_email_addr.setBounds(266, 5, 116, 21);
		
		ch_email = new Choice();
		ch_email.setBounds(401, 5, 85, 21);
		ch_email.add("�����Է�");
		ch_email.add("naver.com");
		ch_email.add("google.com");
		ch_email.add("nate.com");
		ch_email.add("paran.com");
		ch_email.add("hanmail.net");
		p5 = new JPanel();
		north.add(p5);
		p5.setBackground(SystemColor.inactiveCaption);
		p5.setLayout(null);
		p5.add(l_email);
		p5.add(txt_email_id);
		p5.add(l_email1);
		p5.add(txt_email_addr);
		p5.add(ch_email);
		
				// �����ּ� ���̽� �ڽ� ������
				ch_email.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent ie) {
						Object obj = ie.getSource();
						if (obj.equals(ch_email)) {
							if (ch_email.getSelectedIndex() == 0) {
								txt_email_addr.setText("");
							} else {
								
								txt_email_addr.setText((String) ie.getItem());
							}
						}
					}
				});
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		north.add(panel);
		panel.setLayout(null);
		p2.setLayout(null);
		p2.add(l_name);
		p2.add(txt_name);
		p2.add(l_age);
		p2.add(txt_age);
		p2.add(l_gender);
		p2.add(male);
		p2.add(female);
		p3.setLayout(null);
		p3.add(l_addr);
		p3.add(txt_addr);
		p4.setLayout(null);
		p4.add(l_phone);
		p4.add(txt_phone1);
		p4.add(l_phone1);
		p4.add(txt_phone2);
		p4.add(l_phone2);
		p4.add(txt_phone3);

		// ���� checkbox ������ 
		male.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				g = (String) e.getItem();

			}
		});

		female.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				g = (String) e.getItem();

			}
		}); 

		center.setLayout(new GridLayout(1, 1));
		center.add(scroll);
		table.setAutoCreateRowSorter(true); 
		

		south.setLayout(new GridLayout(1, 4));
		south.add(bt_regist);
		south.add(bt_edit);
		south.add(bt_delete);
		south.add(bt_exit);
		

		// ��ư�׼� ������
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_delete.addActionListener(this);
		bt_exit.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selRow = table.getSelectedRow();
				System.out.println(selRow);


				String name = (String) table.getValueAt(selRow, 0);
				String age = table.getValueAt(selRow, 1).toString();
				String gender = (String) table.getValueAt(selRow, 2);
				String address = (String) table.getValueAt(selRow, 3);
				String phone = (String) table.getValueAt(selRow, 4);
				String email = (String) table.getValueAt(selRow, 5);


				idx = (Integer) model.getValueAt(selRow, 6);
				System.out.println(idx);

				txt_name.setText(name);
				txt_age.setText(age);
				txt_addr.setText(address);
				System.out.println(gender);
				if(gender==null){
					male.setState(false);
					female.setState(false);
				}else {
					 if (gender.equals("Male")){
						 male.setState(true);
					 }else if (gender.equals("Female")) {
						 female.setState(true);
					 }
				}				
				txt_phone1.setText(phone.substring(0, phone.indexOf("-")));
				txt_phone2.setText(phone.substring(phone.indexOf("-") + 1, phone.lastIndexOf("-")));
				txt_phone3.setText(phone.substring(phone.lastIndexOf("-") + 1));
				txt_email_id.setText(email.substring(0, email.indexOf("@")));
				txt_email_addr.setText(email.substring(email.lastIndexOf("@")+1));
			}
		});

		// ������ â ������ DB�ڿ��� �ݱ�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDB();
				System.exit(0);
			}
		}); // end windowListner

		
		
		if(log.con!=null) {
			JOptionPane.showMessageDialog(getParent(), "���Ӽ���");
			if(getListAll()){// ����Ʈ ��������
		
			}else{
				JOptionPane.showMessageDialog(getParent(), "����Ʈ �������� ����!");				
			}
		}else{
			JOptionPane.showMessageDialog(getParent(), "���ӽ���");
		}
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pack();
		
		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    
		  int xPos = screenSize.width/2 - getSize().width/2 ;
		  int yPos = screenSize.height/2 - getSize().height/2 ;  
		  setLocation(xPos,yPos);
	
		setVisible(true);
	}
	// ����Ʈ ��������
	public boolean getListAll() {
		boolean result = false;
		String sql = "select * from address";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Vector list = new Vector();
			while (rs.next()) {

				Vector record = new Vector();
				record.add(rs.getString("Name"));
				record.add(rs.getInt("Age"));
				record.add(rs.getString("gender"));
				record.add(rs.getString("Address"));
				record.add(rs.getString("Phone"));
				record.add(rs.getString("Email"));
				record.add(rs.getInt("idx"));

				list.add(record);
			}

			model.setList(list);
			this.repaint();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}	
	
	// ���ڵ� ���
	public int regist() {		
		int result = 0;
		sql = "Insert into address(Name,Age,gender,address,Phone,Email,idx)";
		sql = sql + "Value(?,?,?,?,?,?,?)";		
			try {			
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, txt_name.getText());
				pstmt.setInt(2, Integer.parseInt(txt_age.getText()));
				pstmt.setString(3, g);
				pstmt.setString(4, txt_addr.getText());
				pstmt.setString(5, txt_phone1.getText()+"-"+ txt_phone2.getText()+"-"+txt_phone3.getText());
				pstmt.setString(6,txt_email_id.getText()+"@"+txt_email_addr.getText());
				pstmt.setInt(7, idx++);
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		

		return result;
	}

	// ���� �޼��� ����
	public int delete() {
		int result = 0;
		String sql = "Delete from address where idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public int edit(){
		int result =0;
		String sql = "Update address set Name=?, Age=?, gender=?, address=?, Phone=?, Email=? where idx=?";
		try {
			pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, txt_name.getText());
			pstmt.setInt(2, Integer.parseInt(txt_age.getText()));
			pstmt.setString(3, g);
			pstmt.setString(4, txt_addr.getText());
			pstmt.setString(5,	txt_phone1.getText() + "-" + txt_phone2.getText() +"-"+ txt_phone3.getText());
			pstmt.setString(6,	txt_email_id.getText() + "@" + txt_email_addr.getText());
			pstmt.setInt(7, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//�˻� �޼ҵ�	
	public boolean select(String category, String keyword){
		boolean result = false;
		String sql ="select * from address where "+category+" like '%"+keyword+"%' order by idx desc";		
		System.out.println(sql);
		try {
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			Vector list = new Vector();
			while (rs.next()) {

				Vector record = new Vector();
				record.add(rs.getString("Name"));
				record.add(rs.getInt("Age"));
				record.add(rs.getString("gender"));
				record.add(rs.getString("Address"));
				record.add(rs.getString("Phone"));
				record.add(rs.getString("Email"));
				record.add(rs.getInt("idx")); 
				list.add(record);
			}

			model.setList(list);
			this.repaint();
			result = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	//�ؽ�Ʈ ���� �ʱ�ȭ �ϴ� �޼���
	public void clear(){		
		txt_name.setText("");
		txt_age.setText(""); 
		male.setState(false);
		female.setState(false);
		txt_addr.setText(""); 
		txt_phone1.setText(""); txt_phone2.setText(""); txt_phone3.setText("");
		txt_email_id.setText(""); txt_email_addr.setText("");
		txt_name.requestFocus();
	}
	
	//��ȿ�� �˻�
	public boolean check(){
		boolean result =false;
		//�̸� ��ȿ�� �˻�
		if(txt_name.getText()==null || txt_name.getText().length()==0){
			JOptionPane.showMessageDialog(getParent(), "�̸��� �ʼ��׸��Դϴ�.");
			txt_name.setText("");
			txt_name.requestFocus();


		}else if (txt_name.getText().length()>20){
			JOptionPane.showMessageDialog(getParent(), "�̸��� 20�� �̳��� �Է����ּ���");
			txt_name.setText("");
			txt_name.requestFocus();						

			// ���� ��ȿ��
		} else if(txt_age.getText().length()>4 || txt_age.getText().matches("[0-9]")) {
			JOptionPane.showMessageDialog(getParent(), "���̸� ��Ȯ�� �Է����ּ���");
			txt_age.setText("");
			txt_age.requestFocus();

		}
		
		//��ȭ��ȣ�� ���ڸ� �Է°����ϵ���
		else if (txt_phone1.getText().length()>4 || txt_phone2.getText().length()>4 || txt_phone3.getText().length()>4 || txt_phone1.getText().matches("[0-9]")||txt_phone2.getText().matches("[0-9]")||txt_phone3.getText().matches("[0-9]")) {
			JOptionPane.showMessageDialog(getParent(), "��ȭ��ȣ�� ��Ȯ�� �Է����ּ���.");
			txt_phone1.setText("");
			txt_phone2.setText("");
			txt_phone3.setText("");
			txt_phone1.requestFocus();

		}
		//�̸��� ��ȿ�� �˻�
		else if(txt_email_id.getText().length()>40 || txt_email_addr.getText().length()>40){
			JOptionPane.showMessageDialog(getParent(), "�̸����� ��Ȯ�� �Է����ּ���.");
			txt_email_id.setText("");
			txt_email_addr.setText("");
			txt_email_id.requestFocus();

		}else{
			result = true;
		}
		return result;
	}
	

	public void closeDB() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}	

	// ��ư �׼� �̺�Ʈ ����
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// ��Ϲ�ư
		if (obj.equals(bt_regist)) {
			if(check()){
 
				int result = regist();
				if (result != 0) {
					JOptionPane.showMessageDialog(getParent(), "��ϼ���");
					getListAll();
					clear();
					table.updateUI();
				} else {
					JOptionPane.showMessageDialog(getParent(), "��Ͻ���");
				}
			}
		}
		
		// ������ư
		if (obj.equals(bt_edit)) {
			if(check()){
				if(JOptionPane.showConfirmDialog(getParent(),	(String)table.getValueAt(selRow, 0)+ "���� ������ ���� �Ͻðڽ��ϱ�?")==0){
					int result = edit();
					if(result !=0){
						getListAll();
						clear();
						table.updateUI();
					}else{
						JOptionPane.showMessageDialog(getParent(), "���� ����");
					}
				}
			}
		}
		// ������ư
		if (obj.equals(bt_delete)) {
			if(JOptionPane.showConfirmDialog(getParent(),	(String) table.getValueAt(selRow, 0)+ "���� ������ ���� �Ͻðڽ��ϱ�?")==0){
				int result = delete();
				if (result != 0) {
					getListAll();
					clear();
					table.updateUI();			
				}
			}
		}

		// ��ȸ��ư
		if (obj.equals(bt_select)) {
			if(select(category, txt_keyword.getText())){
				
			}else{
				JOptionPane.showMessageDialog(getParent(), "�˻��� �����Ͽ����ϴ�.");
			};
		}
		
		// ��ü ��ȸ��ư
		if (obj.equals(bt_getListAll)) {
			getListAll();
		}

		// �����ư
		if (obj.equals(bt_exit)) {
			
			exportDataToExcel();
		}
	   
		                                            

			

		}// end exit��ư action����
	}// end action�̺�Ʈ ����



