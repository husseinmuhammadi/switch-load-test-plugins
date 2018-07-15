package ir.javadev.jmeter.plugins;

import ir.javadev.jmeter.plugins.jpos.ATMSampler.Startup;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

public class ATMRequestSampler extends AbstractSampler {
    public SampleResult sample(Entry entry) {
        JMeterVariables variables = JMeterContextService.getContext().getVariables();
        SampleResult sampleResult = new SampleResult();
        sampleResult.setSampleLabel(getName());
        Startup startup = new Startup();
        try {
            System.out.println("#############33");
            System.out.println(System.getProperty("user.dir"));
            startup.start(variables.get("luno"), variables.get("track2"), variables.get("card_pin"));
            sampleResult.setSuccessful(true);
        } catch (Exception e) {
            sampleResult.setSuccessful(true);
            e.printStackTrace();
        }
        return sampleResult;
    }


}
