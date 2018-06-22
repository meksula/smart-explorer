package groovy.com.smartexplorer.core.domain.subject.client

import com.smartexplorer.core.SmarteplorerSpotmakerApplication
import com.smartexplorer.core.domain.subject.client.ProxyClient
import com.smartexplorer.core.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

@SpringBootTest(classes = SmarteplorerSpotmakerApplication)
class ProxyClientTest extends Specification {
    private ProxyClient proxyClient

    @Autowired
    private ClientRepository clientRepository

    private final String CLIENT_NAME = "PROXY_CLIENT_2000"
    private final String PASSWORD = "PROXY_CLIENT_PASS"

    void setup() {
        proxyClient = new ProxyClient(CLIENT_NAME, PASSWORD)
        clientRepository.save(proxyClient)
    }

    def 'client should has correct right'() {
        expect:
        clientRepository.findAll().get(0).getAuthorities().size() == 1
    }

    void cleanup() {
        clientRepository.deleteAll()
    }
}
