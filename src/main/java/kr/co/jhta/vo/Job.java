package kr.co.jhta.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("Job")
public class Job {
	
	private String id;
	private String title;
	private int minSalary;
	private int maxSalary;
	
	public Job() {}
	public Job(String id) {
		this.id = id;
	}
}
