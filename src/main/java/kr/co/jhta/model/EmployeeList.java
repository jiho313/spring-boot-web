package kr.co.jhta.model;

import java.util.List;

import kr.co.jhta.dto.Pagination;
import kr.co.jhta.vo.Employee;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class EmployeeList {

	private Pagination pagination;		// 페이지 내비게이션 정보
	private List<Employee> employees;	// 검색된 직원 목록
	
}
