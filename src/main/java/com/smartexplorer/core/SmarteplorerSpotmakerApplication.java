package com.smartexplorer.core;

import com.smartexplorer.core.cofiguration.BeansConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Author
 * Karol Meksuła
 * 13-06-2018
 * */

@SpringBootApplication
@Import(BeansConfig.class)
public class SmarteplorerSpotmakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmarteplorerSpotmakerApplication.class, args);
    }

}
