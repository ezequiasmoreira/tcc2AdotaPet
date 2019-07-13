package com.adotaPet.api.service.validation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.dto.PessoaNewDTO;
import com.adotaPet.api.repository.PessoaRepository;
import com.adotaPet.api.resource.exceptions.FieldMessage;

public class PessoaInsertValidator implements ConstraintValidator<PessoaInsert, PessoaNewDTO> {

	@Autowired
	private PessoaRepository repo;
	
	@Override
	public void initialize(PessoaInsert ann) {
	}

	@Override
	public boolean isValid(PessoaNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Pessoa aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

