package de.othr.sw.ksburhan.akaunto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountData;
import de.othr.sw.ksburhan.akaunto.DTOs.AccountDataDTO;
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
            final String url = "http://localhost:8081/api/v1/user/advertisingInformation?username=" + username;

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate restTemplate = restTemplateBuilder.basicAuthentication("burhan", "pw").build();
            String result = restTemplate.getForObject(url, String.class);
            Account account = accountService.findByUsername(username);

            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            AccountDataDTO accountDataDTO = objectMapper.readValue(result, AccountDataDTO.class);
            accountDataDTO.setAccount(account);
            AccountData accountDataToSave = updateWithDTOData(accountDataDTO);
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

    public AccountData updateWithDTOData(AccountDataDTO accountDataDTO){
        AccountData accountDataToSave = accountDataRepository.findByAccountID(accountDataDTO.getAccount());
        accountDataToSave.setFavouriteSport(accountDataDTO.getFavouriteSport());
        accountDataToSave.setHometown(accountDataDTO.getHometown());
        return accountDataToSave;
    }

}
