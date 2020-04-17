package fernuni.propra.algorithm.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class BinaryNode<T> {
	private T key;
	private BinaryNode<T> left,right;
	
	public BinaryNode(BinaryNode<T> left, BinaryNode<T> right, T key) {
		this.left = left;
		this.right = right;
		this.key = key;
	}
	
	protected boolean contains(T key, Comparator<? super T> comparator) {
		if (comparator.compare(this.key, key)== 0) {
			return true;
		} 
		boolean leftContains = false;
		boolean rightContains = false;
		
		if (left != null) {
			leftContains = left.contains(key, comparator);
		}
		
		if (right != null) {
			rightContains = right.contains(key, comparator);
		}
		
		return leftContains || rightContains;
	}
	
	protected T firstHigher(T key, Comparator<? super T> comparator) {
		if (comparator.compare(this.key, key) > 0) {
			if(left == null || (comparator.compare(left.key, key)<0)) {
				return this.key;
			} else {
				return left.firstHigher(key, comparator);
			}
		} else {
			if (right == null) {
				return null;
			} else {
				return right.firstHigher(key, comparator);
			}
		}
	}
	
	protected void insert(T key, Comparator<? super T> comparator) {
		if (this.key == null) {
			this.key = key;
			return;
		}
		
		if (comparator.compare(this.key, key) > 0) {
			if (left == null) {
				BinaryNode<T> newNode = new BinaryNode<T>(null, null, key);
				left = newNode;
			} else {
				left.insert(key, comparator);
			}
		} else {
			if (right == null) {
				BinaryNode<T> newNode = new BinaryNode<T>(null, null, key);
				right = newNode;
			} else {
				right.insert(key, comparator);
			}
		}
	}
	
	protected LinkedList<T> inorderNode() {
		LinkedList<T> listAtNode = new LinkedList<T>();
		if (left != null ) {
			listAtNode.addAll(left.inorderNode());
		}
		if (this.key != null) {
			listAtNode.add(key);
		}
		if (right != null) {
			listAtNode.addAll(right.inorderNode());
		}
		return listAtNode;
	}
	
	
	

}