package fernuni.propra.algorithm.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Tree<T> {
	private BinaryNode<T> root = new BinaryNode<T>(null, null, null);
	private LinkedList<T> listInOrder;
	private Comparator<? super T> comparator;
	
	public Tree(Comparator< ?  super T> comparator) {
		this.comparator = comparator;
	}
	
	public T firstHigher(T key) {
		return root.firstHigher(key, comparator);
	}
	
	public void insert(T key) {
		root.insert(key, comparator);
	}
	
	public boolean contains(T key) {
		return root.contains(key, comparator);
	}
	
	public Iterator<T> inOrder() {
		listInOrder = root.inorderNode();
		return listInOrder.iterator();
	}
	
	public void printInOrder () {
		Iterator<T> myIterator = this.inOrder();
		while(myIterator.hasNext()) {
			System.out.println(myIterator.next().toString());
		}
	}

}
