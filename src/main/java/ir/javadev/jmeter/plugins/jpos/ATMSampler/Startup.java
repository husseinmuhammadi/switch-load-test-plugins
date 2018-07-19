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
import java.util.Calendar;
import java.util.Date;

public class Startup {

    public void stop() {
        System.out.println("Stop q2");
        // Q2.getQ2().stop();
    }

    static {
        try {
            Q2.main(null);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ATMSamplerResult start(String luno, String track2, String cardPin) throws Exception {
        ATMSamplerResult atmSamplerResult = new ATMSamplerResult();
        atmSamplerResult.setLuno(luno);
        atmSamplerResult.setTrack2(track2);
        atmSamplerResult.setCardPin(cardPin);

        MUX mux = QMUX.getMUX("atm-mux");
        if (mux.isConnected())
            System.out.println("mux is connected");
        else
            System.out.println("mux is not connected");

        long timeout = 30000;

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

        try {
            Date startTime = new Date();
            atmSamplerResult.setSampleLabel(luno);
            atmSamplerResult.sampleStart();
            AtmNdc ndcMsgRes = (AtmNdc) mux.request(ndcMsgReq, timeout);
            atmSamplerResult.sampleEnd();

            long diff = startTime.getTime() - new Date().getTime();
            System.out.println("---------------------- DIFF: " + diff);


            if (ndcMsgRes != null) {
                atmSamplerResult.setSuccessful(true);
                String result = ndcMsgRes.getFSDMsg().get("printer-data", "000");
                atmSamplerResult.setResponseData(ndcMsgRes.pack());
                System.out.println("Printer Data = " + result);
            } else {
                atmSamplerResult.setSuccessful(false);
                atmSamplerResult.setErrorMessage("NDC message is null");
                System.out.println("++NULL++++NULL++++NULL++++NULL++++NULL++++NULL++++NULL++++NULL++++NULL++++NULL++");
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
            }
        } catch (RuntimeException e) {
            atmSamplerResult.setSuccessful(false);
            atmSamplerResult.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }

        return atmSamplerResult;
    }
}
