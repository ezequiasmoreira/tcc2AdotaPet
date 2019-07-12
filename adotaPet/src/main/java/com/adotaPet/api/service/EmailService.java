package com.adotaPet.api.service;

import org.springframework.mail.SimpleMailMessage;

import com.adotaPet.api.domain.Pessoa;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Pessoa cliente, String newPass);
}
