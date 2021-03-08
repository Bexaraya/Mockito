package com.example.demo.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionStatus {
	private static final long serialVersionUID = 1L;
	private String code;
	private String description;
}
