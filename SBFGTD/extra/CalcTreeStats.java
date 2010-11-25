
public class CalcTreeStats{
	
	public static void main(String[] args){
		int N = 50;
		
		// Results.
		int results = 1;
		if(N > 0){
			for(int i = N; i >= 0; --i){
				results += i + 1;
			}
		}
		System.out.println(results);
		
		// PrefixLists.
		int prefixLists = 2;
		if(N > 0){
			for(int i = N; i >= 1; --i){
				prefixLists += ((i + 1) * 2);
			}
		}
		System.out.println(prefixLists);
		
		// Nodes.
		int nodes = 2;
		int increment = 2;
		if(N > 0){
			for(int i = 0; i < N; ++i){
				increment += (i + 1) + 1;
				nodes += increment;
			}
		}
		System.out.println(nodes);
	}
}
