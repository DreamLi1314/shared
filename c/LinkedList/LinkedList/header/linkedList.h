#ifndef _List_H

#define null NULL;

struct Node;
typedef struct Node *PtrToNode;
typedef PtrToNode List;
typedef PtrToNode Position;

typedef int ElementType;

int equals(ElementType obj1, ElementType obj2);

List makeEmpty(List L);

int isEmpty(List L);

int isLast(Position p, List L);

Position find(ElementType e, List L);

void deleteItem(ElementType e, List L);

Position findPrevious(ElementType e, List L);

void insert(ElementType e, List L, Position p);

void deleteList(List L);

Position header(List L);

Position first(List L);

Position advance(Position p);

ElementType retrieve(Position p);

#endif /* _List_H */
