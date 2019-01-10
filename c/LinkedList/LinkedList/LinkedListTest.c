#include<stdio.h>
#include<stdlib.h>
#include "adt/linkedList.c"

int main(int argc, char const *argv[])
{
    List list = (List) malloc(sizeof(List));
    list->elem = 3;

    throwError("This is my info...");

    printf("Hello World!%d\n", list->elem);

    free(list);

    return 0;
}
