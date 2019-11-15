package ie.curiositysoftware.jobengine.settings;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class JobSettingParser {
    public JobSettingParser()
    {

    }

    public JobSetting parseSettings(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new File(xmlPath));

        JobSetting jobSetting = new JobSetting();

        // Time
        NodeList maxTime = doc.getElementsByTagName("maximumtime");
        jobSetting.setMaximumtimeField(Long.parseLong(maxTime.item(0).getTextContent()));

        // Url
        NodeList url = doc.getElementsByTagName("url");
        jobSetting.setUrlField(url.item(0).getTextContent());

        // APIKey
        NodeList apiKey = doc.getElementsByTagName("apikey");
        jobSetting.setApikeyField(apiKey.item(0).getTextContent());

        // Machine Key
        NodeList machineKey = doc.getElementsByTagName("machinekey");
        jobSetting.setMachinekeyField(machineKey.item(0).getTextContent());

        // Server profile id
        NodeList serverProfileId = doc.getElementsByTagName("severprofileid");
        jobSetting.setSeverprofileidField(Long.parseLong(serverProfileId.item(0).getTextContent()));

        NodeList testsuiteId = doc.getElementsByTagName("testsuiteid");
        jobSetting.setTestsuiteidField(Long.parseLong(testsuiteId.item(0).getTextContent()));

        // automation type
        NodeList automationType = doc.getElementsByTagName("automationtype");
        jobSetting.setAutomationtypeField(automationType.item(0).getTextContent());

        // parameters
        NodeList parameters = doc.getElementsByTagName("parameter");
        for (int i = 0; i < parameters.getLength(); i++) {
            Node paramNode = parameters.item(i);

            JobSettingParameter jobSettingParameter = new JobSettingParameter();
            System.out.println(paramNode.getAttributes().getNamedItem("name").getNodeValue());
            jobSettingParameter.setNameField(paramNode.getAttributes().getNamedItem("name").getNodeValue());
            jobSettingParameter.setTypeField(paramNode.getAttributes().getNamedItem("type").getNodeValue());
            jobSettingParameter.setValueField(paramNode.getTextContent());

            jobSetting.getParametersField().add(jobSettingParameter);
        }

        return jobSetting;
    }
}
