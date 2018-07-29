package ir.javadev.jmeter.plugins;

import ir.javadev.jmeter.plugins.jpos.ATMSampler.Startup;
import ir.javadev.jmeter.plugins.jpos.ATMSamplerResult;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

public class ATMRequestSampler extends AbstractSampler {

    private static Startup startup = new Startup();

    public SampleResult sample(Entry entry) {
        JMeterVariables variables = JMeterContextService.getContext().getVariables();
        ATMSamplerResult atmSamplerResult = new ATMSamplerResult();
        atmSamplerResult.setSampleLabel("NullResult");

        String luno = variables.get("luno");
        String track2 = variables.get("track2");
        String cardPin = variables.get("card_pin");

        atmSamplerResult.setLuno(luno);
        atmSamplerResult.setTrack2(track2);
        atmSamplerResult.setCardPin(cardPin);

        try {
            atmSamplerResult = startup.start(luno, track2, cardPin);
        } catch (Throwable e) {
            atmSamplerResult.setSuccessful(false);
            atmSamplerResult.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }
        return atmSamplerResult;
    }


}
