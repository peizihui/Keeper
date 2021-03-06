package com.keeper.util.validation.util.executors;

import org.springframework.stereotype.Component;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 12.05.2017
 */
@Component
public class EmailValidator extends StringValidator {

    @Override
    public boolean validate(String email) {
        return (super.validate(email) && email.length() > 3 && email.indexOf('@') != -1);
    }
}
