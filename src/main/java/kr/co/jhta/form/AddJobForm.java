package kr.co.jhta.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 매개변수 없는 디폴트 생성자 추가
@NoArgsConstructor
//  Getter, Setter 메소드 추가
@Getter
@Setter
// toString 메소드 추가
@ToString
public class AddJobForm {

	private String id;
	private String title;
	private int minSalary;
	private int maxSalary;
}
