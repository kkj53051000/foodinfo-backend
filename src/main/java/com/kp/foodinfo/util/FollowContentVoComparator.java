package com.kp.foodinfo.util;

import com.kp.foodinfo.vo.FollowContentVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class FollowContentVoComparator implements Comparator<FollowContentVo> {

    @Override
    public int compare(FollowContentVo followContentVo1, FollowContentVo followContentVo2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date day1 = null;
        Date day2 = null;

        try {
            day1 = dateFormat.parse(String.valueOf(followContentVo1.getEventStartDate()));
            day2 = dateFormat.parse(String.valueOf(followContentVo2.getEventStartDate()));
        }catch (ParseException e) {
            e.printStackTrace();
        }

        int compare = day1.compareTo(day2);

        if(compare > 0){
            return 1;
        } else if(compare < 0){
            return -1;
        }
        return 0;
    }
}
