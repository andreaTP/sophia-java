package eu.unicredit.sophia;

import java.nio.ByteBuffer; 

public class MemoryArea {

	private long capacity;
	private long address;
	
	public ByteBuffer buffer;

	MemoryArea(long address,ByteBuffer buffer, long capacity) {
		this.address = address;
		this.buffer = buffer;
		this.capacity = capacity;
	}
	
	public long getAddress() {
		return address;
	}
	
	public long getCapacity() {
		return capacity;
	}
}
