package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a WHERE a.id = ?1")
    public Advertisement findByAdID(long adId);

}