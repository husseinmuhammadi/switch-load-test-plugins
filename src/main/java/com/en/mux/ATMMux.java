package com.en.mux;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.QMUX;

public class ATMMux extends QMUX {
    @Override
    public String getKey(ISOMsg m) throws ISOException {
        return super.getKey(m);
    }
}
