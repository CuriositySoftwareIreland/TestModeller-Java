package ie.curiositysoftware.pageobjects.Identifier;

import org.openqa.selenium.By;

public class ObjectIdentifier {
    private By identifier;

    private double confidence;

    public ObjectIdentifier(By identifier, double confidence)
    {
        this.confidence = confidence;

        this.identifier = identifier;
    }

    public By getIdentifier() {
        return identifier;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void setIdentifier(By identifier) {
        this.identifier = identifier;
    }
}
