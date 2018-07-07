package ir.javadev.jmeter.plugins.jpos.ATMSampler;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import com.en.pinhsm.PinUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Startup {

    public void stop() {
        System.out.println("Stop q2");
        Q2.getQ2().stop();
    }

    public static void main(String[] args) throws Exception {
        new Startup().start(null);
    }

    public void start(String[] args) throws Exception {
        Q2.main(args);

        Thread.sleep(10000);

        MUX mux = QMUX.getMUX("atm-mux");
        if (mux.isConnected())
            System.out.println("mux is connected");
        else
            System.out.println("mux is not connected");

        long timeout = 10000;

        FileReader fileReader = new FileReader(new File(Startup.class.getClassLoader().getResource("cash-withdrawal.txt").toURI()));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        AtmPackager atmPack = new AtmPackager("cfg/atm/ndc-", "request");
        AtmNdc ndcMsgReq = new AtmNdc(atmPack);
        ndcMsgReq.unpack(line.getBytes());
        String pin = "0852";

        PinUtil pinUtil = new PinUtil();
        String track2Data = atmPack.get("track2");
        track2Data = PinUtil.getTrack2DataWithoutSentimental(track2Data);
        String cardNo = track2Data.substring(0, track2Data.indexOf("="));
        String pinblock = pinUtil.createAtmPinBlock(cardNo, pin);
        atmPack.set("pin-buffer-A", pinblock);

        AtmNdc ndcMsgRes = (AtmNdc) mux.request(ndcMsgReq, timeout);

        if (ndcMsgRes != null) {
            AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();
            System.out.println("Printer Data = " + atmpackRes.get("printer-data", "000"));
        }

        stop();

    }
}
