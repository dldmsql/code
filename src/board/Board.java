package board;

import cpu.CentralProcessingUnit;
import loader.Loader;
import memory.Memory;

public class Board {
	// components
	private CentralProcessingUnit cpu;
	private Memory memory;
	
	public Board() {
		this.cpu = new CentralProcessingUnit();
		this.memory = new Memory();
		
		this.cpu.connect(this.memory); // cpu�� memory ����
	}
	
	public void run() {
		Loader loader = new Loader(); // HDD���� Memory�� �ø��� �� => Loader
		loader.load(this.cpu, this.memory, "data/Code.txt"); // SP������ CPU�� �˾ƾ� �ϰ�, �޸𸮿� �÷��� �ؼ� memory�� �˾ƾ� �ϰ�, �о�;� �� ���α׷� �ּҰ�
		
		this.cpu.run();
	}
}
