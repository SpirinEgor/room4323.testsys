#define NOFOOTER

#include "testlib.h"

const int m = 1000;

int main(int, char *[])
{
  registerValidation();

  inf.readInt(-m, m);
  inf.readSpace();
  inf.readInt(-m, m);
  inf.readEoln();

  inf.readEof();
  return 0;
}
