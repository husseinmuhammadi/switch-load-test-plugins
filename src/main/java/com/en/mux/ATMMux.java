package com.en.mux;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.QMUX;

public class ATMMux extends QMUX {
    @Override
    public String getKey(ISOMsg m) throws ISOException {
        AtmNdc ndcMsg = (AtmNdc) m;
        AtmPackager ndcPackager = ndcMsg.getFSDMsg();
        String key = super.getKey(m);
        return key;
    }
}
