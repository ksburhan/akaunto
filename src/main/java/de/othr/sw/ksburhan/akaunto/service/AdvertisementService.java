package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import de.othr.sw.ksburhan.akaunto.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class AdvertisementService {

    AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public Advertisement findByAdID(long adId) {
        return advertisementRepository.findByAdID(adId);
    }

    @Transactional
    public void save(Advertisement ad) {
        advertisementRepository.save(ad);
    }


    public Advertisement getAdforAccount(Account account) {
        List<Advertisement> matchingAds;
        String favoriteSport;

        if(account.getAccountData().getFavouriteSport() != null){
            favoriteSport = account.getAccountData().getFavouriteSport();
            matchingAds = advertisementRepository.findMatchingAd(favoriteSport);
        }else{
            matchingAds = advertisementRepository.findAll();
        }
        Random random = new Random();
        int index = random.nextInt(matchingAds.size());
        return matchingAds.get(index);
    }
}
