#include <iostream>
#include <string>
#include <vector>

using namespace std;

long bits2decimal(const vector<bool>& bits);
string bits2string(const vector<bool>& bits);
vector<bool> string2bits(const string& arg);

int main(int argc, char** argv)
{
  cout << "Welcome to the BinaryCalculator" << endl;

  string arg1, op, arg2;

  while(cin.good()){
    if((cin >> arg1).eof()){
      break;// end of input
    }
    if(arg1 == "quit" || arg1 == "QUIT"){
      break;
    }
    if((cin >> op).eof()){
      break;// end of input
    }
    if((cin >> arg2).eof()){
      break;// end of input
    }

    if(arg1.length() != arg1.length()){
      cerr << "ERROR: '" << arg1 << "' and '" << arg2 << "' are not the same length." << endl;
      continue;
    }
    /*
     * TODO: the strings arg1 and arg2 are all 1 or 0, convert to an array of characters
     */
    vector<bool> bits1 = string2bits(arg1);
    vector<bool> bits2 = string2bits(arg2);

    /*
     * TODO: using the "operator" variable, do the appropriate operations
     */
    if(op == "+"){
      // TODO: etc
    }
    
    /*
     * TODO: print out the result in both binary and decimal.
     */
    /*
     * Binary (for debugging):
     * 
     * TODO: always use "stderr" for debugging in this project
     */
    cerr << "DEBUG: " << bits2string(bits1) << " " << op << " " << bits2string(bits2) << " = TODO" << endl;
			
    /*
     * Decimal (actual output):
     */
    cout << bits2decimal(bits1) << " " << op << " " << bits2decimal(bits2) << " = TODO" << endl;
  }
  return 0;
}

vector<bool> string2bits(const string& arg)
{
  // TODO: write the code that converts a string like "010101" into an array of 1 and 0
  // TODO: take care of the order of bits.  The character at position 0 in the string, is the MSB
    vector<bool> arr = 
  return vector<bool>();
}
	
string bits2string(const vector<bool>& bits)
{
  // TODO: write the code that converts an array of bits back into a String of '0' and '1' chars.
  // TODO: make sure you treat the order of bits the same as you do in string2bits.
  return string("FIXME");
}

long bits2decimal(const vector<bool>& bits)
{
  // TODO: write the code that converts an array of bits into an integer value.
  // TODO: make sure you treat the order of bits the same as you do in string2bits.
  return 0;
}

