set term png
set xlabel "Input characters"
set ylabel "Milliseconds"
set output "grammar-factoring.png"
set xrange [40000:210000]
set xtics (50000,100000,150000,200000)
set format x "%10.0f"
set yrange [0:1600]
set ytics (200,400,600,800,1000,1200,1400,1600)
plot "grammar-factoring.data" using 1:3 title "Non-factored" w linespoints, "grammar-factoring.data" using 1:5 title "Left-factored" w linespoints, "grammar-factoring.data" using 1:7 title "Non-factored prefix-shared" w linespoints
