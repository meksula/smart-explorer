package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.mail.MailSender;
import com.smartexplorer.core.domain.mail.MailType;
import com.smartexplorer.core.domain.mail.TemplateMailSender;
import com.smartexplorer.core.domain.subject.explorers.Explorer;
import com.smartexplorer.core.repository.ExplorerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@RestController
@RequestMapping("/api/v1/explorer/personalization")
public class ExplorerPersonalizationController {
    private ExplorerRepository explorerRepository;
    private MailSender mailSender;

    @Autowired
    public void setExplorerRepository(ExplorerRepository explorerRepository) {
        this.explorerRepository = explorerRepository;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PreAuthorize("hasAuthority('PROXY')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Explorer updateExplorer(@RequestBody Explorer explorer) {
        mailSender.setAttachment(explorer);
        mailSender.sendMail(MailType.EXPLORER_JOINED, explorer);

        return explorerRepository.save(explorer);
    }

}
