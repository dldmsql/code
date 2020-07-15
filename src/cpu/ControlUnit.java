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
		this.eState = EState.running; // running으로 상태 변경
		while(this.eState == EState.running) {
			this.fetch(); // 명령어 인출
			this.decode(); // 명령어 해독
		}
	}
	private void fetch() {
		this.mar.setData(this.pc.getData()); // pc에서 주소값 가져와서 MAR에 넣기
		this.memory.fetch(); // 메모리에 가서 명렁어 인출
		this.ir.setData(this.mbr.getData()); // MBR에서 값 가져와서 IR에 넣기, IR(Instruction Register) : CU가 사용하는 값 저장
		
	}
	private void decode() {
		int instruction = this.ir.getData() >>> 16; // 비트 연산자 오른쪽으로  16 bit 이동, >>> : 앞을 무조건 0으로 채운다, >> : 끝자리 값(0혹은1)로 채운다
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
		int address = this.ir.getData() & 0x0000ffff; // & 둘다 true이면 1 false 면 0
		address += this.sp.getData();
		this.mar.setData(address);
		
		this.memory.fetch();
		
		int data = this.mbr.getData();
		this.ac.setData(data); // AC(Accumulator) : ALU가 사용하는 값 저장
		this.pc.setData(this.pc.getData()+1); // pc의 값을 기준으로 명령어를 읽기 때문에 +1
		
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
