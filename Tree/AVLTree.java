public class AVLTree<T extends Comparable<T>> {
    private static class Node<T> {
        private T key;
        private int height;
        private Node<T> left;
        private Node<T> right;

        /**
         * Creates a {@code Node} instance with the given key
         * and default height to 1.
         *
         * @param key node key
         */
        public Node(T key) {
            this.key = key;
            left = null;
            right = null;
            height = 1;
        }

        /**
         * Get node key.
         *
         * @return node key
         */
        public T getKey() {
            return key;
        }

        /**
         * Update node key.
         *
         * @param key new key
         */
        public void setKey(T key) {
            this.key = key;
        }

        /**
         * Get node height.
         *
         * @return node height
         */
        public int getHeight() {
            return height;
        }

        /**
         * Update node height.
         *
         * @param height node height
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * Get left child.
         *
         * @return left child
         */
        public Node<T> getLeft() {
            return left;
        }

        /**
         * Update left child.
         *
         * @param left left child
         */
        public void setLeft(Node<T> left) {
            this.left = left;
        }

        /**
         * Get right child.
         *
         * @return right child
         */
        public Node<T> getRight() {
            return right;
        }

        /**
         * Update right child.
         *
         * @param right right child
         */
        public void setRight(Node<T> right) {
            this.right = right;
        }
    }

    private int size;
    private Node<T> root;

    /**
     * Default constructor.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Insert new node to avl tree, no
     * duplicate values are allowed.
     *
     * @param t new node value
     */
    public void insert(T t) {
        root = insert(root, t);
    }

    /**
     * A string representation of the tree.
     *
     * @return string representation of the tree
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, 0);
        return sb.toString();
    }

    private void toString(Node<T> node, StringBuilder sb, int level) {
        if (node == null) {
            return;
        }
        toString(node.getRight(), sb, level + 1);
        for (int i = 0; i < 4 * level; i++) {
            sb.append(" ");
        }
        sb.append(node.getKey() + "\n");
        toString(node.getLeft(), sb, level + 1);
    }

    private Node<T> insert(Node<T> node, T t) {
        if (node == null) {
            size++;
            return new Node<T>(t);
        } else if (node.getKey().compareTo(t) > 0) {
            node.setLeft(insert(node.left, t));
        } else if (node.getKey().compareTo(t) < 0) {
            node.setRight(insert(node.right, t));
        }
        // update tree
        int height = Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
        node.setHeight(height);
        int bf = getBalanceFactor(node);
        // left is heavy
        if (bf > 1 && node.getLeft().getKey().compareTo(t) > 0) {
            return rightRotate(node);
        }
        // right is heavy
        if (bf < -1 && node.getRight().getKey().compareTo(t) < 0) {
            return leftRotate(node);
        }

        if (bf > 1 && node.getLeft().getKey().compareTo(t) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (bf < -1 && node.getRight().getKey().compareTo(t) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Delete node from avl tree.
     *
     * @param t node needs to be deleted
     * @return true if succeed to delete false otherwise
     */
    public boolean delete(T t) {
        int tmp = size;
        root = delete(root, t);
        return tmp != size;
    }

    /**
     * Search node in the avl tree.
     *
     * @param t node needs to be searched
     * @return true if exist in the avl tree false otherwise
     */
    public boolean search(T t) {
        return search(root, t);
    }

    /**
     * Inorder traverse.
     */
    public void inorder() {
        if (root != null) {
            inorder(root);
            System.out.println();
        }
    }

    private void inorder(Node<T> node) {
        if (node == null) {
            return;
        }
        inorder(node.getLeft());
        System.out.print(node.getKey() + " ");
        inorder(node.getRight());
    }

    private boolean search(Node<T> node, T t) {
        if (node == null) {
            return false;
        }
        int comp = node.getKey().compareTo(t);
        if (comp == 0) {
            return true;
        } else if (comp < 0) {
            return search(node.getRight(), t);
        } else {
            return search(node.getLeft(), t);
        }
    }

    private Node<T> delete(Node<T> node, T t) {
        if (node == null) {
            return null;
        }
        int comp = node.getKey().compareTo(t);
        if (comp > 0) {
            node.setLeft(delete(node.getLeft(), t));
        } else if (comp < 0) {
            node.setRight(delete(node.getRight(), t));
        } else {
            size--;
            // at most one child
            if (node.getLeft() == null || node.getRight() == null) {
                node = node.getLeft() == null ? node.getRight() : node.getLeft();
            } else {
                Node<T> tmp = maxValue(node.getLeft());
                node.setKey(tmp.getKey());
                node.setLeft(delete(node.getLeft(), tmp.getKey()));
            }
        }

        if (node == null) {
            return null;
        }

        int height = Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1;
        node.setHeight(height);
        int bf = getBalanceFactor(node);
        if (bf > 1 && getBalanceFactor(root.getLeft()) >= 0) {
            return rightRotate(node);
        }
        if (bf > 1 && getBalanceFactor(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        if (bf < -1 && getBalanceFactor(node.getRight()) <= 0) {
            return leftRotate(node);
        }
        if (bf < -1 && getBalanceFactor(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    private Node<T> maxValue(Node<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }

        return node;
    }

    private Node<T> leftRotate(Node<T> node) {
        Node<T> nodeR = node.getRight(); // node's right child
        Node<T> nodeRL = nodeR.getLeft(); // node's right child's left child
        node.setRight(nodeRL);
        nodeR.setLeft(node);
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        nodeR.setHeight(Math.max(getHeight(nodeR.getLeft()), getHeight(nodeR.getRight())) + 1);

        return nodeR;
    }

    private Node<T> rightRotate(Node<T> node) {
        Node<T> nodeL = node.getLeft();  // node's left child
        Node<T> nodeLR = nodeL.getRight(); // node's left child's right child
        node.setLeft(nodeLR);
        nodeL.setRight(node);
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
        nodeL.setHeight(Math.max(getHeight(nodeL.getLeft()), getHeight(nodeL.getRight())) + 1);

        return nodeL;
    }

    private int getBalanceFactor(Node<T> node) {
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }


    public static void main(String[] args) {
        AVLTree<Integer> a = new AVLTree<Integer>();
        a.insert(3);
        a.insert(10);
        a.insert(8);
        System.out.println(a);
        a.delete(8);
        System.out.println(a);
    }
}
