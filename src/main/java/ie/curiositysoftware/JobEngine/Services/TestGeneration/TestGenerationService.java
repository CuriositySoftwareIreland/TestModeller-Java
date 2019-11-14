package ie.curiositysoftware.JobEngine.Services.TestGeneration;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import ie.curiositysoftware.JobEngine.Entities.Job.JobEntity;
import ie.curiositysoftware.JobEngine.Entities.Job.JobType;
import ie.curiositysoftware.JobEngine.Entities.Job.TestGenerationJobEntity;
import ie.curiositysoftware.JobEngine.Services.ConnectionProfile;
import ie.curiositysoftware.JobEngine.Services.Job.JobSubmissionService;
import ie.curiositysoftware.JobEngine.Utils.JobExecutor;
import ie.curiositysoftware.JobEngine.Utils.RESTService;

public class TestGenerationService extends RESTService {

    private final JobSubmissionService jobService;

    public TestGenerationService(ConnectionProfile connectionProfile) {
        super(connectionProfile);
        jobService = new JobSubmissionService(connectionProfile);
    }

    public Long startAnalysisAndGenerationJob(Long profileId) {
        return startJob(profileId, JobType.RunResultAnalysisAndTestGenerationJob);
    }

    public Long startGenerationJob(Long profileId) {
        return startJob(profileId, JobType.TestGenerationJob);
    }

    private Long startJob(Long profileId, JobType type) {

        JobEntity job = new JobEntity();
        job.setJobType(type);
        job.setGenerationJobSettings(new TestGenerationJobEntity());
        job.getGenerationJobSettings().setConfigurationId(profileId);

        JobEntity saved = jobService.AddJob(job);
        if(saved != null)
            return saved.getId();
        else
            return null;
    }
}
