#include<stdio.h>
#include<stdlib.h>
#include "../util/common.h"

#define MAX_DEGREE 100

struct Node {
    int coffs[MAX_DEGREE + 1];
    int hightPower;
};

typedef struct Node * Polynomial;

/**
 * reset a polynimial.
 */
Polynomial reset(Polynomial polynomial/* in or out*/) {
    if(polynomial == NULL) {
        polynomial = (Polynomial) malloc(sizeof(Polynomial));
    }

    polynomial->hightPower = 0;

    for (size_t i = 0; i < MAX_DEGREE + 1; i++) {
        polynomial->coffs[i] = 0;
    }

    return polynomial;
}

/**
 * add method.
 */
Polynomial add(Polynomial p1, Polynomial p2, Polynomial result) {
    result = reset(result);
    result->hightPower = max(p1->hightPower, p2->hightPower);

    for(int i = 0; i < MAX_DEGREE + 1; i++) {
        result->coffs[i] = p1->coffs[i] + p2->coffs[i];
    }
}

int main(int argc, char const *argv[]) {

    return 0;
}
