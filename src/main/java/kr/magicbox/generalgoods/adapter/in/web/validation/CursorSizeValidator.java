package kr.magicbox.generalgoods.adapter.in.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.magicbox.generalgoods.adapter.in.web.constants.CursorConstants;

public class CursorSizeValidator implements ConstraintValidator<CursorSize, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value >= CursorConstants.MIN_SIZE && value <= CursorConstants.MAX_SIZE;
    }
}
