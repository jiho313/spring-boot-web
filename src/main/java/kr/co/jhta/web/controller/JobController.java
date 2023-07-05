package kr.co.jhta.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.co.jhta.service.HrService;

@Controller
public class JobController {

	@Autowired
	private HrService hrService;
}
