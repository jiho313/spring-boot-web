package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.mapper.DepartmentDao;
import kr.co.jhta.mapper.EmployeeDao;
import kr.co.jhta.mapper.JobDao;
import kr.co.jhta.vo.Department;

/**
 * 부서관리, 직원관리, 직종관리 관련 업무로직이 구현된 클래스다.
 * @author jh_te
 *
 */
@Service
public class HrService {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private JobDao jobDao;
	
	public List<Department> getAllDepartments() {
		return departmentDao.getDepartments();
	}
}
