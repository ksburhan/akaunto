package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a WHERE a.id = ?1")
    public Advertisement findByAdID(long adId);

    @Query("SELECT a FROM Advertisement a WHERE LOWER(a.description) LIKE ?1")
    public List<Advertisement> findMatchingAd(String description);
}