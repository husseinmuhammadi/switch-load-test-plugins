package ir.javadev.jmeter.plugins.jpos;

import org.apache.jmeter.samplers.SampleResult;

public class ATMSamplerResult extends SampleResult {

    private String luno;
    private String track2;
    private String cardPin;
    private String errorMessage;

    @Override
    public String getSamplerData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Luno: ").append(luno).append("\nTrack2: ").append(track2).append("\nCard Pin: ").append(cardPin).append((errorMessage != null ? "\nError Msg: " + errorMessage : ""));
        if (super.getSamplerData() != null) {
            stringBuilder.append(super.getSamplerData());
        }
        return stringBuilder.toString();
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
