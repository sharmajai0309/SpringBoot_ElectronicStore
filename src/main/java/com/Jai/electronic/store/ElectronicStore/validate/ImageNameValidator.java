package com.Jai.electronic.store.ElectronicStore.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.*;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {
    public Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("message is invalid :{}",value);
//        logic
        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }


    }
}
