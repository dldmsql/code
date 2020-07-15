package cpu;

public class ArithmaticLogicUnit {
	private Register ac, mbr, status;

	public void connect(Register ac, Register mbr, Register status) {
		this.ac = ac;
		this.mbr = mbr;
		this.status = status;
	}

}
