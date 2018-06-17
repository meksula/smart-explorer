package com.smartexplorer.core.domain.subject.spot

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

class SpotCreationFormTest extends Specification {
    private final String NAME = "Muzeum Wsi Lubelskiej"
    private final String DESCRIPTION = "Placówka muzeala powstała w celu propagowania dziejów polskiej wsi."
    private final boolean SEARCH_ENABLE = true
    private final String SPOT_MAKER_ID = "33fdj4i3fdcj43dmj439d"

    private final String CITY = "Lublin"
    private final String STREET = "aleja warszawska"
    private final String BUILDING_NUMBER = "96A"

    def 'form creation and json deserialize test'() {
        setup:
        def form = new SpotCreationForm(NAME, DESCRIPTION, SEARCH_ENABLE, CITY, STREET, BUILDING_NUMBER)
        form.setSpotMakerId(SPOT_MAKER_ID)

        expect:'json deserialize'
        def json = new ObjectMapper().writeValueAsString(form)
        json == "{\"name\":\"Muzeum Wsi Lubelskiej\",\"description\":\"Placówka muzeala powstała w celu propagowania dziejów polskiej wsi.\",\"searchEnable\":true,\"city\":\"Lublin\",\"street\":\"aleja warszawska\",\"buildingNumber\":\"96A\",\"spotMakerId\":\"33fdj4i3fdcj43dmj439d\"}"
    }

}
