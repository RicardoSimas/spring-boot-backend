package com.rsimas.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rsimas.cursomc.domain.Cliente;
import com.rsimas.cursomc.repositories.ClienteRepository;
import com.rsimas.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email){
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet); 
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { //Gera um dígito.
			return (char) (rand.nextInt(10) + 48);
		}else if(opt == 1) { //Gera letra maiuscula.
			return (char) (rand.nextInt(26) + 67);
		}else { //Gera letra minuscula.
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
}
