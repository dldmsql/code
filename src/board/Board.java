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
		
		this.cpu.connect(this.memory); // cpu랑 memory 연결
	}
	
	public void run() {
		Loader loader = new Loader(); // HDD에서 Memory로 올리는 것 => Loader
		loader.load(this.cpu, this.memory, "data/Code.txt"); // SP때문에 CPU도 알아야 하고, 메모리에 올려야 해서 memory도 알아야 하고, 읽어와야 할 프로그램 주소값
		
		this.cpu.run();
	}
}
