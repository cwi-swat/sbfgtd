set term png
set xlabel "Input characters"
set ylabel "Milliseconds"
set output "grammar-factoring.png"
plot "grammar-factoring.data" using 1:2 title "Non-factored" w linespoints, "grammar-factoring.data" using 1:4 title "Left-factored" w linespoints, "grammar-factoring.data" using 1:6 title "Non-factored - prefix shared" w linespoints
