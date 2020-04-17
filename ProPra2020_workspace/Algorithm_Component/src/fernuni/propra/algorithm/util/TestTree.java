package fernuni.propra.algorithm.util;

import java.util.Comparator;

public class TestTree {

	public static void main(String[] args) {
		Tree<IntegerElement> tree = new Tree<IntegerElement>(new Comparator<IntegerElement>() {

			@Override
			public int compare(IntegerElement o1, IntegerElement o2) {
				if (o1.getValue()<o2.getValue()) {
					return -1;
				} else if (o1.getValue()>o2.getValue()) {
					return 1;
				}
				return 0;
			}
		});
		IntegerElement i1 = new IntegerElement(15);
		IntegerElement i2 = new IntegerElement(115);
		IntegerElement i3 = new IntegerElement(1);
		IntegerElement i4 = new IntegerElement(1);
		IntegerElement i5 = new IntegerElement(-15);
		IntegerElement i6 = new IntegerElement(14);
		
		tree.insert(i1);
		tree.insert(i2);
		tree.insert(i3);
		tree.insert(i4);
		tree.insert(i5);
		tree.printInOrder();
		System.out.println();
		System.out.println(tree.firstHigher(i6).toString());

	}

}
