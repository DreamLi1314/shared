#include "../header/linkedList.h"

struct Node
{
  ElementType elem;
  Position next;
};

int equals(ElementType obj1, ElementType obj2) {
  return obj1 == obj2;
}

List makeEmpty(List L) {
  if(L != NULL) {
    Position delList = L->next;
    L->next = NULL;

    while(delList != NULL) {
      Position temp = delList;
      delList = delList->next;
      free(temp);
    }
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

void deleteItem(ElementType e, List L);

Position findPrevious(ElementType e, List L);

void insert(ElementType e, List L, Position p);

void deleteList(List L);

Position header(List L);

Position first(List L);

Position advance(Position p);

ElementType retrieve(Position p);
