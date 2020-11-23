#pragma once
#include <iostream>

class MinHeap {
public:
	// Constructor with the given capacity
	MinHeap(int capacity);
	// Insert key to heap
	void Insert(int key);
	// Extra the minimum value from the heap
	int ExtractMin();
	// Return true if the heap is empty false otherwise
	bool Empty() const;
	friend std::ostream& operator << (std::ostream& os, const MinHeap& heap);

private:
	void Swap(int& a, int& b);
	void Heapify(int index);
	int Left(int index);
	int Right(int index);
	int Parent(int index);

private:
	int size;
	int capacity;
	int* data;
};