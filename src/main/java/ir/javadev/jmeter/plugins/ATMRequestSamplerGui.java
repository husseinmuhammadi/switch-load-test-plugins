package ir.javadev.jmeter.plugins;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.tcp.config.gui.TCPConfigGui;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

public class ATMRequestSamplerGui extends AbstractSamplerGui {

    private TCPConfigGui tcpConfigGui;

    public ATMRequestSamplerGui() {
        init();
    }

    @Override
    public TestElement createTestElement() {
        ATMRequestSampler atmRequestSampler = new ATMRequestSampler();
        modifyTestElement(atmRequestSampler);
        return atmRequestSampler;
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        testElement.clear();
        testElement.addTestElement(tcpConfigGui.createTestElement());
        this.configureTestElement(testElement);
    }

    @Override
    public void clearGui() {
        super.clearGui();
        tcpConfigGui.clearGui();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);

        VerticalPanel mainPanel = new VerticalPanel();

        // Ajout de la section jPOS
        mainPanel.add(createJPOSPanel());
        tcpConfigGui = new TCPConfigGui(false);

        // On enleve la partie de définition du texte
//        JPanel mainpanel2 = (JPanel) tcpDefaultPanel.getComponent(tcpDefaultPanel.getComponentCount()-1);
//        Box box = (Box) mainpanel2.getComponent(mainpanel2.getComponentCount()-1);
//        JPanel reqDataPanel = (JPanel) box.getComponent(10);
//        reqDataPanel.setVisible(false);

        mainPanel.add(tcpConfigGui);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createJPOSPanel() {
        return new VerticalPanel();
    }

    public String getLabelResource() {
        return this.getClass().getSimpleName();
    }
}
