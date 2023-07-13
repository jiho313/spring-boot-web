package kr.co.jhta.vo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Alias("Employee")
public class Employee implements UserDetails {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String encryptedPassword;
	private String phoneNumber;
	private Date hireDate;
	private Job job;
	private Double salary;
	private Double commissionPct;
	private Employee manager;
	private Department department;
	
	public Employee() {};
	public Employee(int id) {
		this.id = id;
	}
	
	// 직원(사용자)의 보유권한을 반환한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("USER_ROLE"));
	}
	
	// 직원(사용자)의 비밀번호를 반환한다.
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return encryptedPassword;
	}
	
	// 직원(사용자)의 고유한 식별정보(primary key)를 반환한다.
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	// DB컬럼으로 세밀한 조정가능
	// 직원(사용자)의 계정 만료여부를 반환한다. true - 계정만료되지 않았음
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 직원(사용자)의 계정잠김여부를 반환한다. true - 계정 잠기지 않았음
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 직원(사용자)의 비밀번호 만료여부를 반환한다. true - 비밀번호 만료되지 않았음
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 직원(사용자)의 활성화 여부를 반환한다. true - 계정이 사용가능함
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
