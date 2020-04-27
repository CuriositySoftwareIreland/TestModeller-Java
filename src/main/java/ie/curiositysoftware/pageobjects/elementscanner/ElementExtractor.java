package ie.curiositysoftware.pageobjects.elementscanner;

import ie.curiositysoftware.pageobjects.dto.PageObjectParameterEntity;
import ie.curiositysoftware.pageobjects.dto.VipAutomationSelectorEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementExtractor {
    public ElementExtractor()
    {

    }

    public static boolean updateParameter(PageObjectParameterEntity parameter, WebElement element, WebDriver driver)
    {
        // If the identifer no longer works -> We need to update it in the framework
        // Also need to assign a confidence value which gets tracked upwards
        String newIdentifier = null;
        if (parameter.getParamType().equals(VipAutomationSelectorEnum.ClassName)) {
            newIdentifier = element.getAttribute("class");

        } else if (parameter.getParamType().equals(VipAutomationSelectorEnum.Id)) {
            newIdentifier = element.getAttribute("id");

        } else if (parameter.getParamType().equals(VipAutomationSelectorEnum.LinkText)) {
            newIdentifier = element.getText();

        } else if (parameter.getParamType().equals(VipAutomationSelectorEnum.Name)) {
            newIdentifier = element.getAttribute("name");

        } else if (parameter.getParamType().equals(VipAutomationSelectorEnum.TagName)) {
            newIdentifier = element.getTagName();

        } else if (parameter.getParamType().equals(VipAutomationSelectorEnum.XPath)) {
            newIdentifier = GetElementXPath(element, driver);

        } else {
            System.out.println("Unsupported identifier reusing old value - " + parameter.getParamType());

            newIdentifier = parameter.getParamValue();
        }

        if (newIdentifier != null && newIdentifier.length() > 0) {
            parameter.setParamValue(newIdentifier);


            By identifer = GetElementIdentifierForParameter(parameter);
            if (identifer == null) {
                parameter.setConfidence(0);

                return true;
            }

            try {
                List<WebElement> elem = driver.findElements(identifer);

                if (elem.size() == 0) {
                    parameter.setConfidence(0);
                } else {
                    parameter.setConfidence(1.0f / elem.size());
                }
            } catch (Exception e) {
                parameter.setConfidence(0);
            }

            return true;
        } else {
            parameter.setConfidence(0);

            return false;
        }
    }

    public static By GetElementIdentifierForParameter(PageObjectParameterEntity parameterEntity)
    {
        if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.ClassName)) {
            return By.className(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.CssSelector)) {
            return By.cssSelector(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.Id)) {
            return By.id(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.LinkText)) {
            return By.linkText(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.Name)) {
            return By.name(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.PartialLinkText)) {
            return By.partialLinkText(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.TagName)) {
            return By.tagName(parameterEntity.getParamValue());

        } else if (parameterEntity.getParamType().equals(VipAutomationSelectorEnum.XPath)) {
            return By.xpath(parameterEntity.getParamValue());

        } else {
            return null;
        }
    }

    public static String GetElementXPath(WebElement element, WebDriver driver)
    {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "getXPath=function(node)" +
                        "{" +
                        "if (node.id !== '')" +
                        "{" +
                        "return '//' + node.tagName.toLowerCase() + '[@id=\"' + node.id + '\"]'" +
                        "}" +

                        "if (node === document.body)" +
                        "{" +
                        "return node.tagName.toLowerCase()" +
                        "}" +

                        "var nodeCount = 0;" +
                        "var childNodes = node.parentNode.childNodes;" +

                        "for (var i=0; i<childNodes.length; i++)" +
                        "{" +
                        "var currentNode = childNodes[i];" +

                        "if (currentNode === node)" +
                        "{" +
                        "return getXPath(node.parentNode) + '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) + ']'" +
                "}" +

                "if (currentNode.nodeType === 1 && " +
                    "currentNode.tagName.toLowerCase() === node.tagName.toLowerCase())" +
                "{" +
                    "nodeCount++" +
                "}" +
                "}" +
                "};" +

                "return getXPath(arguments[0]);", element);
    }
}
