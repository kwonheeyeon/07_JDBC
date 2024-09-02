package edu.kh.todolist.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {
							
	private int todoNo;
	private String title;
	private String detail;
	private int complete;
	private String regDate;
	
	
}
