package queue.up;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class WaitingQueue {

	private Collection<QueueObserver> observers = new ArrayList<QueueObserver>();
	private LinkedList<Call> calls = new LinkedList<Call>();
	private Queue<Integer> queueNumbers = new LinkedList<Integer>();
	private int queueNumber = 0;
	
	public int getNextQueueNumber(){
		int nextNumber = ++queueNumber;
		
		queueNumbers.add(nextNumber);
		updateObservers();
		
		return nextNumber;
	}
	
	public void addQueueObserver(QueueObserver ob){
		observers.add(ob);
	}
	
	public void updateObservers(){
		for(QueueObserver ob:observers){
			ob.updateView();
		}
	}
	
	public Call getNextCall(DeskApp desk){
		if(!queueNumbers.isEmpty()){
			int queueNumber = queueNumbers.poll();
			Call call = new Call(queueNumber, desk);
			
			calls.add(call);
			updateObservers();
			
			return call;
		}
		
		return null;
	}
	
	public void confirmCall(Call call){
		calls.remove(call);
		updateObservers();
	}
	
	public Call getLastCall(){
		return calls.isEmpty() ? null : calls.getLast();
	}
	
	public LinkedList<Call> getCurrentCalls(){
		return calls;
	}
	
	public int getWaitingClients(){
		return queueNumbers.size();
	}
	
	public String getWaitingText(){
		int waiting = getWaitingClients();
		
		return "Noch " + waiting + " " + (waiting == 1 ? "Kunde" : "Kunden") + " im Wartebereich.";
	}
	
}
