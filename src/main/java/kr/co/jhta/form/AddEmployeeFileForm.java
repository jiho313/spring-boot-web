package kr.co.jhta.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmployeeFileForm {

	private String title;
	// input 박스의 타입이 file이면 MultipartFile 받고 여러개면 List로 
	private MultipartFile xlsfile;
}
