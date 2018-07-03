package com.smartexplorer.core.controller;

import com.smartexplorer.core.controller.command.SpotRemoveCommand;
import com.smartexplorer.core.controller.command.StatisticsMailCommand;
import com.smartexplorer.core.domain.files.FileExchange;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.SpotCreationForm;
import com.smartexplorer.core.domain.subject.spot.SpotCreator;
import com.smartexplorer.core.domain.subject.spot.information.SpotInformation;
import com.smartexplorer.core.domain.subject.spot.information.SpotInformationManager;
import com.smartexplorer.core.domain.subject.spot.stats.OverallStatistics;
import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics;
import com.smartexplorer.core.domain.subject.spot.stats.StatisticsProvider;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.domain.subject.spotmaker.auth.SpotMakerAuthHelper;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotInformationRepository;
import com.smartexplorer.core.repository.SpotMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private SpotCreator spotCreator;
    private SpotMakerRepository spotMakerRepository;
    private FileExchange fileExchange;
    private StatisticsProvider statisticsProvider;
    private StatisticsMailCommand statisticsMailCommand;
    private SpotRemoveCommand spotRemoveCommand;
    private SpotInformationRepository spotInformationRepository;
    private SpotInformationManager spotInformationManager;
    private SpotMakerAuthHelper spotMakerAuthHelper;
    private final String MSG = "You have no rights to modify spot with id: ";

    @Autowired
    public void setSpotCreator(SpotCreator spotCreator) {
        this.spotCreator = spotCreator;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setFileExchange(FileExchange fileExchange) {
        this.fileExchange = fileExchange;
    }

    @Autowired
    public void setStatisticsProvider(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = statisticsProvider;
    }

    @Autowired
    public void setStatisticsMailCommand(StatisticsMailCommand statisticsMailCommand) {
        this.statisticsMailCommand = statisticsMailCommand;
    }

    @Autowired
    public void setSpotRemoveCommand(SpotRemoveCommand spotRemoveCommand) {
        this.spotRemoveCommand = spotRemoveCommand;
    }

    @Autowired
    public void setSpotInformationRepository(SpotInformationRepository spotInformationRepository) {
        this.spotInformationRepository = spotInformationRepository;
    }

    @Autowired
    public void setSpotInformationManager(SpotInformationManager spotInformationManager) {
        this.spotInformationManager = spotInformationManager;
    }

    @Autowired
    public void setSpotMakerAuthHelper(SpotMakerAuthHelper spotMakerAuthHelper) {
        this.spotMakerAuthHelper = spotMakerAuthHelper;
    }

    @PreAuthorize("hasAuthority('SPOT_MAKER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Spot createSpot(@RequestBody SpotCreationForm spotCreationForm, Authentication authentication) {
        SpotMaker spotMaker = spotMakerRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(authentication.getName()));

        spotCreationForm.setSpotMakerId(spotMaker.getSpotMakerId());
        return spotCreator.createSpot(spotCreationForm);
    }

    @PreAuthorize("hasAuthority('SPOT_MAKER')")
    @PostMapping(value = "/picture/{spotId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendPhoto(@PathVariable("spotId") String spotId, @RequestParam("file") MultipartFile file,
                            Authentication authentication) {

        if (spotMakerAuthHelper.hasSpotRights(spotId, authentication.getName())) {
            return fileExchange.uploadPicture(file, spotId);
        }

        else throw new AuthorizationServiceException(MSG + spotId);
    }

    @GetMapping(value = "/picture/{spotId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhoto(@PathVariable("spotId") String spotId) {
        return fileExchange.getPicture(spotId);
    }

    @GetMapping("/statistic/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public SpotStatistics getSpotStatistics(@PathVariable("spotId") String spotId) {
        return statisticsProvider.findSpotStatistics(spotId);
    }

    @PreAuthorize("hasAuthority('SPOT_MAKER')")
    @GetMapping("/statistic/mail/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public void requestStatisticsMail(@PathVariable("spotId") String spotId) {
        statisticsMailCommand.sendStatistics(spotId);
    }

    @GetMapping("/statistic/overall")
    @ResponseStatus(HttpStatus.OK)
    public OverallStatistics getOverallStatistics() {
        return statisticsProvider.serviceSummary();
    }

    @PreAuthorize("hasAuthority('SPOT_MAKER')")
    @DeleteMapping("/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public Spot removeSpot(@PathVariable("spotId") String spotId, Authentication authentication) {
        String username = authentication.getName();
        return spotRemoveCommand.removeSpot(username, spotId);
    }

    @GetMapping("/info/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public SpotInformation getSpotInformation(@PathVariable("spotId") String spotId) {
        return spotInformationRepository.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot find information about spot with id: " + spotId));
    }

    @PreAuthorize("hasAuthority('SPOT_MAKER')")
    @PutMapping("/info")
    @ResponseStatus(HttpStatus.CREATED)
    public SpotInformation modifySpotInformation(@RequestBody SpotInformation spotInformation, Authentication authentication) {
        if (spotMakerAuthHelper.hasSpotRights(spotInformation.getSpotId(), authentication.getName())) {
            return spotInformationManager.modifySpotInformation(spotInformation);
        }

        else throw new AuthorizationServiceException(MSG + spotInformation.getSpotId());
    }

}
