#include <iostream>
#include "MinHeap.hpp"

#define SIZE 10

using namespace std;
void HeapTest();


int main(int argc, char* agrv[]) {
	HeapTest();

	return 0;
}

void HeapTest() {
	MinHeap heap(SIZE);
	for (int i = 0; i < SIZE; i++) {
		heap.Insert(i);
	}
	cout << "===== Heap =====" << endl;
	cout << heap << endl;
	while (!heap.Empty()) {
		cout << "Minimum Value: " << heap.ExtractMin() << endl << endl;
		cout << "===== Heap =====" << endl;
		cout << heap << endl;
	}
}