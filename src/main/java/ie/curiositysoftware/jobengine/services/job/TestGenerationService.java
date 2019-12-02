package ie.curiositysoftware.jobengine.services.job;

import ie.curiositysoftware.jobengine.dto.job.Job;
import ie.curiositysoftware.jobengine.dto.job.JobType;
import ie.curiositysoftware.jobengine.dto.job.TestCoverageEnum;
import ie.curiositysoftware.jobengine.dto.job.settings.RunResultAnalysisJobSettings;
import ie.curiositysoftware.jobengine.dto.job.settings.TestGenerationJobSettings;
import ie.curiositysoftware.jobengine.services.ConnectionProfile;
import ie.curiositysoftware.utils.RestService;

public class TestGenerationService extends RestService {

    private final JobSubmissionService jobService;

    public TestGenerationService(ConnectionProfile connectionProfile) {
        super(connectionProfile);
        jobService = new JobSubmissionService(connectionProfile);
    }

    public Long startAnalysisAndGenerationJob(Long profileId) {
        return startAnalysisAndGenerationJob(profileId, true);
    }

    public Long startAnalysisAndGenerationJob(Long profileId, Boolean includeOldTests) {
        return startAnalysisAndGenerationJob(profileId, includeOldTests, null);
    }

    public Long startAnalysisAndGenerationJob(Long profileId, Boolean includeOldTests, String newProfileName) {
        return startAnalysisAndGenerationJob(profileId, includeOldTests, newProfileName, TestCoverageEnum.Exhaustive);
    }

    public Long startAnalysisAndGenerationJob(Long profileId, Boolean includeOldTests, String newProfileName, TestCoverageEnum targetCoverage) {
        Job job = new Job();
        job.setJobType(JobType.RunResultAnalysisAndTestGenerationJob);
        job.setRunResultAnalysisJobSettings(new RunResultAnalysisJobSettings());
        job.getRunResultAnalysisJobSettings().setProfileId(profileId);
        job.getRunResultAnalysisJobSettings().setIncludeOldTests(includeOldTests);
        job.getRunResultAnalysisJobSettings().setNewProfileName(newProfileName);
        job.getRunResultAnalysisJobSettings().setTargetCoverage(targetCoverage);

        Job saved = jobService.addJob(job);
        return saved == null ? null : saved.getId();
    }

    public Long startGenerationJob(Long profileId) {
        Job job = new Job();
        job.setJobType(JobType.TestGenerationJob);
        job.setGenerationJobSettings(new TestGenerationJobSettings());
        job.getGenerationJobSettings().setConfigurationId(profileId);

        Job saved = jobService.addJob(job);
        return saved == null ? null : saved.getId();
    }

    @Override
    public String getErrorMessage() {
        return jobService.getErrorMessage();
    }
}
