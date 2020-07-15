package cpu;

import memory.Memory;

public class ControlUnit {
	// attributes
	private enum EState {hlt, running}
	private EState eState = EState.hlt;
	// associations
	private Register pc, sp, mar, mbr, ac, ir, status;
	private ArithmaticLogicUnit alu;
	private Memory memory;
	
	public ControlUnit() {
		this.eState = EState.hlt;
	}
	public void connect(ArithmaticLogicUnit alu) {
		this.alu = alu;
	}
	public void connect(Memory memory) {
		this.memory = memory;
	}
	public void connect(Register pc, Register ir, Register sp, Register mar, Register mbr, Register ac,
			Register status) {
		this.pc = pc;
		this.ir = ir;
		this.sp = sp;
		this.mar = mar;
		this.mbr = mbr;
		this.ac = ac;
		this.status = status;
	}
	public void run() {
		this.eState = EState.running; // running���� ���� ����
		while(this.eState == EState.running) {
			this.fetch(); // ��ɾ� ����
			this.decode(); // ��ɾ� �ص�
		}
	}
	private void fetch() {
		this.mar.setData(this.pc.getData()); // pc���� �ּҰ� �����ͼ� MAR�� �ֱ�
		this.memory.fetch(); // �޸𸮿� ���� ���� ����
		this.ir.setData(this.mbr.getData()); // MBR���� �� �����ͼ� IR�� �ֱ�, IR(Instruction Register) : CU�� ����ϴ� �� ����
		
	}
	private void decode() {
		int instruction = this.ir.getData() >>> 16; // ��Ʈ ������ ����������  16 bit �̵�, >>> : ���� ������ 0���� ä���, >> : ���ڸ� ��(0Ȥ��1)�� ä���
		// 1byte = 8bit , 4bit nibble 
		// INTEGER 4 byte
		System.out.println(EInstruction.values()[instruction].name());
		switch(EInstruction.values()[instruction]) {
		case LDA : LDA(); 
		break;
		case HLT : HLT();
		break;
		case LDV : LDV();
		break;
		case STA : STA();
		break;
		case ADD : ADD();
		break;
		case PRT : PRT();
		break;
		default : break;
		}
	}
	private void LDA() {
		int address = this.ir.getData() & 0x0000ffff; // & �Ѵ� true�̸� 1 false �� 0
		address += this.sp.getData();
		this.mar.setData(address);
		
		this.memory.fetch();
		
		int data = this.mbr.getData();
		this.ac.setData(data); // AC(Accumulator) : ALU�� ����ϴ� �� ����
		this.pc.setData(this.pc.getData()+1); // pc�� ���� �������� ��ɾ �б� ������ +1
		
	}
	private void LDV() {
		int value = this.ir.getData() & 0x0000ffff;
		this.ac.setData(value);
		
		this.pc.setData(this.pc.getData()+1);
		
	}
	private void STA() {
		int storeAddress = this.ir.getData() & 0x0000ffff;
		storeAddress += this.sp.getData();
		this.mar.setData(storeAddress);
		
		int value = this.ac.getData();
		this.mbr.setData(value);
		
		this.memory.store();
		
		this.pc.setData(this.pc.getData()+1);
	}
	private void ADD() {
		int address = this.ir.getData() & 0x0000ffff;
		address += this.sp.getData();
		this.mar.setData(address);
		
		this.memory.fetch();
		
		int value = this.mbr.getData();
		
		int calculatedValue = this.ac.getData() + value;
		this.ac.setData(calculatedValue);
		
		this.pc.setData(this.pc.getData() + 1);
	}
	
	private void HLT() {
		this.eState = EState.hlt;
	}
	private void PRT() {
		int address = this.ir.getData() & 0x0000ffff;
		address += this.sp.getData();
		this.mar.setData(address);
		
		this.memory.fetch();
		
		int data = this.mbr.getData();
		System.out.println("Print : " + data);
		this.pc.setData(this.pc.getData() + 1);
	}
}
