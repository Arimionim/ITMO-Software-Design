#include "LRUCache.h"
#include <cassert>

LRUCache::LRUCache(int capacity) : capacity(capacity) {
    assert(capacity > 0);
    st = new ListNode({-1, -1});
    fn = new ListNode({-1, -1});
    st->next = fn;
    fn->prev = st;
    assert(size == 0);
    assert(st->next == fn);
    assert(fn->prev == st);
}

int LRUCache::get(int key) {
    if (map[key]) {
        update(key);
        assert(st->next == map[key]);
        return map[key]->val;
    }
    assert(!map[key]);
    return -1;
}


void LRUCache::put(int key, int value) {
    assert(value >= 0);
    assert(st);
    assert(fn);

    update(key);
    map[key]->val = value;
    assert(size <= capacity);
    assert(map[key]);
    assert(map[key]->val == value);
}

/* static */ void LRUCache::detach(ListNode *node) {
    assert(node);

    node->next->prev = node->prev;
    node->prev->next = node->next;
    node->next = nullptr;
    node->prev = nullptr;

    assert(!node->next);
    assert(!node->prev);
}

/* static */ void LRUCache::insertAfter(ListNode *node, ListNode *n) {
    assert(node);
    assert(node->next);
    assert(node->next->prev);
    assert(n);
    assert(!n->next && !n->prev);
    n->next = node->next;
    node->next->prev = n;
    node->next = n;
    n->prev = node;
    assert(n->next == node->next->next);
    assert(n->prev == node);
    assert(node->next == n);
}

void LRUCache::update(int key) {
    assert(key > 0);
    assert(size <= capacity);
    if (!map[key]) {
        auto *node = new ListNode({key});
        map[key] = node;
        size++;
    } else {
        detach(map[key]);
    }
    insertAfter(st, map[key]);

    if (size > capacity) {
        ListNode *last = fn->prev;
        detach(last);
        map[last->key] = nullptr;
        delete last;
        size--;
    }
    assert(size <= capacity);
    assert(st->next == map[key]);
}