package projectDB;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// 학생정보관리 프로그램 개발
// 학생정보 : 번호(int), 성명, 성적(float), 입학년도, 이메일, 학과(combo_box)
// method : 등록, 수정/삭제, 검색, 종료, 취소 (Eventhandler)
// 권한 : insert, update, select, delete, alter, create, drop
// user : boot / 12345
// DB : itdb -> @localhost / TABLE : testst
// DB url : mysql://localhost:3306/itdb?useUnicode=true&characterEncoding=utf-8
// 환경 : GUI (windows) -> JFrame 상속 -> interface 구현(Event)
// storyboard 설계 (*)

public class DbFrame extends JFrame implements ActionListener {
	// 필드 선언
	private Connection connection;				// DB 연결
	private Statement stmt;						// query 문
	private JTextField Name, Gpa, Year, Email;
	private JComboBox<String> Dept;
	private JButton btnregister, btnreset, btnsearch, btnexit, btnlist;
	private String[] depts = {"스마트IT학과", "AI학과", "컴퓨터공학과", "반도체디스과", "자동차과"};
	
	// 생성자 선언
	public DbFrame() {
		setTitle("SIMS");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 5));	// 행(0=자동), 열, 수평간격, 수직간격
		Name = new JTextField(10);
		Gpa = new JTextField(10);
		Year = new JTextField(10);
		Email = new JTextField(10);
		Dept = new JComboBox<String>(depts);
		
		// 버튼
		btnregister = new JButton("등록");
		btnregister.addActionListener(this);
		btnreset = new JButton("취소");
		btnreset.addActionListener(this);
		btnexit = new JButton("종료");
		btnexit.addActionListener(this);
		btnsearch = new JButton("검색");
		btnsearch.addActionListener(this);
		btnlist = new JButton("수정/삭제");
		btnlist.addActionListener(this);
		
		panel.add(new JLabel("NAME", SwingConstants.RIGHT));
		panel.add(Name);
		panel.add(new JLabel("GPA", SwingConstants.RIGHT));
		panel.add(Gpa);
		panel.add(new JLabel("YEAR", SwingConstants.RIGHT));
		panel.add(Year);
		panel.add(new JLabel("EMAIL", SwingConstants.RIGHT));
		panel.add(Email);
		panel.add(new JLabel("DEPT", SwingConstants.RIGHT));
		panel.add(Dept);
		
		panel.add(btnregister);	// 등록
		panel.add(btnreset);	// 취소
		panel.add(btnlist);		// 목록 -> 수정/삭제
		panel.add(btnsearch);	// 검색
		panel.add(btnexit);		// 종료
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));	// 수평간격 / 수직간격
		add(panel);
		pack();
		connect();
	}
	
	private void connect() {
		try {	// JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		// DB connection -> idb/testst/boot/12345 (데이터베이스/테이블/계정/패스워드)
		String url = "jdbc:mysql://localhost:3306/itdb?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true"; // DB 연결, 인코딩 추가, 인증 및 보안 연결 해제 (개발용)
		String username = "boot";
		String password = "12345";
		try {
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
			// 오류가 발생되는 이유, 예외처리가 발생한 위치
		}
	}
	
	// 프로그램(데이터베이스 연결 종료) 종료 메서드
	private void disconnect() {
		try {
			if(stmt != null) stmt.close();
			if(connection != null) connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 입력 취소 메서드
	private void reset() {
		Name.setText("");
		Gpa.setText("");
		Year.setText("");
		Email.setText("");
		Dept.setSelectedIndex(0);
	}
	
	// 등록, 수정, 삭제, 검색, 종료, 취소
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnregister) { // 등록 버튼 이벤트 처리
			String sql = "insert into testst (name, gpa, year, email, dept) values('"
					+ Name.getText().trim() + "', "
					+ Gpa.getText().trim() + ", "
					+ Year.getText().trim() + ", '"
					+ Email.getText().trim() + "', '"
					+ Dept.getSelectedItem().toString().trim() + "')";
			try {
				stmt.executeUpdate(sql);
			} catch(SQLException e) {
				e.printStackTrace();
			}
			reset();
		} else if(event.getSource() == btnreset) { // 취소 버튼 이벤트 처리
			reset();
		} else if(event.getSource() == btnexit) { // 종료 버튼 이벤트 처리
			disconnect();
			System.exit(0);
		} else if(event.getSource() == btnsearch) {
			(new DBsearch()).setVisible(true);
		} else if(event.getSource() == btnlist) {
			(new DBedit()).setVisible(true);
		}
	}

	public static void main(String[] args) {
		(new DbFrame()).setVisible(true);
	}

}
