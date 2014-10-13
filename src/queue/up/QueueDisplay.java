package queue.up;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QueueDisplay implements QueueObserver {

	private ExecutorService taskService = Executors.newSingleThreadExecutor(); // Teilaufgabe g)
	private WaitingQueue queue;
	private JFrame frame = new JFrame();
	private JLabel callLabel = new JLabel();
	private JLabel waitingLabel = new JLabel();
	private JTextArea callArea = new JTextArea(5, 5);
	private JButton nextButton = new JButton("Nummer ziehen");
	private int callIndex = 0;
	
	public QueueDisplay(WaitingQueue queue){
		this.queue = queue;
		
		queue.addQueueObserver(this);
		createFrame();
		createThread();
	}
	
	@Override
	public void updateView(){
		updateCall();
		waitingLabel.setText(queue.getWaitingText());
		
		StringBuilder callsText = new StringBuilder();
		
		for(Call call:queue.getCurrentCalls()){
			callsText
				.append(call.getQueueNumber())
				.append(": ")
				.append(call.getDesk())
				.append(System.lineSeparator());
		}
		
		callArea.setText(callsText.toString());
	}
	
	// Teilaufgabe g)
	public void updateCall(){
		LinkedList<Call> calls = queue.getCurrentCalls();
		int size = calls.size();
		
		if(size > 0){
			if(callIndex >= size){
				callIndex = 0;
			}
			
			Call call = calls.get(callIndex++);
			
			callLabel.setText(call.getQueueNumber() + ": " + call.getDesk());
		}else{
			callLabel.setText("---");
		}
	}
	
	private void createFrame(){
		JPanel mainPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(callArea);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		callLabel.setFont(new Font(callLabel.getFont().getName(), Font.PLAIN, 24));
		callArea.setEditable(false);
		
		mainPanel.add(callLabel);
		mainPanel.add(waitingLabel);
		mainPanel.add(scrollPane);
		mainPanel.add(nextButton);
		
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 300);
		frame.setLocationRelativeTo(null);
		updateView();
		frame.setVisible(true);
		
		nextButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int queueNumber = queue.getNextQueueNumber();
				
				JOptionPane.showMessageDialog(frame, "Sie haben Nummer " + queueNumber, "Meldung", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	// Teilaufgabe g)
	private void createThread(){
		CallTask task = new CallTask(this);
		
		taskService.submit(task);
	}

}
