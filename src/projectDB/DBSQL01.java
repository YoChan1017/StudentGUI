package projectDB;

import java.sql.*;

public class DBSQL01 {
	// 필드
	// 생성자
	// method
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// String url = "jdbc:mysql://localhost:3306/stdb												// DB 연결
		// String url = "jdbc:mysql://localhost:3306/stdb?useUnicode=true&characterEncoding=utf8";		// 인코딩 추가
		String url = "jdbc:mysql://localhost:3306/stdb?useSSL=false&allowPublicKeyRetrieval=true";		// 인증 및 보안 연결 해제 (개발용)
		String username = "boot";
		String password = "12345";
		String query = "insert into stm (name, birth) values (?, ?)";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "오늘 수업 왜함");
			pstmt.setString(2, "1111");
			
			int r = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("데이트베이스 명, 쿼리 오류");
		} catch (ClassNotFoundException e1) {
			System.out.println("데이터베이스 드라이버 오류");
		}
		finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
