package kr.co.mlec.join.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.co.mlec.join.vo.CeoSignUpVO;
import kr.co.mlec.join.vo.PersonalVO;
import kr.co.mlec.util.ConnectionFactory;
import kr.co.mlec.util.JDBCClose;

public class PersonalDAO {
	
	public void Personal_Signup(PersonalVO person) {
		
		StringBuilder sql = new StringBuilder(); 
		
		sql.append(" insert into personal (id, password, name, email, phone) ");
		sql.append(" values ( ?, ?, ?, ?, ?) ");
		
		try (
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			
			pstmt.setString(1, person.getId());
			pstmt.setString(2, person.getPass());
			pstmt.setString(3, person.getName());
			pstmt.setString(4, person.getEmail());
			pstmt.setString(5, person.getPhone());
			
			pstmt.executeUpdate();
			
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 로그인 서비스
	 */
	public PersonalVO login(PersonalVO loginVO) {

		PersonalVO userVO = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select id, password, type ");
		sql.append("  from personal ");
		sql.append(" where id = ? and password = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		) {
			
			pstmt.setString(1, loginVO.getId());
			pstmt.setString(2, loginVO.getPass());
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String id 		= rs.getString("id");
				String password = rs.getString("password");
				String type 	= rs.getString("type");
				
				userVO = new PersonalVO(id, password, type);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return userVO;
	}

	/**
	 * 마이페이지 정보조회
	 */
	
	public PersonalVO selectById(String id) {
		
		PersonalVO personal = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select id,password,email,name,phone,type" );
		sql.append("       ,to_char(reg_date, 'yyyy-mm-dd') as reg_date");
		sql.append(" from personal ");
		sql.append(" where id=?");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){	
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String personalId = rs.getString("id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String type = rs.getString("type");
				String regDate = rs.getString("reg_date");
				
				personal = new PersonalVO(personalId,password,email,name,phone,type,regDate);
			}
		} catch( Exception e) {
			e.printStackTrace();
		}
		
		return personal;
	}
	
}