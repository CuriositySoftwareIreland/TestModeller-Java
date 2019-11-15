package ie.curiositysoftware.jobengine.services.job;

import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobType;
import ie.curiositysoftware.jobengine.dto.job.settings.TestGenerationJobSettings;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.jobengine.services.RestService;

public class TestGenerationService extends RestService {

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

        Job job = new Job();
        job.setJobType(type);
        job.setGenerationJobSettings(new TestGenerationJobSettings());
        job.getGenerationJobSettings().setConfigurationId(profileId);

        Job saved = jobService.addJob(job);
        if(saved != null)
            return saved.getId();
        else
            return null;
    }
}
