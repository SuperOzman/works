package huawei.texaspoker;
import java.util.HashMap;
import java.util.List;

/**
 * ������
 * @author SQQ
 *
 */
public class Opponent {
	private int pid;//����id
	private int jetton;//������
	private int money;//�����
	public int bet_in=0;//���ָ������Ͷ��ĳ�����
	public int order;//���ִ�д���
	public boolean isDiscard;
	public HashMap<Integer, List<String>> action;//��x�ֵĶ���, check | call | raise | all_in | fold
	public Opponent(int id,int jetton,int money,int order){
		this.pid=id;
		this.jetton=jetton;
		this.money=money;
		this.order=order;
		isDiscard=false;
		action=new HashMap<Integer, List<String>>();		
	}
	public int getPID(){
		return pid;
	}
	public int getJetton(){
		return jetton;
	}
	public int getMoney(){
		return money;
	}
	public void setPID(int pid){
		this.pid=pid;
	}
	public void setJetton(int jetton){
		this.jetton=jetton;
	}
	public void setMoney(int money){
		this.money=money;
	}
}
