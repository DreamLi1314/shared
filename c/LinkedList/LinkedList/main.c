#include<stdio.h>
#include<stdlib.h>

#include "adt/linkedList.c"

#include "best-practices/polynomial-array.c"

void linkedListTest() {
    List list = (List) malloc(sizeof(List));
    list->elem = 3;
    list->next = NULL;
    
    throwWarning("This is my info...");
    
    printf("Hello World!%d\n", list->elem);
    
    free(list);
}

void polynomialArrayTest() {
    Polynomial p1 = reset(null);
    Polynomial p2 = reset(null);
    
    int hightPower1 = 4;
    int hightPower2 = 6;
    
    p1->hightPower = hightPower1;
    p1->coffs [0] = 3;
    p1->coffs [1] = 0;
    p1->coffs [2] = -2;
    p1->coffs [3] = -5;
    p1->coffs [4] = 1;
    
    p2->hightPower = hightPower2;
    p2->coffs [0] = -4;
    p2->coffs [1] = 3;
    p2->coffs [2] = -2;
    p2->coffs [3] = 1;
    p2->coffs [4] = 0;
    p2->coffs [5] = 5;
    p2->coffs [6] = -9;
    
    Polynomial sum = add(p1, p2, null);
    printf("Sum hightPower: %d\n", sum->hightPower);
    
    for(int i = 0; i< sum->hightPower + 1; i++) {
        printf("sum[%d]=%d, ", i, sum->coffs[i]);
    }
    
    Polynomial product = mutiply(p1, p2, null);
    printf("\n\nThe hight power of product is: %d\n", product->hightPower);
    
    for(int i = 0; i < product->hightPower + 1; i++) {
        printf("product[%d]=%d, ", i, product->coffs[i]);
    }
    
    free(p1);
    free(p2);
    free(sum);
    free(product);
}

int main(int argc, char const *argv[])
{
    linkedListTest();
    
    polynomialArrayTest();

    return 0;
}
