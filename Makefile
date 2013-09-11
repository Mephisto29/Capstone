JC = javac
JFLAGS = -g
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = AdressBookEntry.java BinaryTree.java GUI.java HashList.java HashTable.java ListNode.java Main.java NaryTree.java Node.java Reader.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	@rm *.class
