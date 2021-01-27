package de.othr.sw.ksburhan.akaunto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.oth.beachCourts.DTOs.UserAdvertisingInformationDTO;
import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountData;
import de.othr.sw.ksburhan.akaunto.repository.AccountDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountDataService {

    AccountDataRepository accountDataRepository;
    AccountService accountService;

    @Autowired
    public AccountDataService(AccountDataRepository accountDataRepository, AccountService accountService) {
        this.accountDataRepository = accountDataRepository;
        this.accountService = accountService;
    }

    public void getAccountDataFromBeachCourts(String username){
        try {
            final String url = String.format("http://localhost:8959/api/v1/user/advertisingInformation?username=%s", username);

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate restTemplate = restTemplateBuilder.basicAuthentication("burhan", "pw").build();
            String result = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserAdvertisingInformationDTO userAdvertisingInformationDTO = objectMapper.readValue(result, de.oth.beachCourts.DTOs.UserAdvertisingInformationDTO.class);
            AccountData accountDataToSave = updateWithDTOData(userAdvertisingInformationDTO, username);
            save(accountDataToSave);
        } catch (HttpServerErrorException e){
            System.out.println("an error occurred trying to call the api");
        } catch (JsonProcessingException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public AccountData save(AccountData accountData) {
        return accountDataRepository.save(accountData);
    }

    public AccountData updateWithDTOData(de.oth.beachCourts.DTOs.UserAdvertisingInformationDTO userAdvertisingInformationDTO, String username){
        Account account = accountService.findByUsername(username);
        AccountData accountDataToSave = accountDataRepository.findByAccountID(account);
        accountDataToSave.setFavouriteSport(userAdvertisingInformationDTO.getFavouriteSport());
        accountDataToSave.setHometown(userAdvertisingInformationDTO.getHometown());
        return accountDataToSave;
    }

}
