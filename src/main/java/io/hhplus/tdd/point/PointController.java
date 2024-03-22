package io.hhplus.tdd.point;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/point")
@RestController
public class PointController {

    private PointService pointService;
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPoint point(@PathVariable("id") Long id) throws InterruptedException {
        return pointService.selectPointById(id);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(@PathVariable("id") Long id) {
        return pointService.pointHistories(id);
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(@PathVariable("id") Long id, @RequestBody Map<String,Long> amount) throws InterruptedException {
        return pointService.charge(id,amount.get("amount"));
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPoint use(@PathVariable("id") Long id, @RequestBody Map<String,Long> amount) throws InterruptedException {
        return pointService.usePoint(id,amount.get("amount"));
    }
}
