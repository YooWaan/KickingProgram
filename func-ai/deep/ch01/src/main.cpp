

#include <stdio.h>
#include <stdlib.h>


#define BUFSIZE 256


int main()
{
  char linebuf[BUFSIZE];
  double data;
  double sum = 0.0;
  double sum2= 0.0;

  while (fgets(linebuf, BUFSIZE, stdin) != NULL) {
    if (sscanf(linebuf, "%lf", &data) != 0) {
      sum += data;
      sum2 += data * data;
      printf("%lf\t %lf \n", sum, sum2);
    }
  }

  return 0;
}


