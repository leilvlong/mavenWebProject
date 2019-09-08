package com.pingyougou.orderinterface;

import com.pingyougou.pojos.TbOrder;
import com.pingyougou.pojos.TbPayLog;

public interface OrderService {
    void add(TbOrder order);

    TbPayLog getPayLogByUserId(String userId);

    void updateStatus(String out_trade_no, String transaction_id);
}
