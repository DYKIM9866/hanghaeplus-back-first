package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    private PointService pointService;

    @BeforeEach
    void setUp(){
        this.pointService = new PointService(
                new UserPointTable(),
                new PointHistoryTable());
    }

    /**
     * ID가 존재 할 경우와 존재하지 않을 경우 나눠서 작성했었는데 selectById가 ID를 생성하기에 고려X
     *
     * @throws InterruptedException
     */
    @Test
    void 포인트_충전() throws InterruptedException {
        //when
        pointService.charge(1L,1L);
        UserPoint afterUserPoint = pointService.selectPointById(1L);
        //then
        assertEquals(1L,afterUserPoint.point());
        //when
        pointService.charge(1L,3L);
        UserPoint afterUserPoint2 = pointService.selectPointById(1L);
        //then
        assertEquals(4L,afterUserPoint2.point());
        assertThat(afterUserPoint2.point()).isEqualTo(4L);
    }

    /**
     * ID가 존재 할 경우와 존재하지 않을 경우 나눠서 작성했었는데 selectById가 ID를 생성하기에 고려X
     * 포인트가 부족할 경우 PointThrow발생
     * @throws InterruptedException
     */
    @Test
    void 포인트가_부족할_때() throws InterruptedException {
        assertThrows(PointThrows.class,()->{
            pointService.usePoint(1L,2L);
        });
    }

    /**
     * 포인트가 부족하지 않을 경우 포인트 차감 후 남은 포인트가 예상값과 같은지 확인
     * @throws InterruptedException
     */
    @Test
    void 포인트가_부족하지_않을_때() throws InterruptedException {
        //given
        pointService.insertUser(1L,3L);
        pointService.insertUser(2L,10L);
        //when
        UserPoint userPoint = pointService.usePoint(1L,2L);
        UserPoint userPoint2 = pointService.usePoint(2L,6L);
        //then
        assertEquals(1L,userPoint.point());
        assertEquals(4L,userPoint2.point());
    }

    /**
     * ID가 존재하지 않고 조회했을 때 Insert 후 반환
     * @throws InterruptedException
     */
    @Test
    void Id가_없을_때() throws InterruptedException {

        UserPoint userPoint = pointService.selectPointById(1l);
        assertEquals(0L,userPoint.point());
    }

    /**
     * ID가 존재할 경우 존재하는 ID의 포인트를 반환
     * @throws InterruptedException
     */
    @Test
    void Id가_있을_때() throws InterruptedException {
        pointService.insertUser(2L,3L);
        UserPoint userPoint = pointService.selectPointById(2L);
        assertEquals(3L, userPoint.point());
    }

    @Test
    void 포인트_내역_조회() throws InterruptedException {
        pointService.charge(1L,2L);
        pointService.usePoint(1L,1L);
        List<PointHistory> list = pointService.pointHistories(1L);
        assertEquals(TransactionType.CHARGE,list.get(0).type());
        assertEquals(TransactionType.USE,list.get(1).type());
    }
}
