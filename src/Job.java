/**
 * Created with IntelliJ IDEA.
 * Author: konglie [ at ] outlook [ dot ] com
 * Date: 6/24/2014
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Job {
	public final int name;
	private String status;

	// this is the time needed to finish one job
	// of course this is dummy as it is used for sleeping
	int maxTime = 450;
	int minTime = 200;
	int dummyWorkingTime = (int)(Math.random() * (maxTime - minTime)) + minTime;

	public Job(int n){
		name = n;
		status = "Queued";
	}

	private void refreshGuiTable(){
		Main.getGui().refreshJobList(Job.this);
	}

	public String jobName(){
		return "Job" + name;
	}

	public String getStatus(){
		return status;
	}

	public void done(JobWorker w){
		status = "Done by " + w.getName() + " on " + dummyWorkingTime + "ms";
		refreshGuiTable();
	}

	public int onProgress(JobWorker w){
		status = "On Progress by " + w.getName();
		refreshGuiTable();

		Main.safeSleep(dummyWorkingTime);

		return dummyWorkingTime;
	}
}
