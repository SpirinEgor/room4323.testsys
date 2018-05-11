/**
 * Standard checker: compare two 32-bit signed integers
 */

#define NOFOOTER

#include "testlib.h"

int main(int argc, char *argv[])
{
  registerTestlibCmd(argc, argv);

  int a = ouf.readInt();
  int b = ans.readInt();

  if (a != b)
    quitf(_wa, "contestant(%d) != jury(%d)", a, b);
  quitf(_ok, "OK (%d)", a);
  return 0;
}
