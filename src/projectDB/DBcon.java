package projectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 데이터베이스 연동
// 제어판 - windows tools > 서비스 > MySQL server 실행
// import java.sql.*;
public class DBcon {
	// 필드 선언
	// 생성자 선언 - 필드 초기화
	
	// 메서드 생성 - 구현
	// 프로그램 실행 시작 진입점
	public static void main(String[] args) {
		// MySQL DB 연동 - 예외처리
		try {
			Class.forName("com.mysql.jdbc.Driver");	// JDBC(Java DataBase Connectivity)
			System.out.println("Connection complete");
			
		} catch(ClassNotFoundException e1) {
			System.out.println("Connection Failed");
			System.exit(0);
		}
		String url = "jdbc:mysql://localhost:3306/stdb?useSSL=false&allowPublicKeyRetrieval=true";
		String username = "boot";
		String password = "12345";
		Connection con = null;
		System.out.println("MySQL DataBase Connecting!");
		
		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("DataBase Connecte Complete");
		} catch(SQLException e) {
			System.out.println("DataBase Connection Failed");
			System.exit(0);
		}
		
		String query = "select * from stm";
		Statement stmt;
		try {
			stmt = con.createStatement();
			// query = select * from stm;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " : 이름 = " + rs.getString(2) + " : 출생년도 = " + rs.getString(3));
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("Connection Closing");
			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					
				}
			} 
		}
	}
}
