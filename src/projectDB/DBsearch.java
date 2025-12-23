package projectDB;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DBsearch extends JFrame implements ActionListener {
	// 필드 선언
	private Connection connection = null;						// DB 연결 저장 객체 변수 선언
	private Statement stmt = null;								// SQL 문 저장 객체 변수
	private PreparedStatement pstmt = null;						// select 문 처리 query 문 저장 객체 변수
	private ResultSet rs = null;								// query 문에 대한 결과 저장 객체 변수
	
	private JTextField input, Name, Gpa, Year, Email, Dept;
	private JButton btnsearch, btnprev, btnnext, btnback;		// 검색어로 검색된 결과의 목록 이전, 다음, 뒤로
	JLabel Sid = new JLabel("");
	JLabel Nosearch = new JLabel("");							// 검색 결과 값 라벨
	
	// 생성자 선언
	public DBsearch() {
		setTitle("검색 데이터");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		input = new JTextField(15);								// 검색 키워드 저장 객체 변수
		Name = new JTextField();
		Gpa = new JTextField();
		Year= new JTextField();
		Email = new JTextField();
		Dept = new JTextField();
		
		// 버튼 객체의 초기화
		btnsearch = new JButton("검색");
		btnsearch.addActionListener(this);
		btnprev = new JButton("이전");
		btnprev.addActionListener(this);
		btnnext = new JButton("다음");
		btnnext.addActionListener(this);
		btnback = new JButton("현재창 종료");
		btnback.addActionListener(this);
		
		// Board 구성요소 조립
		JPanel panel = new JPanel();							// window board
		panel.setLayout(new GridLayout(0, 2, 10, 3));
		panel.add(input);
		panel.add(btnsearch);
		panel.add(Nosearch);
		panel.add(new JLabel(""));
		panel.add(new JLabel("SID", SwingConstants.RIGHT));
		panel.add(Sid);
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
		
		// 버튼
		panel.add(btnprev);
		panel.add(btnnext);
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		panel.add(btnback);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(panel);
		pack();
		connect();
	}
	
	// 메서드 선언
	public void connect() {
		try {													// JDBC Driver
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
			// 이름과 학과 값으로 like (유사패턴 검색)를 사용하여 일부분만 일치해도 데이터를 다 검색한다.
			pstmt = connection.prepareStatement("select * from testst where name like? or dept like ?");
			stmt = connection.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void showRecord() throws SQLException {
		Sid.setText(rs.getString(1));
		Name.setText(rs.getString("NAME"));
		Gpa.setText("" + rs.getFloat("GPA"));
		Year.setText(rs.getString("YEAR"));
		Email.setText(rs.getString("EMAIL"));
		Dept.setText(rs.getString("DEPT"));
	}
	
	private void disconnect() {
		try {
			if(stmt != null) stmt.close();
			if(connection != null) connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 버튼 클릭 후 Event 처리 메서드 선언
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if(event.getSource() == btnsearch) {
				Nosearch.setText("검색결과");
				String word = input.getText().trim();
				pstmt.setString(1, word+"%");
				pstmt.setString(2, word+"%");
				
				rs = pstmt.executeQuery();
				rs.next();
				showRecord();
			} else if(event.getSource() == btnprev) {
				if(!rs.isFirst()) {
					rs.previous();
					showRecord();
				}
			} else if(event.getSource() == btnnext) {
				if(!rs.isLast()) {
					rs.next();
					showRecord();
				}
			} else if (event.getSource() == btnback) {
				disconnect();
				dispose();
			}
		} catch(SQLException e) {
			e.printStackTrace();
			Nosearch.setText("검색결과가 없습니다.");
		}
	}
	
	public static void main(String[] args) {
		(new DBsearch()).setVisible(true);
	}
}
