package de.othr.sw.ksburhan.akaunto.beans.implementations;

import de.othr.sw.ksburhan.akaunto.beans.interfaces.AdScoreIF;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;

import java.util.List;

public class AdScoreSimple implements AdScoreIF {

    public float calculateAverageClicks(List<Advertisement> ads, int adsSum){
            float averageScore = 0;
            if(ads.size()<=0) {
                averageScore = 0;
                return averageScore;
            }
            return (float) adsSum / (float)ads.size();
        }
    }
