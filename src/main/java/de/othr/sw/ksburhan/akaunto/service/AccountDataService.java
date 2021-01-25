package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.repository.AccountDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountDataService {

    AccountDataRepository accountDataRepository;

    @Autowired
    public AccountDataService(AccountDataRepository accountDataRepository) {
        this.accountDataRepository = accountDataRepository;
    }

    public void getAccountDataFromBeachCourts(String username){
        final String url = "http://localhost:8083/api/v1/user/advertisingInformation?username=" + username;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        System.out.println(result);
    }

}
