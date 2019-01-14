#include<stdio.h>
#include<stdlib.h>
#include "../util/common.h"

#define MAX_DEGREE 100

struct PolynomialNode {
    int coffs[MAX_DEGREE + 1];
    int hightPower;
};

typedef struct PolynomialNode * Polynomial;

/**
 * reset a polynimial.
 */
Polynomial reset(Polynomial polynomial/* in or out*/) {
    if(polynomial == NULL) {
        polynomial = (Polynomial) malloc(sizeof(Polynomial));
    }

    polynomial->hightPower = 0;

    for (int i = 0; i < MAX_DEGREE + 1; i++) {
        polynomial->coffs[i] = 0;
    }

    return polynomial;
}

/**
 * 计算两个多项式的和
 */
Polynomial add(Polynomial p1, Polynomial p2, Polynomial result/* in or out */) {
    result = reset(result);
    result->hightPower = max(p1->hightPower, p2->hightPower);

    for(int i = 0; i < result->hightPower + 1; i++) {
        result->coffs[i] = p1->coffs[i] + p2->coffs[i];
    }
    
    return result;
}

/**
  * 计算两个多项式的乘积
 */
Polynomial mutiply(Polynomial p1, Polynomial p2, Polynomial result/* in or out */) {
    result = reset(result);
    result->hightPower = p1->hightPower + p2->hightPower;
    
    for(int i = 0; i < result->hightPower + 1; i++) {
        for(int j = 0; j < result->hightPower + 1; j++) {
            result->coffs[i + j] += p1->coffs[i] * p2->coffs[j];
        }
    }
    
    return result;
}   
