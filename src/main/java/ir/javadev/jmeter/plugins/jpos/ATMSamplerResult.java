package ir.javadev.jmeter.plugins.jpos;

import org.apache.jmeter.samplers.SampleResult;

public class ATMSamplerResult extends SampleResult {

    private String luno;
    private String track2;
    private String cardPin;

    @Override
    public String getSamplerData() {
        return super.getSamplerData();
    }

    @Override
    public String getRequestHeaders() {
        return "Luno: " + luno + "\nTrack2: " + track2 + "\nCard Pin: " + cardPin;
    }

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }
}
