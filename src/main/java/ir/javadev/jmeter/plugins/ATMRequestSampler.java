package ir.javadev.jmeter.plugins;

import ir.javadev.jmeter.plugins.jpos.ATMSampler.Startup;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;

public class ATMRequestSampler extends AbstractSampler {
    public SampleResult sample(Entry entry) {
        SampleResult sampleResult = new SampleResult();
        sampleResult.setSampleLabel(getName());
        Startup startup = new Startup();
        try {
            System.out.println("#############33");
            System.out.println(System.getProperty("user.dir"));
            startup.start(null);
            sampleResult.setSuccessful(true);
        } catch (Exception e) {
            sampleResult.setSuccessful(true);
            e.printStackTrace();
        }
        return sampleResult;
    }


}
