#ifndef _ADT_LINKED_LIST_IMPL_
#define _ADT_LINKED_LIST_IMPL_

#include "../header/linkedList.h"

struct Node
{
  ElementType elem;
  Position next;
};

int equals(ElementType obj1, ElementType obj2) {
  return obj1 == obj2;
}

void gcList(List L) {
    if(L != null) {
        Position temp;

        while(L != NULL) {
          temp = L;
          L = L->next;
          free(temp);
        }
    }
}

List makeEmpty(List L) {
  if(L != NULL) {
    gcList(L->next);
    L->next = null;
  }
  else {
    L = (List) malloc(sizeof(List));
  }

  return L;
}

int isEmpty(List L) {
  return L != NULL && L->next == NULL;
}

int isLast(Position p, List L) {
  return p != NULL && p->next == NULL;
}

Position find(ElementType e, List L) {
  Position result = NULL;

  // linked list has header point
  if(L != NULL && L->next != NULL) {
    do {
      if(equals(L->next->elem, e)) {
        result = L->next;
        break;
      }

      L = L->next;
    } while(L != NULL);
  }

  return result;
}

void deleteItem(ElementType e, List L) {
    Position p = findPrevious(e, L);

    if(!isLast(p, L)) {
        Position delPosition = p->next;
        p->next = delPosition->next;
        free(delPosition);
    }
}

Position findPrevious(ElementType e, List L) {
    Position result = null;

    while (L != null && L->next != null) {
        if(equals(L->next->elem, e)) {
            // find it
            result = L;
            break;
        }

        L = L->next;
    }

    return result;
}

void insert(ElementType e, List L, Position p) {
    if(p != null) {
        Position insertNode = (Position) malloc(sizeof(Position));

        if(insertNode == null) {
            throwError("Out of space!");
        }

        insertNode->elem = e;

        insertNode->next = p->next;
        p->next = insertNode;
    }
}

#endif
