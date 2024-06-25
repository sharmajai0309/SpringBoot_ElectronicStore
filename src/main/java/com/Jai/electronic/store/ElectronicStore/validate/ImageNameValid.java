package com.Jai.electronic.store.ElectronicStore.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

//    error message
    String message() default "Invalid Image Name !!";


//    represent group of constrain
    Class<?>[] groups() default { };

    // edition information about annotation
    Class<? extends Payload>[] payload() default { };


}
