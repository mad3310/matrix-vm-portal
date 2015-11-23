package com.letv.portal.service.letvcloud;

import com.letv.portal.dao.letvcloud.BillUserInvoiceMapper;
import com.letv.portal.model.letvcloud.BillUserInvoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenliusong on 2015/7/1.
 */
@Service
public class BillUserInvoiceServiceImpl implements BillUserInvoiceService {

    @Autowired
    private BillUserInvoiceMapper billUserInvoiceMapper;

    @Override
    public void createUserInvoice(BillUserInvoice billUserInvoice) {
        billUserInvoiceMapper.insertUserInvoice(billUserInvoice);
    }
}
