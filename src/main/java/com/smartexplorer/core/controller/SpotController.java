package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.files.FileExchange;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.SpotCreationForm;
import com.smartexplorer.core.domain.subject.spot.SpotCreator;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.repository.SpotMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PostMapping(value = "/{spotId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendPhoto(@PathVariable("spotId") String spotId, @RequestParam("file") MultipartFile file) {
        return fileExchange.uploadPicture(file, spotId);
    }

    @GetMapping(value = "/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhoto(@PathVariable("spotId") String spotId) {
        return fileExchange.getPicture(spotId);
    }

}
