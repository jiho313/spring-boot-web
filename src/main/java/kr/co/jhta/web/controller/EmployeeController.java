package kr.co.jhta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.All.MaxOccurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.jhta.form.AddEmployeeFileForm;
import kr.co.jhta.form.AddEmployeeForm;
import kr.co.jhta.model.EmployeeList;
import kr.co.jhta.service.HrService;
import kr.co.jhta.vo.Department;
import kr.co.jhta.vo.Employee;
import kr.co.jhta.vo.EmployeeFile;
import kr.co.jhta.vo.Job;
import kr.co.jhta.web.view.EmployeesExcelView;
import kr.co.jhta.web.view.FileDownloadView;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {
	
	@Value("${hr.employee.xls.save-directory}")
	private String directory;
	
	// 객체 주입
	@Autowired
	private FileDownloadView fileDownloadView;
	
	@Autowired
	private EmployeesExcelView employeesExcelView;
	
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
	
	// 직원 상세 정보를 응답으로 보내는 요청핸들러 메소드
	@GetMapping("/detail")
	@ResponseBody
	public Employee getEmployeeDetail(@RequestParam("id") int empId) {
		Employee employee = hrService.getEmployee(empId);
		return employee;
	}
	
	@PostMapping("/upload")
	public String uploadEmployeeFile(AddEmployeeFileForm form) throws Exception {
		hrService.createEmployeeFile(form);
		
		return "redirect:files";
	}
	
	@GetMapping("/files")
	public String files(Model model) {
		List<EmployeeFile> files = hrService.getAllEmployeeFiles();
		model.addAttribute("files", files);
		
		return "employees/files";
	}
	
	@GetMapping("/add-all")
	public String addEmployees(@RequestParam("id")int fileId) throws Exception {
		hrService.addEmployees(fileId);
		
		return "redirect:files";
	}
	
	/*
	 * 엑셀파일 다운로드 요청
	 * 요청방식: GET
	 * 요청URL: http://localhost/emp/download?id=xxx
	 */
	@GetMapping("/download")
	public ModelAndView fileDownload(@RequestParam("id")int fileId) throws Exception {
		
		EmployeeFile employeeFile = hrService.getEmployeeFile(fileId);
		
		// ModelAndView 객체에 파일다운로드 응답을 보내는 View객체를 저장한다.
		//					   디렉토리 경로를 저장한다.
		//					   파일명을 저장한다.
		// 디렉토리를 직접 삽입하는 건 좋지 않고 컨트롤러에서 처리하게 해야한다.
		
		ModelAndView modelAndView = new ModelAndView();
		// 파일을 응답으로 보내는 fileDownloadView객체를 ModelAndView객체에 저장함
		modelAndView.setView(fileDownloadView);
		// FileDownloadView객체에 전달할 정보를 ModelAndView객체에 저장함
		modelAndView.addObject("directory", directory);
		modelAndView.addObject("filename", employeeFile.getName());
		
		return modelAndView;
	}
	
	/*
	 * 전체 직원 목록에 대한 엑셀파일 요청
	 * 요청방식: GET
	 * 요청URL : http://localhost/emp/xls
	 */
	@GetMapping("/xls")
	public ModelAndView downloadEmployeesXls() {
		List<Employee> employees = hrService.getAllEmployees();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(employeesExcelView);
		modelAndView.addObject("items", employees);
		
		return modelAndView;
	}
	
}
