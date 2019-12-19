/**
 * Created with IntelliJ IDEA.
 * Author: konglie [ at ] outlook [ dot ] com
 * Date: 6/24/2014
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobWorker extends Thread implements Runnable {
	private int id;
	private boolean working;
	private TheQueue queue;
	private Job currentJob;

	public JobWorker(int i, TheQueue q) {
		super();
		setName("Worker" + i);
		id = i;
		working = true;
		queue = q;
	}

	public void stopWorking(){
		working = false;
		Main.getGui().log(getName() + " STOP WORKING");
	}

	public void run(){
		Main.getGui().log(getName() + " START working...");

		int dummyWorkingTime;
		while(working){
			currentJob = queue.dequeue();
			if(currentJob == null){
				Main.safeSleep(75);
			} else {
				Main.getGui().log(getName() + " WORKING " + currentJob.jobName());
				dummyWorkingTime = currentJob.onProgress(this);
				currentJob.done(this);

				Main.getGui().log(getName() + " FINISHED " + currentJob.jobName() + " on " + dummyWorkingTime + "ms");
			}
		}

		Main.getGui().log(getName() + " QUIT the queue");
		queue.updateWorkerNum();
	}
}
