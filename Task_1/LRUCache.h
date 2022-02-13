#pragma once

#include <unordered_map>

class LRUCache {
public:
    LRUCache(int capacity);

    int get(int key);

    void put(int key, int value);

private:
    struct ListNode {
        int key = -1;
        int val = -1;
        ListNode *next = nullptr;
        ListNode *prev = nullptr;
    };

    static void detach(ListNode *node);

    static void insertAfter(ListNode *node, ListNode *n);

    void update(int key);

    std::unordered_map<int, ListNode *> map;
    int capacity;
    int size = 0;
    ListNode *st;
    ListNode *fn;
};