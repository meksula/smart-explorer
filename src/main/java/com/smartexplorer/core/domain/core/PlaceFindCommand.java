package com.smartexplorer.core.domain.core;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 08-06-2018
 * */

public interface PlaceFindCommand {
    Optional<ResponseObject> lookForPlace(RequestObject requestObject);
}
