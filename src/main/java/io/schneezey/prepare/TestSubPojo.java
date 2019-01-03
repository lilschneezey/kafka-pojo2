package io.schneezey.prepare;

/*

 */
public class TestSubPojo {
	

	// Primitives
	private float subPFloat = (float) 0.0;
	private double subPDouble = 0.0;
	private int subPInt = 2000;
	private long subPLong = 3000L;
	private boolean subPBoolean = true;	
	private byte[] subPBytes = new byte[0];

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("testFloat: ").append(subPFloat).append("\n")
		.append("testDouble: ").append(subPDouble).append("\n")
		.append("testInt: ").append(subPInt).append("\n")
		.append("testLong: ").append(subPLong).append("\n")
		.append("testBoolean: ").append(subPBoolean).append("\n")
		.append("testBytes: ").append(subPBytes==null ? "null" : subPBytes).append("\n")

		;
		return buffer.toString();
	}
	
	public float getTestFloat() {
		return subPFloat;
	}

	public void setTestFloat(float testFloat) {
		this.subPFloat = testFloat;
	}

	public double getTestDouble() {
		return subPDouble;
	}

	public void setTestDouble(double testDouble) {
		this.subPDouble = testDouble;
	}

	public int getTestInt() {
		return subPInt;
	}

	public void setTestInt(int testInt) {
		this.subPInt = testInt;
	}

	public long getTestLong() {
		return subPLong;
	}

	public void setTestLong(long testLong) {
		this.subPLong = testLong;
	}

	public boolean isTestBoolean() {
		return subPBoolean;
	}

	public void setTestBoolean(boolean testBoolean) {
		this.subPBoolean = testBoolean;
	}

	public byte[] getTestBytes() {
		return subPBytes;
	}

	public void setTestBytes(byte[] testBytes) {
		this.subPBytes = testBytes;
	}

}