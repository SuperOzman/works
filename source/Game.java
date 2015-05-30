

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Game {
	private String MsgHead;
	Socket player;
	PrintWriter player2server;
	BufferedReader reader;
	MessageHead MsgHeadHanlder;
	Desk desk;
	int inquirecount=1;//��¼��ǰ�ƾ�״̬�ǵڼ���inquire��
	
	String serverip,myip;
	int serverport,myport;  
	    
	int myorder;
	int mypid;
	int mybet;//��ǰ��һ���Ѿ�Ͷ��ĳ�����
	private boolean isDiscard;//��¼�Լ��Ƿ�����
	private List<Card> holdCards;//�Լ�����
	private int mymoney,myjetton;//�Լ��ĳ���ͽ��
	Map<Integer, Opponent> Pid_Opponent;//����pid����һ������ʵ����
	public Game(){
		isDiscard=false;
		holdCards=new ArrayList<Card>(2);
		Pid_Opponent=new HashMap<Integer, Opponent>();
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub	
		//��ʼ��
		Game dsnju=new Game();
		
		dsnju.serverip=args[0];
		dsnju.serverport=new Integer(args[1]);
		dsnju.myip=args[2];
		dsnju.myport=new Integer(args[3]);
		dsnju.mypid=new Integer(args[4]);
		
		/*dsnju.serverip="127.0.0.1";
		dsnju.serverport=8888;
		dsnju.myip="127.0.0.1";
		dsnju.myport=4533;
		dsnju.mypid=1111;*/
		
		dsnju.initialize(args);
		//����ע����Ϣ
		dsnju.player2server.println("reg: "+dsnju.mypid+" DSNJU ");
		dsnju.player2server.flush();
		//�ƾּ���
		int count=0;
		start:while(true){//�������У�ֱ���յ�game-over��Ϣ
			do{
				dsnju.getAllMsg(dsnju.reader);
				
			}while(!dsnju.MsgHead.equals("pot-win")&&!dsnju.MsgHead.equals("game-over "));
			if(dsnju.MsgHead.equals("game-over "))//game-over������/������������head�����ո�
				break start;
			//��һЩ�������
			dsnju.Pid_Opponent.clear();
			dsnju.holdCards.clear();//����Լ��������б�
			dsnju.inquirecount=1;
			dsnju.isDiscard=false;
			dsnju.mybet=0;
			//���ÿ�����ֶ���Ķ���Map
			/*for(Entry<Integer, Opponent> entry:dsnju.Pid_Opponent.entrySet()){
				entry.getValue().action.clear();
			}*/	
			if(!dsnju.player.isConnected())
				dsnju.player.connect(new InetSocketAddress(dsnju.serverip, dsnju.serverport));//����server
			count++;
			//System.out.println(dsnju.mypid+"'s "+count+" round is over");
		}
		dsnju.reader.close();
		dsnju.player2server.close();
		dsnju.player.close();
	}
	private void initialize(String args[]) throws UnknownHostException, IOException{
		boolean connected=false;
		while(!connected){
			try {
				SocketAddress serveraddress = new InetSocketAddress(
						args[0], Integer.parseInt(args[1]));
				SocketAddress hostaddress = new InetSocketAddress(args[2],
						Integer.parseInt(args[3]));

				player = new Socket();
				player.setReuseAddress(true);
				player.bind(hostaddress);//�󶨿ͻ��˵�ָ��IP�Ͷ˿ں�
				player.connect(serveraddress);
				connected=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				continue;
			}//����server
		}
		/*player=new Socket(serverip, serverport);*/
		player2server=new PrintWriter(player.getOutputStream());
		reader=new BufferedReader(new InputStreamReader(player.getInputStream()));
		MsgHeadHanlder=new MessageHead();
		//System.out.println("�ѳ�ʼ��");
	}
	/**
	 * ��ȡ��Ϣ�� ����MsgHead��ֵ
	 * @param reader
	 * @param msghead
	 * @return
	 * @throws IOException
	 */
	public  void getAllMsg(BufferedReader reader) throws IOException{
		//���ܽ�MsgHead��Ϊ��������ʹ��ָ���µĶ�����Ϊ���󴫲�Ҳ�Ǵ��ı��ݣ�ָ���¶����Ͳ���ԭ��������
		//msghead=new String(result.substring(0, head.indexOf("/")));

		String head=reader.readLine();//��ȡ��Ϣͷ
		if(head.equals("game-over ")){//game-over��Ϣ����/
			this.setMsgHead(head);
			return;
		}
		StringBuffer result=new StringBuffer(head);	
		head=result.substring(0, head.indexOf("/"));//ȥ����/��
		/*result.insert(0, "/");
		head=result.substring(0, head.length());//��headǰ����/��ȥ��β����/����/���пո��ô˷���*/
		
		this.setMsgHead(head);
		this.HandleMsg(MsgHead);
	}
	public void setMsgHead(String msghead){
		this.MsgHead=new String(msghead);
	}
	public void HandleMsg(String msghead) throws IOException{
		if(msghead==null)
			return;
		int label=MsgHeadHanlder.map.get(msghead);
		String head="/"+msghead+" ";//ÿһ��ָ����ո�
	switch (label) {
		case 1:
			//���ô���������Ϣ����
			//System.out.println("��������������Ϣ����");
			HanldeSeat(reader, head);
			break;
		case 2:
			//���ô���äע��Ϣ����
			//System.out.println("��������äע��Ϣ����");
			HandleBlind(reader, head);
			break;
		case 3:
			//���ô���������Ϣ����
			//System.out.println("��������������Ϣ����");
			HanldeHoldCards(reader, head);
			break;
		case 4:
			//���ô���ѯ����Ϣ����
			//System.out.println("��������ѯ����Ϣ����");
			HanldeInquire(reader, head);
			break;
		case 5:
			//���ô�������Ϣ����
			//System.out.println("������������Ϣ����");
			HandleFlop(reader, head);
			break;
		case 6:
			//���ô���ת����Ϣ����
			//System.out.println("��������ת����Ϣ����");
			HandleTurn(reader, head);
			break;
		case 7:
			//���ô��������Ϣ����
			//System.out.println("�������������Ϣ����");
			HandleRiver(reader, head);
			break;
		case 8:
			//���ô���̯����Ϣ����
			//System.out.println("��������̯����Ϣ����");
			HandleShowdown(reader, head);			
			break;
		case 9:
			//���ô�������Ϣ����
			//System.out.println("������������Ϣ����");
			HandlePot_Win(reader, head);
			break;
		case 10:
			//���ô��������Ϣ����
			break;
		default:
			break;
		}
	}
		
	/**
	 * ����������Ϣ����¼��Сä��
	 * ���ݴ������Ϣ����ȡ��Ϣ
	 * �������ֶ������Map
	 * ��¼ִ��˳��
	 * @throws IOException 
	 */
	public void HanldeSeat(BufferedReader reader,String head) throws IOException{
		int money,jetton,pid;//��¼ÿһλ��ҵĳ���ͽ����		
		desk=new Desk(0, 0, 0);//��ʼ��desk
		desk.playercount=0;//��¼������Ϣ�ĵڼ��У������ֲ��������
		String temp="";
		int linecount=0;
		while(!(temp=reader.readLine()).equals(head)){
			String splittemp[]=temp.split(" ");//���Կո�Ϊ�ָ����ֳ�����			
			switch (linecount) {
			case 0://button
				pid=new Integer(splittemp[1]);
				jetton=new Integer(splittemp[2]).intValue();//��ø����jetton
				money=new Integer(splittemp[3]).intValue();				
				desk.setButton(pid);
				break;
			case 1://Сä
				pid=new Integer(splittemp[2]);
				jetton=new Integer(splittemp[3]).intValue();
				money=new Integer(splittemp[4]).intValue();				
				desk.setSmallBlind(pid);
			case 2://��ä����ֻ���������ʱû�д�ä��ֻ��button��Сä
				pid=new Integer(splittemp[2]);
				jetton=new Integer(splittemp[3]).intValue();
				money=new Integer(splittemp[4]).intValue();				
				desk.setBigBlind(pid);
				break;
			default:
				pid=new Integer(splittemp[0]).intValue();
				jetton=new Integer(splittemp[1]).intValue();
				money=new Integer(splittemp[2]).intValue();
				break;
			}
			if(pid!=mypid)
				Pid_Opponent.put(pid, new Opponent(pid, jetton, money,linecount));//�������ֶ���
			setMyself(pid, jetton, money,linecount);//�����Լ���jetton��money��ִ�д���
			linecount++;
		}		
		desk.playercount=linecount;
		Opponent o=Pid_Opponent.get(desk.getButton());
		if(o!=null)
			o.order=linecount;//��buttonλ�Ĵ���������Ϊ�������
		else//oΪ�գ�˵���Լ���buttonλ������Ϊ���
			this.myorder=desk.playercount;
		/*if(desk.getButton()==mypid)//
			this.myorder=desk.playercount;*/
		desk.setcardStatus(0);//�����ƾ�״̬
	}
	
	private int setMyself(int pid,int jetton,int money,int linecount){
		if(pid!=mypid)
			return -1;
		this.myjetton=jetton;
		this.mymoney=money;
		if(linecount==0)
			this.myorder=-1;
		else 
			this.myorder=linecount;
		return linecount;
	}
	/**
	 * äע��Ϣ������¼��äע���
	 * @param reader
	 * @param head
	 * @throws IOException
	 */
	public void HandleBlind(BufferedReader reader,String head) throws IOException{
		String temp="";
		int linecount=0;
		while(!(temp=reader.readLine()).equals(head)){	
			String splittemp[]=temp.split(" ");
			int pid=new Integer(splittemp[0].substring(0, splittemp[0].length()-1)).intValue();
			int bb=new Integer(splittemp[1]).intValue();
			if(linecount==0){				
				desk.setBB(bb);//��¼Сäע�������ڴ�äʱ��BB����Ϊ��äֵ���޴�äʱ��ΪСäֵ
				if(pid==mypid)//�������Сä�������Լ��ĵ�ǰ��bet��ΪСäֵ
					mybet=bb;
			}
			else if(linecount==1){//���ڴ�äʱ��linecount�Ż����1				
				desk.setBB(bb);//��¼��äע���
				if(pid==mypid)//������Ǵ�ä�������Լ��ĵ�ǰ��bet��Ϊ��äֵ
					mybet=bb;
			}
			++linecount;
		}
		
		//System.out.println("��ä��"+desk.getBB());
	}
	/**
	 * ������������
	 * @param reader
	 * @param head
	 * @throws IOException
	 */
	private void HanldeHoldCards(BufferedReader reader, String head) throws IOException {
		// TODO Auto-generated method stub
		String temp="";		
		while(!(temp=reader.readLine()).equals(head)){
			String splittemp[]=temp.split(" ");	
			Integer i=Card.NumeralSuit.get(splittemp[0]);
			int suitnumber=i.intValue();
			Card c=new Card(suitnumber, card2number(splittemp[1]));
			holdCards.add(c);
		}
		
		/*System.out.println("�Լ������ƣ�");
		for(Card c:holdCards){
			System.out.println(c.getSuit()+" "+c.getNumber());
		}*/
	}
	/**
	 * �Է�����ѯ����Ϣ��¼���ֵĶ��������������Լ��Ķ���
	 * @param reader
	 * @param head
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void HanldeInquire(BufferedReader reader, String head) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		String temp="";
		int bet=0;
		StringBuffer curRoundAction=new StringBuffer();
		//���汾��Inquire��������pid��jetton��money bet action�С����playercount��
		//����һ���⣬����ÿ�ֶ���ȫ����Ұ�����������ҵĶ���
		String curRoundInquireMsg[]=new String[desk.playercount];
		int linecount=0;
		while(!(temp=reader.readLine()).equals(head)){
			String splittemp[]=temp.split(" ");
			int action_index=splittemp.length-1;
			if(!splittemp[0].equals("total")){
				String action=splittemp[action_index];//��ȡ����
				curRoundAction.append(action);//����֪��������buffer				
				curRoundInquireMsg[linecount]=temp;
				
				/*if(!action.equals("blind")){//��äע��Ϣ������					
					Opponent opp=Pid_Opponent.get(pid);//��ȡ��Ӧ���ֶ���
					if(action.equals("raise"))
						opp.raise_money=new Integer(splittemp[action_index-1]);//��¼���ּ�ע���
					if(!opp.action.containsKey(desk.getcardStatus())){//δ�Ը�״̬��¼
						List<String> action_list=new ArrayList<String>();
						action_list.add(action);
						opp.action.put(desk.getcardStatus(), action_list);//���������붯��Map
					}
					else{
						List<String> action_list=opp.action.get(desk.getcardStatus());//��ȡ��Ӧ�б�
						action_list.add(action);//���б������
					}
				}*/
			}
			else{
				desk.totalpot=new Integer(splittemp[action_index]);
			}
			linecount++;
		}
		
		/*System.out.println("��ǰ��Ͷע��"+desk.totalpot);
		System.out.println("ǰ��������bet��"+bet);
		System.out.println("ǰ������ж���Ϣ��"+curRoundAction.toString());
		System.out.println("��sever�����Լ���action����");*/
		
		bet=getbet(curRoundInquireMsg);
		String myaction="noaction";
		if(!isDiscard){//û�����Ʋŷ�������Ϣ��server
			if(desk.getcardStatus()==0){
				//System.out.println(mypid+" bet="+bet);
				preFlopAction pre=new preFlopAction(holdCards, myorder, bet, desk.getBB(), 
						desk.totalpot, desk.playercount, myjetton,inquirecount);
				myaction=pre.preFlopDecision();
				player2server.println(myaction);
			}
			else {
				//System.out.println(mypid+" bet="+bet+",����="+inquirecount);
				actionDecision mActionDecision=new actionDecision(holdCards, desk.sharedCards, bet, 
						desk.getBB(), getOpponentAction(curRoundAction.toString()), desk.totalpot, myjetton,inquirecount);
				myaction=mActionDecision.actionSendToServer();			
				player2server.println(myaction);
			}
			player2server.flush();
			
		}
		//System.out.println(mypid+"'s action="+myaction);
		inquirecount++;//��������
		/*System.out.println("���ָ����bet_in:");
		for(Map.Entry<Integer, Opponent> entry:Pid_Opponent.entrySet()){//��ȡÿ�����ֶ���  		
			System.out.println(entry.getKey()+"-->"+entry.getValue().bet_in);			
		}*/
	}
	//���bet������ÿ�����ֵ�bet_in
	private int getbet(String curRoundInquireMsg[]){//���ݸ�pid���ֵ�action����bet��������ǰ����Ҽ����������
		int result=0;
		int lastline=curRoundInquireMsg.length-1;
		String myMsg=curRoundInquireMsg[lastline];
		//��ȡ��null�����һ����Ϣ��curRoundInquireMsg������������������һ��ѯ��ʱ������������С��desk.playercount��
		while(myMsg==null){
			lastline--;
			myMsg=curRoundInquireMsg[lastline];
		}
		String splitMsg[]=myMsg.split(" ");
		if(splitMsg[0].equals(mypid+"")){//������Լ�����Ϣ
			mybet=new Integer(splitMsg[3]);//��ȡmybet
			myjetton=new Integer(splitMsg[1]);
			mymoney=new Integer(splitMsg[2]);//�����Լ��ĳ�����
		}
		for(int i=0;i<curRoundInquireMsg.length&&curRoundInquireMsg[i]!=null;i++){
			splitMsg=curRoundInquireMsg[i].split(" ");
			if(splitMsg[4].equals("blind")||splitMsg[4].equals("call")
					||splitMsg[4].equals("raise")||splitMsg[4].equals("all_in")){
				result=new Integer(splitMsg[3])-mybet;
				//��һ��call��raise��all_in�������Ͷ��betֵ��ȥ�Լ��Ѿ�Ͷ���bet����Ϊ��Ҫ��ע����С������
				break;
			}
			else if(splitMsg[4].equals("check")){
				result=0;
				break;
			}
			else{
				//fold
			}
		}
		
		return result<0?0:result;//���С��0������all_in��betֵҲС���Լ���betֵ������Ϊ0
		
	}
	
	private String getOpponentAction(String curRoundAction ){
		if(curRoundAction.contains("all_in"))//����all_in
			return "all_in";
		else if(curRoundAction.contains("raise"))//����raise
			return "raise";
		else if(curRoundAction.contains("call"))//
			return "call";
		else if(curRoundAction.contains("check"))//
			return "check";
		else
			return "fold";
	}
	/**
	 * ��¼������
	 * @param reader2
	 * @param head
	 * @throws IOException
	 */
	private void HandleFlop(BufferedReader reader2, String head) throws IOException {
		// TODO Auto-generated method stub
		String temp="";
		while(!(temp=reader.readLine()).equals(head)){
			String[] splittemp=temp.split(" ");
			desk.sharedCards.add(new Card(Card.NumeralSuit.get(splittemp[0]), card2number(splittemp[1])));
		}
		desk.setcardStatus(1);//�ƾ�״̬Ϊflop
		inquirecount=1;//�µ��ƾ�״̬��ѯ�ʴ�������
		/*System.out.println("���й����ƣ�");
		for(Card c:desk.sharedCards)
			System.out.println(c.getSuit()+","+c.getNumber());*/
	}
	
	/**
	 * ��¼Turn��
	 * @param reader2
	 * @param head
	 * @throws IOException
	 */
	private void HandleTurn(BufferedReader reader, String head) throws IOException {
		// TODO Auto-generated method stub
		String temp="";
		while(!(temp=reader.readLine()).equals(head)){
			String[] splittemp=temp.split(" ");
			desk.sharedCards.add(new Card(Card.NumeralSuit.get(splittemp[0]), card2number(splittemp[1])));
		}
		desk.setcardStatus(2);
		inquirecount=1;//�µ��ƾ�״̬��ѯ�ʴ�������
		/*System.out.println("���й����ƣ�");
		for(Card c:desk.sharedCards)
			System.out.println(c.getSuit()+","+c.getNumber());*/
	}
	
	private void HandleRiver(BufferedReader reader, String head) throws IOException {
		// TODO Auto-generated method stub
		String temp="";
		while(!(temp=reader.readLine()).equals(head)){
			String[] splittemp=temp.split(" ");
			desk.sharedCards.add(new Card(Card.NumeralSuit.get(splittemp[0]), card2number(splittemp[1])));
		}
		desk.setcardStatus(3);
		inquirecount=1;//�µ��ƾ�״̬��ѯ�ʴ�������
	/*	System.out.println("���й����ƣ�");
		for(Card c:desk.sharedCards)
			System.out.println(c.getSuit()+","+c.getNumber());*/
	}
	/**
	 * ̯��
	 * @param reader
	 * @param head
	 * @throws IOException
	 */
	private void HandleShowdown(BufferedReader reader, String head) throws IOException {
		// TODO Auto-generated method stub
		String temp="";
		StringBuffer msgbody=new StringBuffer();
		while(!(temp=reader.readLine()).equals(head)){
			msgbody.append(temp);
		}
		//System.out.println("shutdown��Ϣ��"+msgbody);
	}
	
	public void HandlePot_Win(BufferedReader reader,String head) throws IOException{
		String temp="";
		StringBuffer msgbody=new StringBuffer();
		while(!(temp=reader.readLine()).equals(head)){
			msgbody.append(temp);	
		}
		//System.out.println("pot-win��Ϣ��"+msgbody);
	}
	
	private int card2number(String str){
		int num;
		try{
			num=new Integer(str).intValue();
		}
		catch (IllegalArgumentException e){
			if(str.equals("A"))
				num=14;
			else if(str.equals("J"))
				num=11;
			else if(str.equals("Q"))
				num=12;
			else 
				num=13;
		}
		return num;
	}
}

