package loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cpu.CentralProcessingUnit;
import cpu.EInstruction;
import memory.Memory;

public class Loader {
	
	public void load(CentralProcessingUnit cpu, Memory targetMemory, String exeName) {
		try {
			int codeSegmentSize = 0; // codesegment�� �ҽ��ڵ�
			Scanner exeReader = new Scanner(new File(exeName)); // file ��ü�� �д� scanner
			while(exeReader.hasNextLine()) {
				Scanner codeReader = new Scanner(exeReader.nextLine()); // �� �� �д� scanner
				String assemblyInstruction = codeReader.next(); // txt ������ ����� �� �� �о�
				int instruction = opcodeParseInt(assemblyInstruction); // enum�� ���� ���� �޾ƿͼ� ����.
				int value;
				try{
					value = codeReader.nextInt(); // �ڿ� ���ڰ� �о�
				}catch(NoSuchElementException e) {
					value = 0; // Code.txt ���Ͽ� HLT ó�� �ڿ� ���ڰ� ���� ��� value�� 0���� !
				}
				int code = (instruction << 16) + value; // *bit ���� ����!*
				targetMemory.load(code); // �޸𸮿� �ڵ� �ε�
				codeSegmentSize ++; // �ڵ� �ε������ϱ� ������ ��
				codeReader.close(); // ��ĳ�� �ݱ�
			}
			exeReader.close(); // ��ĳ�� �ݱ�
			cpu.setSP(codeSegmentSize); // SP(Stack Pointer) : Data Segment�� ���� �ּ� ����.
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	private int opcodeParseInt(String opcode) { // ����� �� ��ɾ enum������ ordinal�� return
		for(EInstruction instruction : EInstruction.values()) {
			if(instruction.name().equals(opcode)) {
				return instruction.ordinal();
			}
		}
		return -1; // error
	}
}
