package kr.co.jhta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jhta.form.AddEmployeeForm;
import kr.co.jhta.model.EmployeeList;
import kr.co.jhta.service.HrService;
import kr.co.jhta.vo.Department;
import kr.co.jhta.vo.Employee;
import kr.co.jhta.vo.Job;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {
	
	@Autowired
	private HrService hrService;
	
	// 직원목록 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/list")
	// 기본값이 존재한다면 @RequestParam 사용
	// 입력폼은 어차피 폼이 존재하기에 고려안해도 됨 
	public String list(@RequestParam(name = "sort", required = false, defaultValue = "id") String sort, 
					   @RequestParam(name = "rows", required = false, defaultValue = "10") int rows,
					   @RequestParam(name = "page", required = false, defaultValue = "1") int page,
					   @RequestParam(name = "opt", required = false) String opt,
					   @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
					   Model model) {
		
		log.info("sort='{}', rows='{}', page='{}', opt='{}', keyword='{}'", sort, rows, page, opt, keyword);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sort", sort);
		param.put("rows", rows);
		param.put("page", page);
		if (StringUtils.hasText(opt) && StringUtils.hasText(keyword)) {
			param.put("opt", opt);
			param.put("keyword", keyword);
		}
		
		EmployeeList result = hrService.getEmployees(param);
		
		/*
		 * 아래의 코드를 실행하면 Model객체에 EmployeeList(직원목록과 페이징정보를 포함하고 있는)가 추가된다.
		 * 
		 * JSP에서는 페이징처리 정보를 조회할 때는 ${result.pagination.xxx}
		 * 			 직원목록 정보를 조회할 때는 ${result.employees}
		 */
		model.addAttribute("result", result);
		
		return "employees/list";
	}
	
	// 신규직원 등록폼 화면 요청과 매핑되는 요청핸들러 메소드
	@GetMapping("/add")
	public String form(Model model) {
		List<Department> depts = hrService.getAllDepartments();
		List<Job> jobs = hrService.getAllJobs();
		
		model.addAttribute("depts", depts);
		model.addAttribute("jobs", jobs);
		
		
		
		return "employees/form";
	}
	
	// 신규직원 등록 요청과 매핑되는 요청핸들러 메소드
	@PostMapping("/add")
	public String createEmployee(AddEmployeeForm form) {
		hrService.createEmployee(form);
		
		return "redirect:list";
	}
	
	// 직원상세정보 화면 요청과 매핑되는 요청핸들러 메소드
	
	// 직원수정폼 화면 요청과 매핑되는 요청핸들러 메소드
	
	// 직원정보 수정요청과 매핑되는 요청핸들러 메소드
	
	// 직원목록정보 요청을 처리하는 요청핸들러 메소드
	@GetMapping("/empsByDept")
	@ResponseBody
	public List<Employee> getEmpsByDept(@RequestParam("deptId")int deptId) {
		List<Employee> employees = hrService.getEmployeesByDeptId(deptId);
		return employees;
	}
	
	
}
