

import java.util.ArrayList;
import java.util.List;


public class preFlopAction {
	private int[][] MPSeatHoleCards={
		    {9,6,3,3,0,0,0,0,0,0,0,0,0},
			{6,9,1,0,0,0,0,0,0,0,0,0,0},
			{3,1,9,0,0,0,0,0,0,0,0,0,0},
			{3,0,0,6,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,6,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,3,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,3,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1},
			
	};//��ǰλ��
	private int[][] cutOffSeatHoleCards={
			{9,6,3,3,1,1,1,1,1,1,1,0,0},
			{6,9,1,1,1,1,1,0,0,0,0,0,0},
			{3,1,9,1,1,1,0,0,0,0,0,0,0},
			{3,1,1,6,1,1,1,0,0,0,0,0,0},
			{1,1,1,1,6,1,1,0,0,0,0,0,0},
			{1,0,0,0,0,3,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,3,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1},   
			
	};//cutOff(buttonǰλ��)
	private int[][] buttonSeatHoleCards={
			{9,6,3,1,1,1,1,1,1,1,1,1,1},
			{6,9,3,1,1,1,1,0,0,0,0,0,0},
			{3,3,9,1,1,1,1,0,0,0,0,0,0},
			{1,1,1,6,1,1,1,0,0,0,0,0,0},
			{1,1,1,1,6,1,1,0,0,0,0,0,0},
			{1,0,0,0,0,3,1,1,0,0,0,0,0},
			{1,0,0,0,0,0,3,1,1,0,0,0,0},
			{1,0,0,0,0,0,0,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1},
			
	};//button��������
	private int[][] twoPlayersHoleCards={
			{9,6,6,1,1,1,1,1,1,1,1,1,1},
			{6,9,3,1,1,1,1,1,1,0,0,0,0},
			{6,3,9,1,1,1,1,0,0,0,0,0,0},
			{1,1,1,6,1,1,1,0,0,0,0,0,0},
			{1,1,1,1,6,1,1,0,0,0,0,0,0},
			{1,1,1,1,1,3,1,1,0,0,0,0,0},
			{1,1,1,0,0,0,3,1,1,0,0,0,0},
			{1,1,0,0,0,0,0,1,1,1,0,0,0},
			{1,0,0,0,0,0,0,0,1,1,1,0,0},
			{1,0,0,0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1},
			
	};//2�˵���
	
	private List<Card> holeCards=new ArrayList<Card>();
	private int currentSeat;
	private int bet;
	private int BB;
	private int potSize;
	private int myRestJetton;
	private int playerJoinIn;
	private int timeOfBet;
	public preFlopAction(List<Card> holeCards,int currentSeat,int bet,int BB,int potSize,int playerJoinIn,int myRestJetton, int timeOfBet){
		this.holeCards=holeCards;
		this.currentSeat=currentSeat;
		this.bet=bet;
		this.BB=BB;
		this.potSize=potSize;
		this.playerJoinIn=playerJoinIn;
		this.myRestJetton=myRestJetton;
		this.timeOfBet=timeOfBet;
	}
	public String preFlopDecision(){
		// TODO Auto-generated method stub
			/*if(currentSeat<=2) return betterSeatHoleCards[holeCards.get(0).number][holeCards.get(1).number];
			else return worseSeatHoleCards[holeCards.get(0).number][holeCards.get(1).number];*/
		
	/**
      *1. ����ǿ��Ϊ9�� ��һֱraise�� allin
      *2. Ϊ6�Ļ� ��ǰ�涼ƽ����Сraiseʱ ������raise
      *3. Ϊ3�� ǰ��raiseʱ ƽ�� ǰ��ƽ��ʱraise
      *4. Ϊ1�� ǰ�����˶���ʱ raise ���˶���ʱ fold
      */
      if(playerJoinIn>=5){
    	  return moreThan4Plyers();
      }else{
    	  if(playerJoinIn>2){
    		  return moreThan2Players();
    	  }else{
    		  return twoPlayers();
    	  }
      }
	 }
	public String moreThan4Plyers(){
		int min = Math.min(holeCards.get(0).number, holeCards.get(1).number);
		int max = Math.max(holeCards.get(0).number, holeCards.get(1).number);
		int preFlopRank = 0;
		if (currentSeat == 8||currentSeat==1||currentSeat==2) {
			if (holeCards.get(0).suit == holeCards.get(1).suit) {
				preFlopRank = buttonSeatHoleCards[14 - min][14 - max];
			} else {
				preFlopRank = buttonSeatHoleCards[14 - min][14 - max];
			}
		} else {
			if (currentSeat == 7) {
				if (holeCards.get(0).suit == holeCards.get(1).suit) {
					preFlopRank = cutOffSeatHoleCards[14 - min][14 - max];
				} else {
					preFlopRank = cutOffSeatHoleCards[14 - min][14 - max];
				}
			} else {
				if (holeCards.get(0).suit == holeCards.get(1).suit) {
					preFlopRank = MPSeatHoleCards[14 - min][14 - max];
				} else {
					preFlopRank = MPSeatHoleCards[14 - min][14 - max];
				}
			}
		}
		switch (preFlopRank) {
		case 9:
			if(timeOfBet<=3){
				return "raise " + Math.min(2*BB+potSize * 1 / 2, myRestJetton);
			}else{
				return "call";
			}
		case 6:
			if(timeOfBet>1&&bet>2*BB&&myRestJetton>=5*BB){
				return "fold";
			}else{
				if (bet<BB&&timeOfBet==1) {
					return "raise "
							+ Math.min( 2*BB+potSize * 1 / 2, myRestJetton);
				} else {
					if(bet<=2*BB)
					return "call";// ����λ��call
					else
					return "fold";
				}
			}
			
		case 3:
			if(timeOfBet>1&&bet>2*BB&&myRestJetton>=5*BB){
				return "fold";
			}else{
				if (bet <BB&&timeOfBet==1) {
					return "raise "
							+ Math.min(2*BB+potSize * 1 / 2, myRestJetton);
				} else {
						if(bet<=2*BB)
						return "call";// ����λ��call
						else
						return "fold";
				}
			}
		case 1:
			if(potSize==1.5*BB&&currentSeat>=7){
				return "raise "
						+ Math.min(2*BB+potSize * 1 / 2, myRestJetton);
			}else{
			if (currentSeat == 2) {
				if (myRestJetton < 4 * BB) {
					return "all_in";
				} else {
					if (bet >0) {
						return "fold";
					} else {
						return "check";// ��äλcheck
					}
				}
			} else {
				if (myRestJetton < 4 * BB) {
					return "all_in";
				} else {
					return "fold";// ����λ��call				
				}
			}
			}
		case 0:
			if (currentSeat == 2) {
				if (myRestJetton <= 2 * BB) {
					return "all_in";
				} else {
					if (bet > 0) {
						return "fold";
					} else {
						return "check";// ��äλcheck
					}
				}
			} else {
				return "fold";// ����λ��call
			}
		default:
			return "check";
		}
	}
    public String moreThan2Players(){
    	int min=Math.min(holeCards.get(0).number, holeCards.get(1).number);
        int max=Math.max(holeCards.get(0).number, holeCards.get(1).number);
        int preFlopRank=0;
        if(currentSeat==8||currentSeat==1||currentSeat==2){
      	  if(holeCards.get(0).suit==holeCards.get(1).suit){
      		  preFlopRank=buttonSeatHoleCards[14-min][14-max];	
      	  }else{
      		  preFlopRank=buttonSeatHoleCards[14-min][14-max];
      	  }
  	  }else{
  		  if(currentSeat==7){
  			  if(holeCards.get(0).suit==holeCards.get(1).suit){
  	    		  preFlopRank=cutOffSeatHoleCards[14-min][14-max];	
  	    	  }else{
  	    		  preFlopRank=cutOffSeatHoleCards[14-min][14-max];
  	    	  }  
  		  }else{
  			  if(holeCards.get(0).suit==holeCards.get(1).suit){
  	    		  preFlopRank=MPSeatHoleCards[14-min][14-max];	
  	    	  }else{
  	    		  preFlopRank=MPSeatHoleCards[14-min][14-max];
  	    	  }
  		  }
  	  }
  		switch (preFlopRank) {
  		case 9:
  			return "raise "+Math.min(2*BB+potSize*1/2, myRestJetton);
  		case 6:
  			if(timeOfBet>1&&bet>2*BB&&myRestJetton>=5*BB){
				return "fold";
			}else{
				if (bet<BB&&timeOfBet==1) {
					return "raise "
							+ Math.min( 2*BB+potSize * 1 / 2, myRestJetton);
				} else {
					if(bet<=2*BB)
					return "call";// ����λ��call
					else
					return "fold";
				}
			}
  		case 3:
  			if(timeOfBet>1&&bet>2*BB&&myRestJetton>=5*BB){
				return "fold";
			}else{
				if (bet <BB&&timeOfBet==1) {
					return "raise "
							+ Math.min(2*BB+potSize * 1 / 2, myRestJetton);
				} else {
						if(bet<=2*BB)
						return "call";// ����λ��call
						else
						return "fold";
				}
			}
  		case 1:
  			if(potSize==1.5*BB&&currentSeat>=7){
				return "raise "
						+ Math.min(2*BB+potSize * 1 / 2, myRestJetton);
			}else{
			if (currentSeat == 2) {
				if (myRestJetton < 4 * BB) {
					return "all_in";
				} else {
					if (bet >0) {
						return "fold";
					} else {
						return "check";// ��äλcheck
					}
				}
			} else {
				if (myRestJetton < 4 * BB) {
					return "all_in";
				} else {
					return "fold";// ����λ��call				
				}
			}
			}
  		case 0:
  			if(currentSeat==2){
  				if(myRestJetton<=2*BB){
  					return "all_in";
  				}else{
  					return "check";
  				}		
  			}else{
  				return "fold";//����λ��call
  			}	
  		default:
  			return "fold";
  		}
	}
    public String twoPlayers(){
    	int min=Math.min(holeCards.get(0).number, holeCards.get(1).number);
        int max=Math.max(holeCards.get(0).number, holeCards.get(1).number);
        int preFlopRank=0;
        preFlopRank=twoPlayersHoleCards[14-min][14-max];
  		switch (preFlopRank) {
  		case 9:
  			return "raise "+Math.min(2*BB+potSize*1/2, myRestJetton);
  		case 6:
  			if(bet<=2*BB&&timeOfBet==1){
  				return "raise "+Math.min(2*BB+potSize*1/2, myRestJetton);
  			}else{
  				if(timeOfBet>1){
  					return "fold";
  				}else{
  					if(currentSeat==1){
  	  					return "check";//��äλcheck
  	  				}else{
  	  					return "call";//����λ��call
  	  				}	
  				}
  					
  			}
  		case 3:
  			if(bet>2*BB&&timeOfBet>1){
  				if(bet>=2*BB&&myRestJetton>4*BB){
  					return "fold";
  				}else
  				 return "call";
  			}else{
  				if(timeOfBet==1)
  				return 	"raise "+Math.min(potSize*1/2, myRestJetton);
  				else
  				return "fold";
  			}
  		case 1:
  			if(bet<=BB&&timeOfBet==1){
  				return  "raise "+Math.min(Math.min(3*BB,2*BB+potSize*1/2), myRestJetton);
  			}else{
  				if(currentSeat==1){
  					if(myRestJetton<4*BB){
  						return "all_in";
  					}else{
  						return "check";
  					}				
  				}else{
  					return "fold";			
  				}
  			}
  		case 0:
  			if(currentSeat==1){
  				if(myRestJetton<=2*BB){
  					return "all_in";
  				}else{
  					if(bet>BB){
  						return "fold";
  					}else{
  						return "check";//��äλcheck
  					}
  				}		
  			}else{
  				return "fold";//����λ��call
  			}	
  		default:
  			return "check";
  		}
	}
}
