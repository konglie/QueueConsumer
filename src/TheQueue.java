import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * Author: konglie [ at ] outlook [ dot ] com
 * Date: 6/24/2014
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class TheQueue {
	private int WORKER_LIMIT = 10;

	private int counter = 1;
	private LinkedBlockingQueue<Job> queue = new LinkedBlockingQueue<Job>();
	private List<JobWorker> workers;

	public TheQueue(){
		workers = new ArrayList<JobWorker>();
	}

	public void addWorker(){
		if(workers.size() >= WORKER_LIMIT){
			JOptionPane.showMessageDialog(Main.getGui(), "Telah mencapai batas / is on its limit!");
			return;
		}

		JobWorker w = new JobWorker(counter++, this);
		workers.add(w);
		w.start();
		updateWorkerNum();
	}

	public void decreaseWorker(){
		if(workers.isEmpty()){
			JOptionPane.showMessageDialog(Main.getGui(), "habis / No more Workers");
			return;
		}

		JobWorker w = workers.get(workers.size() - 1);
		w.stopWorking();
		workers.remove(w);

		updateWorkerNum();
	}

	public void updateWorkerNum(){
		Main.getGui().setNumWorker(workers.size());
	}

	public void enqueue(Job job){
		queue.offer(job);
		Main.getGui().addRow(new String[]{
				job.name + "", job.jobName(), job.getStatus()
		});
	}

	public Job dequeue(){
		try {
			return queue.isEmpty() ? null : queue.take();
		} catch (Exception e){
			return null;
		}
	}
}
