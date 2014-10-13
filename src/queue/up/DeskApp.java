package queue.up;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DeskApp implements QueueObserver, ActionListener {

	private WaitingQueue queue;
	private JFrame frame = new JFrame();
	private JLabel waitingLabel = new JLabel();
	private JLabel callLabel = new JLabel();
	private JButton startButton = new JButton("Bearbeitung beginnen");
	private JButton nextButton = new JButton("Nächster Kunde");
	private Call currentCall = null;
	private String workplace, room, name;
	private boolean activeClient = false;
	
	public DeskApp(String workplace, String room, String name, WaitingQueue queue){
		this.queue = queue;
		this.workplace = workplace;
		this.room = room;
		this.name = name;
		
		queue.addQueueObserver(this);
		createFrame();
	}
	
	@Override
	public void updateView(){
		waitingLabel.setText(queue.getWaitingText());
		
		if(currentCall != null){
			callLabel.setText("Kunde Nummer " + currentCall.getQueueNumber());
		}else{
			callLabel.setText("---");
		}
	}
	
	@Override
	public String toString(){
		return workplace + " (" + room + ")";
	}
	
	private void createFrame(){
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		startButton.setEnabled(false);
		startButton.addActionListener(this);
		nextButton.addActionListener(this);
		
		buttonPanel.add(startButton);
		buttonPanel.add(nextButton);
		
		mainPanel.add(waitingLabel);
		mainPanel.add(callLabel);
		mainPanel.add(buttonPanel);
		
		frame.add(mainPanel);
		frame.setTitle(workplace);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 110);
		frame.setLocationRelativeTo(null);
		updateView();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		
		if(source == startButton){
			if(activeClient){
				this.activeClient = false;
				this.currentCall = null;
				
				startButton.setText("Bearbeitung beginnen");
				startButton.setEnabled(false);
				nextButton.setEnabled(true);
				updateView();
			}else{
				this.activeClient = true;
				
				startButton.setText("Bearbeitung abgeschlossen");
				queue.confirmCall(currentCall);
			}
		}else if(source == nextButton){
			int waiting = queue.getWaitingClients();
			
			if(waiting > 0){
				this.currentCall = queue.getNextCall(this);
				
				startButton.setEnabled(true);
				nextButton.setEnabled(false);
			}else{
				JOptionPane.showMessageDialog(frame, "Keine wartenden Kunden!", "Nächster Kunde", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public String getName(){
		return name;
	}
	
}
