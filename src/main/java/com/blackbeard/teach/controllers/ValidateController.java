package com.blackbeard.teach.controllers;

import javax.validation.*;

import com.blackbeard.teach.models.ValidationErrorModel;
import java.util.List;

import com.blackbeard.teach.models.PlayerModel;
import java.util.Set;

public class ValidateController {
	private static Validator	validator;

	public ValidateController() {
		final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	static boolean runValidator(List<ValidationErrorModel> errors, PlayerModel player) {
		boolean ok;

		ok = true;
		Set<ConstraintViolation<PlayerModel>> validationErrors = validator.validate(player);
		if (!validationErrors.isEmpty()) {
			ok = false;
			for (ConstraintViolation<PlayerModel> error : validationErrors) {
				ValidationErrorModel tempModel = new ValidationErrorModel(error.getPropertyPath().toString(), error.getMessage());
				errors.add(tempModel);
			}
		}
		return (ok);
	}
}
