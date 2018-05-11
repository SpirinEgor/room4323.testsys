/**
 * Author: Sergey Kopeliovich (Burunduk30@gmail.com)
 */

#include <cstdio>
#include <cassert>
#include <iostream>

using namespace std;

#define NAME "sum"

int main()
{
  assert(freopen(NAME ".in", "r", stdin));
  assert(freopen(NAME ".out", "w", stdout));

  long long a, b;
  cin >> a >> b;
  cout << a + b << endl;
  return 0;
}
