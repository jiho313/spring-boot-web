package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.form.AddEmployeeForm;
import kr.co.jhta.form.AddJobForm;
import kr.co.jhta.mapper.DepartmentDao;
import kr.co.jhta.mapper.EmployeeDao;
import kr.co.jhta.mapper.JobDao;
import kr.co.jhta.vo.Department;
import kr.co.jhta.vo.Employee;
import kr.co.jhta.vo.Job;

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
	
	/**
	 * 모든 부서목록을 반환한다.
	 * @return 부서목록
	 */
	public List<Department> getAllDepartments() {
		return departmentDao.getDepartments();
	}
	
	/**
	 * 모든 직종목록을 반환한다.
	 * @return 직종목록
	 */
	public List<Job> getAllJobs(){
		return jobDao.getJobs();
	}
	
	/**
	 * 지정된 직종아이디에 종사하는 직원목록을 반환한다.
	 * @param jobid	직종아이디
	 * @return 해당 직종에 종사하는 직원 목록
	 */
	public List<Employee> getAllEmployeesByJobId(String jobid) {
		return employeeDao.getEmployeesByJobId(jobid);
	}
	
	/**
	 * 지정된 부서아이디의 부서에 소속된 사원목록을 반환한다.
	 * @param deptId 부서 아이디
	 * @return 해당 부서에 소속된 직원목록
	 */
	public List<Employee> getEmployeesByDeptId(int deptId) {
		return employeeDao.getEmployeesByDeptId(deptId);
	}
	
	/**
	 * 폼입력 값을 포함하는 Form객체 전달받아서 신규 직종정보를 추가한다.
	 * @param form 신규 직종정보가 포함된 Form객체
	 */
	public void createJob(AddJobForm form) {
		Job job = new Job();
		
		// 변수 이름이 같은 것끼리 서로 복사한다. form의 각 변수가 job에게 복사된다.
		// 주의점 - 변수의 이름이 같으나, 타입이 다르면 오류
		BeanUtils.copyProperties(form, job);
		jobDao.insertJob(job);
	}
	
	/**
	 * 폼입력 값을 포함하는 Form객체를 전달받아서 신규 직원정보를 추가한다.
	 * @param form 신규 직원정보가 포함된 Form객체
	 */
	public void createEmployee(AddEmployeeForm form) {
		Employee employee = new Employee();
		// jobId, departmentId, managerId를 제외한 다른 프로퍼티값이 전부 form에서 employee로 복사됨
		BeanUtils.copyProperties(form, employee);
		
		Job job = new Job(form.getJobId());
		employee.setJob(job);
		
		if (form.getDepartmentId() != null) {
			Department dept = new Department(form.getDepartmentId());
			employee.setDepartment(dept);
		}
		
		if (form.getManagerId() != null) {
			Employee manager = new Employee(form.getManagerId());
			employee.setManager(manager);
		}
		
		employeeDao.insertEmployee(employee);
	}
}
