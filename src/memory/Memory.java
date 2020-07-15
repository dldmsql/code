package memory;

import cpu.Register;

public class Memory {
	private Register mar, mbr;
	private int[] buffer;
	private int now;
	
	public Memory() {
		this.buffer = new int[100];
		this.now = 0; // 몇 번째 줄을 읽고 있는지
	}
	public void connect(Register mar, Register mbr) {
		this.mar = mar;
		this.mbr = mbr;
	}
	public void load(int code) {
		this.buffer[this.now] = code;
		this.now++;
	}
	public void fetch() {
		int codeAddress = this.mar.getData(); // MAR에 넣은 주소값 저장
		this.mbr.setData(this.buffer[codeAddress]); // 해당 주소값에 있는 데이터 값을 MBR에 태워
	}
	public void store() {
		int dataAddress = this.mar.getData();
		int value = this.mbr.getData();
		this.buffer[dataAddress] = value;
	}
}
