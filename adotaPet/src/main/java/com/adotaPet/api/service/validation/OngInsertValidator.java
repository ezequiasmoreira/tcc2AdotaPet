package com.adotaPet.api.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.adotaPet.api.domain.Ong;
import com.adotaPet.api.dto.OngDTO;
import com.adotaPet.api.repository.OngRepository;
import com.adotaPet.api.resource.exceptions.FieldMessage;

public class OngInsertValidator implements ConstraintValidator<OngInsert, OngDTO> {

	@Autowired
	private OngRepository repo;
	
	@Override
	public void initialize(OngInsert ann) {
	}

	@Override
	public boolean isValid(OngDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Ong ongCnpj = repo.findByCnpj(objDto.getCnpj());
		if (ongCnpj != null) {
			list.add(new FieldMessage("cnpj", "Cnpj já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

