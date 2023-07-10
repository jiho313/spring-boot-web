package kr.co.jhta.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dto.Pagination;
import kr.co.jhta.form.AddEmployeeForm;
import kr.co.jhta.form.AddJobForm;
import kr.co.jhta.mapper.DepartmentDao;
import kr.co.jhta.mapper.EmployeeDao;
import kr.co.jhta.mapper.JobDao;
import kr.co.jhta.model.EmployeeList;
import kr.co.jhta.vo.Department;
import kr.co.jhta.vo.Employee;
import kr.co.jhta.vo.Job;

/**
 * 부서관리, 직원관리, 직종관리 관련 업무로직이 구현된 클래스다.
 * 
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
	 * 
	 * @return 부서목록
	 */
	public List<Department> getAllDepartments() {
		return departmentDao.getDepartments();
	}

	/**
	 *  지정된 부서아이디에 해당하는 부서정보를 반환한다.
	 * @param deptId 부서아이디
	 * @return 부서정보
	 */
	public Department getDepartment(int deptId) {
		
		return departmentDao.getDepartmentById(deptId);
	}
	/**
	 * 모든 직종목록을 반환한다.
	 * 
	 * @return 직종목록
	 */
	public List<Job> getAllJobs() {
		return jobDao.getJobs();
	}

	/**
	 * 지정된 직종아이디에 종사하는 직원목록을 반환한다.
	 * 
	 * @param jobid 직종아이디
	 * @return 해당 직종에 종사하는 직원 목록
	 */
	public List<Employee> getAllEmployeesByJobId(String jobid) {
		return employeeDao.getEmployeesByJobId(jobid);
	}

	/**
	 * 지정된 부서아이디의 부서에 소속된 사원목록을 반환한다.
	 * 
	 * @param deptId 부서 아이디
	 * @return 해당 부서에 소속된 직원목록
	 */
	public List<Employee> getEmployeesByDeptId(int deptId) {
		return employeeDao.getEmployeesByDeptId(deptId);
	}

	/**
	 * 폼입력 값을 포함하는 Form객체 전달받아서 신규 직종정보를 추가한다.
	 * 
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
	 * 
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

	/**
	 * 직원 검색에 필요한 파라미터정보를 전달 받아서 직원목록을 조회한다.
	 * <p>
	 * Map객체는 "sort", "rows", "page", "opt", "keyword" 정보를 포함하고 있다.
	 * 
	 * @param param 요청파라미터 정보를 포함하는 Map객체
	 * @return 페이징, 직원목록 정보가 포함된 객체
	 */
	public EmployeeList getEmployees(Map<String, Object> param) {

		int totalRows = employeeDao.getTotalRows(param);

		int page = (int) param.get("page");
		int rows = (int) param.get("rows");
		Pagination pagination = new Pagination(rows, page, totalRows);

		int begin = pagination.getBegin();
		int end = pagination.getEnd();

		param.put("begin", begin);
		param.put("end", end);

		EmployeeList result = new EmployeeList();
		List<Employee> employees = employeeDao.getEmployees(param);

		result.setPagination(pagination);
		result.setEmployees(employees);

		return result;
	}
}
