package address;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener, ItemListener{
	JLabel l_driver, l_ip, l_port, l_db, l_id, l_passwd;
	JTextField txt_ip, txt_port, txt_db, txt_id, txt_passwd;
	Choice ch_driver;
	JButton bt_login, bt_cancel;
	JPanel label_panel, select_panel , button_panel;
	String driver, url, id, passwd;
	Connection con;
	
	public Login(){
		l_driver = new JLabel("Driver"); 
		l_ip = new JLabel("IP"); 
		l_port = new JLabel("Port"); 
		l_db = new JLabel("DataBase");
		l_id = new JLabel("ID");
		l_passwd = new JLabel("PassWord");
		
		txt_ip = new JTextField(30);
		txt_port = new JTextField(30);
		txt_db = new JTextField(30);
		txt_id = new JTextField(30);
		txt_passwd = new JTextField(30);

		ch_driver = new Choice();
		ch_driver.add("접속할 데이터베이스를 선택하세요");
		ch_driver.add("MySql");
		
		
		
		bt_login = new JButton("연결");
		bt_cancel=new JButton("취소");
		
		label_panel=new JPanel();
		select_panel= new JPanel();
		button_panel = new JPanel();
	
		label_panel.setPreferredSize(new Dimension(100, 200));
		select_panel.setPreferredSize(new Dimension(300, 200));
		button_panel.setPreferredSize(new Dimension(400, 50));
		
		label_panel.setLayout(new GridLayout(6,1,10,10));
		select_panel.setLayout(new GridLayout(6,1,10,10));

		label_panel.add(l_driver);
		label_panel.add(l_ip);
		label_panel.add(l_port);
		label_panel.add(l_db);
		label_panel.add(l_id);
		label_panel.add(l_passwd);
		
		select_panel.add(ch_driver);
		select_panel.add(txt_ip);
		select_panel.add(txt_port);
		select_panel.add(txt_db);
		select_panel.add(txt_id);
		select_panel.add(txt_passwd);
		
		button_panel.add(bt_login);
		button_panel.add(bt_cancel);
		
		add(label_panel, BorderLayout.WEST);
		add(select_panel, BorderLayout.CENTER);
		add(button_panel, BorderLayout.SOUTH);	
		
		bt_login.addActionListener(this);
		bt_cancel.addActionListener(this);
				
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		//윈도우 종료시
		this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}); // end windowListner

		ch_driver.addItemListener(this);
		
		pack();		
		this.setLocation( ((this.getToolkit().getScreenSize()).width - this.getWidth())/2,  ((this.getToolkit().getScreenSize()).height - this.getHeight())/2);
		setVisible(true);
		
			
	}	

	public HashMap<String,String> getDBMStype(int index,String ip, String port, String dbname,String id,String pass){
		HashMap<String,String> map=new HashMap<String,String>();
		
		//아무것도 선택하지 않음 
		if(index==0){
			map.put("driver", "");

			map.put("ip", ip=(ip==null)? "":ip); 
			map.put("port", port=(port==null)? "":port);
			map.put("dbname", dbname=(dbname==null)? "":dbname);
			map.put("id", id=(id==null)? "":id);
			map.put("pass", pass=(pass==null)? "":pass);
			url="";
		}
		
		
		if(index==1){
			map.put("driver", "com.mysql.cj.jdbc.Driver");
			map.put("ip", ip=(ip==null)? "localhost":ip);
			map.put("port", port=(port==null)? "3307":port);
			map.put("dbname", dbname=(dbname==null)? "address":dbname);
			map.put("id", id=(id==null)? "root":id);
			map.put("pass", pass=(pass==null)? "root":pass);
		
			
			url="jdbc:mysql:"+"//"+ip+":"+port+"/"+dbname;
		}
		
		


		driver=map.get("driver");
		

		txt_ip.setText(ip);
		txt_port.setText(port);
		txt_db.setText(dbname);
		txt_id.setText(id);
		txt_passwd.setText(pass);		
		return map;
	}
	
	public void itemStateChanged(ItemEvent e) {
		// 초이스 컴포넌트에서 DBMS로그인 버튼 누를시에 접속 정보 넘김 
		getDBMStype(ch_driver.getSelectedIndex(),null, null, null,null,null);
	}	
	
	//유효성 검사
	 // 유효하지 않은 조건을 만날 경우 경우 더 이상 실행부가 진행되지 않도록 return 으로 처리
	
	public void check(){		
		//아이피 유효성 검사
		if(txt_ip.getText()==null || txt_ip.getText().length()==0||txt_ip.getText().length()>40){
			JOptionPane.showMessageDialog(getParent(), "아이피를 정확히 입력해주세요(ex)192.168.0.1 또는 localhost");
			txt_ip.setText("");
			txt_ip.requestFocus();
			return;
		}
		if (txt_port.getText()==null || txt_port.getText().length()==0||txt_port.getText().length()>4 || txt_port.getText().matches("[0-9]")){
			JOptionPane.showMessageDialog(getParent(), "포트번호를 정확히 입력해주세요.");
			txt_port.setText("");
			txt_port.requestFocus();
			return;
		}
		if (txt_db.getText()==null || txt_db.getText().length()==0||txt_db.getText().length()>20){
			JOptionPane.showMessageDialog(getParent(), "데이터베이스를 정확히 입력해주세요.");
			txt_port.setText("");
			txt_port.requestFocus();						
			return;
		}
		if (txt_id.getText()==null || txt_id.getText().length()==0||txt_id.getText().length()>20){
			JOptionPane.showMessageDialog(getParent(), "아이디를 정확히 입력해주세요.");
			txt_port.setText("");
			txt_port.requestFocus();						
			return;
		}
		if (txt_passwd.getText()==null || txt_ip.getText().length()==0||txt_ip.getText().length()>20){
			JOptionPane.showMessageDialog(getParent(), "비밀번호를 정확히 입력해주세요.");
			txt_port.setText("");
			txt_port.requestFocus();
			return;
		}
		
		getDBMStype(ch_driver.getSelectedIndex(),txt_ip.getText(), txt_port.getText(), txt_db.getText(),txt_id.getText(),txt_passwd.getText());
		System.out.println("url is "+url);
		
		
		// 
		connect();
	}		
		
	
	// 접속시도 메소드
		
	public String connect() {
		id=txt_id.getText();
		passwd=txt_passwd.getText();
		
		try {
			// 접속 드라이버 로드
			Class.forName(driver);
			System.out.println(driver);
			// 접속 시도
			con = DriverManager.getConnection(url, id, passwd);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return driver;
	}

		
	//버튼 이벤트 구현
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj.equals(bt_login)){
			check();			
			
			if(con!=null){
			
				if(connect().equals("com.mysql.cj.jdbc.Driver")){
					Member m = new Member(this);
				}
				this.setVisible(false);
			}
		}
		
		if(obj.equals(bt_cancel)){
			System.exit(0);
		}
	}	

	public static void main(String[] args) {
		new Login();
	}

}
