package queue.up;

// Teilaufgabe g)
public class CallTask implements Runnable {

	public QueueDisplay queueDisplay;
	
	public CallTask(QueueDisplay queueDisplay){
		this.queueDisplay = queueDisplay;
	}
	
	@Override
	public void run(){
		while(!Thread.interrupted()){
			queueDisplay.updateCall();
			
			try{
				Thread.sleep(5000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
