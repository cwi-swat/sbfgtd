package gtd.tests;

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
		System.out.println("Ambiguous7:");
		Ambiguous7.main(args);
		System.out.println();
		System.out.println("Ambiguous8:");
		Ambiguous8.main(args);
		System.out.println();
		System.out.println("Ambiguous9:");
		Ambiguous9.main(args);
		System.out.println();
		
		System.out.println("BrokenInDepthFirst:");
		BrokenInDepthFirst.main(args);
		System.out.println();
		
		System.out.println("RightRecursion:");
		RightRecursion.main(args);
		System.out.println();
		System.out.println("LeftRecursion:");
		LeftRecursion.main(args);
		System.out.println();
		
		System.out.println("AmbiguousRecursive:");
		AmbiguousRecursive.main(args);
		System.out.println();
		System.out.println("AmbiguousRecursivePrefixShared:");
		AmbiguousRecursivePrefixShared.main(args);
		System.out.println();
		
		System.out.println("SplitAndMerge1:");
		SplitAndMerge1.main(args);
		System.out.println();
		System.out.println("SplitAndMerge2:");
		SplitAndMerge2.main(args);
		System.out.println();
		System.out.println("SplitAndMerge3:");
		SplitAndMerge3.main(args);
		System.out.println();
		
		System.out.println("Epsilon:");
		Epsilon.main(args);
		System.out.println();
		System.out.println("EmptyRightRecursion:");
		EmptyRightRecursion.main(args);
		System.out.println();
		System.out.println("CycleEpsilon:");
		CycleEpsilon.main(args);
		System.out.println();
		
		System.out.println("UselessSelfLoop:");
		UselessSelfLoop.main(args);
		System.out.println();
		System.out.println("NotAUselessSelfLoop:");
		NotAUselessSelfLoop.main(args);
		System.out.println();
		
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
		
		System.out.println("SeparatedPlusList:");
		SeparatedPlusList.main(args);
		System.out.println();
		System.out.println("SeparatedStarList:");
		SeparatedStarList.main(args);
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
		System.out.println("EpsilonList:");
		EpsilonList.main(args);
		System.out.println();
		System.out.println("AmbiguousEpsilonList:");
		AmbiguousEpsilonList.main(args);
		System.out.println();
		System.out.println("AmbiguousSeparatedEpsilonList:");
		AmbiguousSeparatedEpsilonList.main(args);
		System.out.println();
		
		System.out.println("NullableSharing:");
		NullableSharing.main(args);
		System.out.println();
		
		System.out.println("ListOverlap:");
		ListOverlap.main(args);
		System.out.println();
		
		System.out.println("HiddenRecursionEmpty:");
		HiddenRecursionEmpty.main(args);
		System.out.println();
		System.out.println("HiddenRecursionNonEmpty:");
		HiddenRecursionNonEmpty.main(args);
		System.out.println();
		System.out.println("HiddenRecursionListEmpty:");
		HiddenRecursionListEmpty.main(args);
		System.out.println();
		System.out.println("HiddenHiddenRightRecursive:");
		HiddenHiddenRightRecursive.main(args);
		System.out.println();
		
		System.out.println("AmbiguousRecursiveNullable:");
		AmbiguousRecursiveNullable.main(args);
		System.out.println();
		System.out.println("AmbiguousRecursiveNullablePrefixShared:");
		AmbiguousRecursiveNullablePrefixShared.main(args);
		System.out.println();
	}
}
