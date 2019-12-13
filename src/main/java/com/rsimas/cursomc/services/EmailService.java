package com.rsimas.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.rsimas.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
