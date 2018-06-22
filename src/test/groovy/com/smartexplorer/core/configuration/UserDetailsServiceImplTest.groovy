package groovy.com.smartexplorer.core.configuration

import com.smartexplorer.core.SmarteplorerSpotmakerApplication
import com.smartexplorer.core.cofiguration.UserDetailsServiceImpl
import com.smartexplorer.core.domain.subject.client.ProxyClient
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker
import com.smartexplorer.core.repository.ClientRepository
import com.smartexplorer.core.repository.SpotMakerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

@SpringBootTest(classes = SmarteplorerSpotmakerApplication)
class UserDetailsServiceImplTest extends Specification {

    @Autowired
    private UserDetailsServiceImpl detailsService

    @Autowired
    private SpotMakerRepository spotMakerRepository

    private final String USERNAME = "someUsername2000"

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private ClientRepository clientRepository

    def setup() {
        spotMakerRepository.save(new SpotMaker.SpotMakerBuilder()
                .username(USERNAME)
                .build())

        clientRepository.save(new ProxyClient("PROXY_CLIENT_3d2dc3df22", passwordEncoder.encode("PROXY_CLIENT_3d2dc3df22")))
    }

    def 'easy user load test' () {
        expect:
        detailsService.loadUserByUsername(USERNAME)
    }

    def 'PROXY API access test'() {
        expect:
        detailsService.loadUserByUsername("PROXY_CLIENT_3d2dc3df22")
        clientRepository.findAll().size() == 1
    }

    def 'encode password'() {
        setup:
        String encoded = passwordEncoder.encode("PROXY_CLIENT_3d2dc3df22")

        expect:
        println(encoded)
        passwordEncoder.matches("PROXY_CLIENT_3d2dc3df22", encoded)

    }

    void cleanup() {
        clientRepository.deleteAll()
    }

}
