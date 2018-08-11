package com.arjvik.arjmart.orderqueueconsumer.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	
	private String to;
	private String subject;
	private String body;
	
}
