set term png
set xlabel "Input characters"
set ylabel "Relative execution time in percent"
set output "vsLALR.png"
set xrange [0:1200000]
set xtics (200000,400000,600000,800000,1000000)
set format x "%10.0f"
set yrange [0:120]
set ytics (10,20,30,40,50,60,70,80,90,100);
plot "vsLALR.data" using ($1-40000):($2 / $3 * 100):(60000) title "JFlex + JavaCup" w boxes fs solid 1, "vsLALR.data" using ($1+40000):(100):(60000) title "SGTDBF" w boxes fs solid 1
