package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.SiktamEventWinner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SiktamEventWinnerListVo {

    List<SiktamEventWinnerVo> items = null;

    public SiktamEventWinnerListVo(List<SiktamEventWinner> siktamEventWinners) {
        this.items = siktamEventWinners.stream()
                .map(siktamEventWinner -> new SiktamEventWinnerVo(siktamEventWinner))
                .collect(Collectors.toList());
    }

    @Data
    class SiktamEventWinnerVo {
        private long id;
        private String email;
        private String phoneNumber;

        public SiktamEventWinnerVo(SiktamEventWinner siktamEventWinner) {
            this.id = siktamEventWinner.getId();
            this.email = siktamEventWinner.getUser().getEmail();
            this.phoneNumber = siktamEventWinner.getPhoneNumber();
        }
    }

}
