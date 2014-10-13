package queue.up;

public class Call {

	private DeskApp desk;
	private int queueNumber;
	
	public Call(int queueNumber, DeskApp desk){
		this.desk = desk;
		this.queueNumber = queueNumber;
	}
	
	public DeskApp getDesk(){
		return desk;
	}
	
	public int getQueueNumber(){
		return queueNumber;
	}
	
}
