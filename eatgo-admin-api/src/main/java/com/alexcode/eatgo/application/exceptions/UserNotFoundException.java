package com.alexcode.eatgo.application.exceptions;

import com.alexcode.eatgo.exceptions.EntityNotFoundException;
import com.alexcode.eatgo.exceptions.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(Long id) {
        super("Could not find region with id : " + id, ErrorCode.ENTITY_NOT_FOUND);
    }
}
