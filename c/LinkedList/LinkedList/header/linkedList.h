#ifndef _List_H

#define null NULL

struct Node;
typedef struct Node *PtrToNode;
typedef PtrToNode List;
typedef PtrToNode Position;

typedef int ElementType;

/**
 * 定义 ElementType 相等的规则
 */
int equals(ElementType obj1, ElementType obj2);

/**
 * 将 List 置空(只有一个头结点).
 */
List makeEmpty(List L);

/**
 * 判断 List 是否是为空链表
 */
int isEmpty(List L);

/**
 * 判断指定位置 p 是否是 List 链表的最后一个元素.
 */
int isLast(Position p, List L);

/**
 * 从链表 L 中查找第一个节点的 ElementType 等于 e 的节点位置, 相等规则由 equals 方法指定
 */
Position find(ElementType e, List L);

/**
 * 删除指定 ElementType 第一次出现的结点
 */
void deleteItem(ElementType e, List L);

/**
 * 查找指定 ElementType 的前驱结点
 */
Position findPrevious(ElementType e, List L);

/**
 * 在 p 后面插入一个节点, 节点值为 e
 */
void insert(ElementType e, List L, Position p);

void deleteList(List L);

Position header(List L);

Position first(List L);

Position advance(Position p);

ElementType retrieve(Position p);

#endif /* _List_H */
