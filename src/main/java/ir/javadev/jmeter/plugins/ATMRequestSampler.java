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
    public SampleResult sample(Entry entry) {
        JMeterVariables variables = JMeterContextService.getContext().getVariables();
        ATMSamplerResult atmSamplerResult = new ATMSamplerResult();
        setProperty(TestElement.NAME, "ATM Test");
        atmSamplerResult.setSampleLabel(getName());
        Startup startup = new Startup();
        try {
            System.out.println("#############33");
            atmSamplerResult = startup.start(variables.get("luno"), variables.get("track2"), variables.get("card_pin"));
        } catch (Throwable e) {
            atmSamplerResult.setSuccessful(false);
            atmSamplerResult.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }
        return atmSamplerResult;
    }


}
