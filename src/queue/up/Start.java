package queue.up;

public class Start {

	public static void main(String[] args){
		WaitingQueue qu = new WaitingQueue();
		
		for(int i = 0; i < 5; i++){
			System.out.println("Nummer " + qu.getNextQueueNumber() + " gezogen");
		}
		
		new DeskApp("Sachbearbeitung 1" , "Raum 102", "Herr Maier", qu);
		new DeskApp("Sachbearbeitung 2" , "Raum 104", "Herr Schmid", qu);
		new DeskApp("Sachbearbeitung 4" , "Raum 103", "Herr Müller", qu);
		new QueueDisplay(qu);
		new QueueDisplay(qu);
	}
	
}
