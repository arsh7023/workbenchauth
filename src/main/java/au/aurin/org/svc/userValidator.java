package au.aurin.org.svc;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class userValidator implements Validator {

  @Override
  public boolean supports(final Class<?> clazz) {
    // TODO Auto-generated method stub
    // return false;
    return dummyuserData.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(final Object target, final Errors errors) {
    // TODO Auto-generated method stub
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname",
        "required.firstname", "Field name is required.");

  }

}
