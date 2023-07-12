package kr.co.jhta.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import lombok.extern.slf4j.Slf4j;

/*
 * @Component 어노테이션
 * 		- 컴포넌트 스캔 대상이 되는 클래스에 추가하는 어노테이션이다.
 * 		- 이 어노테이션 및 이 어노테이션의 하위 어노테이션이 추가되어 있는 클래스는 
 * 		  컴포넌트 스캔 대상이 되고, 스프링 컨테이너의 빈으로 자동등록된다.
 */
@Component
@Slf4j
public class FileDownloadView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 디렉토리 경로와 파일명을 모델에서 조회한다.
		String directory = (String) model.get("directory");
		String filename = (String) model.get("filename");
		
		log.info("파일 디렉토리 - {}", directory);
		log.info("파일 이름 - {}", filename);
		
		// attachment 항상 파일 다운로드 바이너리 데이터의 기본타입 - 알지 못하는 미지의 데이터(사진, 영상, 엑셀, 텍스트 등등)
		// byte로 쓰고 읽을 수 있다. 한 글자씩은 쓰고 읽는 것은 텍스트로만 가능
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));
		
		FileInputStream inputStream = new FileInputStream(new File(directory, filename));
		OutputStream outputStream = response.getOutputStream();
		
		FileCopyUtils.copy(inputStream, outputStream);
	}
}
