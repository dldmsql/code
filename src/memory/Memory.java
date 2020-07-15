package memory;

import cpu.Register;

public class Memory {
	private Register mar, mbr;
	private int[] buffer;
	private int now;
	
	public Memory() {
		this.buffer = new int[100];
		this.now = 0; // �� ��° ���� �а� �ִ���
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
		int codeAddress = this.mar.getData(); // MAR�� ���� �ּҰ� ����
		this.mbr.setData(this.buffer[codeAddress]); // �ش� �ּҰ��� �ִ� ������ ���� MBR�� �¿�
	}
	public void store() {
		int dataAddress = this.mar.getData();
		int value = this.mbr.getData();
		this.buffer[dataAddress] = value;
	}
}
