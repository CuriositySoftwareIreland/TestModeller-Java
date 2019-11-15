import ie.curiositysoftware.jobengine.dto.file.UploadFileResponse;
import ie.curiositysoftware.jobengine.dto.job.*;
import ie.curiositysoftware.jobengine.dto.job.settings.VIPAutomationExecutionJobSettings;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.file.FileService;
import ie.curiositysoftware.jobengine.settings.JobSetting;
import ie.curiositysoftware.jobengine.settings.JobSettingParameter;
import ie.curiositysoftware.jobengine.settings.JobSettingParser;
import ie.curiositysoftware.jobengine.utils.JobExecutor;
import ie.curiositysoftware.jobengine.utils.UnirestHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        UnirestHelper.initUnirestMapper();

        // 1 - Parse Configuration File
        String xmlPath = args[0];
        JobSetting result = new JobSettingParser().parseSettings(xmlPath);

        if (result == null) {
            System.out.println("Error parsing configuration");

            return;
        }

        // 2 - Construct connection profile
        ConnectionProfile p = new ConnectionProfile();
        p.setAPIKey(result.getApikeyField());
        p.setAPIUrl(result.getUrlField());

        // 3 - Construct job entity
        Job job = new Job();
        job.setJobType(JobType.VIPAutoExecutionJob);
        job.setVipAutomationJobSettings(new VIPAutomationExecutionJobSettings());
        job.getVipAutomationJobSettings().setMachineKey(result.getMachinekeyField());
        job.getVipAutomationJobSettings().setServerProfileId(result.getSeverprofileidField());
        job.getVipAutomationJobSettings().setAutomationType(result.getAutomationtypeField());

        if (result.getTestsuiteidField() != null) {
            job.getVipAutomationJobSettings().setTestSuiteId(result.getTestsuiteidField());
            job.getVipAutomationJobSettings().setScope(ServerProcessScopeEnum.ModelTestSuite);
        } else {
            job.getVipAutomationJobSettings().setScope(ServerProcessScopeEnum.Global);
        }

        job.getVipAutomationJobSettings().setSharedJobServer(false);
        job.getVipAutomationJobSettings().setAutomationParameters(new ArrayList<AutomationExecutionParameter>());

        int index = 1;
        for(JobSettingParameter curParam : result.getParametersField())
        {
            System.out.println("Creating parameter - " + curParam.getNameField() + " " + curParam.getTypeField() + " " + curParam.getValueField());
            if (curParam.getTypeField().equals("fileupload"))
            {
                // Upload it
                FileService fs = new FileService(p);
                UploadFileResponse fileResponse = fs.addFile(new File(curParam.getValueField()));
                if (fileResponse == null) {
                    System.out.println("Error " + fs.getErrorMessage());
                } else {
                    System.out.println("File " + fileResponse.getDownloadUri());
                }

                AutomationExecutionParameter param = new AutomationExecutionParameter();
                param.setVar(curParam.getNameField());
                param.setValue(fileResponse.getDownloadUri());
                param.setParamIndex(index);
                job.getVipAutomationJobSettings().getAutomationParameters().add(param);
            } else {
                AutomationExecutionParameter param = new AutomationExecutionParameter();
                param.setVar(curParam.getNameField());
                param.setValue(curParam.getValueField());
                param.setParamIndex(index);
                job.getVipAutomationJobSettings().getAutomationParameters().add(param);
            }

            index++;
        }

        // 4 - Submit job
        JobExecutor jobExecutor = new JobExecutor(p);
        if (!jobExecutor.executeJob(job, args[1], result.getMaximumtimeField())) {
            System.out.println(jobExecutor.getErrorMessage());

            return;
        }

        System.out.println("Execution complete!");
    }
}
