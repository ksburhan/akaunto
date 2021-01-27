package de.othr.sw.ksburhan.akaunto.beans.interfaces;

import de.othr.sw.ksburhan.akaunto.entity.Advertisement;

import java.util.List;

public interface AdScoreIF {
    public float calculateAverageClicks(List<Advertisement> ads, int adsSum);
}