package com.smartexplorer.core.domain.files

import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 16-06-2018
 * */

@SpringBootTest
class DefaultFileExchangeTest extends Specification {

    @Autowired
    private FileExchange fileExchange

    private MultipartFile multipartFile = new MockMultipartFile("file")

    private final String FILE_NAME = "sampleFileName"

    def 'instantiate test'() {
        expect:
        fileExchange != null
        multipartFile != null
    }

    def 'file upload test'() {
        when:
        fileExchange.uploadPicture(multipartFile, FILE_NAME)

        then:
        def file = new File("/home/uploads/" + FILE_NAME)
        file != null
    }

    def 'file download test'() {
        when:
        def bytes = fileExchange.getPicture(FILE_NAME)

        then:
        bytes != null
    }

}
