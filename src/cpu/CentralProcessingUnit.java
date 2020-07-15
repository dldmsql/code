package cpu;

import memory.Memory;

public class CentralProcessingUnit {
	// Components
	private ControlUnit cu;
	private ArithmaticLogicUnit alu;
	private Register pc, sp, mar, mbr, ir, ac, status;
	
	// association
	private Memory memory;
	
	public CentralProcessingUnit() {
		this.cu = new ControlUnit(); // CU는 HLT 상태
		this.alu = new ArithmaticLogicUnit();
		
		this.pc = new Register();
		this.sp = new Register();
		this.mar = new Register();
		this.mbr = new Register();
		this.ir = new Register();
		this.ac = new Register();
		this.status = new Register();
		
		this.cu.connect(this.pc, this.ir, this.sp, this.mar, this.mbr, this.ac, this.status); // ac = alu 계산 전 데이터 혹은 계산 후 데이터 저장. // register 연결
		this.alu.connect(this.ac, this.mbr, this.status);
		this.cu.connect(this.alu);
		
		this.pc.setData(0); // PC(Program Counter) : 0으로 초기값 설정
	}
	public void connect(Memory memory) {
		this.memory = memory;
		this.memory.connect(this.mar, this.mbr);
		this.cu.connect(this.memory);
	}

	public void run() {
		this.cu.run();
	}
	public void setSP(int codeSegmentSize) { // loader에서 쓰는 메소드
		this.sp.setData(codeSegmentSize);
	}
}
