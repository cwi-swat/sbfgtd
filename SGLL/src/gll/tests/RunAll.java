package gll.tests;

public class RunAll{
	
	public static void main(String[] args){
		System.out.println("Simple1:");
		Simple1.main(args);
		System.out.println();
		System.out.println("Simple2:");
		Simple2.main(args);
		System.out.println();

		System.out.println("CharRange:");
		CharRange.main(args);
		System.out.println();
		System.out.println("CILiteralTest:");
		CILiteral.main(args);
		System.out.println();

		System.out.println("Ambiguous1:");
		Ambiguous1.main(args);
		System.out.println();
		System.out.println("Ambiguous2:");
		Ambiguous2.main(args);
		System.out.println();
		System.out.println("Ambiguous3:");
		Ambiguous3.main(args);
		System.out.println();
		System.out.println("Ambiguous4:");
		Ambiguous4.main(args);
		System.out.println();
		System.out.println("Ambiguous5:");
		Ambiguous5.main(args);
		System.out.println();
		System.out.println("Ambiguous6:");
		Ambiguous6.main(args);
		System.out.println();
		
		System.out.println("AmbiguousRecursive:");
		AmbiguousRecursive.main(args);
		System.out.println();
		
		System.out.println("LeftRecursion:");
		LeftRecursion.main(args);
		System.out.println();
		
		System.out.println("MergeAndSplit1:");
		MergeAndSplit1.main(args);
		System.out.println();
		System.out.println("MergeAndSplit2:");
		MergeAndSplit2.main(args);
		System.out.println();
		System.out.println("MergeAndSplit3:");
		MergeAndSplit3.main(args);
		System.out.println();
		
		System.out.println("NotAUselessSelfLoop:");
		NotAUselessSelfLoop.main(args);
		System.out.println();
		
		//System.out.println("UselessSelfLoop:");
		//UselessSelfLoop.main(args);
		//System.out.println();
		
		System.out.println("Epsilon:");
		Epsilon.main(args);
		System.out.println();
		
		//System.out.println("LeftRecursionEpsilon:");
		//LeftRecursionEpsilon.main(args);
		//System.out.println();
		
		System.out.println("Optional1:");
		Optional1.main(args);
		System.out.println();
		System.out.println("Optional2:");
		Optional2.main(args);
		System.out.println();
		System.out.println("Optional3:");
		Optional3.main(args);
		System.out.println();
		
		System.out.println("CharPlusList:");
		CharPlusList.main(args);
		System.out.println();
		System.out.println("CharStarList:");
		CharStarList.main(args);
		System.out.println();
		System.out.println("NonTerminalPlusList:");
		NonTerminalPlusList.main(args);
		System.out.println();
		System.out.println("NonTerminalStarList:");
		NonTerminalStarList.main(args);
		System.out.println();
		System.out.println("AmbiguousNonTerminalPlusList1:");
		AmbiguousNonTerminalPlusList1.main(args);
		System.out.println();
		System.out.println("AmbiguousNonTerminalPlusList2:");
		AmbiguousNonTerminalPlusList2.main(args);
		System.out.println();
		System.out.println("AmbiguousNestedPlusList:");
		AmbiguousNestedPlusList.main(args);
		System.out.println();
		
		System.out.println("SeparatedPlusList:");
		SeparatedPlusList.main(args);
		System.out.println();
		System.out.println("SeparatedStarList:");
		SeparatedStarList.main(args);
		System.out.println();
	}
}
