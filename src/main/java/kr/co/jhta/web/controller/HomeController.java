package kr.co.jhta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.jhta.vo.User;

// @RestController 어노테이션은 데이터를 내려보내는 응답을 보낼 때 사용한다. 객체를 제이슨 형식으로 응답한다. - (ajax 비동기 처리)
// @Controller 어노테이션은 view(.jsp)를 내려보내는 응답을 보낼 때 사용한다. jsp의 이름을 응답한다.

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String home(Model model) {
		// Model 객체에는 뷰템플릿에 전달한 값을 담을 수 있다.
		model.addAttribute("message", "홈페이지 방문을 환영합니다.");
		
		return "home";	// "WEB-INF/views/home.jsp"로 내부이동 시키라는 의미
	}
}
