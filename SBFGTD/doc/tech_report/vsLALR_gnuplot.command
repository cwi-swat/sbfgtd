set term png
set xlabel "Input characters"
set ylabel "Relative execution time in percent"
set output "vsLALR.png"
set xrange [0:1200000]
set xtics (200000,400000,600000,800000,1000000)
set format x "%10.0f"
set yrange [0:650]
set ytics (50,100,150,200,250,300,350,400,450,500,550,600);
plot "vsLALR.data" using ($1-45000):(100):(30000) title "JFlex + JavaCup" w boxes fs solid 1, "vsLALR.data" using ($1-15000):($3 / $2 * 100):(30000) title "SGTDBF" w boxes fs solid 1, "vsLALR.data" using ($1+15000):($4 / $2 * 100):(30000) title "SGLR (No GC)" w boxes fs solid 1, "vsLALR.data" using ($1+45000):($5 / $2 * 100):(30000) title "JSGLR" w boxes fs solid 1
