#include "MinHeap.hpp"
#include <limits>

MinHeap::MinHeap(int capacity) {
	size = 0;
	this->capacity = capacity;
	data = new int[capacity + 1];
}

void MinHeap::Insert(int key) {
	if (size == capacity) return;
	data[size + 1] = key;
	int c = size + 1;
	int p = Parent(c);
	while (p != 0) {
		if (data[p] <= data[c]) {
			break;
		}

		Swap(data[p], data[c]);
		c = p;
		p = Parent(p);
	}
	size++;
}

int MinHeap::ExtractMin() {
	if (Empty()) return INT_MIN;
	Swap(data[1], data[size--]);
	Heapify(1);

	return data[size + 1];
}

bool MinHeap::Empty() const {
	return size == 0;
}

void MinHeap::Swap(int& a, int& b) {
	int tmp = a;
	a = b;
	b = tmp;
}

int MinHeap::Parent(int index) {
	return index / 2;
}

void MinHeap::Heapify(int index) {
	int tmp = index;
	int left = Left(index);
	int right = Right(index);
	if (left <= size && data[left] < data[tmp]) {
		tmp = left;
	}

	if (right <= size && data[right] < data[tmp]) {
		tmp = right;
	}

	if (tmp != index) {
		Swap(data[tmp], data[index]);
		Heapify(tmp);
	}
}

int MinHeap::Left(int index) {
	return index * 2;
}

int MinHeap::Right(int index) {
	return index * 2 + 1;
}

std::ostream& operator<<(std::ostream& os, const MinHeap& heap) {
	if (heap.Empty()) {
		os << "Empty Heap!";
	}
	else {
		for (int i = 1; i <= heap.size; i++) {
			os << heap.data[i] << "\t";
		}
	}

	return os;
}
