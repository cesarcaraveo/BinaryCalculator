import java.util.Scanner;

public class BinaryCalculator
{
	public static void main(String[] args)
	{
//		boolean[] zero = string2bits("0000");
//		boolean[] seven = string2bits("1111");
//		boolean[] remainder = new boolean[zero.length];
//		
//		boolean[] result = divide(zero, seven, remainder);
//		System.err.println(String.format("DEBUG: %s %s %s = %s", bits2string(zero), "/", bits2string(seven), bits2string(result) + "R" + bits2string(remainder)));
//		// Decimal (actual output):
//		System.out.println(String.format("%d %s %d = %d%s%d",
//				bits2decimal(zero), "/", bits2decimal(seven), bits2decimal(result), "R", bits2decimal(remainder)));

		System.out.println("Welcome to the BinaryCalculator");
		Scanner in = new Scanner(System.in);
		while(true){
			String arg1;
			
			// first set of bits
			if(in.hasNext()){
				arg1 = in.next();
				if(arg1.equalsIgnoreCase("QUIT")){
					break;
				}
			}
		
			else {
				break;
			}
			
			String operator = "?", arg2 = "0";
			
			// operator
			if(in.hasNext()){
				operator = in.next();
			}
			
			// second set of bits
			if(in.hasNext()){
				arg2 = in.next();
			}
			
			if(arg1.length() != arg2.length()){
				System.err.println("ERROR: '" + arg1 + "' and '" + arg2 + "' are not the same length.");
				continue;
			}
			
			// convert to an array of booleans
			boolean[] bits1 = string2bits(arg1);
			boolean[] bits2 = string2bits(arg2);
			

			// using the "operator" variable, do the appropriate operations
			
			boolean[] result = new boolean[bits1.length];
			boolean[] remainder = null;
			boolean divideUsed = false;
			boolean divideByZero = false;
			
			if (operator.equals("+")) {
				result = add(bits1, bits2);
			}
			
			if (operator.equals("-")) {
				result = subtract(bits1, bits2);
			}
			
			if (operator.equals("*")) {
				result = multiply(bits1, bits2);
			}
			
			if (operator.equals("/")) {
				divideUsed = true;
				remainder = new boolean[bits1.length];
				
				// check if dividing by 0
				if (isZero(bits2)) {
					divideByZero = true;
				}

				else {
					result = divide(bits1, bits2, remainder);
					//System.err.println("result: " + bits2string(result));
				}
			}

			// print out the result in both binary and decimal.
			
			// Binary (for debugging):
			// always use "System.err" for debugging in this project
			
			// if dividing
			if (divideUsed) { 
				
				// if dividing by zero
				if (divideByZero) {
					//System.err.println(String.format("DEBUG: %s %s %s = %s", bits2string(bits1), operator, bits2string(bits2), "ERROR"));
					// Decimal (actual output):
					System.out.println(String.format("%d %s %d = %s",
							bits2decimal(bits1), operator, bits2decimal(bits2), "ERROR"));
				}

				// if not dividing by 0
				else {
					//System.err.println(String.format("DEBUG: %s %s %s = %s", bits2string(bits1), operator, bits2string(bits2), bits2string(result) + "R" + bits2string(remainder)));
					// Decimal (actual output):
					System.out.println(String.format("%d %s %d = %d%s%d",
							bits2decimal(bits1), operator, bits2decimal(bits2), bits2decimal(result), "R", bits2decimal(remainder)));
				}
			}

			// not dividing
			else {
				//System.err.println(String.format("DEBUG: %s %s %s = %s", bits2string(bits1), operator, bits2string(bits2), bits2string(result)));
				// Decimal (actual output):
				System.out.println(String.format("%d %s %d = %d",
						bits2decimal(bits1), operator, bits2decimal(bits2), bits2decimal(result)));
			}
		}
		
		in.close();
	}

	private static boolean[] string2bits(String arg1)
	{
		
		//System.err.println("string2bits: " + arg1);
		// converts a string like "010101" into an array of booleans
		boolean[] array = new boolean[arg1.length()]; // ex. 5
		
		//System.err.println("after creating array: " + bits2string(array));
		
		for (int i = array.length - 1; i >= 0; i--) {  // ex. 0, 1, 2, 3, 4 (.len returns 5)
			if (arg1.charAt(i) == '1') {
				array[i] = true; // true = 1
			}
		}
		
		//System.err.println("initializing array: " + bits2string(array));
		return array;
	}
	
	private static String bits2string(boolean[] bits)
	{
		// converts an array of bits back into a String of '0' and '1' chars.
		StringBuilder string = new StringBuilder();
		
		for (int i = 0; i < bits.length; i++) {
			if (bits[i]) { // if bits[i] is a 1
				string.append("1");
			}
			
			else { // if bits[i] is a 0
				string.append("0");
			}
		}

		return string.toString();
	}
	
	private static long bits2decimal(boolean[] bits1) {
		// converts an array of bits into an integer value.
		long result = 0; // result to return
		int len = bits1.length - 1; // len - 1 (max index value)
		
		if (bits1[0]) { // if first element MSB is a 1
			result += -1 * (long)Math.pow(2, len); // it is negative
		}
		
		for (int i = 1; i < len + 1; i++) { // start at 1 until max index value (included)
			if (bits1[i]) { // if element is a 1
				result += (long)Math.pow(2, len - i); // add value to result
			}
			
			else {
				result += 0; // don't add anything
			}
		}
		
		return result; // return result 
	}
	
	public static boolean[] add(boolean[] a, boolean[] b) {
		boolean[] result = new boolean[a.length]; // array for final result
		boolean carryBit = false; // keeps track of carry bit
		
		// +- = no overflow, ++ overflow if result sign is 1, -- overflow if result sign is 0
		for (int i = a.length - 1; i >= 0; i--) { // goes through all bits except MSB
			if (a[i] && b[i] && !carryBit) { // 1 + 1 + 0 = 0 carry 1
				carryBit = true;
				result[i] = false;
			}
			
			else if (a[i] && b[i] && carryBit) { // 1 + 1 + 1 = 1 carry 1
				carryBit = true;
				result[i] = true;
			}
			
			else if ((a[i] != b[i]) && carryBit) { // 1 + 0 + 1 = 0 carry 1
				carryBit = true;
				result[i] = false;
			}
			
			else if ((a[i] != b[i]) && !carryBit) { // 1 + 0 + 0 = 1 carry 0
				carryBit = false;
				result[i] = true;
			}
			
			else if (!a[i] && !b[i] && !carryBit) { // 0 + 0 + 0 = 0 carry 0
				carryBit = false;
				result[i] = false;
			}
			
			else { // 0 + 0 + 1 = 1 carry 0
				carryBit = false;
				result[i] = true;
			}
		}
	
		return result;
	}
	
	public static boolean[] subtract(boolean[] a, boolean[] b) {
		boolean[] result = new boolean[a.length];
		boolean[] one = new boolean[b.length];
		boolean[] temp = new boolean[b.length];
		
		for (int i = 0; i < b.length; i++) {
			temp[i] = b[i];
		}
		
		one[b.length - 1] = true; // make bool[] one equal one
		
		for (int i = 0; i < temp.length; i++) { // go through elements in b (a-b)
			if (temp[i]) { // if b is a 1
				temp[i] = false; // flip the bit to 0
			}
			
			else { // if b is a 0
				temp[i] = true; // flip the bit to a 1
			}
		}
		
		temp = add(temp, one); // finishing two's complement
		result = add(a, temp); // add a and negative b (a + -b)
		
		return result;
	}
	
	public static boolean[] multiply(boolean[] multi, boolean m[]) {
		boolean[] product = new boolean[multi.length + m.length]; // product array is twice as big 
		boolean[] multiplier = new boolean[multi.length];
		boolean[] multiplicand = expand(m);
		
		for (int i = 0; i < multi.length; i++) {
			multiplier[i] = multi[i];
		}
		
		for (int i = multiplier.length - 1; i >= 0; i--) { // iterate through n amt of times (n = bits in values)
			if (multiplier[multiplier.length - 1]) { // if LSB in multiplier is a 1
				product = add(product, multiplicand); // add multiplicand to product and store result in product
			}
		
			shiftLeft(multiplicand);
			shiftRight(multiplier);
		}
		
		return condense(product);
	}
	
	public static boolean[] divide(boolean[] dividend, boolean[] divi, boolean[] rem) { // do one more repetition than the number of bits
		int divLen = divi.length; // original bit amount
		boolean[] remainder = new boolean[dividend.length];
		boolean[] divisor = new boolean[divLen];
		boolean sign = false;
		boolean isNumNegative = false;
		
		// copy of divisor
		for (int i = 0; i < divLen; i++) {
			divisor[i] = divi[i];
		}
		
		// remainder equals dividend
		for (int i = 0; i < dividend.length; i++) {
			remainder[i] = dividend[i];
		}
	
		// if signs are different
		if (dividend[0] != divisor[0]) {
			sign = true;
		}
		
		// if numerator is negative
		if (dividend[0]) {
			isNumNegative = true;
		}

		// if dividend is negative
		if (dividend[0]) {
			dividend = twos(dividend);
		}

		// if divisor is negative
		if (divisor[0]) {
			divisor = twos(divisor);
		}
		
		boolean[] quotient = new boolean[divLen]; // quotient has same amt of bits as original inputs
		divisor = expand(divisor); // double size of divisor
		remainder = expand(remainder); // double size of remainder
		
		// shift values in divisor
		for (int i = 0; i < divLen; i++) {
			shiftLeft(divisor);
		}
			
		// dividing
		for (int i = 0; i <= divLen; i++) {
			remainder = subtract(remainder, divisor);

			if (remainder[0]) { // checks if less than 0
				remainder = add(remainder, divisor);

				shiftLeft(quotient);
			}
			
			else { // if greater than or equal to 0
				shiftLeft(quotient);
				quotient[quotient.length - 1] = true; // sets LSB (right most bit) to 1
			}
			
			divisor = shiftRight(divisor);
		}
		
		if (!sign) {
			quotient = twos(quotient);
		}
		
		if (isNumNegative) {
			remainder = twos(remainder);
		}

		//System.err.println("rem before condensing: " + bits2string(remainder));
		remainder = condense(remainder);
		
		// copy over remainder to parameter rem array
		for (int i = 0; i < remainder.length; i++) {
			rem[i] = remainder[i];
		}
		
		return quotient;
	}
	
	private static boolean[] shiftRight(boolean[] array) {
		boolean temp1 = false;
		boolean temp2 = false;
		
		for (int i = 0; i < array.length; i++) {
			temp1 = array[i];
			array[i] = temp2;
			temp2 = temp1;
		}
		
		return array;
	}
	
	private static boolean[] shiftLeft(boolean[] array) {
		boolean temp1 = false;
		boolean temp2 = false;
		
		for (int i = array.length - 1; i >= 0; i--) {
			temp1 = array[i];
			array[i] = temp2;
			temp2 = temp1;
		}
		
		return array;
	}
	
	private static boolean[] expand(boolean[] array) {
		boolean[] expandedArray = new boolean[array.length * 2];

		int x = 0;
		for (int i = array.length; i < expandedArray.length; i++) {
			expandedArray[i] = array[x];
			x++;
		}
		
		return expandedArray;
	}
	
	private static boolean[] condense(boolean[] array) {
		boolean[] condensedArray = new boolean[array.length / 2];
		
		int x = 0;
		for (int i = array.length / 2; i < array.length; i++) {
			condensedArray[x] = array[i];
			x++;
		}

		return condensedArray;
	}
	
	public static boolean isZero(boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i]) { // if element is a 1
				return false;
			}
		}
		
		return true; // all 0s
	}
	
	private static boolean[] twos(boolean[] array) {
		boolean[] temp = new boolean[array.length];
		boolean[] one = new boolean[array.length];
		one[one.length - 1] = true;
		
		for (int i = 0; i < array.length; i++) {
			temp[i] = array[i];
		}
		
		for (int i = 0; i < temp.length; i++) { // go through elements in b (a-b)
			if (temp[i]) { // if b is a 1
				temp[i] = false; // flip the bit to 0
			}
			
			else { // if b is a 0
				temp[i] = true; // flip the bit to a 1
			}
		}
		
		temp = add(temp, one); // finishing two's complement
		
		return temp;
	}

	
	
}
