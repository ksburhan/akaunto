package de.othr.sw.ksburhan.akaunto.beans.implementations;

import de.othr.sw.ksburhan.akaunto.beans.interfaces.AdScoreIF;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;

import java.util.List;

public class AdScorePro implements AdScoreIF {

    public float calculateAverageClicks(List<Advertisement> ads, int adsSum){
        float averageScore = 0;
        if(ads.size()<=0) {
            averageScore = 0;
            return averageScore;
        }
        averageScore = (float) adsSum / (float)ads.size();
        averageScore = roundAvgScore(averageScore);
        return averageScore;
    }

    private float roundAvgScore(float avgScore){
        return Math.round(avgScore * 10f) / 10f;
    }
}
