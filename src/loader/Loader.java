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
			int codeSegmentSize = 0; // codesegment는 소스코드
			Scanner exeReader = new Scanner(new File(exeName)); // file 전체를 읽는 scanner
			while(exeReader.hasNextLine()) {
				Scanner codeReader = new Scanner(exeReader.nextLine()); // 한 줄 읽는 scanner
				String assemblyInstruction = codeReader.next(); // txt 파일의 영어로 된 거 읽어
				int instruction = opcodeParseInt(assemblyInstruction); // enum의 순서 값을 받아와서 저장.
				int value;
				try{
					value = codeReader.nextInt(); // 뒤에 숫자값 읽어
				}catch(NoSuchElementException e) {
					value = 0; // Code.txt 파일에 HLT 처럼 뒤에 숫자가 없는 경우 value를 0으로 !
				}
				int code = (instruction << 16) + value; // *bit 연산 질문!*
				targetMemory.load(code); // 메모리에 코드 로드
				codeSegmentSize ++; // 코드 로드했으니까 사이즈 업
				codeReader.close(); // 스캐너 닫기
			}
			exeReader.close(); // 스캐너 닫기
			cpu.setSP(codeSegmentSize); // SP(Stack Pointer) : Data Segment의 시작 주소 저장.
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	private int opcodeParseInt(String opcode) { // 영어로 된 명령어를 enum에서의 ordinal로 return
		for(EInstruction instruction : EInstruction.values()) {
			if(instruction.name().equals(opcode)) {
				return instruction.ordinal();
			}
		}
		return -1; // error
	}
}
