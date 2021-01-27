package de.othr.sw.ksburhan.akaunto.beans.configurations;

import de.othr.sw.ksburhan.akaunto.beans.implementations.AdScorePro;
import de.othr.sw.ksburhan.akaunto.beans.implementations.AdScoreSimple;
import de.othr.sw.ksburhan.akaunto.beans.interfaces.AdScoreIF;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("singleton")
public class AdScoreConfiguration {
    @Bean
    @Qualifier("AdScoreSimple")
    public AdScoreIF courtUtilitiesSimple(){
        return new AdScoreSimple();
    }

    @Bean
    @Qualifier("AdScorePro")
    public AdScoreIF courtUtilitiesPro(){
        return new AdScorePro();
    }

}
