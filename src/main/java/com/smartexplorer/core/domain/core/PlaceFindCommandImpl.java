package com.smartexplorer.core.domain.core;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 08-06-2018
 * */

@Component
public class PlaceFindCommandImpl implements PlaceFindCommand {
    @Override
    public Optional<ResponseObject> lookForPlace(RequestObject requestObject) {
        return Optional.empty();
    }
}
