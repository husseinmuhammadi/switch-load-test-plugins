package com.en.listener;

import org.jdom.JDOMException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class AtmNdc extends ISOMsg implements Cloneable {

    private static final long serialVersionUID = 1L;

    private AtmPackager fsd;

    public AtmNdc() {
    }

    public AtmNdc(AtmPackager fsd) {
        this.fsd = fsd;
    }

    public String getMTI() {
        // return getString(0);
        return "0200";
    }

    public byte[] pack() throws ISOException {
        try {
            String packedValue = fsd.pack();
            System.out.println(packedValue);
            UTF2IranSystem uTF2IranSystem = new UTF2IranSystem();
            return uTF2IranSystem.convert(packedValue);
        } catch (Exception e) {
            throw new ISOException(e);
        }
    }

    public int unpack(byte[] b) throws ISOException {
        try {
            fsd.unpack(b);
            return b.length;
        } catch (Exception e) {
            throw new ISOException(e);
        }
    }

    public void unpack(InputStream in) throws IOException, ISOException {
        synchronized (this) {
            try {
                fsd.unpack(in);
            } catch (JDOMException e) {
                throw new ISOException(e);
            }
        }
    }

    public AtmPackager getFSDMsg() {
        return fsd;
    }

    public String getString(int fldno) {
        return fsd.get(Integer.toString(fldno));
    }

    public String getString(String fldKey) {
        System.out.println("FLD : " + fldKey);
        return fsd.get(fldKey);
    }

    public Object getValue(String fpath) {
        System.out.println("VAL : " + fpath);
        return fsd.get(fpath);
    }

    public boolean hasField(int fldno) {
        return getString(fldno) != null;
    }

    public void dump(PrintStream p, String indent) {
        if (fsd != null)
            fsd.dump(p, indent);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);  // reserved for future expansion (version id)
        out.writeUTF(fsd.getBasePath());
        out.writeObject(fsd.getMap());
    }

    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        in.readByte();  // ignore version for now
        String basePath = in.readUTF();
        String baseSchema = in.readUTF();
        fsd = new AtmPackager(basePath, baseSchema);
        Map map = (Map) in.readObject();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            fsd.set((String) entry.getKey(), (String) entry.getValue());
        }
    }

    public Object clone() {
        AtmNdc m = (AtmNdc) super.clone();
        m.fsd = (AtmPackager) fsd.clone();
        return m;
    }

    public void setResponseMTI() {
        try {
            super.setResponseMTI();
        } catch (ISOException ignored) {
            ignored.printStackTrace();
        }
    }

    public void set(String name, String value) {
        if (value != null)
            this.fsd.set(name, value);
    }

}

