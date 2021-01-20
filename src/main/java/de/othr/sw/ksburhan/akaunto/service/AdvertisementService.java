package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import de.othr.sw.ksburhan.akaunto.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // TODO: Get good according to userdata
        return findByAdID(2L);
    }
}
