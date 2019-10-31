import ie.curiositysoftware.JobEngine.Entities.File.UploadFileResponse;
import ie.curiositysoftware.JobEngine.Entities.Job.*;
import ie.curiositysoftware.JobEngine.Services.ConnectionProfile;
import ie.curiositysoftware.JobEngine.Services.File.FileService;
import ie.curiositysoftware.JobEngine.Settings.JobSetting;
import ie.curiositysoftware.JobEngine.Settings.JobSettingParameter;
import ie.curiositysoftware.JobEngine.Settings.JobSettingParser;
import ie.curiositysoftware.JobEngine.Utils.JobExecutor;
import ie.curiositysoftware.JobEngine.Utils.UnirestHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        UnirestHelper.InitUnirestMapper();

        // 1 - Parse Configuration File
        String xmlPath = args[0];
        JobSetting result = new JobSettingParser().ParseSettings(xmlPath);

        if (result == null) {
            System.out.println("Error parsing configuration");

            return;
        }

        // 2 - Construct connection profile
        ConnectionProfile p = new ConnectionProfile();
        p.setAPIKey(result.getApikeyField());
        p.setAPIUrl(result.getUrlField());

        // 3 - Construct job entity
        JobEntity job = new JobEntity();
        job.setJobType(JobType.VIPAutoExecutionJob);
        job.setVipAutomationJobSettings(new VIPAutomationExecutionJobEntity());
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
                UploadFileResponse fileResponse = fs.AddFile(new File(curParam.getValueField()));
                if (fileResponse == null) {
                    System.out.println("Error " + fs.GetErrorMessage());
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
        JobExecutor jobExecutor = new JobExecutor();
        if (!jobExecutor.ExecuteJob(job, p, args[1], result.getMaximumtimeField())) {
            System.out.println(jobExecutor.getErrorMessage());

            return;
        }

        System.out.println("Execution complete!");
    }
}
