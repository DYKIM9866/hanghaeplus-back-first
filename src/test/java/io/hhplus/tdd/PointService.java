package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    private UserPointTable userPointTable;
    private PointHistoryTable pointHistoryTable;

    public PointService(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    public void insertUser(Long id, Long point) throws InterruptedException {
        userPointTable.insertOrUpdate(id,point);
    }

    public UserPoint selectPointById(long id) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);

        return userPoint;
    }

    public UserPoint charge(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        UserPoint resultUserPoint = userPointTable.insertOrUpdate(userPoint.id(),userPoint.point() + amount);
        pointHistoryTable.insert(id,amount, TransactionType.CHARGE,System.currentTimeMillis());

        return resultUserPoint;
    }

    public UserPoint usePoint(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        if(userPoint.point() < amount){
            throw new PointThrows();
        }
        UserPoint resultUserPoint = userPointTable.insertOrUpdate(userPoint.id(),userPoint.point()-amount);
        pointHistoryTable.insert(id,amount, TransactionType.USE,System.currentTimeMillis());

        return resultUserPoint;
    }

    public List<PointHistory> pointHistories(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
}

