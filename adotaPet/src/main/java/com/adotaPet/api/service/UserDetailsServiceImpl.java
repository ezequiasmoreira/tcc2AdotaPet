package com.adotaPet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PessoaRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Pessoa pessoa = repo.findByEmail(email);
		if (pessoa == null) {
			throw new UsernameNotFoundException(email);
		}
		System.out.println(pessoa.getPerfis());
		return new UserSS(pessoa.getId(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getPerfis());
	}
}
