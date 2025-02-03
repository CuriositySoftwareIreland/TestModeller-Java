package ie.curiositysoftware.gptprompt.services;

import ie.curiositysoftware.gptprompt.dto.ChatGPTModelQuery;
import ie.curiositysoftware.gptprompt.dto.GenerativeAIPromptTemplateType;
import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobState;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.ResourceIdentifier;
import ie.curiositysoftware.jobengine.services.job.JobResultService;
import ie.curiositysoftware.jobengine.services.job.JobSubmissionService;
import ie.curiositysoftware.pageobjects.dto.PageObjectHistoryEntity;
import ie.curiositysoftware.utils.ServiceBase;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;

public class GPTPromptService  extends ServiceBase {
    private ConnectionProfile m_ConnectionProfile;

    private String m_ErrorMessage;

    public GPTPromptService(ConnectionProfile connectionProfile)
    {
        this.m_ConnectionProfile = connectionProfile;

        this.m_ErrorMessage = "";
    }

    public String GetErrorMessage()
    {
        return this.m_ErrorMessage;
    }

    public String RetrieveElementIdentifier(String dom, String identifier)
    {
        // 1) Create prompt
        ResourceIdentifier res = startGptPrompt(dom, identifier);

        // 2) Wait for job
        Long jobId = res.getId();
        // Wait for job to finish and complete
        long startTime = System.currentTimeMillis();

        // Two minutes maximum
        long twoMinutes = 120000;
        JobSubmissionService jobSubmission = new JobSubmissionService(m_ConnectionProfile);
        while (true)
        {
            long ellapsed = System.currentTimeMillis() - startTime;

            if (ellapsed > twoMinutes) {
                m_ErrorMessage = "Maximum time elapsed";

                return m_ErrorMessage;
            }

            Job savedJob = jobSubmission.getJob(jobId);

            if (savedJob == null)
                break;

            if (savedJob.getJobState().equals(JobState.Complete)){
                System.out.println("Executing job - State: " + savedJob.getJobState() + " - Message: " + savedJob.getProgressMessage());

                JobResultService jobResultService = new JobResultService(m_ConnectionProfile);
                String jobres = jobResultService.getResult(savedJob.getId()).getResultValue();
                if (jobres != null && jobres.equals("Null"))
                    return null;

                return jobres;

            } else if (savedJob.getJobState().equals(JobState.Error)) {
                m_ErrorMessage = "Error executing job " + savedJob.getProgressMessage();

                return null;
            }

            System.out.println("Executing job - State: " + savedJob.getJobState() + " - Message: " + savedJob.getProgressMessage());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    private ResourceIdentifier startGptPrompt(String html, String elementIdentifier)
    {
        ChatGPTModelQuery modelQuery = new ChatGPTModelQuery();
        modelQuery.setHtml(html);
        modelQuery.setElementDescription(elementIdentifier);

        try {
            HttpResponse<ResourceIdentifier> jsonResponse = Unirest.post(createURLs(m_ConnectionProfile.getAPIUrl(), "api/apikey/", this.m_ConnectionProfile.getAPIKey(), "/project/release/model/import/gpt-model/template/type/", GenerativeAIPromptTemplateType.ExtractAutomationIdentifier.toString()))
                    .header("accept", "application/json")
                    .header("Content-Type","application/json")
                    .body(modelQuery)
                    .asObject(ResourceIdentifier.class);


            if (jsonResponse.getStatus() != 200) {
                m_ErrorMessage = jsonResponse.getStatusText();

                return null;
            }

            return jsonResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();

            m_ErrorMessage = e.getMessage();

            return null;
        }
    }

}
