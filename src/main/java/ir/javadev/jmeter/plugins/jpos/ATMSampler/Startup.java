package ir.javadev.jmeter.plugins.jpos.ATMSampler;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import com.en.pinhsm.PinUtil;
import ir.javadev.jmeter.plugins.jpos.ATMSamplerResult;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Startup {

    public void stop() {
        System.out.println("Stop q2");
        Q2.getQ2().stop();
    }

    static {
        try {
            System.out.println("Static$$$$$$$$$$");
            Thread.sleep(5000);
            Q2.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ATMSamplerResult start(String luno, String track2, String cardPin) throws Exception {

        ATMSamplerResult atmSamplerResult = new ATMSamplerResult();
        atmSamplerResult.setLuno(luno);
        atmSamplerResult.setTrack2(track2);
        atmSamplerResult.setCardPin(cardPin);

//        Q2.main(args);
        MUX mux = QMUX.getMUX("atm-mux");
        if (mux.isConnected())
            System.out.println("mux is connected");
        else
            System.out.println("mux is not connected");

        long timeout = 10000;

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("cash-withdrawal.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = reader.readLine();

        AtmPackager atmPack = new AtmPackager("cfg/atm/ndc-", "request");
        AtmNdc ndcMsgReq = new AtmNdc(atmPack);
        ndcMsgReq.unpack(line.getBytes());

        atmPack.set("luno", luno);
        atmPack.set("track2", track2);
        String track2Data = PinUtil.getTrack2DataWithoutSentimental(track2);
        String cardNo = track2Data.substring(0, track2Data.indexOf("="));
        PinUtil pinUtil = new PinUtil();
        String pinblock = pinUtil.createAtmPinBlock(cardNo, cardPin);
        atmPack.set("pin-buffer-A", pinblock);

        atmSamplerResult.sampleStart();
        AtmNdc ndcMsgRes = (AtmNdc) mux.request(ndcMsgReq, timeout);
        atmSamplerResult.sampleEnd();

        if (ndcMsgRes != null) {
            AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();
            System.out.println("Printer Data = " + atmpackRes.get("printer-data", "000"));
        }

        return atmSamplerResult;

    }
}
