package ir.javadev.jmeter.plugins;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.jpos.q2.Q2;

public class Q2RunnerSampler extends AbstractSampler {

    @Override
    public SampleResult sample(Entry e) {
        try {
            Q2.main(null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return null;
    }


}
