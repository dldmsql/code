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
		this.cu = new ControlUnit(); // CU�� HLT ����
		this.alu = new ArithmaticLogicUnit();
		
		this.pc = new Register();
		this.sp = new Register();
		this.mar = new Register();
		this.mbr = new Register();
		this.ir = new Register();
		this.ac = new Register();
		this.status = new Register();
		
		this.cu.connect(this.pc, this.ir, this.sp, this.mar, this.mbr, this.ac, this.status); // ac = alu ��� �� ������ Ȥ�� ��� �� ������ ����. // register ����
		this.alu.connect(this.ac, this.mbr, this.status);
		this.cu.connect(this.alu);
		
		this.pc.setData(0); // PC(Program Counter) : 0���� �ʱⰪ ����
	}
	public void connect(Memory memory) {
		this.memory = memory;
		this.memory.connect(this.mar, this.mbr);
		this.cu.connect(this.memory);
	}

	public void run() {
		this.cu.run();
	}
	public void setSP(int codeSegmentSize) { // loader���� ���� �޼ҵ�
		this.sp.setData(codeSegmentSize);
	}
}
